package com.tim.one.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who provides date utilities operations
 *
 */

@Component
public class DateUtil {

	public long createDateAsLong() {
		return new Date().getTime();
	}

	public Date createDate() {
		return new Date();
	}

	public Long dateStartFormat(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.parse(date).getTime();
	}
	
	public Long dateEndFormat(String date) throws ParseException {
		date = date + " 23:59:59";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return dateFormat.parse(date).getTime();
	}

	public Long restOneDay(Long timeAsLong) {
		return (timeAsLong) - ApplicationState.HOURS_IN_DAY * ApplicationState.TIME_UNIT * ApplicationState.TIME_UNIT * ApplicationState.MILISECONDS;
	}
}
