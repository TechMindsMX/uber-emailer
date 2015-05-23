package com.tim.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.ResultType;
import com.tim.one.collaborator.AccountCollaborator;
import com.tim.one.collaborator.UserAccountCollaborator;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;
import com.tim.one.packer.CommonMessagePacker;
import com.tim.one.repository.UserAccountRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.STPAccountService;
import com.tim.one.stp.exception.DuplicatedAccountException;
import com.tim.one.stp.exception.InvalidClabeException;
import com.tim.one.stp.service.STPClabeService;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.stp.validator.ClabeValidator;

@Service
public class STPAccountServiceImpl implements STPAccountService {
	
	@Autowired
	private ClabeValidator clabeValidator;
	@Autowired
	private CommonMessagePacker messagePacker;
	@Autowired
	private AccountCollaborator accountCollaborator;
	@Autowired
	private STPClabeService stpClabeService;
	@Autowired
  private UserAccountRepository userAccountRepository;
	@Autowired
  private UserRepository userRepository;
	@Autowired
	private UserAccountCollaborator userAccountCollaborator;
	
	public ResultType createAccout(Integer userId, Integer bankCode, String clabe) throws UserNotFoundException, DuplicatedAccountException, InvalidClabeException {
		User user = userRepository.findOne(userId);
		if (user == null){
			throw new UserNotFoundException(userId);
		}
		
		if (userRepository.findUserByClabe(clabe) != null){
			throw new DuplicatedAccountException(clabe);
		}
		
		if (!clabeValidator.isValid(clabe)){
			throw new InvalidClabeException(clabe);
		}
		
		UserAccount userAccount = userAccountCollaborator.getUserAccount(user);
		userAccount.setBankCode(bankCode);
		userAccount.setClabe(clabe);
		userAccount.setStpClabe(stpClabeService.generateSTPAccount(user.getId(), ApplicationState.STP_FREAKFUND_PREFIX));
		user.setUserAccount(userAccount);
		userAccount.setUser(user);
		userRepository.save(user);
		
		messagePacker.sendCreacionCuenta(userAccount.getStpClabe(), accountCollaborator.getBankName(bankCode), user.getEmail());
		
		return ResultType.SUCCESS;
	}

	public UserAccount getAccountByUserId(Integer userId) {
		return userAccountRepository.findOne(userId);
	}

}
