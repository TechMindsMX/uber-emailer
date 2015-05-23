package com.tim.one.helper;

import org.springframework.stereotype.Component;

import com.tim.one.bean.mail.CierreBean;
import com.tim.one.bean.mail.CreacionCuentaBean;
import com.tim.one.bean.mail.FinanciamientoInversionBean;
import com.tim.one.bean.mail.SimpleBean;

@Component
public class MessagePackerHelper {

	public FinanciamientoInversionBean createFinanciamientoInversionBean() {
		return new FinanciamientoInversionBean();
	}

	public CierreBean createCierreBean() {
		return new CierreBean();
	}

	public SimpleBean createSimpleBean() {
		return new SimpleBean();
	}

	public CreacionCuentaBean createCreacionCuentaBean() {
		return new CreacionCuentaBean();
	}
	
}
