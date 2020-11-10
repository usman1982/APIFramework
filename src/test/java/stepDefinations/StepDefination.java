package stepDefinations;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefination extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("^Add Place Payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException  {
	
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.useRelaxedHTTPSValidation();
				 
		
		res=given().spec(requestSpecification())
		.body(data.addPlacePayLoad(name, language, address));
		
	}

	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_http_request(String resource, String method) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		if(method.equalsIgnoreCase("POST"))
			response =res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource());

		
	}

	@Then("^the API call got success with status code (\\d+)$")
	public void the_API_call_got_success_with_status_code(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		assertEquals(response.getStatusCode(),200);
	}

	@Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void in_response_body_is(String key, String ExpValue) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions

	    assertEquals(getJsonPath(response, key),ExpValue);
	}
	
	@Then("^verify place_Id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions

		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName=getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
	}
	
	
	@Given("^DeletePlace Payload$")
	public void deleteplace_Payload() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions

		res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
		
	}
		
}
