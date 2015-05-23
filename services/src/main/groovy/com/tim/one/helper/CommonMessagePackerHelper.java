package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.bean.mail.CreacionCuentaBean;

@Component
public class CommonMessagePackerHelper {
	
	public AbonoCuentaBean createAbonoCuentaBean() {
		return new AbonoCuentaBean();
	}

	public CreacionCuentaBean createCreacionCuentaBean() {
		return new CreacionCuentaBean();
	}
	
}
