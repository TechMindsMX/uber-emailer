package com.tim.one.messengine;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.ObjectMessage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.SimpleBean;
import com.tim.one.service.NotificationService;

public class TestMessageConsumer {

	@InjectMocks
	private JmsMessageListener messageConsumer = new JmsMessageListener();

	@Mock
	private ObjectMessage message;
	@Mock
	private SimpleBean simpleBean;
	@Mock
	private NotificationService notificationService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReceiveMessage() throws Exception {
		when(simpleBean.getType()).thenReturn(MessageType.ABONO_CUENTA);
		when(message.getObject()).thenReturn(simpleBean);

		messageConsumer.onMessage(message);

		verify(notificationService).sendNotification(simpleBean);
	}

}
