package com.tim.one.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.tim.one.integra.command.IntegraCashinCommand;
import com.tim.one.service.IntegraTransactionService;
import com.tim.one.validator.CommandValidator;

public class TestIntegraTransactionController {
	
	@InjectMocks
	private IntegraTransactionController controller = new IntegraTransactionController();
	
	@Mock
	private CommandValidator validator;
	@Mock
	private IntegraTransactionService service;

	private String uuid = "uuid";
	private String transactionUuid = "transactionUuid";
	private BigDecimal amount = new BigDecimal(100);
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCashin() throws Exception {
		IntegraCashinCommand command = new IntegraCashinCommand();
		command.setUuid(uuid);
		command.setAmount(amount);
		String json = new Gson().toJson(command);
		
		when(validator.isValid(isA(IntegraCashinCommand.class))).thenReturn(true);
		when(service.cashIn(uuid, amount)).thenReturn(transactionUuid);
		
		ResponseEntity<String> result = controller.cashin(json);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(transactionUuid, result.getBody());
	}
	
	@Test
	public void shouldNotCashinDueToCommandIsNotValid() throws Exception {
		IntegraCashinCommand command = new IntegraCashinCommand();
		command.setUuid(uuid);
		command.setAmount(amount);
		String json = new Gson().toJson(command);
		
		when(validator.isValid(isA(IntegraCashinCommand.class))).thenReturn(false);
		
		ResponseEntity<String> result = controller.cashin(json);
		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Validation error", result.getBody());
	}

}
