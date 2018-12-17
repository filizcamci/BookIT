package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.DBUtils;
import utilities.Environment;
import utilities.RestUtils;
import utilities.RestUtils.UserType;

public class UserInfoStepDefs {
	String token;
	Response responce;
	String user;
	private List<Map<String, Object>> queryResultList;

	@Given("I am logged reservation api using {string} and {string}")
	public void i_am_logged_reservation_api_using_and(String username, String password) {
		user = username;
		RestAssured.baseURI = Environment.BASE_URI;

		Response res = RestAssured.given().param("email", username).param("password", password).when()
				.get(RestAssured.baseURI + "/sign");
		token = res.jsonPath().get("accessToken");
		System.out.println(token);

	}

	@When("I get the current user information using the me endpoint")
	public void i_get_the_current_user_information_using_the_me_endpoint() {
		RestAssured.basePath = "api/students/me";

		responce = RestAssured.given().header("Authorization", token).and().when().get();
		responce.then().statusCode(200);

	}

	@Then("the information about current user should be returned")
	public void the_information_about_current_user_should_be_returned() {
		String firstname = responce.jsonPath().get("firstName");
		String lastname = responce.jsonPath().get("lastName");
		assertTrue(user.contains(firstname.toLowerCase()));
		assertTrue(user.contains(lastname.toLowerCase()));
	}

	@Given("I am logged reservation api as teacher")
	public void i_am_logged_reservation_api_as_teacher() {
		token = RestUtils.accessToken(UserType.TEACHER);

	}

	@When("I get the user information by id {int} using the student endpoint")
	public void i_get_the_user_information_by_id_using_the_student_endpoint(Integer id) {
		RestAssured.basePath = "api/students/" + id;
		responce = RestAssured.given().header("Authorization", token).and().when().get();
		responce.then().statusCode(200);

	}

	@Then("the correct user information should be returned by the student endpoint")
	public void the_correct_user_information_should_be_returned_by_the_student_endpoint(Map<String, String> user) {
		String expectedFirstName = user.get("firstName");
		String expectedLastName = user.get("lastName");
		String expectedRole = user.get("role");
		String expectedId = user.get("id");

		String actualFistName = responce.jsonPath().get("firstName");
		String actualLastName = responce.jsonPath().get("lastName");
		String actualRole = responce.jsonPath().get("role");
		String actualId = responce.jsonPath().getString("id");

		assertEquals(expectedFirstName, actualFistName);
		assertEquals(expectedLastName, actualLastName);
		assertEquals(expectedId, actualId);
		assertEquals(expectedRole, actualRole);

	}

	@Then("the information about current user should match the user table")
	public void the_information_about_current_user_should_match_the_user_table() {
		// actual data is from responce using rest
		String id = responce.jsonPath().getString("id");
		String sql = "Select * from users where id = " + id;
		// expected is from the database directly using jdbc
		Map<String, Object> rowMap = DBUtils.getRowMap(sql);
		System.out.println(rowMap);

		String expectedFistName = (String) rowMap.get("firstname");
		String expectedLastName = (String) rowMap.get("lastname");
		String expectedRole = (String) rowMap.get("role");

		String actualFirstName = responce.jsonPath().getString("firstName");
		String actualLastName = responce.jsonPath().getString("lastName");
		String actualRole = responce.jsonPath().getString("role");

		assertEquals(expectedFistName, actualFirstName);
		assertEquals(expectedLastName, actualLastName);
		assertTrue(actualRole.endsWith(expectedRole));
	}

	@When("I get all students from users table")
	public void i_get_all_students_from_users_table() {
		String sql = "select  id, firstname, lastname, role from users where role like 'student%';";

		queryResultList = DBUtils.getQueryResultMap(sql);
		System.out.println(queryResultList.size());

	}

	@Then("student service should return same students")
	public void student_service_should_return_same_students() {
		for (Map<String, Object> rowMap : queryResultList) {
			
			RestAssured.basePath = "api/students/" + rowMap.get("id");
			responce = RestAssured.given().header("Authorization", token).and().when().get();
			responce.then().statusCode(200);

			String expectedFistName = (String) rowMap.get("firstname");
			String expectedLastName = (String) rowMap.get("lastname");
			String expectedRole = (String) rowMap.get("role");

			String actualFirstName = responce.jsonPath().getString("firstName");
			String actualLastName = responce.jsonPath().getString("lastName");
			String actualRole = responce.jsonPath().getString("role");

			assertEquals(expectedFistName, actualFirstName);
			assertEquals(expectedLastName, actualLastName);
			assertTrue(actualRole.endsWith(expectedRole));

		}
	}

}

// expexted
// {firstname=Mike, role=teacher, dtype=Student, id=119, team_id=116,
// email=teachervamikemarcus@gmail.com, campus_id=1, lastname=Marcus}
// actual
// {"id":139,"firstName":"Ase","lastName":"Norval","role":"student-team-leader"}

