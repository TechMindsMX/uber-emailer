package com.tim.one.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.TransactionBean;
import com.tim.one.service.TransactionService;

@Component
public class TransactionManagerHelper {
	
	@Autowired
	private TransactionService transactionService;
	
	public List<TransactionBean> getUserTransactionBeans(Integer userId, Long start, Long end) {
		List<TransactionBean> userBeans = transactionService.getUserTransactions(userId, start, end);
		List<TransactionBean> unitBeans = transactionService.getUnitTransactions(userId, start, end);
		List<TransactionBean> providerBeans = transactionService.getProviderTransactions(userId, start, end);
		userBeans.addAll(unitBeans);
		userBeans.addAll(providerBeans);
		return userBeans;
	}

	public List<TransactionBean> getProjectTransactionBeans(Integer projectId) {
		return transactionService.getProjectTransactions(projectId);
	}

}
