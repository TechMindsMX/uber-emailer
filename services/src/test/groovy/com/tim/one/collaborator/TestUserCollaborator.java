package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.AccountType;
import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.AccountCreator;
import com.tim.one.collaborator.UnitCollaborator;
import com.tim.one.collaborator.UserCollaborator;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.UserHelper;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.impl.UserServiceImpl;

public class TestUserCollaborator {

	@InjectMocks
	private UserCollaborator userCollaborator = new UserCollaborator();
	
	@Mock
	private UserServiceImpl userService;
	@Mock
	private User user;
	@Mock
	private UserHelper userHelper;
	@Mock
	private UserRepository userRepository;
	@Mock
	private AccountCreator accountCreator;
	@Mock
	private UnitCollaborator unitCollaborator;
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectFinancialData projectFinancialData;
	
	private String email = "email";
	private String name = "name";
	private String account = "account";

	private Integer userId = 1;

	private List<UnitTx> units = new ArrayList<UnitTx>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetUserById() throws Exception {
		when(userService.getUserById(userId)).thenReturn(user);
		assertEquals(user, userCollaborator.getUserById(userId));
	}
	
	@Test (expected=UserNotFoundException.class)
	public void shouldNotGetUserById() throws Exception {
		userCollaborator.getUserById(userId);
	}
	
	@Test
	public void shouldCreateUser() throws Exception {
		when(userHelper.createUser()).thenReturn(user);
		when(user.getId()).thenReturn(userId);
		when(accountCreator.createAccount(AccountType.USER, userId)).thenReturn(account);
		
		userCollaborator.createUser(email, name);
		
		verify(user).setEmail(email);
		verify(user).setName(name);
		verify(user).setBalance(new BigDecimal("0"));
		verify(userRepository, times(2)).save(user);
		verify(user).setAccount(account);
	}
	
	@Test
	public void shouldGetFinanciers() throws Exception {
		units.add(unitTx);
		when(unitCollaborator.getUnitsByProjectAndType(projectFinancialData, TransactionType.FUNDING)).thenReturn(units);
		when(unitTx.getUserId()).thenReturn(userId);
		
		Set<Integer> result = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.FUNDING);
		
		assertTrue(result.contains(userId));
	}
	
	@Test
	public void shouldGetInvestors() throws Exception {
		units.add(unitTx);
		when(unitCollaborator.getUnitsByProjectAndType(projectFinancialData, TransactionType.INVESTMENT)).thenReturn(units);
		when(unitTx.getUserId()).thenReturn(userId);
		
		Set<Integer> result = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.INVESTMENT);
		
		assertTrue(result.contains(userId));
	}

}
