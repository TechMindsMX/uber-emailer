package com.tim.one.collaborator;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.tim.one.bean.AccountType;

@Component
public class AccountCreator {
	
	private Integer year = Calendar.getInstance().get(Calendar.YEAR);

	public String createAccount(AccountType type, Integer id) {
		String prefix = "P";
		if (type.equals(AccountType.USER)){
			prefix = "U";
		}
		StringBuilder account = new StringBuilder();
		if (id.toString().length() < 8){
			for(int i=0; i<8-id.toString().length(); i++){
				account.append("0");
			}
		}
		if(id.toString().length() > 8){
			account.append(id.toString().substring(id.toString().length() - 8));
		} else {
			account.append(id.toString());
		}
		return prefix + year.toString().substring(2, 4) + account.toString();
	}

}
