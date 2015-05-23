package com.tim.one.service.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.EntityType;
import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.bean.PaymentType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.IntegraUserTx;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProviderTx;
import com.tim.one.model.StpLogTx;
import com.tim.one.model.TramaTx;
import com.tim.one.model.UnitTx;
import com.tim.one.model.UserTx;
import com.tim.one.repository.IntegraUserTxRepository;
import com.tim.one.repository.ProjectTxRepository;
import com.tim.one.repository.ProviderTxRepository;
import com.tim.one.repository.StpLogTxRepository;
import com.tim.one.repository.TramaTxRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.repository.UserTxRepository;
import com.tim.one.service.SecurityService;
import com.tim.one.service.TransactionLogService;
import com.tim.one.util.DateUtil;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

	@Autowired
	private TransactionHelper transactionHelper;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private ProjectTxRepository projectTxRepository;
	@Autowired
  private UserTxRepository userTxRepository;
	@Autowired
  private TramaTxRepository tramaTxRepository;
	@Autowired
  private UnitTxRepository unitTxRepository;
	@Autowired
  private ProviderTxRepository providerTxRepository;
	@Autowired
	private StpLogTxRepository stpLogTxRepository;
	@Autowired
	private IntegraUserTxRepository integraUserTxRepository;
	@Autowired
	private SecurityService securityService;
	
	private Log log = LogFactory.getLog(getClass());

	public Integer createProjectLog(Integer projectId, Integer userId, BigDecimal amount, ProjectTxType type) {
		ProjectTx projectTx = transactionHelper.createProjectTx();
		projectTx.setProjectId(projectId);
		projectTx.setUserId(userId);
		projectTx.setAmount(amount);
		projectTx.setType(type);
		projectTx.setTimestamp(dateUtil.createDateAsLong());
		projectTxRepository.save(projectTx);
		return projectTx.getId();
		
	}

	public Integer createUserLog(Integer projectId, Integer receiverId, Integer senderId, BigDecimal amount, String reference, TransactionType type) {
		UserTx userTx = transactionHelper.createUserTx();
		userTx.setProjectId(projectId);
		userTx.setReceiverId(receiverId);
		userTx.setSenderId(senderId);
		userTx.setAmount(amount);
		userTx.setTimestamp(dateUtil.createDateAsLong());
		userTx.setReference(reference);
		userTx.setType(type);
		userTxRepository.save(userTx);
		return userTx.getId();
	}
	
	public String createIntegraUserLog(String origin, String destination, BigDecimal amount, String reference, IntegraTransactionType type) {
		IntegraUserTx userTx = new IntegraUserTx();
		userTx.setOrigin(origin);
		userTx.setDestination(destination);
		userTx.setAmount(amount);
		userTx.setTimestamp(dateUtil.createDateAsLong());
		userTx.setReference(reference);
		userTx.setType(type);
		userTx.setUuid(securityService.generateKey());
		integraUserTxRepository.save(userTx);
		return userTx.getUuid();
	}

	public Integer createTramaLog(Integer entityId, BigDecimal amount, EntityType entityType, TramaAccountType type) {
		log.info("Transaction LOG. amount: " + amount + " type: " + type);
		TramaTx tramaTx = transactionHelper.createTramaTx();
		tramaTx.setAmount(amount);
		tramaTx.setEntityId(entityId);
		tramaTx.setTimestamp(dateUtil.createDateAsLong());
		tramaTx.setEntityType(entityType);
		tramaTx.setType(type);
		tramaTxRepository.save(tramaTx);
		return tramaTx.getId();
	}

	public UnitTx createUnitLog(Integer projectUnitSaleId, Integer quantity, Integer userId, BulkUnitTx bulkUnitTx, TransactionType type) {
		UnitTx unitTx = transactionHelper.createUnitTx();
		unitTx.setTimestamp(dateUtil.createDateAsLong());
		unitTx.setType(type);
		unitTx.setProjectUnitSaleId(projectUnitSaleId);
		unitTx.setQuantity(quantity);
		unitTx.setUserId(userId);
		unitTx.setBulk(bulkUnitTx);
		unitTxRepository.save(unitTx);
		return unitTx;
	}

	public void createProviderLog(Integer providerId, Integer projectId, BigDecimal amount, PaymentType paymentType, TransactionType type) {
		ProviderTx providerTx = transactionHelper.createProviderTx();
		providerTx.setProviderId(providerId);
		providerTx.setProjectId(projectId);
		providerTx.setType(TransactionType.PAYMENT);
		providerTx.setPaymentType(paymentType);
		providerTx.setTimestamp(dateUtil.createDateAsLong());
		providerTx.setAmount(amount);
		providerTxRepository.save(providerTx);
	}

	public void createStpLog(Integer id, Integer claveRastreo, Integer estado, Long timestamp, TransactionType type) {
		StpLogTx stpLogTx = transactionHelper.createStpLogTx();
		stpLogTx.setSpeiId(id);
		stpLogTx.setClaveRastreo(claveRastreo);
		stpLogTx.setEstado(estado);
		stpLogTx.setTimestamp(timestamp);
		stpLogTx.setType(type);
		stpLogTxRepository.save(stpLogTx);
	}
	
}
