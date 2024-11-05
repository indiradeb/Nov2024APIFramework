package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHTTPStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;

public class APISchemaValidationTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop,baseURI);
	}
	
	@Test()
	public void createUserAPISchematEst(String name, String gender, String status) {
		
		//1.Post
		//User user = new User(name, StringUtils.getRandomEmailId(), gender ,status);
		User user = new User("Timi", StringUtils.getRandomEmailId(), "male" ,"active");
		//RestClient restClient = new RestClient();
		restClient.post(GOREST_ENDPOINT, "json", user, true, true)
		          .then().log().all()
		          .assertThat()
		          .statusCode(APIHTTPStatus.CREATED_201.getCode())
		          .body(matchesJsonSchemaInClasspath("createuserschema.json"));
		      
		
		
	}
	
}
