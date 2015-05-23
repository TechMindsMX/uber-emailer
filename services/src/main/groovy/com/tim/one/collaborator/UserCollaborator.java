package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.AccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.UserHelper;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.UserService;

/**
 * @author josdem
 * @understands A class who knows how to retrieve an user from database 
 */

@Component
public class UserCollaborator {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserHelper userHelper;
	@Autowired
	private AccountCreator accountCreator;
	@Autowired
	private UnitCollaborator unitCollaborator;
	
  private Log log = LogFactory.getLog(getClass());
	
	public User getUserById(Integer userId) throws UserNotFoundException {
		User user = userService.getUserById(userId);
		if (user == null)throw new UserNotFoundException(userId);
		return user;
	}

	public User createUser(String email, String name) {
	  log.info("CREATING user");
		User user = userHelper.createUser();
		user.setEmail(email);
		user.setName(name);
		user.setBalance(new BigDecimal("0"));
		userRepository.save(user);
		user.setAccount(accountCreator.createAccount(AccountType.USER, user.getId()));
		userRepository.save(user);
		return user;
	}

	public Set<Integer> getPartnersByType(ProjectFinancialData projectFinancialData, TransactionType type) {
		Set<Integer> users = new HashSet<Integer>();
		List<UnitTx> units = unitCollaborator.getUnitsByProjectAndType(projectFinancialData, type);
		for (UnitTx unitTx : units) {
			users.add(unitTx.getUserId());
		}
		return users;
	}
	
}
