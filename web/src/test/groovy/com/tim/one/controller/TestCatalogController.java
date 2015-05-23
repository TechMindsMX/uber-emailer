package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.service.impl.CatalogServiceImpl;

public class TestCatalogController {

	@InjectMocks
	private CatalogController catalogController = new CatalogController();
	
	@Mock
	private CatalogServiceImpl catalogService;
	@Mock
	private List<String> closedReasons;
	
	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldGetClosedReason() throws Exception {
		when(catalogService.getClosedReason()).thenReturn(closedReasons);
		assertEquals(closedReasons, catalogController.getClosedReason());
	}

}
