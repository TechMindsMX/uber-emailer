package com.tim.one.service

import com.tim.one.model.Persona

interface CashinStrategy {
	void cashIn(Persona persona, BigDecimal amount, String reference, String clabeOrdenante)  
}
