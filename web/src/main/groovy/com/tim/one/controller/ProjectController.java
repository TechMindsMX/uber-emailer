package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.tim.one.bean.AccountType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectProviderBean;
import com.tim.one.bean.RatingBean;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.SectionBean;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.AccountCreator;
import com.tim.one.collaborator.BeanAggregator;
import com.tim.one.collaborator.CalcSheetStorerCollaborator;
import com.tim.one.collaborator.DateFormatter;
import com.tim.one.collaborator.FormParamFormatter;
import com.tim.one.collaborator.ProjectProviderAdapter;
import com.tim.one.collaborator.ProjectRanger;
import com.tim.one.collaborator.ProjectUnitSaleCollaborator;
import com.tim.one.command.ProjectCategoryCommand;
import com.tim.one.command.ProjectChangeStatusCommand;
import com.tim.one.command.ProjectCommand;
import com.tim.one.command.ProjectCreateCommand;
import com.tim.one.command.ProjectCreateFinancialDataCommand;
import com.tim.one.command.ProjectDeleteUnitSaleCommand;
import com.tim.one.command.ProjectProducerPaymentCommand;
import com.tim.one.command.ProjectProviderCommand;
import com.tim.one.command.ProjectProviderPaymentCommand;
import com.tim.one.command.ProjectRateCommand;
import com.tim.one.exception.AdvanceProviderPaidException;
import com.tim.one.exception.FormParamsException;
import com.tim.one.exception.InvalidLengthException;
import com.tim.one.exception.NoFinancialDataException;
import com.tim.one.exception.NonExistentCodeException;
import com.tim.one.exception.NonProjectProviderException;
import com.tim.one.exception.NonProjectUnitSaleException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NonSufficientUnitsException;
import com.tim.one.exception.NotTramaAccountFoundException;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.exception.OperationStatusException;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.exception.SettlementProviderPaidException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.GsonHelper;
import com.tim.one.helper.ProjectHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectType;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.service.ProjectCollaboratorService;
import com.tim.one.service.ProjectPersisterService;
import com.tim.one.service.ProjectService;
import com.tim.one.service.ProjectTransactionService;
import com.tim.one.service.RevenuePotentialService;
import com.tim.one.service.SecurityService;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ApplicationState;
import com.tim.one.state.ErrorState;
import com.tim.one.state.ResponseState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.CommandValidator;
import com.tim.one.validator.ProjectValidator;
import com.tim.one.validator.VariableCostsValidator;
import com.tim.one.wrapper.ProviderWrapper;

/**
 * @author josdem
 * @understands A class who knows how receive http post params and command to
 *              the service to manage the project
 */

@Controller
@RequestMapping("/project/*")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private BeanAggregator beanAggregator;
	@Autowired
	private VariableCostsValidator variableCostsValidator;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private RevenuePotentialService revenuePotentialService;
	@Autowired
	private FormParamFormatter formParamFormatter;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private ProjectHelper projectHelper;
	@Autowired
	private CalcSheetStorerCollaborator calcSheetStorerService;
	@Autowired
	private DateFormatter dateFormatter;
	@Autowired
	private ProjectRanger projectManager;
	@Autowired
	private GsonHelper gsonHelper;
	@Autowired
	private ProjectProviderAdapter projectProviderAdapter;
	@Autowired
	private ProjectUnitSaleCollaborator unitSaleService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private ProjectTransactionService projectTransactionService;
	@Autowired
	private ProjectCollaboratorService projectCollaborator;
	@Autowired
	private AccountCreator accountCreator;
	@Autowired
	private Properties properties;
	@Autowired
	private ProjectValidator projectValidator;
	@Autowired
	private ProjectPersisterService projectPersisterService;
	@Autowired
	private CommandValidator validator;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/get/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Project getProject(ProjectCommand command) {
		log.info("GETTING Project with id: " + command.getProjectId());
		Project project = projectService.getProjectById(command.getProjectId());
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		project.setCre(projectTransactionService.getCRE(projectFinancialData));
		project.setTri(projectTransactionService.getTRI(projectFinancialData));
		project.setTrf(projectTransactionService.getTRF(projectFinancialData));
		project.setTra(projectService.getTRA(project.getTri(), project.getTrf(), projectFinancialData));
		project.setRating(projectService.getRating(command.getProjectId()));
		return project;
	}

	@RequestMapping(method = GET, value = "/getByUser/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsByUserId(ProjectCommand command) {
		log.info("GETTING Projects from userId: " + command.getUserId());
		List<Project> projects = projectService.getProjectsByUserId(command.getUserId());
		return projects;
	}

	@RequestMapping(method = GET, value = "/subcategory/{type}/{subcategoryId}/{statuses}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsByTypeSubcategoryId(ProjectCategoryCommand command) {
		log.info("LISTING Projects with subcategory id: " + command.getSubcategoryId());
		return projectService.getProjectsByTypeSubcategoryId(ProjectType.getTypeByCode(command.getType()), command.getSubcategoryId(), command.getStatuses());
	}

	@RequestMapping(method = GET, value = "/category/{categoryId}/{statuses}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsByStatusesAndCategoryId(ProjectCategoryCommand command) {
		log.info("LISTING Projects with category id: " + command.getCategoryId());
		List<Project> projects = projectService.getProjectsByCategoryId(command.getCategoryId(), command.getStatuses());
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/subcategory/all/{subcategoryId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsBySubcategoryId(ProjectCategoryCommand command) {
		log.info("LISTING Projects with subcategory id: " + command.getSubcategoryId());
		return projectService.getProjectsBySubcategoryId(command.getSubcategoryId());
	}

	@RequestMapping(method = GET, value = "/category/all/{categoryId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsByCategoryId(ProjectCategoryCommand command) {
		log.info("LISTING Projects with category id: " + command.getCategoryId());
		return projectService.getProjectsByCategoryId(command.getCategoryId());
	}

	@RequestMapping(method = GET, value = "/{type}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> listProjectsByType(ProjectCategoryCommand command) {
		log.info("LISTING Projects by type: " + command.getType());
		return projectService.getProjectsByType(ProjectType.getTypeByCode(command.getType()));
	}

	@RequestMapping(method = GET, value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Iterable<Project> listProjects() {
		log.info("LISTING Project's categories");
		return projectService.getAllProjects();
	}

	@RequestMapping(method = GET, value = "/getByTag/{tag}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getByTag(ProjectCommand command) {
		log.info("SEARCHING Project's by tag: " + command.getTag());
		return projectService.getByTag(command.getTag());
	}

	@RequestMapping(method = GET, value = "/getByKeyword/{keyword}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getByKeyword(ProjectCommand command) {
		log.info("SEARCHING Project's by keyword: " + command.getKeyword());
		try {
			Set<Project> byKeywordSet = projectService.getByKeyword(command.getKeyword());
			byKeywordSet.addAll(projectService.getByTag(command.getKeyword()));
			Set<Project> filteredProjects = projectManager.distinct(byKeywordSet);
			for (Project project : filteredProjects) {
				project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
				project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
			}
			return filteredProjects;
		} catch (InvalidLengthException ile) {
			log.warn(ile, ile);
		}
		return new HashSet<Project>();
	}

	@RequestMapping(method = GET, value = "/getByClosestToEndFunding")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getByClosestToEndFunding() {
		log.info("LISTING Project's closest to end");
		Set<Project> projects = projectService.getProjectsByClosestToEndFunding();
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/getByClosestToEndPresentation")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getByClosestToEndPresentation() {
		log.info("LISTING Project's closest to end");
		List<Project> projects = projectService.getProjectsByClosestToEndPresentation();
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/getByHigherBalance")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getByHigherBalance() {
		log.info("LISTING Project's highest balance");
		Set<Project> projects = projectService.getProjectsByHighBalance();
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "getMoreProfitable/{max}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getMoreProfitable(ProjectCommand command) {
		log.info("LISTING more profitable projects");
		return projectTransactionService.getMoreProfitable(command.getMax());
	}

	@RequestMapping(method = GET, value = "/status/{statuses}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Project> getProjectsByStatuses(ProjectCategoryCommand command) {
		log.info("LISTING Projects by statuses: " + command.getStatuses());
		List<Project> projects = projectService.getProjectsByStatuses(command.getStatuses());
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/get/projects/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getProjectsFundedByUserId(ProjectCommand command) {
		log.info("LISTING Projects by userId: " + command.getUserId());
		List<Integer> statuses = projectHelper.createArrayListInteger();
		statuses.add(new Integer(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)));
		Set<Project> projects = projectService.getProjectsByStatusAndUserId(statuses, command.getUserId());
		for (Project project : projects) {
			project.setFundedAmount(transactionService.getAmountByUser(TransactionType.FUNDING, project, command.getUserId()));
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/get/products/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getProductsInvestedByUserId(ProjectCommand command) {
		log.info("LISTING Products by userId: " + command.getUserId());
		List<Integer> statuses = projectHelper.createArrayListInteger();
		statuses.add(new Integer(properties.getProperty(ApplicationState.PRODUCTION_STATUS)));
		statuses.add(new Integer(properties.getProperty(ApplicationState.PRESENTACION_STATUS)));
		statuses.add(new Integer(properties.getProperty(ApplicationState.PENDIENTE_STATUS)));
		statuses.add(new Integer(properties.getProperty(ApplicationState.CERRANDO_STATUS)));
		Set<Project> projects = projectService.getProjectsByStatusAndUserId(statuses, command.getUserId());
		for (Project project : projects) {
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setInvestedAmount(transactionService.getAmountByUser(TransactionType.INVESTMENT, project, command.getUserId()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/get/partnership/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Set<Project> getProjectsFundedOrInvestedByUserId(ProjectCommand command) {
		log.info("LISTING Projects funded or invested by userId: " + command.getUserId());
		Set<Project> projects = projectCollaborator.getProjectsFundedOrInvestedByUserId(command.getUserId());
		for (Project project : projects) {
			project.setFundedAmount(transactionService.getAmountByUser(TransactionType.FUNDING, project, command.getUserId()));
			project.setInvestedAmount(transactionService.getAmountByUser(TransactionType.INVESTMENT, project, command.getUserId()));
			project.setTri(projectTransactionService.getTRI(project.getProjectFinancialData()));
			project.setTrf(projectTransactionService.getTRF(project.getProjectFinancialData()));
		}
		return projects;
	}

	@RequestMapping(method = GET, value = "/getSections/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SectionBean> getSections(ProjectCommand command) {
		log.info("GETTING Project's sections");
		return projectService.getSections(command.getProjectId());
	}

	@RequestMapping(method = POST, value = "/changeStatus")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void changeStatus(@Valid ProjectChangeStatusCommand command, HttpServletResponse response) throws IOException {
		log.info("TRYING to change status");

		if (securityService.isValid(command.getToken())) {
			log.info("CHANGING Project's status: " + command.getStatus() + " to project: " + command.getProjectId() + "userId" + command.getUserId() + " w/comments: " + command.getComment() + " w/reason: "
					+ command.getReason() + "callback: " + command.getCallback());

			try {
				projectService.changeStatus(command.getProjectId(), command.getStatus(), command.getUserId(), command.getComment(), command.getReason());
				response.sendRedirect(command.getCallback());
			} catch (NotTramaAccountFoundException tae) {
				log.warn(tae, tae);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_ADMINISTRATIVE_ACCOUNT_FOUND));
			} catch (ProjectNotExistException nfe) {
				log.warn(nfe, nfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.PROJECT_NOT_FOUND));
			} catch (OperationStatusException cee) {
				log.warn(cee, cee);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_OPERATION_STATUS));
			} catch (NoFinancialDataException nfde) {
				log.warn(nfde, nfde);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FINANTIAL_DATA));
			} catch (NonSufficientFundsException nsfe) {
				log.warn(nsfe, nsfe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			} catch (NotValidParameterException nvpe) {
				log.warn(nvpe, nvpe);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}

	}

	@RequestMapping(method = POST, value = "/rate")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String rate(@Valid ProjectRateCommand command, HttpServletResponse response) {

		response.addHeader("Allow-Control-Allow-Methods", "POST");
		response.addHeader("Access-Control-Allow-Origin", "*");

		log.info("TRYING Project's rating userId: " + command.getUserId() + " to project: " + command.getProjectId() + " w/score: " + command.getScore());

		if (securityService.isValid(command.getToken())) {
			log.info("SETTING Project's rating userId: " + command.getUserId() + " to project: " + command.getProjectId() + " w/score: " + command.getScore());

			ResultType resultType = projectService.createRate(command.getProjectId(), command.getUserId(), command.getScore());
			RatingBean bean = projectHelper.createRatingBean();
			bean.setResultType(resultType);
			bean.setRate(projectService.getRating(command.getProjectId()));

			return projectHelper.toJson(bean);
		}
		return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
	}

	@RequestMapping(method = POST, value = "/saveProvider")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void saveProviders(@Valid ProjectProviderCommand command, HttpServletResponse response) throws IOException {

		if (securityService.isValid(command.getToken())) {
			log.info("SAVING Project's providers: " + command.getProjectProvider());

			Gson gson = gsonHelper.createWithDateFormat("dd-MM-yyyy");
			ProviderWrapper providerWrapper = gson.fromJson(command.getProjectProvider(), ProviderWrapper.class);
			List<ProjectProviderBean> projectProviders = providerWrapper.getProjectProviders();
			for (ProjectProviderBean projectProviderBean : projectProviders) {
				projectProviderBean.setProjectId(command.getProjectId());
			}
			Set<ProjectProvider> providers = projectProviderAdapter.adapt(projectProviders);

			if (projectService.createProjectProvider(command.getProjectId(), providers)) {
				projectService.measureBreakevenAndBudget(command.getProjectId(), providers);
				response.sendRedirect(command.getCallback());
			} else {
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST));
			}

		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/saveVariableCosts")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void saveVariableCosts(@RequestParam MultiValueMap<String, String> formParams, HttpServletResponse response) throws URISyntaxException, IOException {
		log.info("TRYING TO READ Project's variable costs");

		Map<String, String> params = formParamFormatter.formatParams(formParams);
		String callback = params.get("callback");
		params.remove("callback");
		log.info("token: " + params.get("token"));
		if (securityService.isValid(params.get("token"))) {
			log.info("SAVING Project's variable costs");
			params.remove("token");
			if (variableCostsValidator.isValid(params)) {
				projectService.createVariableCosts(params);
				response.sendRedirect(callback);
			} else {
				log.info("INVALID Project's variable costs");
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.VARIABLE_COST_NOT_VALID));
			}
		} else {
			response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/saveProviderPayment")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String saveProviderPayment(@Valid ProjectProviderPaymentCommand command, HttpServletResponse response) throws URISyntaxException, IOException {

		response.addHeader("Allow-Control-Allow-Methods", "POST");
		response.addHeader("Access-Control-Allow-Origin", "*");

		log.info("TRYING saving provider's payment: " + command.getProviderId());

		if (securityService.isValid(command.getToken())) {
			log.info("SAVING Provider's payment: " + command.getProviderId());
			try {
				ResultType result = projectService.createProviderPayment(command.getProjectId(), command.getProviderId(), PaymentType.getTypeByCode(command.getType()));
				return ResponseState.getJsonResponse(result.ordinal());
			} catch (NonProjectProviderException npe) {
				log.warn(npe, npe);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.PROVIDER_NOT_FOUND);
			} catch (AdvanceProviderPaidException ape) {
				log.warn(ape, ape);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.ADVANCE_PAYMENT_ALREADY_PAID);
			} catch (SettlementProviderPaidException spe) {
				log.warn(spe, spe);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.SETTLEMENT_ALREADY_PAID);
			} catch (UserNotFoundException unfe) {
				log.warn(unfe, unfe);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.USER_NOT_FOUND);
			}
		} else {
			return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
		}
	}

	@RequestMapping(method = POST, value = "/saveProducerPayment")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void saveProducerPayment(@Valid ProjectProducerPaymentCommand command, HttpServletResponse response) throws IOException {
		if (securityService.isValid(command.getToken())) {
			try {
				projectService.createProducerPayment(command.getProjectId(), command.getProducerId(), command.getPayment());
				log.info("SAVING Producer's payment: " + command.getProducerId() + " " + ResultType.SUCCESS);
				response.sendRedirect(command.getCallback());
			} catch (NonSufficientFundsException nse) {
				log.warn(nse, nse);
				response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			}
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/consumeUnits")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void consumeUnits(@RequestParam MultiValueMap<String, String> formParams, HttpServletResponse response) throws URISyntaxException, IOException {

		Integer transactionId = 0;
		Map<String, String> params = formParamFormatter.formatParams(formParams);
		String callback = params.get("callback");
		params.remove("callback");

		if (securityService.isValid(params.get("token"))) {
			log.info("SAVING Project's units");
			params.remove("token");

			try {
				transactionId = projectService.createConsumingUnits(params);
				response.sendRedirect(callback + ResponseState.getResponse(transactionId));
			} catch (NonSufficientFundsException nfe) {
				log.warn(nfe, nfe);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			} catch (NonSufficientUnitsException nue) {
				log.warn(nue, nue);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_UNITS));
			}
		} else {
			response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/boxOfficeSales")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void boxOfficeSales(@RequestParam MultiValueMap<String, String> formParams, HttpServletResponse response) throws URISyntaxException, IOException {

		Map<String, String> params = formParamFormatter.formatParams(formParams);
		String callback = params.get("callback");
		params.remove("callback");

		if (securityService.isValid(params.get("token"))) {
			log.info("SAVING Project's box office section ");
			params.remove("token");

			try {
				projectService.createBoxOfficeSales(params);
				response.sendRedirect(callback);
			} catch (NonExistentCodeException nece) {
				log.warn(nece, nece);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.SECTION_CODE_NOT_FOUND));
			} catch (NonSufficientUnitsException nsue) {
				log.warn(nsue, nsue);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_UNITS));
			} catch (NotTramaAccountFoundException ntafe) {
				log.warn(ntafe, ntafe);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_ADMINISTRATIVE_ACCOUNT_FOUND));
			} catch (NonSufficientFundsException nsfe) {
				log.warn(nsfe, nsfe);
				response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
			}
		} else {
			response.sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/create")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String createProject(ProjectCreateCommand command) throws IOException {
		log.info("RECEIVING Create Project Request");
		
		if(validator.isValid(command)){
			if (securityService.isValid(command.getToken())) {
				log.info("CREATING Project");

				try {
					Project project = projectService.getProjectById(command.getId());
					project.setName(command.getName());
					project.setSubcategory(command.getSubcategory());
					project.setInclosure(command.getInclosure());
					project.setShowground(command.getShowground());
					project.setDescription(command.getDescription());
					project.setCast(command.getCast());
					project.setTimeCreated(dateUtil.createDateAsLong());
					project.setStatus(command.getStatus());
					project.setType(command.getType());
					project.setUrl(command.getUrl());
					project.setVideoPublic(command.getVideoPublic());
					project.setAudioPublic(command.getAudioPublic());
					project.setImagesPublic(command.getImagePublic());
					project.setInfoPublic(command.getInfoPublic());
					project.setBanner(command.getBanner());
					project.setAvatar(command.getAvatar());

					List<String> videoLinks = projectHelper.createArrayListString();
					videoLinks.add(command.getVideoLink1());
					videoLinks.add(command.getVideoLink2());
					videoLinks.add(command.getVideoLink3());
					videoLinks.add(command.getVideoLink4());
					videoLinks.add(command.getVideoLink5());
					List<String> soundcloudLinks = projectHelper.createArrayListString();
					soundcloudLinks.add(command.getSoundCloudLink1());
					soundcloudLinks.add(command.getSoundCloudLink2());
					soundcloudLinks.add(command.getSoundCloudLink3());
					soundcloudLinks.add(command.getSoundCloudLink4());
					soundcloudLinks.add(command.getSoundCloudLink5());

					projectPersisterService.save(project, videoLinks, soundcloudLinks, command.getPhotos(), command.getTags(), command.getUserId());
					return ResponseState.getJsonResponse(project.getId());
				} catch (UserNotFoundException unfe) {
					log.warn(unfe, unfe);
					return ErrorState.getJsonErrorCode(TimoneErrorStatus.USER_NOT_FOUND);
				}
			} else {
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
			}
		}
		
		return "OK";
	}

	@RequestMapping(method = POST, value = "/createFinancialData")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void createFinantialData(@Valid ProjectCreateFinancialDataCommand command, HttpServletResponse response) throws URISyntaxException, FormParamsException, ParseException, IOException {

		log.info("RECEIVING Create Project Financial Data Request: " + command.getProjectId());

		if (securityService.isValid(command.getToken())) {
			log.info("SAVING Create Project Financial Data Request: " + command.getProjectId());

			Project project = projectService.getProjectById(command.getProjectId());
			ProjectFinancialData projectFinancialData = projectCollaborator.getProjectFinancialData(project);
			projectFinancialData.setAccount(accountCreator.createAccount(AccountType.PROJECT, command.getProjectId()));
			projectFinancialData.setEventCode(command.getEventCode());
			projectFinancialData.setProductionStartDate(dateFormatter.format(command.getProductionStartDate()));
			projectFinancialData.setPremiereStartDate(dateFormatter.format(command.getPremiereStartDate()));
			projectFinancialData.setPremiereEndDate(dateFormatter.format(command.getPremiereEndDate()));
			projectFinancialData.setNumbersPublic(command.getNumberPublic());
			Set<ProjectUnitSale> projectUnitSales = unitSaleService.createUnitSales(projectFinancialData, command.getSection(), command.getUnitSale(), command.getCapacity(), command.getCodeSection());
			projectFinancialData.setProjectUnitSales(projectUnitSales);
			projectFinancialData.setRevenuePotential(revenuePotentialService.getRevenuePotential(projectUnitSales));
			Long timestamp = calcSheetStorerService.saveCalcSheet(command.getBusinessCase());
			beanAggregator.addProjectBusinessCase(projectFinancialData, timestamp);
			projectService.saveProject(project);
			response.sendRedirect(command.getCallback());
		} else {
			response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
		}
	}

	@RequestMapping(method = POST, value = "/deleteProjectUnitSale")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String deleteProjectUnitSale(@Valid ProjectDeleteUnitSaleCommand command) throws URISyntaxException {

		if (securityService.isValid(command.getToken())) {
			log.info("DELETING Project's unit sale: " + command.getProjectUnitSaleId());
			try {
				ResultType response = projectService.changeProjectUnitSale(command.getProjectUnitSaleId());
				return ResponseState.getJsonResponse(response.ordinal());
			} catch (NonProjectUnitSaleException npuse) {
				log.warn(npuse, npuse);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.SECTION_CODE_NOT_FOUND);
			} catch (OperationStatusException ose) {
				log.warn(ose, ose);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_OPERATION_STATUS);
			} catch (NotValidParameterException nvpe) {
				log.warn(nvpe, nvpe);
				return ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST);
			}
		} else {
			return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
		}
	}

}
