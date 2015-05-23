package com.tim.one.collaborator;

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.tim.one.bean.AdminAccountType
import com.tim.one.bean.EntityType
import com.tim.one.bean.MessageType
import com.tim.one.bean.ProjectStatusType;
import com.tim.one.bean.ProjectTxType
import com.tim.one.bean.ResultType
import com.tim.one.bean.TramaAccountType
import com.tim.one.bean.TransactionType
import com.tim.one.exception.NoFinancialDataException
import com.tim.one.exception.NonSufficientFundsException
import com.tim.one.exception.NotTramaAccountFoundException
import com.tim.one.helper.TransactionHelper
import com.tim.one.model.BulkUnitTx
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectType;
import com.tim.one.model.ProjectUnitSale
import com.tim.one.model.TramaAccount
import com.tim.one.model.TramaTx
import com.tim.one.model.TransferUserLimit
import com.tim.one.model.UnitTx
import com.tim.one.model.UserTx
import com.tim.one.packer.MessagePacker
import com.tim.one.repository.ProjectFinancialDataRepository
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.ProjectUnitSaleRepository
import com.tim.one.repository.TramaAccountRepository
import com.tim.one.repository.TramaTxRepository
import com.tim.one.repository.TransferUserLimitRepository
import com.tim.one.repository.UserRepository
import com.tim.one.repository.UserTxRepository
import com.tim.one.service.TransactionApplier
import com.tim.one.service.TransactionLogService
import com.tim.one.service.UserTransactionService
import com.tim.one.state.ApplicationState
import com.tim.one.util.DateUtil

/**
 * @author josdem
 * @understands A class who helps to complete transactions
 *
 */

@Component
public class TransactionCollaborator {

	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private TransactionHelper transactionHelper;
	@Autowired
	private RevertCollaborator revertCollaborator;
	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private UserCollaborator userCollaborator;
	@Autowired
	private UserTransactionService userTransactionService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageCollaborator messageCollaborator;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Autowired
	private UserTxRepository userTxRepository;
	@Autowired
  private TransferUserLimitRepository transferUserLimitRepository;
	@Autowired
  private TramaAccountRepository tramaAccountRepository;
	@Autowired
	private TramaTxRepository tramaTxRepository;
	@Autowired
	private TransactionApplier transactionApplier;
	
	@Autowired
  private Properties properties;
	
	private Log log = LogFactory.getLog(getClass());
	
	public void log(UserTx tx) {
	  userTxRepository.save(tx);
	}

	public void setTramaCharge(Project project) throws NotTramaAccountFoundException, NonSufficientFundsException {
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
		if(tramaAccount == null){
			throw new NotTramaAccountFoundException();
		}
		if(projectFinancialData.getBalance().compareTo(projectFinancialData.getTramaFee()) < 0){
			throw new NonSufficientFundsException(project.getId());
		}
		transactionApplier.addAmount(tramaAccount, projectFinancialData.getTramaFee());
		transactionApplier.substractAmount(projectFinancialData, projectFinancialData.getTramaFee());
		
		transactionLogService.createProjectLog(project.id, null, projectFinancialData.getTramaFee(), ProjectTxType.TRAMA_FEE);
		transactionLogService.createTramaLog(project.id, projectFinancialData.getTramaFee(), EntityType.PROJECT, TramaAccountType.PRODUCT);
	}

	public ResultType revertTramaCharge(Project project, Integer reason) throws NonSufficientFundsException {
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		if (project.getStatus().equals(new Integer(properties.getProperty(ApplicationState.PRODUCTION_STATUS)))){
			TramaTx tramaTxCredit = tramaTxRepository.findByEntityIdAndType(project.id, TramaAccountType.PRODUCT);
			TramaAccount tramaAccount = tramaAccountRepository.findByType(AdminAccountType.TRAMA);
			if(tramaAccount.getBalance().compareTo(tramaTxCredit.getAmount()) < 0){
				throw new NonSufficientFundsException(project.id);
			}
			transactionApplier.substractAmount(tramaAccount, tramaTxCredit.getAmount());
			transactionLogService.createTramaLog(project.id, tramaTxCredit.getAmount(), EntityType.PROJECT, TramaAccountType.PROJECT_REJECTED);
			
			transactionApplier.addAmount(projectFinancialData, tramaTxCredit.getAmount());
			transactionLogService.createProjectLog(project.id, null, tramaTxCredit.getAmount(), ProjectTxType.RETURN_TRAMA_FEE);
		}
		
		Project projectSnapshot = project;
		revertCollaborator.revertTransactions(project, reason);
		
		if(reason.equals(new Integer(properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_REASON)))){
			messagePacker.sendCierreProductor(projectSnapshot);
			messagePacker.sendCierreAdministrador(projectSnapshot);
		}
		return ResultType.SUCCESS; 
	}

	public void addFunds(Integer projectId, BigDecimal amount) {
		ProjectFinancialData project = projectFinancialDataRepository.findOne(projectId);
		project.setBalance(project.getBalance().add(amount));
		projectFinancialDataRepository.save(project);
	}

	public BigDecimal getProducerRevenue(BigDecimal budget) {
		Integer producerRevenuePercentage = new Integer(properties.getProperty(ApplicationState.PRODUCER_REVENUE_PERCENTAGE));
		return budget.multiply(new BigDecimal(producerRevenuePercentage)).divide(ApplicationState.ONEHUNDERT);
	}

	public void verifyBreakeven(Project project, ProjectFinancialData projectFinancialData) {
		if(projectFinancialData.getBalance().compareTo(projectFinancialData.getBreakeven()) >= 0){
			project.setStatus(ProjectStatusType.PENDING.ordinal());
			projectRepository.save(project);
			messagePacker.sendBreakevenReachedProductor(project);
			messagePacker.sendBreakevenReachedAdministrador(project);
			messageCollaborator.sendBreakevenReachedPartners(project);
		}
	}

	public Integer save(UserTx tx) {
		userTxRepository.save(tx);
		return tx.getId();
	}

	public TransferUserLimit getTransferUserLimit(Integer userId, Integer destinationId) {
		TransferUserLimit transferUserLimit = transferUserLimitRepository.findByUserIdAndDestinationId(userId, destinationId);
		return transferUserLimit == null ? transactionHelper.createTransferUserLimit() : transferUserLimit;
	}

	public Long setFundingEndDate(ProjectFinancialData projectFinancialData) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateUtil.createDate());
		c.add(Calendar.DATE, new Integer(properties.getProperty(ApplicationState.FUNDING_END_DAYS)));
		Long timestamp = c.getTime().getTime();
		projectFinancialData.setFundEndDate(timestamp);
		projectFinancialDataRepository.save(projectFinancialData);
		return timestamp;
	}

	public Integer save(TransferUserLimit transferUserLimit) {
	  transferUserLimitRepository.save(transferUserLimit);
		return transferUserLimit.getId();
	}

	public void closeProject(Project project) throws NotTramaAccountFoundException, NonSufficientFundsException {
	  ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		Set<Integer> financiers = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.FUNDING);
		for (Integer userId : financiers) {
			BigDecimal amount = userTransactionService.getRDF(userId, projectFinancialData);
			userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDF);
		}
		Set<Integer> investors = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.INVESTMENT);
		for (Integer userId : investors) {
			BigDecimal amount = userTransactionService.getRDI(userId, projectFinancialData);
			userTransactionService.measureReturnOfInvestment(userId, projectFinancialData, amount, TransactionType.RDI);
		}
		messagePacker.sendAbonoCuenta(projectFinancialData.getBalance(), project.getName(), project.getUser().getEmail(), 0, MessageType.CIERRE_PRODUCTO_PRODUCTOR);
		messagePacker.sendCierreVentaProductor(project, projectFinancialData);
	}

	public TransactionType getDescription(Set<UnitTx> units) {
		return units[0].type
	}

	public BigDecimal getAmount(Set<UnitTx> units) {
		BigDecimal amount = new BigDecimal("0"); 
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			amount = amount.add(new BigDecimal(unitTx.getQuantity()).multiply(projectUnitSale.getUnitSale()));
		}
		return amount;
	}

	public void setProjectName(BulkUnitTx bulk, Integer projectUnitSaleId) {
		if (bulk.getProjectName() == null){
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(projectUnitSaleId);
			Project project = projectRepository.findOne(projectUnitSale.getProjectFinancialData().getProject().getId());
			bulk.setProjectName(project.getName());
		}
	}

}
