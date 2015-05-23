package com.tim.one.stp.formatter;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tim.one.stp.command.IntegraCashinCommand;


@Component
public class CommandFormatter {
	
	public String format(IntegraCashinCommand command){
		return new Gson().toJson(command);
	}
	
}
