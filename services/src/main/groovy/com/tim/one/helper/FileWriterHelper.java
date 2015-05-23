package com.tim.one.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tim.one.exception.FileWriterException;
import com.tim.one.state.ApplicationState;

/**
 * @author josdem
 * @understands A class who has save business case logic flow
 * 
 */

@Component
public class FileWriterHelper {
	
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	public Long write(InputStream businessCase, String name, String path) throws IOException, FileWriterException {
		if (businessCase != null){
			File file = new File(path + ApplicationState.FILE_SEPARATOR + name + ApplicationState.POINT + properties.getProperty(ApplicationState.BUSINESS_CASE_EXTENSION));
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			IOUtils.copy(businessCase , fileOutputStream);
			return file.length(); 
		} else {
			throw new FileWriterException();
		}
	}
	
	public Long write(String name, String path, String line) throws IOException {
		File file = new File(path + ApplicationState.FILE_SEPARATOR + name + ApplicationState.POINT + properties.getProperty(ApplicationState.TRANSACTION_TEXT_EXTENSION));
		PrintWriter printer = new PrintWriter(file);
		printer.write(line);
		printer.close();
		return file.length(); 
	}

}
