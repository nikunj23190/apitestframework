package com.automationtest.assignment.utils;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Resources {
	
	private Resources() {
		
	}
	
	public static String getFileAsString(String filePath) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(filePath);
		return new String(IOUtils.readAll(classPathResource.getInputStream()));
	}

	public static <T> T getFileAsObject(String filePath, Class<T> type) throws IOException{
		String expected = getFileAsString(filePath);
		return new ObjectMapper().readValue(expected, type);
	}
}
