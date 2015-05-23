package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.BuyingSource;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectStatusType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.SectionBean;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.MessageCollaborator;
import com.tim.one.collaborator.ProjectCollectionInitializer;
import com.tim.one.collaborator.ProjectProviderCollectionDeleter;
import com.tim.one.collaborator.ProjectVariableCostCollaborator;
import com.tim.one.collaborator.TransactionCollaborator;
import com.tim.one.exception.AdvanceProviderPaidException;
import com.tim.one.exception.InvalidLengthException;
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
import com.tim.one.helper.ProjectHelper;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectCategory;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectLog;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectRate;
import com.tim.one.model.ProjectType;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.TramaAccount;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.BulkUnitTxRepository;
import com.tim.one.repository.ProjectCategoryRepository;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectProviderRepository;
import com.tim.one.repository.ProjectRateRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.ProjectVariableCostRepository;
import com.tim.one.repository.TramaAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.BreakevenService;
import com.tim.one.service.BudgetService;
import com.tim.one.service.ProjectCollaboratorService;
import com.tim.one.service.ProjectService;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.service.TransactionService;
import com.tim.one.service.UnitCalculator;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.util.IntegerParser;
import com.tim.one.validator.ProjectValidator;

/**
 * @author josdem
 * @understands A class who knows how to do project business flows
 * 
 */

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private BreakevenService breakevenService;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private ProjectHelper projectHelper;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private IntegerParser integerParser;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionCollaborator transactionCollaborator;
	@Autowired
	private UnitCalculator unitCalculator;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionHelper transactionHelper;
	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private MessageCollaborator messageCollaborator;
	@Autowired
	private ProjectCollaboratorService projectCollaborator;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private ProjectValidator projectValidator;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
	private ProjectProviderRepository projectProviderRepository;
	@Autowired
	private ProjectRateRepository projectRateRepository;
	@Autowired
	private ProjectVariableCostRepository projectVariableCostRepository;
	@Autowired
	private ProjectVariableCostCollaborator projectVariableCostCollaborator;
	@Autowired
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Autowired
	private ProjectCategoryRepository projectCategoryRepository;
	@Autowired
	private BulkUnitTxRepository bulkUnitTxRepository;
	@Autowired
	private TramaAccountRepository tramaAccountRepository;
	@Autowired
	private TransactionApplier transactionApplier;
	@Autowired
	private ProjectCollectionInitializer projectCollectionInitializer;
	@Autowired
	private ProjectProviderCollectionDeleter projectProviderCollectionDeleter;

	@Autowired
	@Qualifier("properties")
	private Properties properties;

	public Integer saveProject(Project project, Integer userId) throws UserNotFoundException {
		User user = userRepository.findOne(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		project.setUser(user);
		projectRepository.save(project);
		return project.getId();
	}

	public Integer saveProject(ProjectFinancialData project) {
		projectFinancialDataRepository.save(project);
		return project.getId();
	}

	public Iterable<Project> getAllProjects() {
		Iterable<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
		}
		return projects;
	}

	public Project getProjectById(Integer projectId) {
		if (projectId == null)
			return new Project();
		Project project = projectRepository.findOne(projectId);
		projectCollectionInitializer.initializeCollections(project);
		return project == null ? new Project() : project;
	}

	public List<Project> getProjectsByTypeSubcategoryId(ProjectType type, Integer subcategoryId, String statuses) {
		List<Integer> statusesAsList = integerParser.parse(statuses);
		if (statusesAsList.isEmpty()) {
			return projectRepository.findProjectsByTypeAndSubcategory(type, subcategoryId);
		}
		return projectRepository.findProjectsByTypeAndSubcategoryAndStatuses(type, subcategoryId, statusesAsList);
	}

	public List<Project> getProjectsByCategoryId(Integer categoryId, String statuses) {
		List<ProjectCategory> subcategories = projectCategoryRepository.findSubCategories(categoryId);
		List<Integer> subcategoryIds = new ArrayList<Integer>();
		for (ProjectCategory subcategory : subcategories) {
			subcategoryIds.add(subcategory.getId());
		}
		List<Integer> statusesAsList = integerParser.parse(statuses);
		if (statusesAsList.isEmpty()) {
			return projectRepository.findBySubcategoryIn(subcategoryIds);
		}
		return projectRepository.findProjectsBySubcategoriesAndStatuses(subcategoryIds, statusesAsList);
	}

	public List<Project> getProjectsByType(ProjectType type) {
		return projectRepository.findProjectsByType(type);
	}

	public List<Project> getProjectsBySubcategoryId(Integer subcategoryId) {
		return projectRepository.findProjectsBySubcategory(subcategoryId);
	}

	public List<Project> getProjectsByCategoryId(Integer categoryId) {
		List<ProjectCategory> subcategories = projectCategoryRepository.findSubCategories(categoryId);
		List<Integer> subcategoryIds = new ArrayList<Integer>();
		for (ProjectCategory subcategory : subcategories) {
			subcategoryIds.add(subcategory.getId());
		}
		return projectRepository.findBySubcategoryIn(subcategoryIds);
	}

	public ResultType changeStatus(Integer projectId, Integer status, Integer userId, String comment, Integer reason) {
		if (status != 0) {
			Project project = projectRepository.findOne(projectId);
			projectCollectionInitializer.initializeCollections(project);
			if (project == null)
				throw new ProjectNotExistException(projectId);
			if (reason == null && ProjectStatusType.getTypeByCode(status) == ProjectStatusType.REJECTED)
				throw new NotValidParameterException(reason);
			if (status.equals(new Integer(properties.getProperty(ApplicationState.RECHAZADO_STATUS)))) {
				transactionCollaborator.revertTramaCharge(project, reason);
			}
			if (status.equals(new Integer(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)))) {
				messageCollaborator.sendAutorizadoStatusNotifications(project);
				transactionCollaborator.setFundingEndDate(project.getProjectFinancialData());
			}
			if (status.equals(new Integer(properties.getProperty(ApplicationState.PRODUCTION_STATUS)))) {
				transactionCollaborator.setTramaCharge(project);
			}
			if (status.equals(new Integer(properties.getProperty(ApplicationState.FINALIZADO_STATUS)))) {
				transactionCollaborator.closeProject(project);
			}
			project.setStatus(status);
			ProjectLog projectLog = projectHelper.createProjectLog();
			projectLog.setProject(project);
			projectLog.setTimestamp(dateUtil.createDateAsLong());
			projectLog.setStatus(status);
			projectLog.setUserId(userId);
			projectLog.setComment(comment);
			projectLog.setReason(reason);
			project.getLogs().add(projectLog);
			projectRepository.save(project);
		} else {
			throw new OperationStatusException(status);
		}
		return ResultType.SUCCESS;
	}

	public Set<Project> getByTag(String tag) {
		List<Project> projects = projectRepository.findProjectsByTag(tag);
		Set<Project> projectSet = new HashSet<Project>();
		for (Project project : projects) {
			projectSet.add(project);
		}
		return projectSet;
	}

	public ResultType createRate(Integer projectId, Integer userId, Integer score) {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}

		if (project.getProjectRate() == null) {
			ProjectRate projectRate = projectHelper.createProjectRate();
			projectRate.setProject(project);
			projectRate.setUserId(userId);
			projectRate.setScore(score);
			project.setProjectRate(projectRate);
			projectRepository.save(project);
			return ResultType.SUCCESS;
		}
		return ResultType.FAIL;
	}

	public Float getRating(Integer projectId) {
		Float rate = 0f;
		Integer counter = 0;

		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			return rate;
		}

		List<ProjectRate> projectRates = projectRateRepository.findByProject(project);
		for (ProjectRate projectRate : projectRates) {
			rate = rate + projectRate.getScore();
			counter++;
		}
		return (float) (rate / counter);
	}

	public List<Project> getProjectsByUserId(Integer userId) {
		User user = userRepository.findOne(userId);
		List<Project> projects = projectRepository.findProjectsByUser(user);
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
		}
		return projects;
	}

	public void createVariableCosts(Map<String, String> params) {
		Integer projectId = Integer.parseInt(params.get("projectId"));
		params.remove("projectId");
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}

		if (project.getProjectFinancialData() == null) {
			ProjectFinancialData projectFinancialData = projectHelper.createProjectFinancialData();
			project.setProjectFinancialData(projectFinancialData);
			projectFinancialData.setProject(project);
		}

		projectVariableCostCollaborator.deleteProjectVariableCosts(project.getProjectFinancialData());
		Set<ProjectVariableCost> costs = new HashSet<ProjectVariableCost>();
		for (String key : params.keySet()) {
			if (!key.equals("callback")) {
				ProjectVariableCost projectVariableCost = projectHelper.createProjectVariableCost();
				projectVariableCost.setName(key);
				projectVariableCost.setProjectFinancialData(project.getProjectFinancialData());
				projectVariableCost.setValue(new BigDecimal(params.get(key)));
				costs.add(projectVariableCost);
			}
		}

		project.getProjectFinancialData().setVariableCosts(costs);
		projectRepository.save(project);
	}

	public Set<Project> getProjectsByClosestToEndFunding() {
		Set<Project> projects = projectRepository.findProjectsByClosestToEndFunding(dateUtil.createDateAsLong());
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
		}
		return projects;
	}

	public List<Project> getProjectsByClosestToEndPresentation() {
		return projectRepository.findProjectsByClosestToEndPresentation(dateUtil.createDateAsLong());
	}

	public ResultType createProviderPayment(Integer projectId, Integer providerId, PaymentType paymentType) throws NonProjectProviderException, AdvanceProviderPaidException,
			SettlementProviderPaidException, UserNotFoundException {

		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}

		User provider = userRepository.findOne(providerId);
		if (provider == null) {
			throw new UserNotFoundException(providerId);
		}

		projectCollectionInitializer.initializeCollections(project);

		ProjectProvider projectProvider = projectProviderRepository.findByProjectAndProviderId(project, providerId);
		if (projectProvider == null) {
			throw new NonProjectProviderException(providerId);
		} else if (userRepository.findOne(providerId) == null) {
			throw new UserNotFoundException(providerId);
		} else if (paymentType.equals(PaymentType.ADVANCE) && projectProvider.getAdvancePaidDate() != null) {
			throw new AdvanceProviderPaidException(providerId);
		} else if (paymentType.equals(PaymentType.SETTLEMENT) && projectProvider.getSettlementPaidDate() != null) {
			throw new SettlementProviderPaidException(providerId);
		} else if (paymentType.equals(PaymentType.BOUTH) && (projectProvider.getSettlementPaidDate() != null || (projectProvider.getAdvancePaidDate() != null))) {
			throw new AdvanceProviderPaidException(providerId);
		}

		BigDecimal amount = new BigDecimal("0");

		switch (paymentType) {
		case ADVANCE:
			amount = projectProvider.getAdvanceQuantity();
			projectProvider.setAdvancePaidDate(dateUtil.createDateAsLong());
			break;
		case SETTLEMENT:
			amount = projectProvider.getSettlementQuantity();
			projectProvider.setSettlementPaidDate(dateUtil.createDateAsLong());
			break;
		case BOUTH:
			amount = amount.add(projectProvider.getAdvanceQuantity()).add(projectProvider.getSettlementQuantity());
			projectProvider.setAdvancePaidDate(dateUtil.createDateAsLong());
			projectProvider.setSettlementPaidDate(dateUtil.createDateAsLong());
			break;
		}

		projectProviderRepository.save(projectProvider);
		transactionLogService.createProviderLog(providerId, projectId, amount, paymentType, TransactionType.PAYMENT);

		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		transactionApplier.substractAmount(projectFinancialData, amount);
		transactionApplier.addAmount(provider, amount);

		transactionLogService.createProjectLog(projectId, providerId, amount, ProjectTxType.PROVIDER_PAYMENT);
		return ResultType.SUCCESS;
	}

	public List<Project> getProjectsByStatuses(String statuses) {
		List<Integer> statusesAsList = integerParser.parse(statuses);
		List<Project> projects = projectRepository.findByStatusIn(statusesAsList);
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
		}
		return projects;
	}

	public Set<Project> getProjectsByHighBalance() {
		List<Project> projects = projectRepository.findProjectsByHigherBalance();
		Set<Project> projectsAsSet = new HashSet<Project>();
		for (Project project : projects) {
			projectCollectionInitializer.initializeCollections(project);
			projectsAsSet.add(project);
		}
		return projectsAsSet;
	}

	public ResultType createProducerPayment(Integer projectId, Integer producerId, BigDecimal amount) {
		Project project = projectRepository.findOne(projectId);
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		if (projectFinancialData.getBalance().compareTo(amount) >= 0) {
			transactionApplier.substractAmount(projectFinancialData, amount);

			User producer = userRepository.findOne(producerId);
			transactionApplier.addAmount(producer, amount);

			Integer transactionId = transactionLogService.createProjectLog(projectId, producerId, amount, ProjectTxType.PRODUCER_PAYMENT);
			transactionLogService.createUserLog(projectFinancialData.getId(), producer.getId(), null, amount, null, TransactionType.PRODUCER_PAYMENT);

			messagePacker.sendAbonoCuenta(amount, project.getName(), producer.getEmail(), transactionId, MessageType.PRODUCTOR);

			return ResultType.SUCCESS;
		}
		throw new NonSufficientFundsException(projectId);
	}

	public BigDecimal getTRA(BigDecimal tri, BigDecimal trf, ProjectFinancialData projectFinancialData) {
		BigDecimal zero = new BigDecimal("0.00");
		if (projectFinancialData == null || projectFinancialData.getBudget() == null) {
			return zero;
		}
		BigDecimal budget = projectFinancialData.getBudget();
		BigDecimal tra = tri.add(trf);
		BigDecimal twoHundredPercent = budget.multiply(new BigDecimal("2"));
		if (twoHundredPercent.compareTo(tra) == -1) {
			return twoHundredPercent;
		}
		return tra;
	}

	public Integer createConsumingUnits(Map<String, String> params) {
		Integer userId = Integer.parseInt(params.get("userId"));
		Integer projectId = Integer.parseInt(params.get("projectId"));

		params.remove("userId");
		params.remove("projectId");

		User user = userRepository.findOne(userId);
		BigDecimal total = unitCalculator.getTotal(params);
		if (user.getBalance().compareTo(total) == -1) {
			throw new NonSufficientFundsException(userId);
		}
		if (!unitCalculator.sufficientUnits(params)) {
			throw new NonSufficientUnitsException();
		}
		user.setBalance(user.getBalance().subtract(total));
		userRepository.save(user);

		return transactionService.createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
	}

	public Set<Project> getProjectsByStatusAndUserId(List<Integer> statuses, Integer userId) {
		Set<Project> projectsByStatus = new HashSet<Project>();
		Set<Project> projects = projectCollaborator.getProjectsFundedOrInvestedByUserId(userId);
		for (Project project : projects) {
			if (statuses.contains(project.getStatus())) {
				projectsByStatus.add(project);
			}
		}
		return projects;
	}

	public Set<Project> getByKeyword(String keyword) throws InvalidLengthException {
		if (keyword.length() < new Integer(properties.getProperty(ApplicationState.KEYWORD_MIN_LENGHT))) {
			throw new InvalidLengthException();
		}
		List<Project> projects = projectRepository.findProjectsLikeByNameOrDescriptionOrShowground(keyword);
		Set<Project> projectSet = new HashSet<Project>();
		for (Project project : projects) {
			projectSet.add(project);
		}
		return projectSet;
	}

	public ResultType changeProjectUnitSale(Integer projectUnitSaleId) {
		if (projectUnitSaleId == null) {
			throw new NotValidParameterException(projectUnitSaleId);
		}
		ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(projectUnitSaleId);
		if (projectUnitSale == null) {
			throw new NonProjectUnitSaleException(projectUnitSaleId.toString());
		}
		Project project = projectRepository.findOne(projectUnitSale.getProjectFinancialData().getId());
		if (project.getStatus() == 0 || project.getStatus() == 2) {
			projectUnitSaleRepository.delete(projectUnitSale);
			return ResultType.SUCCESS;
		} else {
			throw new OperationStatusException(project.getStatus());
		}
	}

	public List<SectionBean> getSections(Integer projectId) {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}

		projectCollectionInitializer.initializeCollections(project);
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		Set<ProjectUnitSale> projectUnitsales = projectFinancialData.getProjectUnitSales();
		List<SectionBean> sectionBeans = new ArrayList<SectionBean>();
		for (ProjectUnitSale projectUnitSale : projectUnitsales) {
			SectionBean bean = projectHelper.createSectionBean();
			bean.setName(projectUnitSale.getSection());
			bean.setUnits(projectUnitSale.getUnit());
			bean.setTotal(projectUnitSale.getUnit() + projectCollaborator.getTotalUnits(projectUnitSale.getId()));
			sectionBeans.add(bean);
		}
		return sectionBeans;
	}

	public Boolean createProjectProvider(Integer projectId, Set<ProjectProvider> projectProviders) {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}
		if (!projectValidator.valid(projectProviders)) {
			return false;
		}
		projectProviderCollectionDeleter.deleteProviders(project);
		for (ProjectProvider projectProvider : projectProviders) {
			projectProvider.setProject(project);
		}
		project.setProviders(projectProviders);
		projectRepository.save(project);
		return true;
	}

	public void measureBreakevenAndBudget(Integer projectId, Set<ProjectProvider> providers) {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			throw new ProjectNotExistException(projectId);
		}

		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		BigDecimal tramaFee = breakevenService.getTramaFee(providers);
		BigDecimal budget = budgetService.getBudget(providers);
		projectFinancialData.setBudget(budget.add(tramaFee));

		BigDecimal breakeven = breakevenService.getCalculatedBreakeven(projectFinancialData);

		projectFinancialData.setBreakeven(breakeven);
		projectFinancialData.setTramaFee(tramaFee);
		projectRepository.save(project);
	}

	public ResultType createBoxOfficeSales(Map<String, String> params) {
		Set<UnitTx> units = transactionHelper.createUnits();

		BulkUnitTx bulkUnitTx = transactionHelper.createBulkUnit();
		for (Map.Entry<String, String> mp : params.entrySet()) {
			Integer sectionCode = Integer.parseInt(mp.getKey());
			Integer quantity = Integer.parseInt(mp.getValue());

			if (quantity != 0) {
				ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(sectionCode);
				if (projectUnitSale == null) {
					throw new NonExistentCodeException(sectionCode.toString());
				}

				if (projectUnitSale.getUnit() < quantity) {
					throw new NonSufficientUnitsException();
				}

				TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
				if (tramaAccount == null) {
					throw new NotTramaAccountFoundException();
				}

				ProjectFinancialData projectFinancialData = projectUnitSale.getProjectFinancialData();
				BigDecimal amount = new BigDecimal(quantity).multiply(projectUnitSale.getUnitSale());
				if (tramaAccount.getBalance().compareTo(amount) < 0) {
					throw new NonSufficientFundsException(tramaAccount.getId());
				}

				transactionApplier.addAmount(projectFinancialData, amount);
				transactionApplier.substractAmount(tramaAccount, amount);
				transactionLogService.createProjectLog(projectFinancialData.getProject().getId(), null, amount, ProjectTxType.BOX_OFFICE_SALES);

				projectUnitSale.setUnit(projectUnitSale.getUnit() - quantity);
				projectUnitSaleRepository.save(projectUnitSale);

				UnitTx unitTx = transactionLogService.createUnitLog(projectUnitSale.getId(), quantity, 0, bulkUnitTx, TransactionType.CONSUMING);
				units.add(unitTx);
			}
		}

		if (!units.isEmpty()) {
			bulkUnitTx.setTimestamp(dateUtil.createDateAsLong());
			bulkUnitTx.setUnits(units);

			bulkUnitTxRepository.save(bulkUnitTx);
		}

		return ResultType.SUCCESS;
	}

	@Override
	public Integer saveProject(Project project) {
		projectRepository.save(project);
		return project.getId();
	}

}
