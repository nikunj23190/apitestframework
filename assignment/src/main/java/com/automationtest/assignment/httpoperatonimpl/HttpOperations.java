package com.automationtest.assignment.httpoperatonimpl;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.automationtest.assignment.utils.GlobalConstants;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Component
@Configuration
public class HttpOperations {

	private static Logger logger = LoggerFactory.getLogger(HttpOperations.class);

	@Autowired
	protected Gson gson;
	
	
	@Value("${baseurl}")
	protected String url;

	public static final RequestSpecBuilder builder = new RequestSpecBuilder();

	public static Map<String, Object> commonHeaders = new HashMap<String, Object>();

	public static String jsonFilePath;
	public JSONParser parser = new JSONParser();

	/**
	 * Get request.
	 *
	 * @param requestUrl the request url
	 * @param headers    the headers
	 * @return the response
	 */
	public Response getRequest(String requestUrl, Map<String, Object> headers) {

		logger.info("Get -Request URL  = " + requestUrl);

		RequestSpecification requestSpec = builder.build();

		Response response = RestAssured.given().headers(headers).spec(requestSpec).when().relaxedHTTPSValidation().log().all()
						.get(requestUrl);
		logger.info("Get - Response  = " + response.prettyPrint());

		return response;
	}

	
	/**
	 * Delete request.
	 *
	 * @param requestUrl the request url
	 * @param headers    the headers
	 * @return the response
	 */
	public Response deleteRequest(String requestUrl, Map<String, Object> headers) {

		logger.info("DELETE-RequestURL  = " + requestUrl);

		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName(GlobalConstants.BASIC_AUTH_USERNAME);
		authScheme.setPassword(GlobalConstants.BASIC_AUTH_PASSWORD);
		RestAssured.authentication = authScheme;

		RequestSpecification requestSpec = builder.build();

		Response response = RestAssured.given().headers(headers).spec(requestSpec).when().relaxedHTTPSValidation().log().all()
						.delete(requestUrl);
		logger.info("Delete - Response  = " + response.prettyPrint());

		return response;
	}

	/**
	 * Put request.
	 *
	 * @param requestUrl the request url
	 * @param headers    the headers
	 * @return the response
	 */
	public Response putRequest(String requestUrl, String body, Map<String, Object> headers) {

		logger.info("PUT -RequestURL  = " + requestUrl);

		builder.setBody(body);
		builder.setContentType(ContentType.JSON);
		

		RequestSpecification requestSpec = builder.build();

		Response response = RestAssured.given().headers(headers).spec(requestSpec).when().relaxedHTTPSValidation().log().all()
						.put(requestUrl);
		logger.info("PUT - Response  = " + response.prettyPrint());

		return response;
	}
	
	/**
	 * POST request.
	 *
	 * @param requestUrl the request url
	 * @param headers    the headers
	 * @return the response
	 */
	public Response postRequest(String requestUrl, String body, Map<String, Object> headers) {

		logger.info("POST -RequestURL  = " + requestUrl);

		builder.setBody(body);
		builder.setContentType(ContentType.JSON);
		
		RequestSpecification requestSpec = builder.build();

		Response response = RestAssured.given().headers(headers).spec(requestSpec).when().relaxedHTTPSValidation().log().all()
						.post(requestUrl);
		logger.info("POST - Response  = " + response.prettyPrint());

		return response;
	}
	
	/**
	 * POST request.
	 *
	 * @param requestUrl the request url
	 * @param headers    the headers
	 * @return the response
	 */
	public Response patchRequest(String requestUrl, String body, Map<String, Object> headers) {

		logger.info("PATCH -RequestURL  = " + requestUrl);

		builder.setBody(body);
		builder.setContentType(ContentType.JSON);
		
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName(GlobalConstants.BASIC_AUTH_USERNAME);
		authScheme.setPassword(GlobalConstants.BASIC_AUTH_PASSWORD);
		RestAssured.authentication = authScheme;
		
		RequestSpecification requestSpec = builder.build();

		Response response = RestAssured.given().headers(headers).spec(requestSpec).when().relaxedHTTPSValidation().log().all()
						.patch(requestUrl);
		logger.info("PATCH - Response  = " + response.prettyPrint());

		return response;
	}
	
	
	

}
