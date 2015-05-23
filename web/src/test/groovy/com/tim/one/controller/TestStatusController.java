package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.service.impl.StatusServiceImpl;

public class TestStatusController {

	@InjectMocks
	private StatusController statusController = new StatusController();

	@Mock
	private StatusServiceImpl statusService;
	@Mock
	private List<Map<Integer, String>> statusList;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldListProjectStatus() throws Exception {
		when(statusService.getProjectStatus()).thenReturn(statusList);

		List<Map<Integer, String>> result = statusController.listProjectStatus();

		assertEquals(statusList, result);
	}

}
