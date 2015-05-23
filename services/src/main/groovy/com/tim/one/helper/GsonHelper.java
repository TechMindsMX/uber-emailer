package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class GsonHelper {
	
	public Gson createWithDateFormat(String dateFormat) {
		return new GsonBuilder().setDateFormat(dateFormat).create();
	}

	public Gson createGson() {
		return new Gson();
	}

}
