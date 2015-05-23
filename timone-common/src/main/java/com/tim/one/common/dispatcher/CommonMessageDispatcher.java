package com.tim.one.common.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tim.one.bean.mail.MessageBean;

/**
 * @author josdem
 * @understands A class who knows how receive http GET requests and delegate to
 *              the MessageService
 */

@Service
public class CommonMessageDispatcher {
	
	private Log log = LogFactory.getLog(getClass());

	public void message(MessageBean bean) {
		log.info("CALLING Message");
	}
	
}
