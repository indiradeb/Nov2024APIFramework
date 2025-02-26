package com.qa.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHTTPStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;


public class GetUserTest extends BaseTest{
	
	
	/*@BeforeMethod
	public void getUserSetUp() {
		
		restClient = new RestClient(prop, baseURI);
	}*/
	
	@Test(priority=3)
	public void getAllUsers() {
		/*restClient.get(GOREST_ENDPOINT,false, true)
		          .then().log().all()
		          .assertThat()
		          .statusCode(APIHTTPStatus.OK_200.getCode());*/
		
		
		Response gorestResponse = restClient.get(GOREST_ENDPOINT, false,true);
		int statusCode  = gorestResponse.statusCode();
		Assert.assertEquals(statusCode, APIHTTPStatus.OK_200.getCode());
		
		JsonPathValidator js = new JsonPathValidator();
		List<String> userName = js.readList(gorestResponse, "$..[3].name");
		System.out.println(userName);
		Assert.assertTrue(userName.contains("Adripathi Tagore"));
		List<String> userEmail = js.readList(gorestResponse, "$..[3].email");
		System.out.println(userEmail);
		Assert.assertTrue(userEmail.contains("adripathi_tagore@greenholt-mertz.test"));
		}
	

	@Test(priority=2, enabled=true)
	public void getUser() {
	/*	restClient.get(GOREST_ENDPOINT+"/"+7503114, false,true)//"/public/v2/users/7494978"
		          .then().log().all()
		          .assertThat()
		          .statusCode(APIHTTPStatus.OK_200.getCode())
		          .and()
		          .body("id", equalTo(7503114));*/
		
	//	Response gorestResponse = restClient.get(GOREST_ENDPOINT+"/"+7503114, false,true);//"/public/v2/users/7494978"
        
	}
	//$..[3].name,gender,status
	
	

	
	@Test(priority=1, enabled=false)
	public void getUser_with_queryParamTest() {
		
		Map<String,Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name","Pranavi");
		queryParams.put("status","active");
		
		restClient.get(GOREST_ENDPOINT,queryParams, null,false, true)
		          .then().log().all()
		          .assertThat()
		          .statusCode(APIHTTPStatus.OK_200.getCode());
		          
	}
	
	
	
}
