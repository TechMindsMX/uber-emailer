package com.tim.one.collaborator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.tim.one.collaborator.CalcSheetStorerCollaborator;
import com.tim.one.helper.FileCalcSheetHelper;
import com.tim.one.helper.FileWriterHelper;
import com.tim.one.util.DateUtil;

public class TestCalcSheetStorerCollaborator {

	@InjectMocks
	private CalcSheetStorerCollaborator calcSheetStorerCollaborator = new CalcSheetStorerCollaborator();
	
	@Mock
	private FileWriterHelper calcSheetHelper;
	@Mock
	private MultipartFile businessCase;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private FileCalcSheetHelper fileCalcSheetHelper;
	@Mock
	private InputStream inputStream;
	@Mock
	private Properties properties;
	
	private Integer projectId = 1;
	private Long timestamp = 1L;

	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void shouldSaveSheet() throws Exception {
		setInputStreamExpectations();
		when(calcSheetHelper.write(inputStream, projectId.toString(), null)).thenReturn(1L);
		
		Long result = calcSheetStorerCollaborator.saveCalcSheet(businessCase);
		
		assertEquals(timestamp, result);
	}
	
	@Test
	public void shouldNotSaveSheet() throws Exception {
		setInputStreamExpectations();
		when(calcSheetHelper.write(inputStream, projectId.toString(), null)).thenReturn(0L);
		
		Long result = calcSheetStorerCollaborator.saveCalcSheet(businessCase);
		
		assertNull(result);
	}

	private void setInputStreamExpectations() throws IOException {
		when(dateUtil.createDateAsLong()).thenReturn(timestamp);
		when(fileCalcSheetHelper.getStream(businessCase)).thenReturn(inputStream);
	}

}
