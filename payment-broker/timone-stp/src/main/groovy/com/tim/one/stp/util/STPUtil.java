package com.tim.one.stp.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author josdem
 * @understands A class who provides date utilities operations
 *
 */

@Component
public class STPUtil {

	public Integer getReferenciaNumerica(){
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
	    calendar.setTime(today);
		
	    String yearAsString = new Integer(calendar.get(Calendar.YEAR)).toString().substring(2);
	    String monthAsString = new Integer(calendar.get(Calendar.MONTH)) + new Integer(1) + "";
	    if (monthAsString.length() < 2){
	    	monthAsString = "0" + monthAsString;
	    }
	    String dayAsString = new Integer(calendar.get(Calendar.DAY_OF_MONTH)).toString();
	    if (dayAsString.length() < 2){
	    	dayAsString = "0" + dayAsString;
	    }
	    
		return new Integer("1" + yearAsString + monthAsString + dayAsString);
	}
	
}
