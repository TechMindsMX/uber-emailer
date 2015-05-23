package com.tim.one.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionBean;
import com.tim.one.service.TransactionService;

public class TestTransactionManagerHelper {

	@InjectMocks
	private TransactionManagerHelper transactionManagerHelper = new TransactionManagerHelper();
	
	@Mock
	private TransactionService transactionService;
	@Mock
	private TransactionBean transactionBean;
	@Mock
	private TransactionBean otherTransactionBean;
	@Mock
	private TransactionBean providerTransactionBean;
	@Mock
	private List<TransactionBean> projectTransactionBeans;
	
	private Integer userId = 1;
	private Integer projectId = 2;

	private Long startDate = 1L;
	private Long endDate = 2L;
	
	private List<TransactionBean> userTransactionBeans = new ArrayList<TransactionBean>();
	private List<TransactionBean> unitTransactionBeans = new ArrayList<TransactionBean>();
	private List<TransactionBean> providerTransactionBeans = new ArrayList<TransactionBean>();


	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetTransactionBeans() throws Exception {
		List<TransactionBean> expectedBean = new ArrayList<TransactionBean>();
		expectedBean.add(transactionBean);
		expectedBean.add(otherTransactionBean);
		expectedBean.add(providerTransactionBean);
		
		userTransactionBeans.add(transactionBean);
		unitTransactionBeans.add(otherTransactionBean);
		providerTransactionBeans.add(providerTransactionBean);
		
		when(transactionService.getUserTransactions(userId, startDate, endDate)).thenReturn(userTransactionBeans);
		when(transactionService.getUnitTransactions(userId, startDate, endDate)).thenReturn(unitTransactionBeans);
		when(transactionService.getProviderTransactions(userId, startDate, endDate)).thenReturn(providerTransactionBeans);
		
		List<TransactionBean> result = transactionManagerHelper.getUserTransactionBeans(userId, startDate, endDate);
		
		assertEquals(3, result.size());
		assertEquals(transactionBean, result.get(0));
		assertEquals(otherTransactionBean, result.get(1));
		assertEquals(providerTransactionBean, result.get(2));
	}
	
	@Test
	public void shouldGetProjectTransactionBeans() throws Exception {
		when(transactionService.getProjectTransactions(projectId)).thenReturn(projectTransactionBeans);
		assertEquals(projectTransactionBeans, transactionManagerHelper.getProjectTransactionBeans(projectId));
	}

}
