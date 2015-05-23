package com.tim.one.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.TransactionBean;
import com.tim.one.helper.TransactionManagerHelper;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

@Service
public class TransactionFreakfundManager {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private TransactionManagerHelper transactionMangerHelper;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	private BigDecimal zero = new BigDecimal("0");

	public BigDecimal getStartingBalance(Integer userId, String endDate) throws ParseException {
		Long start = dateUtil.dateStartFormat(properties.getProperty(ApplicationState.THE_BEGGINING_OF_THE_TIMES));
		Long end = dateUtil.dateEndFormat(endDate);
		List<TransactionBean> transactionBeans = transactionMangerHelper.getUserTransactionBeans(userId, start, dateUtil.restOneDay(end));
		List<TransactionBean> beansWithBalances = transactionService.setBalances(transactionBeans, zero);
		return beansWithBalances.isEmpty() ? zero : beansWithBalances.get(beansWithBalances.size()-1).getBalance();
	}

}
