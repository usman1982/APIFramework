/*
package stepDefinations;

import cucumber.api.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable
	{
		StepDefination m=new StepDefination();
		
		if(StepDefination.place_id == null)
		{
	
			m.add_Place_Payload_with("Shetty", "FRENCH", "Asia");
			m.user_calls_with_http_request("addPlaceAPI", "POST");
			m.verify_place_Id_created_maps_to_using("Shetty", "getPlaceAPI");
			
		}
		
	}
	
}
*/