package com.tim.one.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionBean;
import com.tim.one.helper.TransactionManagerHelper;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

public class TestTransactionManager {

	@InjectMocks
	private TransactionFreakfundManager transactionManager = new TransactionFreakfundManager();
	
	@Mock
	private DateUtil dateUtil;
	@Mock
	private TransactionManagerHelper transactionMangerHelper;
	@Mock
	private List<TransactionBean> transactionBeans;
	@Mock
	private TransactionService transactionService;
	@Mock
	private TransactionBean transactionBean;
	@Mock
	private Properties properties;
	
	private Integer userId = 1;
	
	private Long startDate = 1L;
	private Long endDate = 2L;
	private Long parameterDate = 3L;
	
	private String end = "end";
	private String theBegginingOfTheTimes = "theBegginingOfTheTimes";
	
	private BigDecimal zero = new BigDecimal("0");
	private BigDecimal balance = new BigDecimal("1000");

	private List<TransactionBean> beansWithBalance = new ArrayList<TransactionBean>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(properties.getProperty(ApplicationState.THE_BEGGINING_OF_THE_TIMES)).thenReturn(theBegginingOfTheTimes);
	}
	
	@Test
	public void shouldGetStartingBalance() throws Exception {
		beansWithBalance.add(transactionBean);
		
		setTransactionManagerExpectations();
		
		assertEquals(balance, transactionManager.getStartingBalance(userId, end));
	}
	
	@Test
	public void shouldGetZeroAtStartingBalance() throws Exception {
		setTransactionManagerExpectations();
		
		assertEquals(zero, transactionManager.getStartingBalance(userId, end));
	}

	private void setTransactionManagerExpectations() throws ParseException {
		when(dateUtil.dateStartFormat(theBegginingOfTheTimes)).thenReturn(startDate);
		when(dateUtil.dateEndFormat(end)).thenReturn(endDate);
		when(dateUtil.restOneDay(endDate)).thenReturn(parameterDate);
		when(transactionMangerHelper.getUserTransactionBeans(userId, startDate, parameterDate)).thenReturn(transactionBeans);
		when(transactionService.setBalances(transactionBeans, zero)).thenReturn(beansWithBalance);
		when(transactionBean.getBalance()).thenReturn(balance);
	}
	
}
