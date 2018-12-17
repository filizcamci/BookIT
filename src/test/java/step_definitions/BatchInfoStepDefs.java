package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pages.MapPage;
import utilities.BrowserUtils;
import utilities.DBUtils;

public class BatchInfoStepDefs {
	private List<Map<String, Object>> batches;

	@Given("I retrieve the batches information")
	public void i_retrieve_the_batches_information() {
		String query = "select * from batch";
		batches = DBUtils.getQueryResultMap(query);
		System.out.println(batches);
	}

	@Then("following batches should be displayed")
	public void following_batches_should_be_displayed(Map<String, String> eBatches) {
		System.out.println(eBatches);

		/*
		 * We need to compare values from different source 1. expected value from
		 * feature file data format: Map<ek, ev> 2. actual from databse data format:
		 * List<Map <col av>>
		 * 
		 * ek ---> av
		 * 
		 * 
		 */
		// [
		// {number=8, isgraduated=false},
		// {number=7, isgraduated=true}
		// ]

		// {7=true, 8=false}

		for (String key : eBatches.keySet()) {
			boolean found = false;
			String eGrad = eBatches.get(key);

			for (Map<String, Object> dbRow : batches) {
				int number = (int) dbRow.get("number");
				Boolean grad = (Boolean) dbRow.get("isgraduated");

				if (String.valueOf(number).equals(key) && String.valueOf(grad).equals(eGrad)) {
					found = true;
					continue;
				}
			}

			assertTrue("did not find: " + key + " " + eGrad, found);
		}
	}

	@Then("correct campus should be displayed for {string}")
	public void correct_campus_should_be_displayed_for(String email) {
		MapPage mapPage = new MapPage();
		BrowserUtils.waitFor(2);
		
		String query = "select location from campus where id = (select campus_id from users\n"
				+ "where email = '"+email+"');";
		String expectedCampus = (String) DBUtils.getCellValue(query);
		String actualCampus = mapPage.campus.getText();
		assertEquals("Campus name did not match", expectedCampus, actualCampus);
	}

}
