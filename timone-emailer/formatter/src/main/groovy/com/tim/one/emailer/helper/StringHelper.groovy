package com.tim.one.emailer.helper

import org.springframework.stereotype.Component

@Component
public class StringHelper {

	public String format(String messageFromFile, Object... args) {
		return String.format(messageFromFile, args)
	}

}
