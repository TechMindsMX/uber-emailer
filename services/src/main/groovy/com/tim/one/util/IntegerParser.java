package com.tim.one.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class IntegerParser {
	
	public List<Integer> parse(String string){
		String[] integersAsArray = string.split(",");
		List<Integer> integersAsList = new ArrayList<Integer>();
		for (String element : integersAsArray) {
			if (!StringUtils.isEmpty(element)) {
				integersAsList.add(Integer.parseInt(element));
			}
		}
		return integersAsList;
	}

}
