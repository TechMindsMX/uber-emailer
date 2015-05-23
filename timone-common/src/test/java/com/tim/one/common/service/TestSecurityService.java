package com.tim.one.common.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.KeyStatus;
import com.tim.one.common.collabotator.SecurityCollaborator;
import com.tim.one.common.helper.SecurityHelper;
import com.tim.one.common.state.ApplicationState;
import com.tim.one.common.util.DateUtil;
import com.tim.one.model.SessionKey;
import com.tim.one.repository.SecurityRepository;

public class TestSecurityService {

	@InjectMocks
	private SecurityService securityService = new SecurityService();
	
	@Mock
	private SecurityHelper securityHelper;
	@Mock
	private SessionKey sessionKey;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private SecurityRepository securityDao;
	@Mock
	private SecurityCollaborator securityCollaborator;
	@Mock
	private Properties properties;

	private String token = "key";
	private Long timestamp = 1L;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGenerateKey() throws Exception {
		when(securityCollaborator.generateKey()).thenReturn(token);
		when(securityHelper.createSessionKey()).thenReturn(sessionKey);
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		
		securityService.generateKey();
		
		verify(sessionKey).setApiKey(token);
		verify(sessionKey).setTimestamp(timestamp);
		verify(sessionKey).setKeyStatus(KeyStatus.VALID);
		verify(securityDao).save(sessionKey);
	}
	
	@Test
	public void shouldKnowIfTonenIsValid() throws Exception {
		when(securityDao.findByApiKey(token)).thenReturn(sessionKey);
		when(dateUtil.createDateAsLong()).thenReturn(1000 * 60 * 6L);
		when(sessionKey.getTimestamp()).thenReturn(1000 * 60 * 5L);
		when(properties.getProperty(ApplicationState.TIMEOUT)).thenReturn("10");
		
		assertTrue(securityService.isValid(token));
		
		verify(sessionKey).setKeyStatus(KeyStatus.INVALID);
		verify(securityDao).save(sessionKey);
	}
	
	@Test
	public void shouldKnowTonenIsNotValidDueToNoSessionKey() throws Exception {
		when(securityDao.findByApiKey(token)).thenReturn(null);
		
		assertFalse(securityService.isValid(token));
	}
	
	@Test
	public void shouldKnowTonenIsNotValidDueToTokenExpired() throws Exception {
		when(securityDao.findByApiKey(token)).thenReturn(sessionKey);
		when(dateUtil.createDateAsLong()).thenReturn(1000 * 60 * 15L);
		when(sessionKey.getTimestamp()).thenReturn(1000 * 60 * 5L);
		when(properties.getProperty(ApplicationState.TIMEOUT)).thenReturn("10");
		
		assertFalse(securityService.isValid(token));
		
		verify(sessionKey).setKeyStatus(KeyStatus.INVALID);
		verify(securityDao).save(sessionKey);
	}
	
	@Test
	public void shouldKnowTonenIsNotValidDueToNoToken() throws Exception {
		assertFalse(securityService.isValid(null));
		verify(securityDao, never()).findByApiKey(null);
	}

}
