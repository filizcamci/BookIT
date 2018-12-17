package utilities;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestUtils {
	public static String token = null;

	public enum UserType {
		TEACHER, MEMBER, LEADER;
	}

	public static String accessToken(UserType type) {
		RestAssured.baseURI = Environment.BASE_URI;
		String email = null;
		String password = null;

		switch (type) {
		case TEACHER:
			email = Environment.TEACHER_USERNAME;
			password = Environment.TEACHER_PASSWORD;
			break;
		case MEMBER:
			email = Environment.MEMBER_USERNAME;
			password = Environment.MEMBER_PASSWORD;
			break;
		case LEADER:
			email = Environment.LEADER_USERNAME;
			password = Environment.LEADER_PASSWORD;
			break;

		}

		Response res = RestAssured.given().param("email", email).param("password", password).when()
				.get(RestAssured.baseURI + "/sign");
		res.then().statusCode(200);
		token = res.jsonPath().get("accessToken");
		System.out.println(token);

		return token;
	}
}
