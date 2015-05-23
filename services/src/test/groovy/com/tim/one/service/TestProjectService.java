package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.AdminAccountType;
import com.tim.one.bean.BuyingSource;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.PaymentType;
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
import com.tim.one.helper.ProjectHelper;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectCategory;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectLog;
import com.tim.one.model.ProjectPhoto;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectRate;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProjectType;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.ProviderTx;
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
import com.tim.one.service.impl.ProjectServiceImpl;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.util.IntegerParser;
import com.tim.one.validator.ProjectValidator;

public class TestProjectService {

	@InjectMocks
	private ProjectService projectService = new ProjectServiceImpl();
	
	@Mock
	private Project project;
	@Mock
	private Project project1;
	@Mock
	private List<Project> projects;
	@Mock
  private Set<Project> projectsAsSet;
	@Mock
	private List<ProjectPhoto> projectPhotos;
	@Mock
	private ProjectCategory projectCategory;
	@Mock
	private ProjectHelper projectHelper;
	@Mock
	private ProjectLog projectLog;
	@Mock
	private ProjectRate projectRate;
	@Mock
	private ProjectRate projectRate1;
	@Mock
	private List<String> tags;
	@Mock
	private List<ProjectLog> projectLogs;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private ProjectProvider projectProvider;
	@Mock
	private ProjectVariableCost projectVariableCost;
	@Mock
	private List<ProjectVariableCost> projectVariableCosts;
	@Mock
	private IntegerParser integerParser;
	@Mock
	private List<Integer> statusesAsList;
	@Mock
	private UserRepository userRepository;
	@Mock
	private TransactionCollaborator transactionCollaborator;
	@Mock
	private User user;
	@Mock
	private UnitCalculator unitCalculator;
	@Mock
	private TransactionService transactionService;
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private ProjectTx projectTx;
	@Mock
	private MessagePacker messagePacker;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private MessageCollaborator messageCollaborator;
	@Mock
	private ProviderTx providerTx;
	@Mock
	private ProjectCollaboratorService projectCollaborator;
	@Mock
	private SectionBean sectionBean;
	@Mock
	private List<ProjectProvider> providers;
	@Mock
	private BreakevenService breakevenService;
	@Mock
	private BudgetService budgetService;
	@Mock
	private BulkUnitTx bulkUnitTx;
	@Mock
	private TramaAccount tramaAccount;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private ProjectValidator projectValidator;
	@Mock
	private Properties properties;
	@Mock
	private ProjectRepository projectRepository;
  @Mock
  private ProjectFinancialDataRepository projectFinancialDataRepository;
  @Mock
  private ProjectProviderRepository projectProviderRepository;
  @Mock
  private ProjectRateRepository projectRateRepository;
  @Mock
  private ProjectVariableCostRepository projectVariableCostRepository;
  @Mock
  private ProjectVariableCostCollaborator projectVariableCostCollaborator;
  @Mock
  private ProjectUnitSaleRepository projectUnitSaleRepository;
  @Mock
  private ProjectCategoryRepository projectCategoryRepository;
  @Mock
  private BulkUnitTxRepository bulkUnitTxRepository;
  @Mock
  private TramaAccountRepository tramaAccountRepository;
  @Mock
  private Set<ProjectLog> logs;
  @Mock
  private TransactionApplier transactionApplier;
  @Mock
  private ProjectCollectionInitializer projectCollectionInitializer;
  @Mock
  private ProjectProviderCollectionDeleter projectProviderCollectionDeleter;

  private Project projectAsObject = new Project(); 
	private List<ProjectCategory> subcategories = new ArrayList<ProjectCategory>();
	private List<Integer> subcategoryIds = new ArrayList<Integer>();
	private List<ProjectRate> projectRates = new ArrayList<ProjectRate>();
	private List<Project> projectList = new ArrayList<Project>();
	private List<ProjectProvider> projectProviders = new ArrayList<ProjectProvider>();
	private Set<ProjectProvider> providersAsSet = new HashSet<ProjectProvider>();
	private Set<Project> projectSet = new HashSet<Project>();

	private Integer zeroAsInteger = 0;
	private Integer desarrolloStatus = 0;
	private Integer projectId = 1;
	private Integer subcategoryId = 2;
	private Integer categoryId = 3;
	private Integer rechazadoStatusAsInteger = 4;
	private Integer autorizadoStatusAsInteger = 5;
	private Integer produccionStatusAsInteger = 6;
	private Integer userId = 9;
	private Integer score = 10;
	private Integer providerId = 11;
	private Integer reason = 12;
	private Integer transactionId = 13;
	private Integer projectUnitSaleId = 14;
	private Integer producerId = 15;
	private Integer units = 17;
	private Integer quantity = 18;
	private Integer totalUnits = 19;
	private Integer sectionCodeAsInteger = 401;

	private Long timestamp = 1L;

	private String rechazadoStatus = "4";
	private String autorizadoStatus = "5";
	private String produccionStatus = "6";
	private String finalizadoStatus = "8";
	private String keywordMaxLength = "16";
	private String comment = "comments";
	private String tag = "tag";
	private String statuses = "statuses";
	private String keyword = "keyword";
	private String name = "name";
	private String email = "email";
	private String sectionName = "sectionName";
	private String sectionCode = "401";
	private String projectName = "projectName";

	private ProjectType type = ProjectType.PROJECT;
	private PaymentType typePayment = PaymentType.ADVANCE;
	private BigDecimal zero = new BigDecimal("0.00");
	private BigDecimal unitSale = new BigDecimal("100");
	private BigDecimal payment = new BigDecimal("1000");
	private BigDecimal balance = new BigDecimal("20000");
	private BigDecimal advanceQuantity = new BigDecimal("3000");
	private BigDecimal settlementQuantity = new BigDecimal("4000");
	private BigDecimal breakeven = new BigDecimal("50000");
	private BigDecimal tramaFee = new BigDecimal("2500");
	private BigDecimal budget = new BigDecimal("3500");
	private BigDecimal tramaBalance = new BigDecimal("10000");
	
	private List<Integer> statusList = new ArrayList<Integer>();
	private Set<Project> projectsFunded = new HashSet<Project>();
	private Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();
	private List<UnitTx> unitList = new ArrayList<UnitTx>();
	private Map<String, String> params = new HashMap<String, String>();
  private Set<UnitTx> unitsAsSet = new HashSet<UnitTx>();

	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldCreateProject() throws Exception {
	  when(userRepository.findOne(userId)).thenReturn(user);
	  when(user.getId()).thenReturn(userId);
	  
		projectService.saveProject(project, userId);
		
		verify(project).setUser(user);
		verify(projectRepository).save(project);
	}
	
	@Test(expected=UserNotFoundException.class)
  public void shouldNotCreateProjectDueToNoUser() throws Exception {
    projectService.saveProject(project, userId);
    
    verify(project, never()).setUser(user);
    verify(projectRepository, never()).save(project);
  }
	
	@Test
	public void shouldListProjects() throws Exception {
		when(projectRepository.findAll()).thenReturn(projectList);
		
		Iterable<Project> result = projectService.getAllProjects();
		
		assertEquals(projectList, result);
	}
	
	@Test
	public void shouldGetProject() throws Exception {
		when(projectRepository.findOne(projectId)).thenReturn(project);
		assertEquals(project, projectService.getProjectById(projectId));
	}
	
	@Test
	public void shouldGetEmptyProject() throws Exception {
		assertNotNull(projectService.getProjectById(projectId));
	}
	
	@Test
	public void shouldGetProjectIfNoId() throws Exception {
		when(projectRepository.findOne(null)).thenThrow(new IllegalArgumentException());
		assertNotNull(projectService.getProjectById(null));
	}
	
	@Test
	public void shouldListProjectsByTypeSubcategoryId() throws Exception {
		when(integerParser.parse(statuses)).thenReturn(new ArrayList<Integer>());
		when(projectRepository.findProjectsByTypeAndSubcategory(type , subcategoryId)).thenReturn(projects);
		
		assertEquals(projects, projectService.getProjectsByTypeSubcategoryId(type, subcategoryId, statuses));
	}
	
	@Test
	public void shouldListProjectsByTypeSubcategoryAndStatuses() throws Exception {
		List<Integer> statusesAsList = new ArrayList<Integer>();
		statusesAsList.add(1);
		when(integerParser.parse(statuses)).thenReturn(statusesAsList);
		when(projectRepository.findProjectsByTypeAndSubcategoryAndStatuses(type , subcategoryId, statusesAsList)).thenReturn(projects);
		
		assertEquals(projects, projectService.getProjectsByTypeSubcategoryId(type, subcategoryId, statuses));
	}
	
	@Test
	public void shouldListProjectsBySubcategoryId() throws Exception {
		when(projectRepository.findProjectsBySubcategory(subcategoryId)).thenReturn(projects);
		assertEquals(projects, projectService.getProjectsBySubcategoryId(subcategoryId));
	}
	
	@Test
	public void shouldListProjectsByTypeCategoryId() throws Exception {
		when(projectCategory.getId()).thenReturn(subcategoryId);
		subcategories.add(projectCategory);
		subcategoryIds.add(subcategoryId);
		when(projectCategoryRepository.findSubCategories(categoryId)).thenReturn(subcategories);
		
		projectService.getProjectsByCategoryId(categoryId, statuses);
		
		verify(projectCategory).getId();
		verify(projectRepository).findBySubcategoryIn(subcategoryIds);
	}
	
	@Test
	public void shouldListProjectsByTypeCategoryAndStatuses() throws Exception {
		List<Integer> statusesAsList = new ArrayList<Integer>();
		statusesAsList.add(1);
		when(projectCategory.getId()).thenReturn(subcategoryId);
		subcategories.add(projectCategory);
		subcategoryIds.add(subcategoryId);
		when(projectCategoryRepository.findSubCategories(categoryId)).thenReturn(subcategories);
		when(integerParser.parse(statuses)).thenReturn(statusesAsList);
		
		projectService.getProjectsByCategoryId(categoryId, statuses);
		
		verify(projectCategory).getId();
		verify(projectRepository).findProjectsBySubcategoriesAndStatuses(subcategoryIds, statusesAsList);
	}
	
	@Test
	public void shouldListProjectsByCategoryId() throws Exception {
		when(projectCategory.getId()).thenReturn(subcategoryId);
		subcategories.add(projectCategory);
		subcategoryIds.add(subcategoryId);
		when(projectCategoryRepository.findSubCategories(categoryId)).thenReturn(subcategories);
		
		projectService.getProjectsByCategoryId(categoryId);
		
		verify(projectCategory).getId();
		verify(projectRepository).findBySubcategoryIn(subcategoryIds);
	}
	
	@Test
	public void shouldGetProjectByType() throws Exception {
		when(projectRepository.findProjectsByType(type)).thenReturn(projects);
		
		List<Project> result = projectService.getProjectsByType(type);
		
		assertEquals(projects, result);
	}
	
	@Test
	public void shouldChangeStatusToRevision() throws Exception {
		Integer status = 1;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verify(projectRepository).save(project);
		verifyProjectLogExpectations(status);
	}

	private void setStatusExpectations() {
		when(properties.getProperty(ApplicationState.RECHAZADO_STATUS)).thenReturn(rechazadoStatus);
		when(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)).thenReturn(autorizadoStatus);
		when(properties.getProperty(ApplicationState.PRODUCTION_STATUS)).thenReturn(produccionStatus);
		when(properties.getProperty(ApplicationState.FINALIZADO_STATUS)).thenReturn(finalizadoStatus);
		when(project.getLogs()).thenReturn(logs);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
	}
	
	@Test
	public void shouldChangeStatusToRechazado() throws Exception {
		Integer status = 4;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verify(transactionCollaborator).revertTramaCharge(project, reason);
		verifyProjectLogExpectations(status);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotChangeStatusToRechazado() throws Exception {
		Integer status = 4;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(properties.getProperty(ApplicationState.RECHAZADO_STATUS)).thenReturn(rechazadoStatus);
		when(transactionCollaborator.revertTramaCharge(project, reason)).thenThrow(new NonSufficientFundsException(projectId));
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project, never()).setStatus(status);
		verify(projectHelper, never()).createProjectLog();
	}
	
	@Test
	public void shouldChangeStatusToProduccion() throws Exception {
		Integer status = 6;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verify(transactionCollaborator).setTramaCharge(project);
		verifyProjectLogExpectations(status);
	}
	
	@Test
	public void shouldChangeStatusToFinalizado() throws Exception {
		Integer status = 8;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(properties.getProperty(ApplicationState.FINALIZADO_STATUS)).thenReturn(finalizadoStatus);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verify(transactionCollaborator).closeProject(project);
		verifyProjectLogExpectations(status);
	}
	
	@Test
	public void shouldChangeStatusToAutorizado() throws Exception {
		Integer status = 5;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verify(messageCollaborator).sendAutorizadoStatusNotifications(project);
		verify(transactionCollaborator).setFundingEndDate(projectFinancialData);
		verifyProjectLogExpectations(status);
	}
	
	@Test
	public void shouldChangeStatusToFinanciamiento() throws Exception {
		Integer status = 5;
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		setStatusExpectations();
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project).setStatus(status);
		verifyProjectLogExpectations(status);
	}
	
	@Test (expected = OperationStatusException.class)
	public void shouldNotChangeStatusDefinicion() throws Exception {
		Integer status = 0;
		
		projectService.changeStatus(projectId, status, userId, comment, reason);
		
		verify(project, never()).setStatus(status);
		verify(projectHelper, never()).createProjectLog();
	}
	
	@Test (expected = NotValidParameterException.class)
  public void shouldNotChangeStatusDueToNoReason() throws Exception {
    Integer status = 4;
    when(projectRepository.findOne(projectId)).thenReturn(project);
    
    projectService.changeStatus(projectId, status, userId, comment, null);
    
    verify(project, never()).setStatus(status);
    verify(projectHelper, never()).createProjectLog();
  }
	
	private void verifyProjectLogExpectations(Integer status) {
		verify(projectLog).setStatus(status);
		verify(projectLog).setTimestamp(timestamp);
		verify(projectLog).setProject(project);
		verify(projectLog).setUserId(userId);
		verify(projectLog).setComment(comment);
		verify(projectLog).setReason(reason);	
		verify(logs).add(projectLog);
	}
	
	@Test (expected = ProjectNotExistException.class)
	public void shouldNotChangeStatusIfNoProject() throws Exception {
		Integer status = 4;
		projectService.changeStatus(projectId, status, userId, comment, reason);
	}
	
	@Test (expected = NoFinancialDataException.class)
	public void shouldNotChangeStatusDueToNoFinancialData() throws Exception {
		when(properties.getProperty(ApplicationState.AUTORIZADO_STATUS)).thenReturn(autorizadoStatus);
		when(projectHelper.createProjectLog()).thenReturn(projectLog);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(transactionCollaborator.setFundingEndDate(projectFinancialData)).thenThrow(new NoFinancialDataException(projectId));
		setStatusExpectations();
		
		projectService.changeStatus(projectId, autorizadoStatusAsInteger, userId, comment, reason);
	}
	
	@Test
	public void shouldGetByTag() throws Exception {
		projectList.add(project);
		projectList.add(project1);
		projectList.add(project);
		when(projectRepository.findProjectsByTag(tag)).thenReturn(projectList);
		
		Set<Project> result = projectService.getByTag(tag);
		
		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldNotRatingProject() throws Exception {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
	  when(project.getProjectRate()).thenReturn(projectRate);
		
		ResultType result = projectService.createRate(projectId, userId, score);
		
		verify(projectRate, never()).setProject(project);
		verify(projectRate, never()).setUserId(userId);
		verify(projectRate, never()).setScore(score);
		verify(project, never()).setProjectRate(projectRate);
		verify(projectRateRepository, never()).save(projectRate);
		assertEquals(result, ResultType.FAIL);
	}
	
	@Test
	public void shouldRatingProject() throws Exception {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectHelper.createProjectRate()).thenReturn(projectRate);
		
		ResultType result = projectService.createRate(projectId, userId, score);
		
		verify(projectRate).setUserId(userId);
		verify(projectRate).setScore(score);
		verify(project).setProjectRate(projectRate);
		verify(projectRate).setProject(project);
		verify(projectRepository).save(project);
		assertEquals(result, ResultType.SUCCESS);
	}
	
	@Test
	public void shouldGetProjectRating() throws Exception {
		Float expectedResult = 4.5f;
		projectRates.add(projectRate);
		projectRates.add(projectRate1);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectRate.getScore()).thenReturn(4);
		when(projectRate1.getScore()).thenReturn(5);
		
		when(projectRateRepository.findByProject(project)).thenReturn(projectRates);
		
		Float result = projectService.getRating(projectId);
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void shouldGetProjectsByUserId() throws Exception {
	  when(userRepository.findOne(userId)).thenReturn(user);
		when(projectRepository.findProjectsByUser(user)).thenReturn(projectList);
		
		List<Project> result = projectService.getProjectsByUserId(userId);
		
		assertEquals(projectList, result);
	}
	
	@Test
	public void shouldSaveProjectProviders() throws Exception {
		providersAsSet.add(projectProvider);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectValidator.valid(providersAsSet)).thenReturn(true);
		
		
		projectService.createProjectProvider(projectId, providersAsSet);
		
		verify(projectProviderCollectionDeleter).deleteProviders(project);
		verify(projectProvider).setProject(project);
		verify(project).setProviders(providersAsSet);
		verify(projectRepository).save(project);
	}
	
	@Test
	public void shouldNotSaveProjectProviders() throws Exception {
		projectProviders.add(projectProvider);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectValidator.valid(providersAsSet)).thenReturn(false);
		
		assertFalse(projectService.createProjectProvider(projectId, providersAsSet));
		
		verify(projectProviderRepository, never()).save(projectProvider);
	}
	
	@Test
	public void shouldSaveVariableCosts() throws Exception {
		Map<String, String> params = setParamsExpectations();
		
		when(projectHelper.createProjectVariableCost()).thenReturn(projectVariableCost);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		
		assertEquals(3, params.size());
		
		projectService.createVariableCosts(params);
		
		verifyVariableCostExpectations();
		verify(projectVariableCostCollaborator).deleteProjectVariableCosts(projectFinancialData);
		verify(projectRepository).save(project);
		assertEquals(2, params.size());
	}

	private void verifyVariableCostExpectations() {
		verify(projectVariableCost).setName("key");
		verify(projectVariableCost).setProjectFinancialData(projectFinancialData);
		verify(projectVariableCost).setValue(new BigDecimal(3.5));
	}

	private Map<String, String> setParamsExpectations() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("projectId", "1");
		params.put("key", "3.5");
		params.put("callback", "callback");
		return params;
	}

	
	@Test
	public void shouldSaveVariableCostsWithNoProjectFinancialData() throws Exception {
		Map<String, String> params = setParamsExpectations();
		
		when(projectRepository.findOne(projectId)).thenReturn(projectAsObject);
		when(projectHelper.createProjectVariableCost()).thenReturn(projectVariableCost);
		when(projectHelper.createProjectFinancialData()).thenReturn(projectFinancialData);
		
		assertEquals(3, params.size());
		
		projectService.createVariableCosts(params);
		
		verifyVariableCostExpectations();
		verify(projectRepository).save(projectAsObject);
		verify(projectFinancialData).setProject(projectAsObject);
		assertEquals(2, params.size());
	}
	
	@Test
	public void shouldGetProjectsByClosestToEndFunding() throws Exception {
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(projectRepository.findProjectsByClosestToEndFunding(timestamp)).thenReturn(projectSet);
		assertEquals(projectSet, projectService.getProjectsByClosestToEndFunding());
	}
	
	@Test
	public void shouldGetProjectsByClosestToEndPresentation() throws Exception {
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(projectRepository.findProjectsByClosestToEndPresentation(timestamp)).thenReturn(projects);
		assertEquals(projects, projectService.getProjectsByClosestToEndPresentation());
	}
	
	@Test
	public void shouldSaveAdvanceProviderPayment() throws Exception {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getAdvancePaidDate()).thenReturn(null);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		when(projectProvider.getAdvanceQuantity()).thenReturn(advanceQuantity);
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(user.getBalance()).thenReturn(balance);
		setProjectTxExpectations();

		projectService.createProviderPayment(projectId, providerId, PaymentType.ADVANCE);
		
		verify(projectProvider).setAdvancePaidDate(timestamp);
		verify(projectProvider, never()).setSettlementPaidDate(timestamp);
		verify(transactionLogService).createProviderLog(providerId, projectId, advanceQuantity, PaymentType.ADVANCE, TransactionType.PAYMENT);
		verify(projectProviderRepository).save(projectProvider);
		verify(transactionApplier).substractAmount(projectFinancialData, advanceQuantity);
		verify(transactionApplier).addAmount(user, advanceQuantity);
	}
	
	@Test
	public void shouldSaveSettlementProviderPayment() throws Exception {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getSettlementPaidDate()).thenReturn(null);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		when(projectProvider.getSettlementQuantity()).thenReturn(settlementQuantity);
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(user.getBalance()).thenReturn(balance);
		setProjectTxExpectations();

		projectService.createProviderPayment(projectId, providerId, PaymentType.SETTLEMENT);
		
		verify(projectProvider, never()).setAdvancePaidDate(timestamp);
		verify(projectProvider).setSettlementPaidDate(timestamp);
		verify(transactionLogService).createProviderLog(providerId, projectId, settlementQuantity, PaymentType.SETTLEMENT, TransactionType.PAYMENT);
		verify(projectProviderRepository).save(projectProvider);
		verify(transactionApplier).substractAmount(projectFinancialData, settlementQuantity);
		verify(transactionApplier).addAmount(user, settlementQuantity);
	}
	
	@Test
	public void shouldSaveTotalProviderPayment() throws Exception {
		BigDecimal expectedAmount = new BigDecimal("7000");
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getAdvancePaidDate()).thenReturn(null);
		when(projectProvider.getSettlementPaidDate()).thenReturn(null);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		when(projectProvider.getAdvanceQuantity()).thenReturn(advanceQuantity);
		when(projectProvider.getSettlementQuantity()).thenReturn(settlementQuantity);
		setProjectTxExpectations();
		
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(user.getBalance()).thenReturn(balance);

		projectService.createProviderPayment(projectId, providerId, PaymentType.BOUTH);
		
		verify(projectProvider).setAdvancePaidDate(timestamp);
		verify(projectProvider).setSettlementPaidDate(timestamp);
		
		verify(transactionLogService).createProviderLog(providerId, projectId, expectedAmount, PaymentType.BOUTH, TransactionType.PAYMENT);
		verify(transactionLogService).createProjectLog(projectId, providerId, expectedAmount, ProjectTxType.PROVIDER_PAYMENT);
		verify(transactionApplier).substractAmount(projectFinancialData, expectedAmount);
		verify(transactionApplier).addAmount(user, expectedAmount);
	}

	private void setProjectTxExpectations() {
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getBalance()).thenReturn(balance);
		when(transactionHelper.createProjectTx()).thenReturn(projectTx);
	}
	
	@Test (expected=NonProjectProviderException.class)
	public void shouldNotSaveProviderPaymentDueToNoProvider() throws Exception {
	  when(projectRepository.findOne(projectId)).thenReturn(project);
	  when(userRepository.findOne(providerId)).thenReturn(user);
		projectService.createProviderPayment(projectId, providerId, typePayment);
	}
	
	@Test (expected=UserNotFoundException.class)
	public void shouldNotSaveProviderPaymentDueToNoProviderUserFound() throws Exception {
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getId()).thenReturn(providerId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		
		projectService.createProviderPayment(projectId, providerId, typePayment);
	}
	
	@Test (expected=AdvanceProviderPaidException.class)
	public void shouldNotSaveProviderPaymentDueToAlreadyAdvancePaid() throws Exception {
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getAdvancePaidDate()).thenReturn(timestamp);
		when(projectProvider.getSettlementDate()).thenReturn(null);
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		
		projectService.createProviderPayment(projectId, providerId, PaymentType.ADVANCE);
	}
	
	@Test (expected=AdvanceProviderPaidException.class)
	public void shouldNotSaveProviderPaymentDueToAlreadyBouthPaid() throws Exception {
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getAdvancePaidDate()).thenReturn(timestamp);
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		
		projectService.createProviderPayment(projectId, providerId, PaymentType.BOUTH);
	}
	
	@Test (expected=SettlementProviderPaidException.class)
	public void shouldNotSaveProviderPaymentDueToAlreadySettlementPaid() throws Exception {
		when(projectProviderRepository.findByProjectAndProviderId(project, providerId)).thenReturn(projectProvider);
		when(projectProvider.getAdvancePaidDate()).thenReturn(null);
		when(projectProvider.getSettlementDate()).thenReturn(timestamp);
		when(userRepository.findOne(providerId)).thenReturn(user);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		
		projectService.createProviderPayment(projectId, providerId, PaymentType.SETTLEMENT);
	}
	
	@Test
	public void shouldListProjectsByStatuses() throws Exception {
		when(integerParser.parse(statuses)).thenReturn(statusesAsList);
		when(projectRepository.findByStatusIn(statusesAsList)).thenReturn(projectList);
		assertEquals(projectList, projectService.getProjectsByStatuses(statuses));
	}
	
	@Test
	public void shouldGetProjectsByHighBalance() throws Exception {
		projectList.add(project);
		when(projectRepository.findProjectsByHigherBalance()).thenReturn(projectList);
		Set<Project> results = projectService.getProjectsByHighBalance();
		assertTrue(results.contains(project));
	}
	
	@Test
	public void shouldGetProjectsByHighBalanceAndAvoidDuplicated() throws Exception {
		projectList.add(project);
		projectList.add(project);
		when(project.getId()).thenReturn(projectId);
		when(projectRepository.findProjectsByHigherBalance()).thenReturn(projectList);
		
		assertEquals(1, projectService.getProjectsByHighBalance().size());
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotSaveProductPaymentDueToNoFunds() throws Exception {
		when(projectFinancialData.getBalance()).thenReturn(zero);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		
		projectService.createProducerPayment(projectId, producerId, payment);
	}
	
	@Test
	public void shouldSaveProducerPayment() throws Exception {
		User producer = new User();
		ProjectFinancialData projectFinancialData = new ProjectFinancialData();
		projectFinancialData.setBalance(balance);
		projectFinancialData.setId(projectId);
		producer.setName(name);
		producer.setEmail(email);
		producer.setBalance(balance);
		producer.setId(producerId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getName()).thenReturn(projectName);
		when(userRepository.findOne(producerId)).thenReturn(producer);
		when(transactionLogService.createProjectLog(projectId, producerId, payment, ProjectTxType.PRODUCER_PAYMENT)).thenReturn(transactionId);
		
		when(transactionHelper.createProjectTx()).thenReturn(projectTx);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		assertEquals(ResultType.SUCCESS, projectService.createProducerPayment(projectId, producerId, payment));
		verify(transactionApplier).substractAmount(projectFinancialData, payment);
		verify(transactionApplier).addAmount(producer, payment);
		
		verify(transactionLogService).createUserLog(projectId, producerId, null, payment, null, TransactionType.PRODUCER_PAYMENT);
		verify(messagePacker).sendAbonoCuenta(payment, projectName, email, transactionId, MessageType.PRODUCTOR);
	}
	
	@Test
	public void shouldGetTra() throws Exception {
		BigDecimal budget = new BigDecimal("100000");
		BigDecimal tri = new BigDecimal("25000");
		BigDecimal trf = new BigDecimal("10000");
		BigDecimal expectedResult = new BigDecimal("35000");
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBudget()).thenReturn(budget);
		
		assertEquals(expectedResult, projectService.getTRA(tri, trf, projectFinancialData));
	}
	
	@Test
	public void shouldExceedTra() throws Exception {
		BigDecimal budget = new BigDecimal("100000");
		BigDecimal tri = new BigDecimal("150000");
		BigDecimal trf = new BigDecimal("60000");
		BigDecimal expectedResult = new BigDecimal("200000");
		
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getBudget()).thenReturn(budget);
		
		assertEquals(expectedResult, projectService.getTRA(tri, trf, projectFinancialData));
	}
	
	@Test
	public void shouldGetProjectFinancialDataWhenTraIsNull() throws Exception {
		BigDecimal tri = new BigDecimal("150000");
		BigDecimal trf = new BigDecimal("60000");
		assertEquals(zero, projectService.getTRA(tri, trf, projectFinancialData));
	}
	
	@Test
	public void shouldSaveConsumingUnits() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId.toString());
		params.put("projectId", "1");
		BigDecimal balance = new BigDecimal("100000");
		BigDecimal total = new BigDecimal("50000");
		
		when(userRepository.findOne(userId)).thenReturn(user);
		when(unitCalculator.getTotal(params)).thenReturn(total);
		when(user.getBalance()).thenReturn(balance);
		when(unitCalculator.sufficientUnits(params)).thenReturn(true);
		when(transactionService.createUnits(userId, projectId, params, BuyingSource.FREAKFUND)).thenReturn(transactionId);
		when(projectFinancialData.getBalance()).thenReturn(balance);
		
		assertEquals(transactionId, projectService.createConsumingUnits(params));
		verify(user).setBalance(total);
		verify(userRepository).save(user);
	}
	
	@Test (expected=NonSufficientFundsException.class)
	public void shouldNotSaveConsumingUnitsDueToNonSufficientFundsException() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId.toString());
		params.put("projectId", "1");
		BigDecimal balance = new BigDecimal("50000");
		BigDecimal total = new BigDecimal("100000");
		
		when(userRepository.findOne(userId)).thenReturn(user);
		when(unitCalculator.getTotal(params)).thenReturn(total);
		when(user.getBalance()).thenReturn(balance);
		when(unitCalculator.sufficientUnits(params)).thenReturn(true);
		
		projectService.createConsumingUnits(params);
		
		verify(user, never()).setBalance(total);
		verify(userRepository, never()).save(user);
		verify(transactionService, never()).createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
	}
	
	@Test (expected=NonSufficientUnitsException.class)
	public void shouldNotSaveConsumingUnitsDueToNonSufficientUnitsException() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId.toString());
		params.put("projectId", "1");
		BigDecimal balance = new BigDecimal("100000");
		BigDecimal total = new BigDecimal("50000");
		
		when(userRepository.findOne(userId)).thenReturn(user);
		when(unitCalculator.getTotal(params)).thenReturn(total);
		when(user.getBalance()).thenReturn(balance);
		when(unitCalculator.sufficientUnits(params)).thenReturn(false);
		
		projectService.createConsumingUnits(params);
		
		verify(user, never()).setBalance(total);
		verify(userRepository, never()).save(user);
		verify(transactionService, never()).createUnits(userId, projectId, params, BuyingSource.FREAKFUND);
	}
	
	@Test
	public void shouldGetProjectsFundedByUserId() throws Exception {
		projectsFunded.add(project);
		statusList.add(autorizadoStatusAsInteger);
		when(projectCollaborator.getProjectsFundedOrInvestedByUserId(userId)).thenReturn(projectsFunded);
		when(project.getStatus()).thenReturn(autorizadoStatusAsInteger);
		
		Set<Project> result = projectService.getProjectsByStatusAndUserId(statusList, userId);
		
		assertEquals(1, result.size());
		assertTrue(result.contains(project));
	}
	
	@Test
	public void shouldNotGetAnyProjectsFundedByUserId() throws Exception {
		when(project.getStatus()).thenReturn(produccionStatusAsInteger);
		
		Set<Project> result = projectService.getProjectsByStatusAndUserId(statusList, userId);
		
		assertTrue(result.isEmpty());
	}

	@Test
	public void shouldGetByKeyword() throws Exception {
		String keywordMaxLength = "4";
		when(properties.getProperty(ApplicationState.KEYWORD_MIN_LENGHT)).thenReturn(keywordMaxLength);
		
		projectList.add(project);
		projectList.add(project1);
		projectList.add(project);
		when(projectRepository.findProjectsLikeByNameOrDescriptionOrShowground(keyword)).thenReturn(projectList);
		
		Set<Project> result = projectService.getByKeyword(keyword);
		
		assertEquals(2, result.size());
	}
	
	@Test (expected=InvalidLengthException.class)
	public void shouldValidateKeywordLength() throws Exception {
		when(properties.getProperty(ApplicationState.KEYWORD_MIN_LENGHT)).thenReturn(keywordMaxLength);
		String keyword = "123";
		projectService.getByKeyword(keyword);
	}
	
	@Test
	public void shouldDeleteProjectUnitSale() throws Exception {
	  when(projectFinancialData.getId()).thenReturn(projectId);
	  when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getStatus()).thenReturn(desarrolloStatus);
		
		projectService.changeProjectUnitSale(projectUnitSaleId);
		verify(projectUnitSaleRepository).delete(projectUnitSale);
	}
	
	@Test (expected=NotValidParameterException.class)
	public void shouldNotDeleteProjectUnitSaleDueToNotValidParameterException() throws Exception {
		projectService.changeProjectUnitSale(null);
	}
	
	@Test (expected=NonProjectUnitSaleException.class)
	public void shouldNotDeleteProjectUnitSaleDueToNonProjectUnitSaleException() throws Exception {
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(null);
		projectService.changeProjectUnitSale(projectUnitSaleId);
	}
	
	@Test (expected=OperationStatusException.class)
	public void shouldNotDeleteProjectUnitSaleDueToOperationStatusException() throws Exception {
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialData.getId()).thenReturn(projectId);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getStatus()).thenReturn(rechazadoStatusAsInteger);
		
		projectService.changeProjectUnitSale(projectUnitSaleId);
		verify(projectUnitSaleRepository).delete(projectUnitSale);
	}
	
	@Test
	public void shouldGetSections() throws Exception {
		projectUnitSales.add(projectUnitSale);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(projectFinancialData.getProjectUnitSales()).thenReturn(projectUnitSales);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectHelper.createSectionBean()).thenReturn(sectionBean);
		when(projectUnitSale.getSection()).thenReturn(sectionName);
		when(projectUnitSale.getUnit()).thenReturn(units);
		when(projectUnitSale.getId()).thenReturn(projectUnitSaleId);
		when(projectCollaborator.getTotalUnits(projectUnitSaleId)).thenReturn(totalUnits);
		
		List<SectionBean> result = projectService.getSections(projectId);
		
		verify(sectionBean).setName(sectionName);
		verify(sectionBean).setUnits(units);
		verify(sectionBean).setTotal(units + totalUnits);
		assertEquals(1, result.size());
		assertEquals(sectionBean, result.get(0));
	}
	
	@Test
	public void shouldMeasureBreakevenAndBudget() throws Exception {
		BigDecimal breakevenPlusTramaFee = new BigDecimal("50000");
		when(breakevenService.getCalculatedBreakeven(projectFinancialData)).thenReturn(breakeven);
		when(breakevenService.getTramaFee(providersAsSet)).thenReturn(tramaFee);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(budgetService.getBudget(providersAsSet)).thenReturn(budget);
		
		projectService.measureBreakevenAndBudget(projectId, providersAsSet);
		
		verify(projectFinancialData).setBreakeven(breakevenPlusTramaFee);
		verify(projectFinancialData).setBudget(budget.add(tramaFee));
		verify(projectFinancialData).setTramaFee(tramaFee);
		verify(projectRepository).save(project);
	}
	
	@Test
	public void shouldRegisterBoxOfficeSales() throws Exception {
	  BigDecimal amount = unitSale.multiply(new BigDecimal(quantity));
		params.put(sectionCode, quantity.toString());
		when(projectUnitSaleRepository.findOne(Integer.parseInt(sectionCode))).thenReturn(projectUnitSale);
		when(projectUnitSale.getUnit()).thenReturn(totalUnits);
		
		when(transactionHelper.createUnits()).thenReturn(unitsAsSet);
		when(transactionLogService.createUnitLog(projectUnitSale.getId(), quantity, 0, bulkUnitTx, TransactionType.BUYING)).thenReturn(unitTx);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(projectUnitSale.getId()).thenReturn(projectUnitSaleId);
		
		when(transactionHelper.createBulkUnit()).thenReturn(bulkUnitTx);
		
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectFinancialDataRepository.findOne(projectId)).thenReturn(projectFinancialData);
		when(projectFinancialData.getProject()).thenReturn(project);
		when(project.getId()).thenReturn(projectId);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		when(tramaAccountRepository.findByType(AdminAccountType.TRAMA)).thenReturn(tramaAccount);
		when(tramaAccount.getBalance()).thenReturn(amount);
		when(projectFinancialData.getId()).thenReturn(projectId);
		
		assertTrue(unitList.isEmpty());
		
		assertEquals(ResultType.SUCCESS, projectService.createBoxOfficeSales(params));
		
		verify(projectUnitSale).setUnit(totalUnits - quantity);
		verify(projectUnitSaleRepository).save(projectUnitSale);
		
		verify(bulkUnitTx).setTimestamp(timestamp);
		verify(bulkUnitTx).setUnits(unitsAsSet);
		assertFalse(unitsAsSet.isEmpty());
		
		verify(bulkUnitTxRepository).save(bulkUnitTx);
		
		verify(transactionApplier).addAmount(projectFinancialData, amount);
		verify(transactionApplier).substractAmount(tramaAccount, amount);
		verify(transactionLogService).createProjectLog(projectId, null, new BigDecimal(quantity).multiply(unitSale), ProjectTxType.BOX_OFFICE_SALES);
	}
	
	@Test
	public void shouldNotRegisterBoxOfficeSalesDueToQuantityZero() throws Exception {
		params.put(sectionCode, "0");
		BigDecimal expectedProjectBalannce = new BigDecimal("21800");
		BigDecimal expectedTramaBalance = new BigDecimal("8200");
		
		when(projectUnitSale.getUnit()).thenReturn(totalUnits);
		
		when(transactionHelper.createUnits()).thenReturn(unitsAsSet);
		when(transactionLogService.createUnitLog(projectUnitSale.getId(), quantity, 0, bulkUnitTx, TransactionType.BUYING)).thenReturn(unitTx);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(projectUnitSale.getId()).thenReturn(projectUnitSaleId);
		
		when(transactionHelper.createBulkUnit()).thenReturn(bulkUnitTx);
		
		when(projectUnitSale.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		when(tramaAccount.getBalance()).thenReturn(tramaBalance);
		when(projectFinancialData.getBalance()).thenReturn(balance);
		when(projectFinancialData.getId()).thenReturn(projectId);
		
		assertTrue(unitList.isEmpty());
		
		assertEquals(ResultType.SUCCESS, projectService.createBoxOfficeSales(params));
		
		verify(projectUnitSale, never()).setUnit(totalUnits - quantity);
		verify(projectUnitSaleRepository, never()).save(projectUnitSale);
		
		verify(bulkUnitTx, never()).setTimestamp(timestamp);
		verify(bulkUnitTx, never()).setUnits(unitsAsSet);
		assertTrue(unitList.isEmpty());
		
		verify(bulkUnitTxRepository, never()).save(bulkUnitTx);
		
		verify(transactionLogService, never()).createProjectLog(projectId, null, new BigDecimal(quantity).multiply(unitSale), ProjectTxType.BOX_OFFICE_SALES);
		verify(projectFinancialData, never()).setBalance(expectedProjectBalannce);
		verify(tramaAccount, never()).setBalance(expectedTramaBalance);
		verify(tramaAccountRepository, never()).save(tramaAccount);
		verify(projectFinancialDataRepository, never()).save(projectFinancialData);
	}
	
	@Test (expected=NonExistentCodeException.class)
	public void shouldNotRegisterBoxOfficeSalesDueToNoSectionCode() throws Exception {
		params.put(sectionCode, quantity.toString());
		when(projectUnitSaleRepository.findOne(sectionCodeAsInteger)).thenReturn(null);
		
		projectService.createBoxOfficeSales(params);
	}
	
	@Test (expected=NonSufficientUnitsException.class)
	public void shouldNotRegisterBoxOfficeSalesDueToNoEnoughUnits() throws Exception {
		params.put(sectionCode, quantity.toString());
		when(projectUnitSaleRepository.findOne(Integer.parseInt(sectionCode))).thenReturn(projectUnitSale);
		when(projectUnitSale.getUnit()).thenReturn(zeroAsInteger);
		
		projectService.createBoxOfficeSales(params);
	}
	
	@Test (expected=NotTramaAccountFoundException.class)
	public void shouldNotRegisterBoxOfficeSalesDueToNotTramaAccountFound() throws Exception {
		params.put(sectionCode, quantity.toString());
		when(projectUnitSaleRepository.findOne(Integer.parseInt(sectionCode))).thenReturn(projectUnitSale);
		when(projectUnitSale.getUnit()).thenReturn(totalUnits);
		
		projectService.createBoxOfficeSales(params);
	}

}
