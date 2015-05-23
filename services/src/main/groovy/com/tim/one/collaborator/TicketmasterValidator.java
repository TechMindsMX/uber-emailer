package com.tim.one.collaborator;

import org.springframework.stereotype.Component;

@Component
public class TicketmasterValidator {

	public Boolean validate(String string) {
		if(string.isEmpty()){
			return true;
		}
		int characterCounter = 0;
		for (int i=0; i<string.length() ; i++) {
			if (string.charAt(i) == '/'){
				characterCounter++;
			}
		}
		if(characterCounter == 6){
			return true;
		}
		return false;
	}
	
}
