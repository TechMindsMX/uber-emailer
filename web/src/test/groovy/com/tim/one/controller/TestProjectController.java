package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.GsonBuilder;
import com.tim.one.bean.AccountType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectProviderBean;
import com.tim.one.bean.RatingBean;
import com.tim.one.bean.ResultType;
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
import com.tim.one.command.ProjectMessageCommand;
import com.tim.one.command.ProjectProducerPaymentCommand;
import com.tim.one.command.ProjectProviderCommand;
import com.tim.one.command.ProjectProviderPaymentCommand;
import com.tim.one.command.ProjectRateCommand;
import com.tim.one.exception.AdvanceProviderPaidException;
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
import com.tim.one.model.ProjectLog;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectSoundcloud;
import com.tim.one.model.ProjectType;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVideo;
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
import com.tim.one.validator.ProjectValidator;
import com.tim.one.validator.VariableCostsValidator;
import com.tim.one.wrapper.ProviderWrapper;

public class TestProjectController {

	@InjectMocks
	private ProjectController projectController = new ProjectController();

	@Mock
	private ProjectService projectService;
	@Mock
	private ProjectRanger projectRanger;
	@Mock
	private Project project;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private FormParamFormatter formParamFormatter;
	@Mock
	private MultipartFile businessCase;
	@Mock
	private CalcSheetStorerCollaborator calcSheetStorerService;
	@Mock
	private BeanAggregator beanAggregator;
	@Mock
	private File bannerFile;
	@Mock
	private File avatarFile;
	@Mock
	private ProjectUnitSaleCollaborator unitSaleCollaborator;
	@Mock
	private DateFormatter dateFormatter;
	@Mock
	private List<Integer> userIds;
	@Mock
	private List<Integer> users;
	@Mock
	private List<String> tagList;
	@Mock
	private HttpServletRequest servlerRequest;
	@Mock
	private HttpServletResponse servlerResponse;
	@Mock
	private RatingBean ratingBean;
	@Mock
	private List<ProjectLog> projectLogs;
	@Mock
	private ProviderWrapper providerWrapper;
	@Mock
	private ProjectProvider projectProvider;
	@Mock
	private SecurityService securityService;
	@Mock
	private VariableCostsValidator variableCostsValidator;
	@Mock
	private GsonHelper gsonHelper;
	@Mock
	private List<ProjectProviderBean> providersWrapperBeans;
	@Mock
	private ProjectProviderAdapter projectProviderAdapter;
	@Mock
	private List<String> videoLinks;
	@Mock
	private List<String> soundcloudLinks;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private TransactionService transactionService;
	@Mock
	private ProjectTransactionService projectTransactionService;
	@Mock
	private Set<Project> byKeyword;
	@Mock
	private Set<Project> byTag;
	@Mock
	private ProjectCollaboratorService projectCollaborator;
	@Mock
	private AccountCreator accountCreator;
	@Mock
	private RevenuePotentialService revenuePotentialService;
	@Mock
	private MultiValueMap<String, String> formParams;
	@Mock
	private Properties properties;
	@Mock
	private ProjectValidator projectValidator;
	@Mock
	private ProjectPersisterService projectPersisterService;

	private ProjectCommand projectCommand;
	private ProjectMessageCommand projectMessageCommand;
	private ProjectCategoryCommand projectCategoryCommand;
	private ProjectChangeStatusCommand projectChangeStatusCommand;
	private ProjectRateCommand projectRateCommand;
	private ProjectProviderCommand projectProviderCommand;
	private ProjectProviderPaymentCommand projectProviderPaymentCommand;
	private ProjectProducerPaymentCommand projectProducerPaymentCommand;
	private ProjectCreateCommand projectCreateCommand;
	private ProjectCreateFinancialDataCommand projectCreateFinancialDataCommand;
	private ProjectDeleteUnitSaleCommand projectDeleteUnitSaleCommand;

	private List<File> banners = new ArrayList<File>();
	private List<File> avatars = new ArrayList<File>();
	private List<Project> projects = new ArrayList<Project>();
	private Set<Project> projectsAsSet = new HashSet<Project>();

	private long now = new java.util.Date().getTime();

	private String productionStartDate = "productionStartDate";
	private String premiereStartDate = "premiereStartDate";
	private String premiereEndDate = "premiereEndDate";
	private String name = "The Theory of Moral Sentiments";
	private String showground = "Glasgow Forum";
	private String inclosure = "Inclosure";
	private String videoLink1 = "videoLink1";
	private String videoLink2 = "videoLink2";
	private String videoLink3 = "videoLink3";
	private String videoLink4 = "videoLink4";
	private String videoLink5 = "videoLink5";
	private String soundCloudLink1 = "soundCloudLink1";
	private String soundCloudLink2 = "soundCloudLink2";
	private String soundCloudLink3 = "soundCloudLink3";
	private String soundCloudLink4 = "soundCloudLink4";
	private String soundCloudLink5 = "soundCloudLink5";
	private String description = "description";
	private String cast = "cast";
	private String bannerName = "bannerName";
	private String avatarName = "avatarName";
	private String section = "section";
	private String unitSale = "unitSale";
	private String capacity = "capacity";
	private String projectPhotosIds = "projectPhotosIds";
	private String comment = "comments";
	private String tags = "tags";
	private String tag = "tag";
	private String url = "url";
	private String serverAddress = "192.168.0.122";
	private String token = "token";
	private String statuses = "statuses";
	private String eventCode = "eventCode";
	private String photo = "photo";
	private String banner = "banner";
	private String avatar = "avatar";
	private String codeSection = "codeSection";

	private Integer paymentType = 0;
	private Integer projectId = 1;
	private Integer typeAsInteger = 1;
	private Integer subcategoryId = 2;
	private Integer categoryId = 3;
	private Integer userId = 4;
	private String autorizadoStatus = "5";
	private String produccionStatus = "6";
	private String presentacionStatus = "7";
	private Integer status = 8;
	private Integer subcategory = 9;
	private String pendienteStatus = "10";
	private String cerrandoStatus = "11";
	private Integer score = 13;
	private Integer videoPublic = 14;
	private Integer audioPublic = 15;
	private Integer imagePublic = 16;
	private Integer infoPublic = 17;
	private Integer numberPublic = 18;
	private Integer providerId = 19;
	private Integer max = 20;
	private Integer reason = 21;
	private Integer transactionId = 22;
	private Integer producerId = 23;
	private Integer projectUnitSaleId = 24;
	private Integer codeSectionAsInteger = 25;

	private ProjectType type = ProjectType.PRODUCT;

	private Long timestamp = 1L;

	private Set<ProjectVideo> projectVideos = new HashSet<ProjectVideo>();
	private Set<ProjectSoundcloud> projectSoundclouds = new HashSet<ProjectSoundcloud>();
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();
	private List<ProjectProvider> providersWrappers = new ArrayList<ProjectProvider>();
	private List<Integer> statusList = new ArrayList<Integer>();

	private String json = "{\"projectProviders\":[{\"projectId\":\"4\",\"providerId\":\"382\",\"advanceQuantity\":\"34\",\"advanceDate\":\"2013-08-27\",\"settlementQuantity\":\"45\"}]}";
	private String callback = "callback";
	private String keyword = "keyword";
	private String account = "account";

	private BigDecimal payment = new BigDecimal("0.789");
	private BigDecimal tri = new BigDecimal("0.123");
	private BigDecimal trf = new BigDecimal("0.012");
	private BigDecimal fundedAmount = new BigDecimal("1000");
	private BigDecimal investedAmount = new BigDecimal("2000");
	private BigDecimal revenuePotential = new BigDecimal("3000");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		projectCommand = new ProjectCommand();
		projectCommand.setProjectId(projectId);
		projectCommand.setUserId(userId);
		projectCommand.setTag(tag);
		projectCommand.setKeyword(keyword);
		projectCommand.setMax(max);

		projectCategoryCommand = new ProjectCategoryCommand();
		projectCategoryCommand.setType(typeAsInteger);
		projectCategoryCommand.setSubcategoryId(subcategoryId);
		projectCategoryCommand.setStatuses(statuses);
		projectCategoryCommand.setCategoryId(categoryId);

		projectMessageCommand = new ProjectMessageCommand();
		projectMessageCommand.setProjectId(projectId);
		projectMessageCommand.setUserId(userId);
		projectMessageCommand.setComment(comment);
		projectMessageCommand.setToken(token);
		projectMessageCommand.setCallback(callback);

		projectChangeStatusCommand = new ProjectChangeStatusCommand();
		projectChangeStatusCommand.setProjectId(projectId);
		projectChangeStatusCommand.setStatus(status);
		projectChangeStatusCommand.setUserId(userId);
		projectChangeStatusCommand.setToken(token);
		projectChangeStatusCommand.setCallback(callback);
		projectChangeStatusCommand.setComment(comment);
		projectChangeStatusCommand.setReason(reason);

		projectRateCommand = new ProjectRateCommand();
		projectRateCommand.setProjectId(projectId);
		projectRateCommand.setUserId(userId);
		projectRateCommand.setScore(score);
		projectRateCommand.setToken(token);

		projectProviderCommand = new ProjectProviderCommand();
		projectProviderCommand.setProjectProvider(json);
		projectProviderCommand.setProjectId(projectId);
		projectProviderCommand.setToken(token);
		projectProviderCommand.setCallback(callback);

		projectProviderPaymentCommand = new ProjectProviderPaymentCommand();
		projectProviderPaymentCommand.setProjectId(projectId);
		projectProviderPaymentCommand.setProviderId(providerId);
		projectProviderPaymentCommand.setToken(token);
		projectProviderPaymentCommand.setType(paymentType);

		projectProducerPaymentCommand = new ProjectProducerPaymentCommand();
		projectProducerPaymentCommand.setProjectId(projectId);
		projectProducerPaymentCommand.setProducerId(producerId);
		projectProducerPaymentCommand.setToken(token);
		projectProducerPaymentCommand.setPayment(payment);
		projectProducerPaymentCommand.setCallback(callback);

		projectCreateCommand = new ProjectCreateCommand();
		projectCreateCommand.setName(name);
		projectCreateCommand.setSubcategory(subcategory);
		projectCreateCommand.setInclosure(inclosure);
		projectCreateCommand.setShowground(showground);
		projectCreateCommand.setBanner(banner);
		projectCreateCommand.setAvatar(avatar);
		projectCreateCommand.setVideoLink1(videoLink1);
		projectCreateCommand.setVideoLink2(videoLink2);
		projectCreateCommand.setVideoLink3(videoLink3);
		projectCreateCommand.setVideoLink4(videoLink4);
		projectCreateCommand.setVideoLink5(videoLink5);
		projectCreateCommand.setSoundCloudLink1(soundCloudLink1);
		projectCreateCommand.setSoundCloudLink2(soundCloudLink2);
		projectCreateCommand.setSoundCloudLink3(soundCloudLink3);
		projectCreateCommand.setSoundCloudLink4(soundCloudLink4);
		projectCreateCommand.setSoundCloudLink5(soundCloudLink5);
		projectCreateCommand.setDescription(description);
		projectCreateCommand.setCast(cast);
		projectCreateCommand.setStatus(status);
		projectCreateCommand.setType(type);
		projectCreateCommand.setUserId(userId);
		projectCreateCommand.setId(projectId);
		projectCreateCommand.setProjectPhotosIds(projectPhotosIds);
		projectCreateCommand.setTags(tags);
		projectCreateCommand.setUrl(url);
		projectCreateCommand.setVideoPublic(videoPublic);
		projectCreateCommand.setAudioPublic(audioPublic);
		projectCreateCommand.setImagePublic(imagePublic);
		projectCreateCommand.setInfoPublic(infoPublic);
		projectCreateCommand.setToken(token);
		projectCreateCommand.setCallback(callback);
		projectCreateCommand.setPhotos(photo);

		projectCreateFinancialDataCommand = new ProjectCreateFinancialDataCommand();
		projectCreateFinancialDataCommand.setProjectId(projectId);
		projectCreateFinancialDataCommand.setEventCode(eventCode);
		projectCreateFinancialDataCommand.setSection(section);
		projectCreateFinancialDataCommand.setUnitSale(unitSale);
		projectCreateFinancialDataCommand.setCapacity(capacity);
		projectCreateFinancialDataCommand.setCodeSection(codeSection);
		projectCreateFinancialDataCommand.setBusinessCase(businessCase);
		projectCreateFinancialDataCommand.setProductionStartDate(productionStartDate);
		projectCreateFinancialDataCommand.setPremiereStartDate(premiereStartDate);
		projectCreateFinancialDataCommand.setPremiereEndDate(premiereEndDate);
		projectCreateFinancialDataCommand.setNumberPublic(numberPublic);
		projectCreateFinancialDataCommand.setToken(token);
		projectCreateFinancialDataCommand.setCallback(callback);

		projectDeleteUnitSaleCommand = new ProjectDeleteUnitSaleCommand();
		projectDeleteUnitSaleCommand.setProjectUnitSaleId(projectUnitSaleId);
		projectDeleteUnitSaleCommand.setToken(token);
	}

	@Test
	public void shouldListProjects() throws Exception {
		projects.add(project);
		when(project.getId()).thenReturn(projectId);
		when(projectService.getAllProjects()).thenReturn(projects);

		Iterable<Project> result = projectController.listProjects();

		assertEquals(result, projects);
	}

	// @Test
	// public void shouldGetProjectById() throws Exception {
	// when(projectService.getProjectById(projectId)).thenReturn(project);
	// when(project.getProjectVideos()).thenReturn(projectVideos);
	// when(project.getProjectSoundclouds()).thenReturn(projectSoundclouds);
	// when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
	// when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
	// when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);
	// when(projectTransactionService.getCRE(projectFinancialData)).thenReturn(cre);
	// when(project.getTri()).thenReturn(tri);
	// when(project.getTrf()).thenReturn(trf);
	// when(projectService.getTRA(tri, trf, projectId)).thenReturn(tra);
	//
	// Project result = projectController.getProject(projectCommand);
	//
	// assertEquals(project, result);
	// verify(result).setTri(tri);
	// verify(result).setTrf(trf);
	// verify(result).setCre(cre);
	// verify(result).setTra(tra);
	// }

	@Test
	public void shouldFindProjectsByUserId() throws Exception {
		projects.add(project);
		when(projectService.getProjectsByUserId(userId)).thenReturn(projects);
		when(project.getProjectVideos()).thenReturn(projectVideos);
		when(project.getProjectSoundclouds()).thenReturn(projectSoundclouds);
		when(project.getId()).thenReturn(projectId);

		List<Project> result = projectController.getProjectsByUserId(projectCommand);

		assertEquals(projects, result);
	}

	@Test
	public void shouldGetProjectsByTypeSubategoryId() throws Exception {
		projectController.getProjectsByTypeSubcategoryId(projectCategoryCommand);
		verify(projectService).getProjectsByTypeSubcategoryId(type, subcategoryId, statuses);
	}

	@Test
	public void shouldGetProjectsByTypeCategoryId() throws Exception {
		projects.add(project);
		when(project.getId()).thenReturn(projectId);
		when(projectService.getProjectsByCategoryId(categoryId, statuses)).thenReturn(projects);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		projectController.getProjectsByStatusesAndCategoryId(projectCategoryCommand);

		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldGetProjectsBySubategoryId() throws Exception {
		projectController.getProjectsBySubcategoryId(projectCategoryCommand);
		verify(projectService).getProjectsBySubcategoryId(subcategoryId);
	}

	@Test
	public void shouldGetProjectsByCategoryId() throws Exception {
		projectController.getProjectsByCategoryId(projectCategoryCommand);
		verify(projectService).getProjectsByCategoryId(categoryId);
	}

	@Test
	public void shouldListProjectsByType() throws Exception {
		projects.add(project);
		when(project.getId()).thenReturn(projectId);
		when(projectService.getProjectsByType(type)).thenReturn(projects);

		List<Project> result = projectController.listProjectsByType(projectCategoryCommand);

		assertEquals(projects, result);
	}

	@Test
	public void shouldPostProject() throws Exception {
		banners.add(bannerFile);
		avatars.add(avatarFile);
		when(bannerFile.getName()).thenReturn(bannerName);
		when(avatarFile.getName()).thenReturn(avatarName);
		when(dateUtil.createDateAsLong()).thenReturn(now);
		when(projectService.getProjectById(projectId)).thenReturn(project);
		when(projectService.saveProject(project, userId)).thenReturn(projectId);
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(projectHelper.createArrayListString()).thenReturn(videoLinks).thenReturn(soundcloudLinks);
		when(project.getId()).thenReturn(projectId);
		when(securityService.isValid(token)).thenReturn(true);

		String response = projectController.createProject(projectCreateCommand);

		verify(project).setName(name);
		verify(project).setSubcategory(subcategory);
		verify(project).setInclosure(inclosure);
		verify(project).setShowground(showground);
		verify(project).setDescription(description);
		verify(project).setCast(cast);
		verify(project).setTimeCreated(now);
		verify(project).setStatus(status);
		verify(project).setType(type);
		verify(project).setUrl(url);
		verify(project).setVideoPublic(videoPublic);
		verify(project).setAudioPublic(audioPublic);
		verify(project).setImagesPublic(imagePublic);
		verify(project).setInfoPublic(infoPublic);
		verify(project).setBanner(banner);
		verify(project).setAvatar(avatar);

		verify(projectPersisterService).save(project, videoLinks, soundcloudLinks, photo, tags, userId);

		assertEquals(response, ResponseState.getJsonResponse(projectId));
	}

	@Test
	public void shouldCreateFinancialData() throws Exception {
		when(projectService.saveProject(project, userId)).thenReturn(projectId);
		when(projectService.getProjectById(projectId)).thenReturn(project);
		when(projectCollaborator.getProjectFinancialData(project)).thenReturn(projectFinancialData);
		when(calcSheetStorerService.saveCalcSheet(businessCase)).thenReturn(timestamp);
		when(unitSaleCollaborator.createUnitSales(projectFinancialData, section, unitSale, capacity, codeSection))
				.thenReturn(projectUnitSales);
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(accountCreator.createAccount(AccountType.PROJECT, projectId)).thenReturn(account);
		when(revenuePotentialService.getRevenuePotential(projectUnitSales)).thenReturn(revenuePotential);
		when(securityService.isValid(token)).thenReturn(true);

		projectController.createFinantialData(projectCreateFinancialDataCommand, servlerResponse);

		verify(projectFinancialData).setAccount(account);
		verify(projectFinancialData).setProductionStartDate(dateFormatter.format(productionStartDate));
		verify(projectFinancialData).setPremiereStartDate(dateFormatter.format(premiereStartDate));
		verify(projectFinancialData).setPremiereEndDate(dateFormatter.format(premiereEndDate));
		verify(projectFinancialData).setProjectUnitSales(projectUnitSales);
		verify(projectFinancialData).setNumbersPublic(numberPublic);
		verify(projectFinancialData).setRevenuePotential(revenuePotential);

		verify(beanAggregator).addProjectBusinessCase(projectFinancialData, timestamp);

		verify(projectService).saveProject(project);

		verify(servlerResponse).sendRedirect(callback);
	}

	@Test
	public void shouldChangeStatus() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(projectService).changeStatus(projectId, status, userId, comment, reason);
		verify(servlerResponse).sendRedirect(callback);
	}

	@Test
	public void shouldNotChangeStatusDueToNoAdministrativeAccount() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeStatus(projectId, status, userId, comment, reason)).thenThrow(
				new NotTramaAccountFoundException());

		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(
				callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_ADMINISTRATIVE_ACCOUNT_FOUND));
	}

	@Test
	public void shouldNotChangeStatusDueToNoProject() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeStatus(projectId, status, userId, comment, reason)).thenThrow(
				new ProjectNotExistException(projectId));

		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.PROJECT_NOT_FOUND));
	}

	@Test
	public void shouldNotChangeStatusDueToNoFinancialData() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeStatus(projectId, status, userId, comment, reason)).thenThrow(
				new NoFinancialDataException(projectId));

		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(
				callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FINANTIAL_DATA));
	}

	@Test
	public void shouldNotChangeStatusDueToChangeStatusException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeStatus(projectId, status, userId, comment, reason)).thenThrow(
				new OperationStatusException(5));

		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(
				callback + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_OPERATION_STATUS));
	}

	@Test
	public void shouldNotChangeStatusDueToNoValidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(projectService, never()).changeStatus(projectId, status, userId, comment, reason);
		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotChangeStatusDueToNonSufficientFundsException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeStatus(projectId, status, userId, comment, reason)).thenThrow(
				new NonSufficientFundsException(projectId));

		projectController.changeStatus(projectChangeStatusCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
	}

	@Test
	public void shouldGetByTag() throws Exception {
		projectController.getByTag(projectCommand);
		verify(projectService).getByTag(tag);
	}

	@Test
	public void shouldRate() throws Exception {
		when(projectService.createRate(projectId, userId, score)).thenReturn(ResultType.SUCCESS);
		when(projectHelper.createRatingBean()).thenReturn(ratingBean);
		when(projectHelper.toJson(ratingBean)).thenReturn(json);
		when(securityService.isValid(token)).thenReturn(true);

		projectController.rate(projectRateCommand, servlerResponse);

		verify(ratingBean).setResultType(ResultType.SUCCESS);
		verify(servlerResponse).addHeader("Allow-Control-Allow-Methods", "POST");
		verify(servlerResponse).addHeader("Access-Control-Allow-Origin", "*");
	}

	@Test
	public void shouldNotRate() throws Exception {
		when(projectService.createRate(projectId, userId, score)).thenReturn(ResultType.SUCCESS);
		when(projectHelper.createRatingBean()).thenReturn(ratingBean);
		when(projectHelper.toJson(ratingBean)).thenReturn(json);
		when(securityService.isValid(token)).thenReturn(false);

		projectController.rate(projectRateCommand, servlerResponse);

		verify(ratingBean, never()).setResultType(ResultType.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSaveProviders() throws Exception {
		providersWrappers.add(projectProvider);
		when(gsonHelper.createWithDateFormat("dd-MM-yyyy")).thenReturn(
				new GsonBuilder().setDateFormat("dd-MM-yyyy").create());
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProjectProvider(eq(projectId), isA(Set.class))).thenReturn(true);

		projectController.saveProviders(projectProviderCommand, servlerResponse);

		verify(projectService).measureBreakevenAndBudget(eq(projectId), isA(Set.class));
		verify(servlerResponse).sendRedirect(callback);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotSaveProvidersDueToErrorOnSaving() throws Exception {
		providersWrappers.add(projectProvider);
		when(gsonHelper.createWithDateFormat("dd-MM-yyyy")).thenReturn(
				new GsonBuilder().setDateFormat("dd-MM-yyyy").create());
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProjectProvider(eq(projectId), isA(Set.class))).thenReturn(false);

		projectController.saveProviders(projectProviderCommand, servlerResponse);

		verify(projectService, never()).measureBreakevenAndBudget(eq(projectId), isA(Set.class));
		verify(servlerResponse).sendRedirect(
				callback + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotSaveProvidersDueNoToken() throws Exception {
		providersWrappers.add(projectProvider);
		when(gsonHelper.createWithDateFormat("dd-MM-yyyy")).thenReturn(
				new GsonBuilder().setDateFormat("dd-MM-yyyy").create());
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(securityService.isValid(token)).thenReturn(false);

		projectController.saveProviders(projectProviderCommand, servlerResponse);

		verify(projectService, never()).createProjectProvider(eq(projectId), isA(Set.class));
		verify(projectService, never()).measureBreakevenAndBudget(eq(projectId), isA(Set.class));
		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldSaveVariableCosts() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("projectId", "1");
		params.put("callback", callback);

		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(securityService.isValid(token)).thenReturn(true);
		when(variableCostsValidator.isValid(params)).thenReturn(true);

		assertEquals(3, params.size());
		projectController.saveVariableCosts(formParams, servlerResponse);
		assertEquals(1, params.size());

		verify(projectService).createVariableCosts(params);
		verify(servlerResponse).sendRedirect(callback);
	}

	@Test
	public void shouldNotSaveVariableCostsDueToNoValid() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("projectId", "1");
		params.put("callback", callback);

		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(servlerRequest.getLocalAddr()).thenReturn(serverAddress);
		when(securityService.isValid(token)).thenReturn(true);
		when(variableCostsValidator.isValid(params)).thenReturn(false);

		assertEquals(3, params.size());
		projectController.saveVariableCosts(formParams, servlerResponse);
		assertEquals(1, params.size());

		verify(projectService, never()).createVariableCosts(params);
		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.VARIABLE_COST_NOT_VALID));
	}

	@Test
	public void shouldNotSaveVariableCosts() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);

		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(securityService.isValid(token)).thenReturn(false);

		assertEquals(1, params.size());
		projectController.saveVariableCosts(formParams, servlerResponse);
		assertEquals(1, params.size());

		verify(projectService, never()).createVariableCosts(params);
		verify(servlerResponse).sendRedirect(
				params.get("callback") + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldGetProjectClosestToEndFunding() throws Exception {
		projectsAsSet.add(project);
		when(project.getId()).thenReturn(projectId);
		when(projectService.getProjectsByClosestToEndFunding()).thenReturn(projectsAsSet);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		projectController.getByClosestToEndFunding();

		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldGetProductsClosestToEndPresentation() throws Exception {
		projects.add(project);
		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectService.getProjectsByClosestToEndPresentation()).thenReturn(projects);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		projectController.getByClosestToEndPresentation();

		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldSaveProviderPayment() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProviderPayment(projectId, providerId, PaymentType.getTypeByCode(paymentType)))
				.thenReturn(ResultType.SUCCESS);

		assertEquals(ResponseState.getJsonResponse(ResultType.SUCCESS.ordinal()),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));

		verify(servlerResponse).addHeader("Allow-Control-Allow-Methods", "POST");
		verify(servlerResponse).addHeader("Access-Control-Allow-Origin", "*");
	}

	@Test
	public void shouldNotSaveProviderPaymentDueToToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);
		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));
	}

	@Test
	public void shouldNotSaveProviderPaymentDueToNonProjectProviderException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProviderPayment(projectId, providerId, PaymentType.getTypeByCode(paymentType)))
				.thenThrow(new NonProjectProviderException(providerId));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.PROVIDER_NOT_FOUND),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));
	}

	@Test
	public void shouldNotSaveProviderPaymentDueToAdvanceProviderPaidException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProviderPayment(projectId, providerId, PaymentType.getTypeByCode(paymentType)))
				.thenThrow(new AdvanceProviderPaidException(providerId));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.ADVANCE_PAYMENT_ALREADY_PAID),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));
	}

	@Test
	public void shouldNotSaveProviderPaymentDueToSettlementProviderPaidException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProviderPayment(projectId, providerId, PaymentType.getTypeByCode(paymentType)))
				.thenThrow(new SettlementProviderPaidException(providerId));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.SETTLEMENT_ALREADY_PAID),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));
	}

	@Test
	public void shouldNotSaveProviderPaymentDueToNoUserException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProviderPayment(projectId, providerId, PaymentType.getTypeByCode(paymentType)))
				.thenThrow(new UserNotFoundException(userId));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.USER_NOT_FOUND),
				projectController.saveProviderPayment(projectProviderPaymentCommand, servlerResponse));
	}

	@Test
	public void shouldGetProjectsByStatuses() throws Exception {
		projects.add(project);
		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectService.getProjectsByStatuses(statuses)).thenReturn(projects);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		assertEquals(projects, projectController.getProjectsByStatuses(projectCategoryCommand));

		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldGetProjectsByHigherBalance() throws Exception {
		projectsAsSet.add(project);

		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectService.getProjectsByHighBalance()).thenReturn(projectsAsSet);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);
		assertEquals(projectsAsSet, projectController.getByHigherBalance());
		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldNotSaveProducerPaymentDueToToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);

		projectController.saveProducerPayment(projectProducerPaymentCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldSaveProducerPayment() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProducerPayment(projectId, producerId, payment)).thenReturn(ResultType.SUCCESS);

		projectController.saveProducerPayment(projectProducerPaymentCommand, servlerResponse);

		verify(projectService).createProducerPayment(projectId, producerId, payment);
		verify(servlerResponse).sendRedirect(callback);
	}

	@Test
	public void shouldNotSaveProducerPaymentDueToError() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createProducerPayment(projectId, producerId, payment)).thenThrow(
				new NonSufficientFundsException(projectId));

		projectController.saveProducerPayment(projectProducerPaymentCommand, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
	}

	@Test
	public void shouldGetBestSupportedProjects() throws Exception {
		when(projectTransactionService.getMoreProfitable(max)).thenReturn(projects);
		assertEquals(projects, projectController.getMoreProfitable(projectCommand));
	}

	@Test
	public void shouldConsumeUnits() throws Exception {
		Map<String, String> params = setParamsExpectations();

		when(securityService.isValid(token)).thenReturn(true);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(projectService.createConsumingUnits(params)).thenReturn(transactionId);

		projectController.consumeUnits(formParams, servlerResponse);

		verify(projectService).createConsumingUnits(params);
		verify(servlerResponse).sendRedirect(callback + "&response=" + transactionId);
		assertEquals(1, params.size());
	}

	private Map<String, String> setParamsExpectations() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("projectId", "1");
		params.put("callback", callback);
		return params;
	}

	@Test
	public void shouldNotConsumeUnitsDueToNonValidToken() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);

		when(securityService.isValid(token)).thenReturn(false);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.consumeUnits(formParams, servlerResponse);
		verify(servlerResponse).sendRedirect(
				params.get("callback") + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotConsumeUnitsDueToNonSufficientFunds() throws Exception {
		Map<String, String> params = setParamsExpectations();

		when(securityService.isValid(token)).thenReturn(true);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(projectService.createConsumingUnits(params)).thenThrow(new NonSufficientFundsException(projectId));

		projectController.consumeUnits(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
		assertEquals(1, params.size());
	}

	@Test
	public void shouldNotConsumeUnitsDueToNonSufficientUnits() throws Exception {
		Map<String, String> params = setParamsExpectations();

		when(securityService.isValid(token)).thenReturn(true);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);
		when(projectService.createConsumingUnits(params)).thenThrow(new NonSufficientUnitsException());

		projectController.consumeUnits(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_UNITS));
		assertEquals(1, params.size());
	}

	@Test
	public void shouldGetProjectsFundedByUserId() throws Exception {
		Integer expectedAutorizadoStatus = 5;
		projectsAsSet.add(project);
		when(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)).thenReturn(autorizadoStatus);

		setProjectExpectations();
		when(transactionService.getAmountByUser(TransactionType.FUNDING, project, userId)).thenReturn(fundedAmount);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		assertEquals(projectsAsSet, projectController.getProjectsFundedByUserId(projectCommand));

		verify(project).setFundedAmount(fundedAmount);
		verify(project).setTri(tri);
		verify(project).setTrf(trf);
		assertEquals(1, statusList.size());
		assertEquals(expectedAutorizadoStatus, statusList.get(0));
	}

	@Test
	public void shouldGetProductsInvestedByUserId() throws Exception {
		projectsAsSet.add(project);

		Integer expectedProduccionStatus = 6;
		Integer expectedPresentacionStatus = 7;
		Integer expectedPendienteStatus = 10;
		Integer expectedCerrandoStatus = 11;

		when(properties.getProperty(ApplicationState.PRESENTACION_STATUS)).thenReturn(presentacionStatus);
		when(properties.getProperty(ApplicationState.PRODUCTION_STATUS)).thenReturn(produccionStatus);
		when(properties.getProperty(ApplicationState.PENDIENTE_STATUS)).thenReturn(pendienteStatus);
		when(properties.getProperty(ApplicationState.CERRANDO_STATUS)).thenReturn(cerrandoStatus);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(transactionService.getAmountByUser(TransactionType.INVESTMENT, project, userId)).thenReturn(investedAmount);

		setProjectExpectations();

		assertEquals(projectsAsSet, projectController.getProductsInvestedByUserId(projectCommand));

		verify(project).setTri(tri);
		verify(project).setInvestedAmount(investedAmount);
		assertEquals(4, statusList.size());
		assertEquals(expectedProduccionStatus, statusList.get(0));
		assertEquals(expectedPresentacionStatus, statusList.get(1));
		assertEquals(expectedPendienteStatus, statusList.get(2));
		assertEquals(expectedCerrandoStatus, statusList.get(3));
	}

	private void setProjectExpectations() {
		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectService.getProjectsByStatusAndUserId(statusList, userId)).thenReturn(projectsAsSet);
		when(projectHelper.createArrayListInteger()).thenReturn(statusList);
	}

	@Test
	public void shouldGetByKeyword() throws Exception {
		projectsAsSet.add(project);
		when(projectService.getByKeyword(keyword)).thenReturn(byKeyword);
		when(projectService.getByTag(keyword)).thenReturn(byTag);
		when(projectRanger.distinct(byKeyword)).thenReturn(projectsAsSet);

		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);
		Set<Project> result = projectController.getByKeyword(projectCommand);

		assertEquals(1, result.size());
		assertTrue(result.contains(project));
		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldNotGetByKeyword() throws Exception {
		when(projectService.getByKeyword(keyword)).thenThrow(new InvalidLengthException());
		assertEquals(new HashSet<Project>(), projectController.getByKeyword(projectCommand));
	}

	@Test
	public void shouldDeleteProjectUnitSale() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeProjectUnitSale(projectUnitSaleId)).thenReturn(ResultType.SUCCESS);

		assertEquals(ResponseState.getJsonResponse(ResultType.SUCCESS.ordinal()),
				projectController.deleteProjectUnitSale(projectDeleteUnitSaleCommand));
	}

	@Test
	public void shouldNotDeleteProjectUnitSaleDueToNotValidToken() throws Exception {
		when(securityService.isValid(token)).thenReturn(false);

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED),
				projectController.deleteProjectUnitSale(projectDeleteUnitSaleCommand));

		verify(projectService, never()).changeProjectUnitSale(projectUnitSaleId);
	}

	@Test
	public void shouldNotDeleteProjectUnitSaleDueNonProjectUnitSaleException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeProjectUnitSale(projectUnitSaleId)).thenThrow(
				new NonProjectUnitSaleException(codeSection));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.SECTION_CODE_NOT_FOUND),
				projectController.deleteProjectUnitSale(projectDeleteUnitSaleCommand));
	}

	@Test
	public void shouldNotDeleteProjectUnitSaleDueToOperationStatusException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeProjectUnitSale(projectUnitSaleId)).thenThrow(new OperationStatusException(5));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_OPERATION_STATUS),
				projectController.deleteProjectUnitSale(projectDeleteUnitSaleCommand));
	}

	@Test
	public void shouldNotDeleteProjectUnitSaleDueToNotValidParameterException() throws Exception {
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.changeProjectUnitSale(projectUnitSaleId)).thenThrow(
				new NotValidParameterException(projectUnitSaleId));

		assertEquals(ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST),
				projectController.deleteProjectUnitSale(projectDeleteUnitSaleCommand));
	}

	@Test
	public void shouldGetProjectsFundedOrInvestedByUserId() throws Exception {
		projectsAsSet.add(project);
		when(projectCollaborator.getProjectsFundedOrInvestedByUserId(userId)).thenReturn(projectsAsSet);
		when(projectRanger.distinct(projectsAsSet)).thenReturn(projectsAsSet);
		when(project.getId()).thenReturn(projectId);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(transactionService.getAmountByUser(TransactionType.FUNDING, project, userId)).thenReturn(fundedAmount);
		when(transactionService.getAmountByUser(TransactionType.INVESTMENT, project, userId)).thenReturn(investedAmount);
		when(projectTransactionService.getTRI(projectFinancialData)).thenReturn(tri);
		when(projectTransactionService.getTRF(projectFinancialData)).thenReturn(trf);

		projectController.getProjectsFundedOrInvestedByUserId(projectCommand);

		verify(project).setFundedAmount(fundedAmount);
		verify(project).setInvestedAmount(investedAmount);
		verify(project).setTri(tri);
		verify(project).setTrf(trf);
	}

	@Test
	public void shouldGetSections() throws Exception {
		projectController.getSections(projectCommand);
		verify(projectService).getSections(projectId);
	}

	private Map<String, String> setSaveBoxOfficeSalesParamsExpectations() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("section", "A1");
		params.put("quantity", "2");
		params.put("token", token);
		params.put("callback", callback);
		return params;
	}

	@Test
	public void shouldSaveBoxOfficeSales() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();

		when(securityService.isValid(token)).thenReturn(true);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(projectService).createBoxOfficeSales(params);
		verify(servlerResponse).sendRedirect(callback);

	}

	@Test
	public void shouldNotSaveBoxOfficeSalesDueToInvalidToken() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();
		when(securityService.isValid(token)).thenReturn(false);
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
	}

	@Test
	public void shouldNotSaveBoxOfficeSalesDueToNonExistentCode() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createBoxOfficeSales(params)).thenThrow(
				new NonExistentCodeException(codeSectionAsInteger.toString()));
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.SECTION_CODE_NOT_FOUND));
	}

	@Test
	public void shouldNotSaveBoxOfficeSalesDueToNoSufficientUnits() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createBoxOfficeSales(params)).thenThrow(new NonSufficientUnitsException());
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_UNITS));
	}

	@Test
	public void shouldNotSaveBoxOfficeSalesDueToNoTramaAccountWasFound() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createBoxOfficeSales(params)).thenThrow(new NotTramaAccountFoundException());
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(
				callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_ADMINISTRATIVE_ACCOUNT_FOUND));
	}

	@Test
	public void shouldNotSaveBoxOfficeSalesDueToNoSufficientFundsFound() throws Exception {
		Map<String, String> params = setSaveBoxOfficeSalesParamsExpectations();
		when(securityService.isValid(token)).thenReturn(true);
		when(projectService.createBoxOfficeSales(params)).thenThrow(new NonSufficientFundsException(projectId));
		when(formParamFormatter.formatParams(formParams)).thenReturn(params);

		projectController.boxOfficeSales(formParams, servlerResponse);

		verify(servlerResponse).sendRedirect(callback + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
	}

}
