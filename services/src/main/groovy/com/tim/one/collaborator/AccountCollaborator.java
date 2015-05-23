package com.tim.one.collaborator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.model.BankCode;
import com.tim.one.repository.BankCodeRepository;

@Component
public class AccountCollaborator {
	
	@Autowired
	private BankCodeRepository bankDao;

	public String getBankName(Integer code) {
		BankCode bankCode = bankDao.findByBankCode(code);
		return bankCode == null ? StringUtils.EMPTY : bankCode.getName();
	}

}
