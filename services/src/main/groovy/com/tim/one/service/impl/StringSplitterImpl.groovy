package com.tim.one.service.impl

import java.util.ArrayList
import java.util.List
import java.util.StringTokenizer

import org.springframework.stereotype.Service

import com.tim.one.service.StringSplitter

@Service
public class StringSplitterImpl implements StringSplitter {
	
	public List<String> split(String string) {
		StringTokenizer token = new StringTokenizer(string, ",")
	    List<String> list = new ArrayList<String>(string.length())
	    while(token.hasMoreTokens()){
	       list.add(token.nextToken())
	     }
		return list
	}

}
