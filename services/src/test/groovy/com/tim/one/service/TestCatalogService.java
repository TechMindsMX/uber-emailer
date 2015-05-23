 package com.tim.one.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.ClosedReasonType;
import com.tim.one.service.impl.CatalogServiceImpl;

public class TestCatalogService {

	private CatalogService catalogService = new CatalogServiceImpl();
	
	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldGetClosedReason() throws Exception {
		List<String> reasons = catalogService.getClosedReason();
		assertEquals(reasons.get(0), ClosedReasonType.NOT_DELIVERY_PRODUCT.getName());
		assertEquals(reasons.get(1), ClosedReasonType.INCOMPLETE_DOCUMENTATION.getName());
		assertEquals(reasons.get(2), ClosedReasonType.NOT_BE_REACHED.getName());
	}
}