package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.tim.one.collaborator.TagSplitter;

public class TestTagSplitter {

private TagSplitter tagSplitter = new TagSplitter();
	
	
	@Test
	public void shouldSplit() throws Exception {
		String tags = "one, two";
		List<String> result = tagSplitter.split(tags);
		assertEquals("one", result.get(0));
		assertEquals("two", result.get(1));
		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldNotSplitWhenNullTags() throws Exception {
		String tags = null;
		List<String> result = tagSplitter.split(tags);
		assertTrue(result.isEmpty());
	}

	@Test
	public void shouldNotSplitWhenEmptyTags() throws Exception {
		String tags = "";
		List<String> result = tagSplitter.split(tags);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void shouldNotSplitWhenSingleWord() throws Exception {
		String tags = "one";
		List<String> result = tagSplitter.split(tags);
		assertEquals("one", result.get(0));
		assertEquals(1, result.size());
	}

}
