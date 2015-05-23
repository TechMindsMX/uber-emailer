package com.tim.one.emailer.helper

import java.io.File
import java.io.IOException

import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

@Component
public class FileHelper {
	
	public String readFromFile(String htmlFilePath) throws IOException {
		return FileUtils.readFileToString(new File(htmlFilePath))
	}

}
