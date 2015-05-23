package com.tim.one.collaborator;

import org.springframework.stereotype.Component;

import com.tim.one.bean.AccountType;
import com.tim.one.state.AccountState;

/**
 * @author josdem
 * @understands It helps to identify whether is user or project account 
 * 
 */

@Component
public class AccountIdentifier {
	
	public AccountType getTypeByAccount(String account) {
		return account.startsWith(AccountState.USER_ACCOUNT_PREFIX) ? AccountType.USER : AccountType.PROJECT;
	}

}
