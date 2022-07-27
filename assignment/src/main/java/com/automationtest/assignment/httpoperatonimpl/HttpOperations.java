package com.automationtest.assignment.httpoperatonimpl;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import io.restassured.builder.RequestSpecBuilder;



@Component
@Configuration
public class HttpOperations {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpOperations.class);
	
	@Value("${baseurl}")
	protected String url;
	
	
	public static final RequestSpecBuilder builder = new RequestSpecBuilder();
	
	public static String jsonFilePath;
	public JSONParser parser = new JSONParser();
	
	

	
	
	
}
