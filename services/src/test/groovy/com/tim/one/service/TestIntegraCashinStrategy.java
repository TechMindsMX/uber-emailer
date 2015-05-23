package com.tim.one.service;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.IntegraTransactionType;
import com.tim.one.model.IntegraUser;
import com.tim.one.service.impl.IntegraCashinStrategy;
import com.tim.one.stp.command.IntegraCashinCommand;
import com.tim.one.stp.formatter.CommandFormatter;
import com.tim.one.stp.service.RestService;
import com.tim.one.util.DateUtil;

public class TestIntegraCashinStrategy {
	
	@InjectMocks
	private IntegraCashinStrategy integraCashinStrategy = new IntegraCashinStrategy();
	
	@Mock
	private TransactionApplier transactionApplier;
	@Mock
	private TransactionLogService transactionLogService;
	@Mock
	private RestService restService;
	@Mock
	private CommandFormatter commandFormatter;
	@Mock
	private DateUtil dateUtil;

	private IntegraUser user = new IntegraUser();
	private BigDecimal amount = new BigDecimal(100);

	private String uuid = "uuid";
	private String transactionUuid = "transactionUuid";
	private String reference = "reference";
	private String clabeOrdenante = "clabeOrdenante";
	private String json = "json";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCashIn() throws Exception {
		user.setTimoneUuid(uuid);
		when(commandFormatter.format(isA(IntegraCashinCommand.class))).thenReturn(json);
		when(transactionLogService.createIntegraUserLog(clabeOrdenante, uuid, amount, reference, IntegraTransactionType.CASH_IN)).thenReturn(transactionUuid);
		
		integraCashinStrategy.cashIn(user, amount, reference, clabeOrdenante);
		
		verify(transactionApplier).addAmount(user, amount);
		verify(restService).postForObject(json);
	}

}
