package com.tim.one.interceptor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.service.LoggerService;
import com.tim.one.service.StringSplitter;
import com.tim.one.state.ApplicationState;

public class TestLoggerInterceptor {
	
	@InjectMocks
	private LoggerInterceptor logger = new LoggerInterceptor();
	
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private Object handler;
	@Mock
	private LoggerService loggerService;
	@Mock
	private Properties dynamic;
	@Mock
	private StringSplitter splitter;

	private String ips = "127.0.0.1";

	private List<String> whiteList = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		whiteList.add(ips);
		when(splitter.split(ips)).thenReturn(whiteList);
		when(dynamic.getProperty(ApplicationState.WHITE_LIST)).thenReturn(ips);
		logger.setup();
	}

	@Test
	public void shouldAcceptLocalhost() throws Exception {
		when(request.getRemoteHost()).thenReturn("127.0.0.1");
		
		assertTrue(logger.preHandle(request, response, handler));
		verify(loggerService).notifyRequest(isA(Map.class));
	}
	
	@Test
	public void shouldNotAcceptGoogle() throws Exception {
		when(request.getRemoteHost()).thenReturn("200.77.168.173");
		
		assertFalse(logger.preHandle(request, response, handler));
		verify(loggerService).notifyRequest(isA(Map.class));
	}

}
