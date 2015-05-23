package com.tim.one.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileCalcSheetHelper {

	public InputStream getStream(MultipartFile businessCase) throws IOException {
		byte [] byteArray = businessCase.getBytes();
		return new ByteArrayInputStream(byteArray);
	}

}
