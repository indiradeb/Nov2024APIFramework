package com.qa.gorest.client;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkException.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "5da41fe4a2f025bed4d01599d07622913b6c170581661d3afbb12141244aab3a";
	private  RequestSpecBuilder specBuilder;
	
	/*static {
		
		specBuilder = new RequestSpecBuilder();
	}*/
	private Properties prop;
	private String baseURI;
	private boolean isAuthorizationHeaderAdded = false;
	
	public RestClient(Properties prop, String baseURI){
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}
	
	
	public void addAuthorizationHeader() {
		if(!isAuthorizationHeaderAdded) {
		specBuilder.addHeader("Authorization", "Bearer "+ prop.getProperty("tokenId"));
		isAuthorizationHeaderAdded=true;
		}
	}
	
	/*public void addAuthorizationHeader(String BEARER_TOKEN) {
		if(BEARER_TOKEN==null) {
		specBuilder.addHeader("Authorization", "Bearer "+ BEARER_TOKEN);
		}
	}*/
	
private void setRequestContentType(String contentType) {
		switch(contentType.toLowerCase()) {
		case "json" : specBuilder.setContentType(ContentType.JSON);
		              	break;
		case "xml" : specBuilder.setContentType(ContentType.XML);
						break;
		case "text" : specBuilder.setContentType(ContentType.TEXT);
						break;
		case "multipart" : specBuilder.setContentType(ContentType.MULTIPART);
						break;
        default:
        	System.out.println("Please pass the right content type....");
        	throw new APIFrameworkException("INVALID CONTENT TYPE");
		}
	}
	
private RequestSpecification createRequestSpec(boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) { addAuthorizationHeader(); }
		//addAuthorizationHeader(BEARER_TOKEN);
		return specBuilder.build();
	}
	
private RequestSpecification createRequestSpec(Map<String,String> headerMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {addAuthorizationHeader();}
		//addAuthorizationHeader(BEARER_TOKEN);
		if(headerMap!=null) {
			specBuilder.addHeaders(headerMap);
		}
		return specBuilder.build();
	}
	
private RequestSpecification createRequestSpec(Map<String,String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
	
	specBuilder.setBaseUri(baseURI);
	if(includeAuth) {addAuthorizationHeader();}
	
	if(headersMap!=null) {
		specBuilder.addHeaders(headersMap);
	}
	if(queryParams!=null) {
		specBuilder.addQueryParams(queryParams);
	}
	return specBuilder.build();
}
	
private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
	
	specBuilder.setBaseUri(baseURI);
	if(includeAuth) {addAuthorizationHeader();}
	setRequestContentType(contentType);
	if(requestBody!=null) {
		specBuilder.setBody(requestBody);
	}
	return specBuilder.build();
}
	
private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
	
	specBuilder.setBaseUri(baseURI);
	if(includeAuth) {addAuthorizationHeader();}
	//addAuthorizationHeader(BEARER_TOKEN);
	setRequestContentType(contentType);
	if(headersMap!=null) {
		specBuilder.addHeaders(headersMap);
	}
	if(requestBody!=null) {
		specBuilder.setBody(requestBody);
	}
	return specBuilder.build();
}

//Http methdos

public Response get(String serviceUrl,boolean includeAuth,  boolean log) {
	   if(log) {
	   return  RestAssured.given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
  }

public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {
	   if(log) {
	   return  RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all().when().get(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
  }

   public Response get(String serviceUrl,Map<String, Object> queryParam,  Map<String, String> headersMap, boolean includeAuth, boolean log) {
	   if(log) {
	   return  RestAssured.given(createRequestSpec(headersMap, queryParam, includeAuth)).log().all().when().get(serviceUrl);
	   }
	   return RestAssured.given(createRequestSpec(headersMap, queryParam, includeAuth)).when().get(serviceUrl);
     }
   
   //Post Methods
   public Response post(String serviceUrl,  String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth)).log().all()
		    .when()
		    .post(serviceUrl);
           }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth))
			    .when()
			    .post(serviceUrl);
	   
   }
   
public Response post(String serviceUrl,  String contentType, Object requestBody,boolean includeAuth, boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		    .when()
		    .post(serviceUrl);
            }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			    .post(serviceUrl);
	   
   }

//Put Methods
public Response put(String serviceUrl,  String contentType, Object requestBody,Map<String, String> headersMap, boolean includeAuth, boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth)).log().all()
		    .when()
		    .put(serviceUrl);
        }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth))
			    .when()
			    .put(serviceUrl);
	   
}

public Response put(String serviceUrl,  String contentType, Object requestBody,boolean includeAuth, boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		    .when()
		    .put(serviceUrl);
         }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			    .put(serviceUrl);
	   
}

//Patch Methods
public Response patch(String serviceUrl,  String contentType, Object requestBody,Map<String, String> headersMap,boolean includeAuth,  boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth)).log().all()
		    .when()
		    .patch(serviceUrl);
        }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType,headersMap, includeAuth))
			    .when()
			    .patch(serviceUrl);
	   
}

public Response patch(String serviceUrl,  String contentType, Object requestBody, boolean includeAuth, boolean log ) {
	   
	   if(log) {
		  return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
		    .when()
		    .patch(serviceUrl);
         }
	   return   RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			    .patch(serviceUrl);
	   
}

//Delete methods
public Response delete(String serviceUrl, boolean includeAuth, boolean log){
	 if(log) {
		  return   RestAssured.given(createRequestSpec(includeAuth)).log().all()
		    .when()
		    .delete(serviceUrl);
        }
	   return   RestAssured.given(createRequestSpec(includeAuth))
			    .when()
			    .delete(serviceUrl);
	
}

/*******************************************/

public String getAccesstoken(String serviceURL, String grantType, String clinetId, String clinetSecret) {
	//1.POST get the accessToken
	    RestAssured.baseURI = "https://test.api.amadeus.com";
	    
	  String  accessToken = RestAssured.given()
	    	            //.header("Content-Type","application/x-www-form-urlencoded")
	    	            .contentType(ContentType.URLENC)
	    	            .formParam("grant_type", grantType)
	    	            .formParam("client_id", clinetId)
	    	            .formParam("client_secret", clinetSecret)
	    	            .when()
	    	            .post(serviceURL)
	    	            .then()
	    	            .assertThat()
	    	            .statusCode(200)
	    	            .extract().path("access_token");
	    System.out.println(" access token: "+accessToken);
	    return accessToken;
}  
	


}
