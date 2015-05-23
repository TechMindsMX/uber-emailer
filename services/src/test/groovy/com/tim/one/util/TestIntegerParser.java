package com.tim.one.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestIntegerParser {

private IntegerParser integerParser = new IntegerParser();
	
	private String string = "1,2,3,4";
	
	@Test
	public void shouldParse() throws Exception {
		Integer one = 1;
		Integer two = 2;
		Integer three = 3;
		Integer four = 4;
		
		List<Integer> result = integerParser.parse(string);
		
		assertEquals(4, result.size());
		assertEquals(one, result.get(0));
		assertEquals(two, result.get(1));
		assertEquals(three, result.get(2));
		assertEquals(four, result.get(3));
	}
	
	@Test
	public void shouldNotParse() throws Exception {
		List<Integer> result = integerParser.parse("");
		assertTrue(result.isEmpty());
	}

}
