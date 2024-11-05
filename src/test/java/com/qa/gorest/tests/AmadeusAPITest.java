package com.qa.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHTTPStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class AmadeusAPITest extends BaseTest {

    private String accessToken;
    
	@Parameters({"baseURI","grantType","clientId","clientSecret"})
	@BeforeMethod
	public void flightAPISetup(String baseURI, String grantType, String clientId, String clientSecret) {
		
		//restClient = new RestClient(prop, baseURI);
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.getAccesstoken(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
		
	}
	
	@Test
	public void getFlightInfoTest()
	{
		//2.get flight info :GEt
		
	   Map<String, Object> queryParams = new  HashMap<String, Object>();
	   queryParams.put("origin", "PAR");
	   queryParams.put("maxPrice", 200);
		     
	   Map<String, String> headersMap = new HashMap	<String, String>();
	   headersMap.put("Authorization", "Bearer "+accessToken);
	   
		Response flightDataResponse = restClient.get(AMADEUS_FLIGHBOOKING_ENDPOINT, queryParams, headersMap,false, true)
				     .then().log().all()
				     .assertThat()
				     . statusCode(APIHTTPStatus.OK_200.getCode())
	    	            .extract()
	    	            .response();
	    	JsonPath js =    flightDataResponse.jsonPath();
	    	String type = js.getString("data[0].type");
	    	System.out.println(type);  //flight destination
				     
		}
	
}
