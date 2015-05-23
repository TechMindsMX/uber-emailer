package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.TransactionType;
import com.tim.one.collaborator.TransactionDaoCollaborator;
import com.tim.one.model.UnitTx;
import com.tim.one.repository.UnitTxRepository;

public class TestTransactionDaoCollaborator {

	@InjectMocks
	private TransactionDaoCollaborator transactionDaoCollaborator = new TransactionDaoCollaborator();
	
	@Mock
	private UnitTxRepository unitTxRepository;
	@Mock
	private List<UnitTx> units;
	
	private Integer unitSaleId = 2;
	
	private List<Integer> ids = new ArrayList<Integer>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldFindConsumingUnitsByType() throws Exception {
		ids.add(unitSaleId);
		
		when(unitTxRepository.findUnitsByTypeAndProjectUnitSales(TransactionType.CONSUMING, ids)).thenReturn(units);
		
		assertEquals(units, transactionDaoCollaborator.findUnits(TransactionType.CONSUMING, ids));
	}
	
	@Test
  public void shouldFindEmtyUnitsByType() throws Exception {
    assertEquals(new ArrayList<UnitTx>(), transactionDaoCollaborator.findUnits(TransactionType.CONSUMING, ids));
    verify(unitTxRepository, never()).findUnitsByTypeAndProjectUnitSales(TransactionType.CONSUMING, ids);
  }
	
	@Test
	public void shouldFindConsumingUnitsByProjectUnitsales() throws Exception {
		ids.add(unitSaleId);
		
		when(unitTxRepository.findUnitsByprojectUnitSaleIdIn(ids)).thenReturn(units);
		
		assertEquals(units, transactionDaoCollaborator.findUnits(TransactionType.ALL, ids));
	}

}
