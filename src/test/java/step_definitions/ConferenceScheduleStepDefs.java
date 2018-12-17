package step_definitions;

import org.junit.Assert;
import org.openqa.selenium.Keys;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.ConfirmationPage;
import pages.Hunt2Page;
import pages.HuntPage;
import pages.MapPage;
import pages.SigninPage;
import utilities.BrowserUtils;
import utilities.Driver;
import utilities.Environment;

public class ConferenceScheduleStepDefs {
	MapPage mapPage = new MapPage();

	@Given("the user logs in using {string} and {string}")
	public void the_user_logs_in_using_and(String username, String password) {
		Driver.getDriver().get(Environment.URL);
		SigninPage signInPage = new SigninPage();
		BrowserUtils.waitFor(2);
		signInPage.email.sendKeys(username);
		signInPage.password.sendKeys(password + Keys.ENTER);
	}

	@When("the user is on the map page")
	public void the_user_is_on_the_map_page() {
		BrowserUtils.waitFor(4);
		String currentURL = Driver.getDriver().getCurrentUrl();
		System.out.println("Current URL: "+currentURL);
		Assert.assertTrue(currentURL.contains("map"));
	}

	@Then("user schedules a conference")
	public void user_schedules_a_conference() {
		mapPage.hunt.click();
		HuntPage huntPage=new HuntPage();
		huntPage.date.sendKeys("12/12/2018");
		huntPage.selectFrom(huntPage.from);
		huntPage.selectTo(huntPage.to);
		huntPage.clickSearch();
		Hunt2Page hunt2Page=new Hunt2Page();
		hunt2Page.book();
		ConfirmationPage confirmationPage=new ConfirmationPage();
		//confirmationPage.confirm.click();
	}

	@Then("user tries to schedule a conference")
	public void user_tries_to_schedule_a_conference() {
		// Write code here that turns the phrase above into concrete actions
	}

	@Then("user schedules a conference for prevoius time period")
	public void user_schedules_a_conference_for_prevoius_time_period() {
		// Write code here that turns the phrase above into concrete actions
		
	}

}
