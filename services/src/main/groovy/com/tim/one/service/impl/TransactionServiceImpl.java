package com.tim.one.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.BuyingSource;
import com.tim.one.bean.MessageType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectStatusType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.PurchaseTransactionBean;
import com.tim.one.bean.ResultType;
import com.tim.one.bean.StatementType;
import com.tim.one.bean.TransactionBean;
import com.tim.one.bean.TransactionType;
import com.tim.one.bean.TransferUserLimitBean;
import com.tim.one.collaborator.MessagePackerCollaborator;
import com.tim.one.collaborator.ProjectCollectionInitializer;
import com.tim.one.collaborator.ProjectUnitSaleCollaborator;
import com.tim.one.collaborator.TransactionCollaborator;
import com.tim.one.exception.AdvanceProviderPaidException;
import com.tim.one.exception.MaxTransferLimitExceedException;
import com.tim.one.exception.NonProjectProviderException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NotBulkTransactionFoundExeption;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.exception.SettlementProviderPaidException;
import com.tim.one.exception.TransferUserLimitNotExistException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.ProviderTx;
import com.tim.one.model.TransferUserLimit;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.model.UserTx;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.BulkUnitTxRepository;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectProviderRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectTxRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.ProviderTxRepository;
import com.tim.one.repository.TransferUserLimitRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.repository.UserTxRepository;
import com.tim.one.service.TransactionApplier;
import com.tim.one.service.TransactionLogService;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.AmountValidator;

/**
 * @author josdem
 * @understands A class who knows how to do transactions
 * 
 */

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionCollaborator transactionCollaborator;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private TransactionHelper transactionHelper;
	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private MessagePackerCollaborator messagePackerCollaborator;
	@Autowired
	private ProjectUnitSaleCollaborator projectUnitsaleCollaborator;
	@Autowired
	private TransactionApplier transactionApplier;
	@Autowired
	private AmountValidator amountValidator;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	@Autowired
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectProviderRepository projectProviderRepository;
	@Autowired
	private TransferUserLimitRepository transferUserLimitRepository;
	@Autowired
	private BulkUnitTxRepository bulkUnitTxRepository;
	@Autowired
	private UserTxRepository userTxRepository;
	@Autowired
	private ProviderTxRepository providerTxRepository;
	@Autowired
	private UnitTxRepository unitTxRepository;
	@Autowired
	private ProjectTxRepository projectTxRepository;
	@Autowired
	private ProjectCollectionInitializer projectCollectionInitializer;
	
	@Autowired
  @Qualifier("properties")
  private Properties properties;
	
	private Log log = LogFactory.getLog(getClass());
	
	public Integer transferFunds(Integer senderId, Integer receiverId, BigDecimal amount) throws UserNotFoundException, NonSufficientFundsException, NotValidParameterException, MaxTransferLimitExceedException {
		if (senderId == receiverId){
			throw new NotValidParameterException(senderId);
		}
		
		User sender = userRepository.findOne(senderId);
		User receiver = userRepository.findOne(receiverId);
		
		if (sender == null){
			throw new UserNotFoundException(senderId);
		}
		
		if (receiver == null){
			throw new UserNotFoundException(receiverId);
		}
		
		if (!transactionApplier.hasFunds(sender, amount)){
			throw new NonSufficientFundsException(senderId);
		}
		
		TransferUserLimit transferUserLimit = transactionCollaborator.getTransferUserLimit(senderId, receiverId);
		if (transferUserLimit.getAmount().compareTo(amount) < 0){
			throw new MaxTransferLimitExceedException();
		}
		
		transactionApplier.addAmount(receiver, amount);
		transactionApplier.substractAmount(sender, amount);
		Integer userTxId = transactionLogService.createUserLog(null, receiverId, senderId, amount, null, TransactionType.TRANSFER);
		
		messagePacker.sendAbonoCuenta(amount, sender.getName(), receiver.getEmail(), userTxId, MessageType.TRASPASO_CUENTA_BENEFICIARIO);
		messagePacker.sendAbonoCuenta(amount, receiver.getName(), sender.getEmail(), userTxId, MessageType.TRASPASO_CUENTA_ORDENANTE);
		
		return userTxId;
	}

	public Integer createUnits(Integer userId, Integer projectId, Map<String, String> params, BuyingSource source) {
		Set<UnitTx> units = transactionHelper.createUnits();
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
      throw new ProjectNotExistException(projectId);
    }
		
		projectCollectionInitializer.initializeCollections(project);
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		BulkUnitTx bulkUnitTx = transactionHelper.createBulkUnit();
		for (Map.Entry<String, String> mp : params.entrySet()) {
			ProjectUnitSale projectUnitSale = projectUnitsaleCollaborator.getProjectUnitsale(Integer.parseInt(mp.getKey()), projectFinancialData.getProjectUnitSales()); 
			projectUnitSale.setUnit(projectUnitSale.getUnit() - Integer.parseInt(mp.getValue()));
			projectUnitSaleRepository.save(projectUnitSale);

			BigDecimal amount = projectUnitSale.getUnitSale().multiply(new BigDecimal(mp.getValue()));
			projectFinancialData.setBalance(projectFinancialData.getBalance().add(amount));
			projectFinancialDataRepository.save(projectFinancialData);
			
			TransactionType transactionType = TransactionType.FUNDING;
			if(project.getStatus().equals(ProjectStatusType.AUTORIZED.ordinal())){
				transactionLogService.createProjectLog(projectId, userId, amount, ProjectTxType.FUNDING);
				transactionCollaborator.verifyBreakeven(project, projectFinancialData);
			} else {
				transactionLogService.createProjectLog(projectId, userId, amount, ProjectTxType.INVESTMENT);
				transactionType = TransactionType.INVESTMENT;
			}
			
			UnitTx unitTx = transactionLogService.createUnitLog(projectUnitSale.getId(), Integer.parseInt(mp.getValue()), userId, bulkUnitTx, transactionType);
			units.add(unitTx);
		}
		bulkUnitTx.setTimestamp(dateUtil.createDateAsLong());
		bulkUnitTx.setUnits(units);
		
		bulkUnitTxRepository.save(bulkUnitTx);
		Integer transactionId = bulkUnitTx.getId();
		if(source.equals(BuyingSource.FREAKFUND)){
			messagePacker.sendInversionFinanciamiento(userId, project, units, transactionId);
		} else {
			User user = userRepository.findOne(userId);
			messagePacker.sendAbonoCuenta(messagePackerCollaborator.getAmount(units), user.getName(), user.getEmail(), transactionId, MessageType.REDENCION);
		}
		log.info("4");
		return transactionId;
	}
	
	public BigDecimal getAmountByUser(TransactionType type, Project project, Integer userId) {
		BigDecimal fundedAmount = new BigDecimal("0");
		List<UnitTx> units = unitTxRepository.findUnitsByTypeAndUserId(type, userId);
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			if(projectUnitSale.getProjectFinancialData().getProject().equals(project)){
				fundedAmount = fundedAmount.add(projectUnitSale.getUnitSale().multiply(new BigDecimal(unitTx.getQuantity())));
			}
		}
		return fundedAmount;
	}

	public List<PurchaseTransactionBean> getTransactions(Integer transactionId) throws NotBulkTransactionFoundExeption {
		BulkUnitTx bulk = bulkUnitTxRepository.findById(transactionId);
		if (bulk == null){
			throw new NotBulkTransactionFoundExeption(transactionId);
		}
		
		projectCollectionInitializer.initializeCollections(bulk);
		List<PurchaseTransactionBean> beans = new ArrayList<PurchaseTransactionBean>();
		for (UnitTx unit : bulk.getUnits()) {
			PurchaseTransactionBean bean = new PurchaseTransactionBean();
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unit.getProjectUnitSaleId());
			bean.setProjectId(projectUnitSale.getProjectFinancialData().getId());
			bean.setSection(projectUnitSale.getSection());
			bean.setUnitSale(projectUnitSale.getUnitSale());
			bean.setQuantity(unit.getQuantity());
			beans.add(bean);
		}
		return beans;
	}

	public ResultType createProviderPartnership(Integer providerId, Integer projectId, PaymentType paymentType) throws NonProjectProviderException, AdvanceProviderPaidException, SettlementProviderPaidException {
  	  Project project = projectRepository.findOne(projectId);
      if (project == null) {
        throw new ProjectNotExistException(projectId);
      }
      
      User provider = userRepository.findOne(providerId);
      if (provider == null) {
        throw new UserNotFoundException(providerId);
      }
      
			ProjectProvider projectProvider = projectProviderRepository.findByProjectAndProviderId(project, providerId);
			
			if (projectProvider == null) {
				throw new NonProjectProviderException(providerId);
			} else if (paymentType.equals(PaymentType.ADVANCE) && projectProvider.getAdvancePaidDate() != null){
				throw new AdvanceProviderPaidException(providerId);
			} else if (paymentType.equals(PaymentType.SETTLEMENT) && projectProvider.getSettlementPaidDate() != null){
				throw new SettlementProviderPaidException(providerId);
			}
			
			BigDecimal amount = new BigDecimal("0");
			
			switch (paymentType){
			case BOUTH:
				amount = amount.add(projectProvider.getAdvanceQuantity()).add(projectProvider.getSettlementQuantity());
				projectProvider.setAdvanceFundingDate(dateUtil.createDateAsLong());
				projectProvider.setSettlementFundingDate(dateUtil.createDateAsLong());
				break;
			case ADVANCE:
				amount = projectProvider.getAdvanceQuantity();
				projectProvider.setAdvanceFundingDate(dateUtil.createDateAsLong());
				break;
			case SETTLEMENT:
				amount = projectProvider.getSettlementQuantity();
				projectProvider.setSettlementFundingDate(dateUtil.createDateAsLong());
				break;	
			}
			
			transactionLogService.createProviderLog(providerId, projectId, amount, paymentType, TransactionType.FUNDING);
			transactionLogService.createProjectLog(projectId, providerId, amount, ProjectTxType.PROVIDER_PARTNERSHIP);
			
			transactionCollaborator.addFunds(projectId, amount);
			projectProviderRepository.save(projectProvider);
			return ResultType.SUCCESS;
	}

	public ResultType createProducerPartnership(Integer projectId) throws UserNotFoundException {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
      throw new ProjectNotExistException(projectId);
    }
		
		User producer = userRepository.findOne(project.getUser().getId());
		if (producer == null){
			throw new UserNotFoundException(project.getUser().getId());
		}
		
		ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findOne(projectId);
		BigDecimal amount = transactionCollaborator.getProducerRevenue(projectFinancialData.getBudget());
		transactionApplier.addAmount(projectFinancialData, amount);
		transactionLogService.createProjectLog(projectId, project.getUser().getId(), amount, ProjectTxType.PRODUCER_PARTNERSHIP);
		return ResultType.SUCCESS;
	}

	public ResultType createProducerFunding(Integer projectId, BigDecimal amount) throws UserNotFoundException, NonSufficientFundsException {
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
      throw new ProjectNotExistException(projectId);
    }

		User producer = userRepository.findOne(project.getUser().getId());
		if (producer == null){
			throw new UserNotFoundException(project.getUser().getId());
		}
		
		if (producer.getBalance().compareTo(amount) < 0){
			throw new NonSufficientFundsException(project.getUser().getId());
		}
		
		producer.setBalance(producer.getBalance().subtract(amount));
		userRepository.save(producer);
		
		ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findOne(projectId);
		transactionApplier.addAmount(projectFinancialData, amount);
		transactionLogService.createProjectLog(projectId, project.getUser().getId(), amount, ProjectTxType.PRODUCER_FUNDING);
		return ResultType.SUCCESS;
	}

	public Integer measureAmountToTransfer(Integer userId, Integer destinationId, BigDecimal amount) throws UserNotFoundException, NotValidParameterException {
		if (userId == destinationId){
			throw new NotValidParameterException(destinationId);
		}
		User user = userRepository.findOne(userId);
		User targetUser = userRepository.findOne(destinationId);
		
		if (user == null){
			throw new UserNotFoundException(userId);
		}
		
		if (targetUser == null){
			throw new UserNotFoundException(destinationId);
		}
		
		if (!amountValidator.isValid(amount)){
		  throw new NotValidParameterException(destinationId);
		}
		TransferUserLimit transferUserLimit = transactionCollaborator.getTransferUserLimit(userId, destinationId);
		transferUserLimit.setUserId(userId);
		transferUserLimit.setDestinationId(destinationId);
		transferUserLimit.setAmount(amount);
		transferUserLimit.setTimestamp(dateUtil.createDateAsLong());
		return transactionCollaborator.save(transferUserLimit);
	}

	public List<TransferUserLimitBean> getLimitAmountsToTransfer(Integer userId) {
		List<TransferUserLimitBean> beans = new ArrayList<TransferUserLimitBean>();
		List<TransferUserLimit> transferUserLimits = transferUserLimitRepository.findByUserId(userId);
		for (TransferUserLimit transferUserLimit : transferUserLimits) {
			TransferUserLimitBean bean = transactionHelper.createTransferUserLimitBean();
			bean.setDestinationId(transferUserLimit.getDestinationId());
			bean.setId(transferUserLimit.getId());
			User user = userRepository.findOne(transferUserLimit.getDestinationId());
			bean.setAccount(user.getAccount());
			bean.setAmount(transferUserLimit.getAmount());
			bean.setName(user.getName());
			bean.setEmail(user.getEmail());
			beans.add(bean);
		}
		return beans;
	}

	public ResultType changeLimitAmountToTransfer(Integer userId, Integer destinationId) throws TransferUserLimitNotExistException{
	  TransferUserLimit transferUserLimit = transferUserLimitRepository.findByUserIdAndDestinationId(userId, destinationId);
	  if(transferUserLimit == null){
	    throw new TransferUserLimitNotExistException(userId, destinationId);
	  }
	  transferUserLimitRepository.delete(transferUserLimit);
    return ResultType.SUCCESS;
	}

	public UserTx getUserTransaction(Integer transactionId) {
		return userTxRepository.findById(transactionId);
	}

	public List<TransactionBean> getUserTransactions(Integer userId, Long start, Long end) {
		List<UserTx> units = userTxRepository.findByUserAndDate(userId, start, end);
		List<TransactionBean> beans = new ArrayList<TransactionBean>();
		for (UserTx userTx : units) {
			TransactionBean bean = transactionHelper.createTransactionBean();
			bean.setReference(ApplicationState.USER_TX_PREFIX + userTx.getId());
			bean.setDescription(userTx.getType().toString());
			bean.setTimestamp(userTx.getTimestamp());
			bean.setAmount(userTx.getAmount());
			if(userTx.getType().equals(TransactionType.CREDIT)){
				bean.setType(StatementType.CREDIT);
			} else {
				if(userTx.getType().equals(TransactionType.TRANSFER) && userTx.getReceiverId().equals(userId)){
					bean.setType(StatementType.CREDIT);
				} else if (userTx.getType().equals(TransactionType.RETURN_FUNDS)){
					bean.setType(StatementType.CREDIT);
				} else if (userTx.getType().equals(TransactionType.RDF)){
					bean.setType(StatementType.CREDIT);
					Project project = projectRepository.findOne(userTx.getProjectId());
					bean.setProjectName(project.getName());
				} else if (userTx.getType().equals(TransactionType.RDI)){
					bean.setType(StatementType.CREDIT);
					Project project = projectRepository.findOne(userTx.getProjectId());
					bean.setProjectName(project.getName());
				} else if (userTx.getType().equals(TransactionType.PRODUCER_PAYMENT)){
					bean.setType(StatementType.CREDIT);
					Project project = projectRepository.findOne(userTx.getProjectId());
					bean.setProjectName(project.getName());
				} else if (userTx.getType().equals(TransactionType.PROVIDER_RETURN_CAPITAL)){
					bean.setType(StatementType.CREDIT);
					Project project = projectRepository.findOne(userTx.getProjectId());
					bean.setProjectName(project.getName());
				} else if (userTx.getType().equals(TransactionType.CASH_IN)){
					bean.setType(StatementType.CREDIT);
				} else if (userTx.getType().equals(TransactionType.TRANSFER_FROM_TRAMA)){
					bean.setType(StatementType.CREDIT);	
				} else {
					bean.setType(StatementType.DEBIT);
				}
			}
			beans.add(bean);
		}
		return beans;
	}
	
	public List<TransactionBean> getProviderTransactions(Integer userId, Long start, Long end) {
		List<ProviderTx> providerTxs = providerTxRepository.findByUserAndDate(userId, start, end);
		List<TransactionBean> beans = new ArrayList<TransactionBean>();
		if(providerTxs != null){
			for (ProviderTx providerTx : providerTxs) {
				TransactionBean bean = transactionHelper.createTransactionBean();
				bean.setReference(ApplicationState.PROVIDER_TX_PREFIX + providerTx.getId());
				bean.setDescription(providerTx.getType().toString());
				bean.setTimestamp(providerTx.getTimestamp());
				bean.setAmount(providerTx.getAmount());
				Project project = projectRepository.findOne(providerTx.getProjectId());
				bean.setProjectName(project.getName());
				if(providerTx.getType().equals(TransactionType.FUNDING)){
					bean.setType(StatementType.DEBIT);
				} else {
					bean.setType(StatementType.CREDIT);
				}
				beans.add(bean);
			}
		}
		return beans;
	}

	public List<TransactionBean> setBalances(List<TransactionBean> beans, BigDecimal balance) {
		Collections.sort(beans);
		for (TransactionBean bean : beans) {
			if(bean.getType().equals(StatementType.CREDIT)){
				balance = balance.add(bean.getAmount());
			} else {
				balance = balance.subtract(bean.getAmount());
			}
			bean.setBalance(balance);
		}
		return beans;
	}

	public List<TransactionBean> getUnitTransactions(Integer userId, Long startDate, Long endDate) {
		Set<BulkUnitTx> bulks = new HashSet<BulkUnitTx>();
		List<UnitTx> units = unitTxRepository.findByUserAndDate(userId, startDate, endDate);
		List<TransactionBean> beans = new ArrayList<TransactionBean>();
		for (UnitTx unitTx : units) {
			log.info("GETTING transaction from unit: " + unitTx.getId());
			BulkUnitTx bulk = unitTx.getBulk();
			transactionCollaborator.setProjectName(bulk, unitTx.getProjectUnitSaleId());
			bulks.add(bulk);
		}
		for (BulkUnitTx bulk : bulks) {
			TransactionBean bean = transactionHelper.createTransactionBean();
			bean.setReference(ApplicationState.UNIT_TX_PREFIX + bulk.getId());
			bean.setBulkId(bulk.getId());
			bean.setDescription(transactionCollaborator.getDescription(bulk.getUnits()).toString());
			bean.setTimestamp(bulk.getTimestamp());
			bean.setAmount(transactionCollaborator.getAmount(bulk.getUnits()));
			bean.setType(StatementType.DEBIT);
			bean.setProjectName(bulk.getProjectName());
			beans.add(bean);
		}
		return beans;
	}

	public List<TransactionBean> getBulkTransactions(Integer bulkId) {
		BulkUnitTx bulk = bulkUnitTxRepository.findById(bulkId);
		List<TransactionBean> beans = new ArrayList<TransactionBean>();
		for (UnitTx unitTx : bulk.getUnits()) {
			TransactionBean bean = transactionHelper.createTransactionBean();
			bean.setReference(ApplicationState.UNIT_TX_PREFIX + unitTx.getId());
			bean.setBulkId(bulk.getId());
			bean.setDescription(unitTx.getType().toString());
			bean.setTimestamp(unitTx.getTimestamp());
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			bean.setAmount(new BigDecimal(unitTx.getQuantity()).multiply(projectUnitSale.getUnitSale()));
			bean.setType(StatementType.DEBIT);
			beans.add(bean);
		}
		return beans;
	}

	public List<TransactionBean> getProjectTransactions(Integer projectId) {
		List<ProjectTx> txs = projectTxRepository.getByProjectId(projectId);
		List<TransactionBean> beans = new ArrayList<TransactionBean>();
		for (ProjectTx projectTx : txs) {
			TransactionBean bean = transactionHelper.createTransactionBean();
			bean.setReference(ApplicationState.PROJECT_TX_PREFIX + projectTx.getId());
			bean.setDescription(projectTx.getType().toString());
			bean.setTimestamp(projectTx.getTimestamp());
			bean.setAmount(projectTx.getAmount());
			switch (projectTx.getType()) {
			case PRODUCER_PARTNERSHIP:
				bean.setType(StatementType.CREDIT);
				break;
			case PRODUCER_PAYMENT:
				bean.setType(StatementType.DEBIT);
				break;
			case PRODUCER_FUNDING:
				bean.setType(StatementType.CREDIT);
				break;
			case RETURN_FUNDS:
				bean.setType(StatementType.DEBIT);
				break;
			case RETURN_TRAMA_FEE:
				bean.setType(StatementType.CREDIT);
				break;
			case REDEEM_OUT:
				bean.setType(StatementType.DEBIT);
				break;
			case REDEEM_IN:
				bean.setType(StatementType.CREDIT);
				break;
			case FUNDING:
				bean.setType(StatementType.CREDIT);
				break;
			case INVESTMENT:
				bean.setType(StatementType.CREDIT);
				break;
			case TRANSFER_FROM_TRAMA:
				bean.setType(StatementType.CREDIT);
				break;
			case PROVIDER_PAYMENT:
				bean.setType(StatementType.DEBIT);
				break;
			case PROVIDER_PARTNERSHIP:
				bean.setType(StatementType.CREDIT);
				break;
			case PROVIDER_RETURN_CAPITAL:
				bean.setType(StatementType.DEBIT);
				break;
			case TRAMA_FEE:
				bean.setType(StatementType.DEBIT);
				break;
			case BOX_OFFICE_SALES:
				bean.setType(StatementType.CREDIT);
				break;
			case RDI:
				bean.setType(StatementType.DEBIT);
				break;
			case RDF:
				bean.setType(StatementType.DEBIT);
				break;	
			default:
				bean.setType(StatementType.DEBIT);
			}
			beans.add(bean);
		}
		return beans;
	}

	public ResultType createReturnOfCapitalInjection(Integer projectId, Integer providerId, BigDecimal amount) throws NonSufficientFundsException, ProjectNotExistException {
		ProjectFinancialData project = projectFinancialDataRepository.findOne(projectId);
		if (project == null) {
      throw new ProjectNotExistException(projectId);
    }
		
		User provider = userRepository.findOne(providerId);
		if (provider == null){
      throw new UserNotFoundException(providerId);
    }
		
		if(project.getBalance().compareTo(amount) < 0){
			throw new NonSufficientFundsException(projectId);
		}
		
		transactionApplier.substractAmount(project, amount);
		transactionApplier.addAmount(provider, amount);
		transactionLogService.createProjectLog(projectId, providerId, amount, ProjectTxType.PROVIDER_RETURN_CAPITAL);
		transactionLogService.createUserLog(projectId, providerId, null, amount, null, TransactionType.PROVIDER_RETURN_CAPITAL);
		return ResultType.SUCCESS;
	}
	
}
