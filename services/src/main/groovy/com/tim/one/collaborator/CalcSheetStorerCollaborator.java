package com.tim.one.collaborator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tim.one.exception.FileWriterException;
import com.tim.one.helper.FileCalcSheetHelper;
import com.tim.one.helper.FileWriterHelper;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

/**
 * @author josdem
 * @understands A class who has save business case logic flow
 * 
 */

@Component
public class CalcSheetStorerCollaborator {

	@Autowired
	private FileWriterHelper calcSheetHelper;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private FileCalcSheetHelper fileCalcSheetHelper;
	@Autowired
	private Properties dynamic;

	private Log log = LogFactory.getLog(getClass());

	public Long saveCalcSheet(MultipartFile businessCase) {
		log.info("SAVING CALC SHEET TO PROJECT");
		Long timestamp = null;
		Long size = null;
		try {
			InputStream inputStream = fileCalcSheetHelper.getStream(businessCase);
			timestamp = dateUtil.createDateAsLong();
			size = calcSheetHelper.write(inputStream, "" +  timestamp, dynamic.getProperty(ApplicationState.BUSINESS_CASE_PATH));
			log.info("size: " + size);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (FileWriterException bce) {
			log.warn(bce.getMessage());
		}
		return (size != null && size != 0) ? timestamp : null;
	}
	
}
