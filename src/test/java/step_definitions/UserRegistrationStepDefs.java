package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pages.SigninPage;
import utilities.DBUtils;
import utilities.Driver;
import utilities.Environment;
import utilities.RestUtils;
import utilities.RestUtils.UserType;

public class UserRegistrationStepDefs {
	private String token;
	private Response responce;
	private String email;
	private String firstName;
	private String lastName;
	private String password = "password";

	@Given("I am logged reservation api as team lead")
	public void i_am_logged_reservation_api_as_team_lead() {
		token = RestUtils.accessToken(UserType.LEADER);

	}

	@When("I try to register a new user")
	public void i_try_to_register_a_new_user() {
		responce = RestAssured.given().header("Authorization", RestUtils.token)
				// .param("first-name", "Johnny")
				.param("last-name", "Cage").param("email", "johnnycage@gmail.com").param("password", "subzerobest")
				.param("role", "student-team-member").param("batch-number", "8").param("team-name", "CodeHunters")
				.param("campus-location", "VA").when().post(RestAssured.baseURI + "/api/students/student");
	}

	@Then("system should return only teacher can register message")
	public void system_should_return_only_teacher_can_register_message() {
		responce.then().statusCode(403);
		assertEquals("only teacher allowed to modify database.", responce.body().asString());
	}

	@Given("I am logged reservation api as team member")
	public void i_am_logged_reservation_api_as_team_member() {
		token = RestUtils.accessToken(UserType.MEMBER);
	}

	@Then("the teacher should be authorised to add users")
	public void the_teacher_should_be_authorised_to_add_users() {
		responce.then().statusCode(422);
		assertEquals("pay attention to how you specifying first-name in the query.", responce.body().asString());

	}

	@When("I try to register a new user with existing email")
	public void i_try_to_register_a_new_user_with_existing_email() {
		// go tpo database and get a existing user
		// using the user information from databse, create new querypath
		// post

		String sql = "select  email from users\n" + "where email is not null\n" + " limit 1;";
		email = (String) DBUtils.getCellValue(sql);
		assertNotNull(email);

		responce = RestAssured.given().header("Authorization", RestUtils.token).param("first-name", "Johnny")
				.param("last-name", "Cage").param("email", email).param("password", "subzerobest")
				.param("role", "student-team-member").param("batch-number", "8").param("team-name", "CodeHunters")
				.param("campus-location", "VA").when().post(RestAssured.baseURI + "/api/students/student");

	}

	@Then("user with same email exists message should be returned")
	public void user_with_same_email_exists_message_should_be_returned() {
		responce.then().statusCode(422);
		assertEquals("user with the email: " + email + " is already exist.", responce.body().asString());

	}

	@When("I register a new user")
	public void i_register_a_new_user() {
		Faker faker = new Faker();
		email = getNewEMail();

		firstName = faker.name().firstName();
		lastName = faker.name().lastName();
		responce = RestAssured.given().header("Authorization", RestUtils.token).param("first-name", firstName)
				.param("last-name", lastName).param("email", email).param("password", password)
				.param("role", "student-team-member").param("batch-number", "8").param("team-name", "CodeHunters")
				.param("campus-location", "VA").when().post(RestAssured.baseURI + "/api/students/student");

	}

	private String getNewEMail() {
		Faker faker = new Faker();
		String email = faker.internet().emailAddress();
		List<Object> columnData = DBUtils.getColumnData("select email from users;", "email");
		if (columnData.contains(email)) {
			return email = getNewEMail();
		} else {
			return email;
		}

	}

	@Then("new user should registered")
	public void new_user_should_registered() {
		responce.then().statusCode(201);
		assertEquals("user " + firstName + " " + lastName + " has been added to database.",
				responce.body().asString());

	}

	@Then("I should be able to login as the new user")
	public void i_should_be_able_to_login_as_the_new_user() {
	    SigninPage signinPage = new SigninPage();
		Driver.getDriver().get(Environment.URL);
		SigninPage signInPage = new SigninPage();
		signInPage.email.sendKeys(email);
		signInPage.password.sendKeys(password+ Keys.ENTER);

		String url = "https://cybertek-reservation-qa.herokuapp.com/map";
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
		wait.until(ExpectedConditions.urlToBe(url));
		assertEquals(url, Driver.getDriver().getCurrentUrl());
				
	}

}
