package com.tim.one.collaborator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @author josdem
 * @understands A class who receive "dd/MM/yyyy" and create a java.sql.Date
 * 
 */

@Component
public class DateFormatter {

	private Log log = LogFactory.getLog(getClass());

	public Long format(String string) throws ParseException {
		if (!StringUtils.isEmpty(string)){
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date date = format.parse(string);
			log.info("date: " + date.getTime());
			return date.getTime();
		} else {
			return null;
		}
	}
	
}
