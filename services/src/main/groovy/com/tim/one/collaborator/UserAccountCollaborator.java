package com.tim.one.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.STPConsumerHelper;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;

@Component
public class UserAccountCollaborator {
	
	@Autowired
	private STPConsumerHelper stpHelper;

	public UserAccount getUserAccount(User user) {
		return user.getUserAccount() == null ? stpHelper.createUserAccount() : user.getUserAccount();
	}
	
}
