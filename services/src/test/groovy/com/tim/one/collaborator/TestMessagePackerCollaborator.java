package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.collaborator.MessagePackerCollaborator;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.ProjectUnitSaleRepository;

public class TestMessagePackerCollaborator {

	@InjectMocks
	private MessagePackerCollaborator messagePackerCollaborator = new MessagePackerCollaborator();
	
	@Mock
	private UnitTx unitTx;
	@Mock
	private ProjectUnitSale projectUnitSale;
	@Mock
	private ProjectUnitSaleRepository projectUnitSaleRepository;
	
	private Integer projectUnitSaleId = 1;
	private Integer quantity = 2;

	private BigDecimal unitSale = new BigDecimal("100");
  private Set<UnitTx> unitsAsSet = new HashSet<UnitTx>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetAmount() throws Exception {
		BigDecimal expectedResult = new BigDecimal("200");
		
		unitsAsSet.add(unitTx);
		when(unitTx.getProjectUnitSaleId()).thenReturn(projectUnitSaleId);
		when(projectUnitSaleRepository.findOne(projectUnitSaleId)).thenReturn(projectUnitSale);
		when(unitTx.getQuantity()).thenReturn(quantity);
		when(projectUnitSale.getUnitSale()).thenReturn(unitSale);
		
		assertEquals(expectedResult, messagePackerCollaborator.getAmount(unitsAsSet));
	}

}
