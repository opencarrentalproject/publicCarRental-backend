package com.publicCarRentalDemo.publicCarRentalBackend;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = PublicCarRentalBackendApplicationTests.Initializer.class)
public class PublicCarRentalBackendApplicationTests {

	@ClassRule
	public static GenericContainer mongodb = new GenericContainer<>("mongo:latest")
			.withExposedPorts(27017);

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues values = TestPropertyValues.of(
					"spring.data.mongodb.host=" + mongodb.getContainerIpAddress(),
					"spring.data.mongodb.port=" + mongodb.getMappedPort(27017)
			);
			values.applyTo(configurableApplicationContext);
		}
	}

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}


	@Test
	public void contextLoads() {
	}

	@Test
	public void givenNonMatchingVINWhenCalling_GET_endpoint_ThenRespondWithStatusNotFound() {
		String nonMatchingVIN = "nonMatchingVIN";

		given()
				.when()
				.get("/cars/" + nonMatchingVIN)
				.then()
				.log().ifValidationFails()
				.statusCode(NOT_FOUND.value());
	}

	@Test
	public void givenACarWhenCalling_POST_endpoint_ThenRespondWithStatusOK() {
		String carToBeAddedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"GRAY\",\"fuelType\":\"Benzin\",\"fuelConsumption\":5," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		given().
				contentType("application/json").
				body(carToBeAddedJSONString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedJSONString));
	}

	@Test
	public void givenACarWhenCalling_DELETE_endpoint_ThenRespondWithStatusOK() {
		String carToBeAddedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"GRAY\",\"fuelType\":\"Benzin\",\"fuelConsumption\":5," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		given().
				contentType("application/json").
				body(carToBeAddedJSONString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedJSONString));

		String carIDToBeDeleted = "101";

		given().
				contentType("application/json").
				when().
				delete("/cars/" + carIDToBeDeleted).
				then().
				assertThat().
				statusCode(OK.value());
	}

	@Test
	public void givenNonMatchingVINWhenCalling_DELETE_endpoint_ThenRespondWithStatusNotFound() {
		String carToBeAddedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"GRAY\",\"fuelType\":\"Benzin\",\"fuelConsumption\":5," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		given().
				contentType("application/json").
				body(carToBeAddedJSONString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedJSONString));

		String nonMatchingVIN = "5";

		given().
				contentType("application/json").
				when().
				delete("/cars/" + nonMatchingVIN).
				then().
				assertThat().
				statusCode(NOT_FOUND.value());
	}

	@Test
	public void givenACarWhenCalling_UPDATE_endpoint_ThenRespondWithStatusNoContent() {
		String carToBeAddedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"GRAY\",\"fuelType\":\"Benzin\",\"fuelConsumption\":5," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		String carAfterBeingUpdatedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"BLUE\",\"fuelType\":\"Benzin\",\"fuelConsumption\":7," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		given().
				contentType("application/json").
				body(carToBeAddedJSONString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedJSONString));

		String updatedCarID = "101";

		given().
				contentType("application/json").
				body(carAfterBeingUpdatedJSONString).
				when().
				put("/cars/" + updatedCarID).
				then().
				assertThat().
				statusCode(NO_CONTENT.value());
	}

	@Test
	public void givenNonMatchingVINWhenCalling_UPDATE_endpoint_ThenRespondWithStatusNotFound() {
		String carToBeAddedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"GRAY\",\"fuelType\":\"Benzin\",\"fuelConsumption\":5," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		String carAfterBeingUpdatedJSONString = "{\"id\":\"101\",\"brand\":\"BMW\",\"model\":\"5.20\"," +
				"\"constructionYear\":\"2019\",\"color\":\"BLUE\",\"fuelType\":\"Benzin\",\"fuelConsumption\":7," +
				"\"numberOfDoors\":4,\"numberOfSeats\":4,\"numberOfCylinders\":8,\"infotainmentTypes\":[\"NAVIGATION\"," +
				"\"AIRCONDITION\"]}";

		given().
				contentType("application/json").
				body(carToBeAddedJSONString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedJSONString));

		String nonMatchingCarID = "202";

		given().
				contentType("application/json").
				body(carAfterBeingUpdatedJSONString).
				when().
				put("/cars/" + nonMatchingCarID).
				then().
				assertThat().
				statusCode(NOT_FOUND.value());
	}
}