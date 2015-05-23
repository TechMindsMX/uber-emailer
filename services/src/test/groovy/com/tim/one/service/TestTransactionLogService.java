package com.tim.one.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.EntityType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TramaAccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.helper.TransactionHelper;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.StpLogTx;
import com.tim.one.model.TramaTx;
import com.tim.one.model.UnitTx;
import com.tim.one.model.UserTx;
import com.tim.one.repository.ProjectTxRepository;
import com.tim.one.repository.ProviderTxRepository;
import com.tim.one.repository.StpLogTxRepository;
import com.tim.one.repository.TramaTxRepository;
import com.tim.one.repository.UnitTxRepository;
import com.tim.one.repository.UserTxRepository;
import com.tim.one.service.impl.TransactionLogServiceImpl;
import com.tim.one.util.DateUtil;


public class TestTransactionLogService {

	@InjectMocks
	private TransactionLogService transactionLogService = new TransactionLogServiceImpl();
	
	@Mock
	private TransactionHelper transactionHelper;
	@Mock
	private ProjectTx projectTx;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private UserTx userTx;
	@Mock
	private TramaTx tramaTx;
	@Mock
	private UnitTx unitTx;
	@Mock
	private StpLogTx stpLogTx;
	@Mock
  private ProjectTxRepository projectTxRepository;
  @Mock
  private UserTxRepository userTxRepository;
  @Mock
  private TramaTxRepository tramaTxRepository;
  @Mock
  private UnitTxRepository unitTxRepository;
  @Mock
  private ProviderTxRepository providerTxRepository;
  @Mock
  private StpLogTxRepository stpLogTxRepository;
  @Mock
  private BulkUnitTx bulkUnitTx;
	
	private Integer projectId = 1;
	private Integer providerId = 2;
	private Integer projectUnitSaleId = 3;
	private Integer quantity = 4;
	private Integer userId = 5;
	private Integer speiId = 6;
	private Integer claveRastreo = 7;
	private Integer senderId = 8;
	
	private Long timestamp = 1L;

	private BigDecimal amount = new BigDecimal("9500");

	private Integer estado;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
	}

	@Test
	public void shouldCreateProjectLog() throws Exception {
		Integer projectTxId = 1;
		when(transactionHelper.createProjectTx()).thenReturn(projectTx);
		when(projectTx.getId()).thenReturn(projectTxId);
		
		Integer result = transactionLogService.createProjectLog(projectId, providerId, amount, ProjectTxType.PROVIDER_RETURN_CAPITAL);
		
		verify(projectTx).setAmount(amount);
		verify(projectTx).setUserId(providerId);
		verify(projectTx).setProjectId(projectId);
		verify(projectTx).setTimestamp(timestamp);
		verify(projectTx).setType(ProjectTxType.PROVIDER_RETURN_CAPITAL);
		verify(projectTxRepository).save(projectTx);
		assertEquals(projectTxId, result);
	}
	
	@Test
	public void shouldCreateUserLog() throws Exception {
		when(transactionHelper.createUserTx()).thenReturn(userTx);
		
		transactionLogService.createUserLog(projectId, providerId, senderId, amount, null, TransactionType.PROVIDER_RETURN_CAPITAL);
		
		verify(userTx).setReceiverId(providerId);
		verify(userTx).setProjectId(projectId);
		verify(userTx).setSenderId(senderId);
		verify(userTx).setAmount(amount);
		verify(userTx).setTimestamp(timestamp);
		verify(userTx).setType(TransactionType.PROVIDER_RETURN_CAPITAL);
		verify(userTxRepository).save(userTx);
	}
	
	@Test
	public void shouldCreateTramaLog() throws Exception {
		Integer tramaTxId = 1;
		when(transactionHelper.createTramaTx()).thenReturn(tramaTx);
		when(tramaTx.getId()).thenReturn(tramaTxId);
		
		Integer result = transactionLogService.createTramaLog(projectId, amount, EntityType.PROJECT, TramaAccountType.INVESTMENT);
		
		verify(tramaTx).setAmount(amount);
		verify(tramaTx).setEntityId(projectId);
		verify(tramaTx).setTimestamp(timestamp);
		verify(tramaTx).setEntityType(EntityType.PROJECT);
		verify(tramaTx).setType(TramaAccountType.INVESTMENT);
		verify(tramaTxRepository).save(tramaTx);
		assertEquals(tramaTxId, result);
	}
	
	@Test
	public void shouldCreateUnitLog() throws Exception {
		when(transactionHelper.createUnitTx()).thenReturn(unitTx);
		
		UnitTx result = transactionLogService.createUnitLog(projectUnitSaleId, quantity, userId, bulkUnitTx, TransactionType.BUYING);
		
		verify(unitTx).setTimestamp(timestamp);
		verify(unitTx).setType(TransactionType.BUYING);
		verify(unitTx).setProjectUnitSaleId(projectUnitSaleId);
		verify(unitTx).setQuantity(quantity);
		verify(unitTx).setUserId(userId);
		verify(unitTxRepository).save(unitTx);
		assertEquals(unitTx, result);
	}
	
	@Test
	public void shouldCreateUnitLogAsFunding() throws Exception {
		when(transactionHelper.createUnitTx()).thenReturn(unitTx);
		transactionLogService.createUnitLog(projectUnitSaleId, quantity, userId, bulkUnitTx, TransactionType.FUNDING);
		verify(unitTx).setType(TransactionType.FUNDING);
		verify(unitTxRepository).save(unitTx);
	}
	
	@Test
	public void shouldCreateStpLog() throws Exception {
		when(transactionHelper.createStpLogTx()).thenReturn(stpLogTx);
		
		transactionLogService.createStpLog(speiId, claveRastreo, estado, timestamp, TransactionType.STP_STATUS);
		
		verify(stpLogTx).setSpeiId(speiId);
		verify(stpLogTx).setClaveRastreo(claveRastreo);
		verify(stpLogTx).setEstado(estado);
		verify(stpLogTx).setTimestamp(timestamp);
		verify(stpLogTx).setType(TransactionType.STP_STATUS);
		
		verify(stpLogTxRepository).save(stpLogTx);
	}

}
