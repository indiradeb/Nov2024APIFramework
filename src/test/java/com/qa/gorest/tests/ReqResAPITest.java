package com.qa.gorest.tests;

import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;

public class ReqResAPITest extends BaseTest{
	
	
	@Test
	public void getCircuitTest() {
		restClient.get(REQRES_ENDPOINT + "/2",false, false)
		          .then().log().all()
		          .assertThat()
		          .statusCode(200);

}
}
