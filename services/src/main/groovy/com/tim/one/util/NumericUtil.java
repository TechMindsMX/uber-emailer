package com.tim.one.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

import org.springframework.stereotype.Component;

@Component
public class NumericUtil {
	
	public boolean isNumeric(String str){
		  NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
	}

}
