package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHTTPStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest{
	
//RestClient restClient;
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop,baseURI);
	}
	
	@DataProvider
	public Object[][] getUserTestData() {
		
		return new Object[][] {
			{"Subodh","male", "active"},
			{"Srinidhi", "female", "inactive"},
			{"Maaduri","female", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserTestSheetData( ){	
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserTestData")
	public void createUser(String name, String gender, String status) {
		
		//1.Post
		//User user = new User(name, StringUtils.getRandomEmailId(), gender ,status);
		User user = new User("Nikhil", StringUtils.getRandomEmailId(), "male" ,"active");
		//RestClient restClient = new RestClient();
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
		          .then().log().all()
		          .assertThat()
		          .statusCode(APIHTTPStatus.CREATED_201.getCode())
		          .extract().path("id");
		
		System.out.println("User id: "+userId);
		
		//2.Get
		RestClient restClientGet = new RestClient(prop,baseURI);
		restClientGet.get(GOREST_ENDPOINT +"/"+ userId, true, true)
        .then().log().all()
        .assertThat()
        .statusCode(APIHTTPStatus.OK_200.getCode())
        .and().body("id", equalTo(userId));		
	}
	
	
	

}
