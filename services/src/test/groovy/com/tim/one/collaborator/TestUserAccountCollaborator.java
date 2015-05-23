package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.helper.STPConsumerHelper;
import com.tim.one.model.User;
import com.tim.one.model.UserAccount;

public class TestUserAccountCollaborator {

	@InjectMocks
	private UserAccountCollaborator userAccountCollaborator = new UserAccountCollaborator();
	
	@Mock
	private STPConsumerHelper stpHelper;
	@Mock
	private UserAccount userAccount;
	
	private User user = new User();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetNewUserAccount() throws Exception {
		when(stpHelper.createUserAccount()).thenReturn(userAccount);
		assertNotNull(userAccountCollaborator.getUserAccount(user));
	}
	
	@Test
	public void shouldGetExistentUserAccount() throws Exception {
		user.setUserAccount(userAccount);
		assertEquals(userAccount, userAccountCollaborator.getUserAccount(user));
	}

}
