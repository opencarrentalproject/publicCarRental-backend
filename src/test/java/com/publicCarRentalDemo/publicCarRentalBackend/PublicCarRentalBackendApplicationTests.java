package com.publicCarRentalDemo.publicCarRentalBackend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
		Gson gson = new Gson();
		JsonObject carToBeAdded = new JsonObject();
		JsonArray infotainmentTypes = new JsonArray();

		carToBeAdded.addProperty("id", "101");
		carToBeAdded.addProperty("brand", "BMW");
		carToBeAdded.addProperty("model", "5.20");
		carToBeAdded.addProperty("constructionYear", "2019");
		carToBeAdded.addProperty("color", "GRAY");
		carToBeAdded.addProperty("fuelType", "Benzin");
		carToBeAdded.addProperty("fuelConsumption", 5);
		carToBeAdded.addProperty("numberOfDoors", 4);
		carToBeAdded.addProperty("numberOfSeats", 4);
		carToBeAdded.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carToBeAdded.add("infotainmentTypes", infotainmentTypes);

		String carToBeAddedAsString = gson.toJson(carToBeAdded);

		given().
				contentType("application/json").
				body(carToBeAddedAsString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedAsString));
	}


	@Test
	public void givenACarWhenCalling_DELETE_endpoint_ThenRespondWithStatusOK() {
		Gson gson = new Gson();
		JsonObject carToBeAdded = new JsonObject();
		JsonArray infotainmentTypes = new JsonArray();

		carToBeAdded.addProperty("id", "101");
		carToBeAdded.addProperty("brand", "BMW");
		carToBeAdded.addProperty("model", "5.20");
		carToBeAdded.addProperty("constructionYear", "2019");
		carToBeAdded.addProperty("color", "GRAY");
		carToBeAdded.addProperty("fuelType", "Benzin");
		carToBeAdded.addProperty("fuelConsumption", 5);
		carToBeAdded.addProperty("numberOfDoors", 4);
		carToBeAdded.addProperty("numberOfSeats", 4);
		carToBeAdded.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carToBeAdded.add("infotainmentTypes", infotainmentTypes);

		String carToBeAddedAsString = gson.toJson(carToBeAdded);

		given().
				contentType("application/json").
				body(carToBeAddedAsString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedAsString));

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
		Gson gson = new Gson();
		JsonObject carToBeAdded = new JsonObject();
		JsonArray infotainmentTypes = new JsonArray();

		carToBeAdded.addProperty("id", "101");
		carToBeAdded.addProperty("brand", "BMW");
		carToBeAdded.addProperty("model", "5.20");
		carToBeAdded.addProperty("constructionYear", "2019");
		carToBeAdded.addProperty("color", "GRAY");
		carToBeAdded.addProperty("fuelType", "Benzin");
		carToBeAdded.addProperty("fuelConsumption", 5);
		carToBeAdded.addProperty("numberOfDoors", 4);
		carToBeAdded.addProperty("numberOfSeats", 4);
		carToBeAdded.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carToBeAdded.add("infotainmentTypes", infotainmentTypes);

		String carToBeAddedAsString = gson.toJson(carToBeAdded);

		given().
				contentType("application/json").
				body(carToBeAddedAsString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedAsString));

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
		Gson gson = new Gson();
		JsonObject carToBeAdded = new JsonObject();
		JsonArray infotainmentTypes = new JsonArray();

		carToBeAdded.addProperty("id", "101");
		carToBeAdded.addProperty("brand", "BMW");
		carToBeAdded.addProperty("model", "5.20");
		carToBeAdded.addProperty("constructionYear", "2019");
		carToBeAdded.addProperty("color", "GRAY");
		carToBeAdded.addProperty("fuelType", "Benzin");
		carToBeAdded.addProperty("fuelConsumption", 5);
		carToBeAdded.addProperty("numberOfDoors", 4);
		carToBeAdded.addProperty("numberOfSeats", 4);
		carToBeAdded.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carToBeAdded.add("infotainmentTypes", infotainmentTypes);

		String carToBeAddedAsString = gson.toJson(carToBeAdded);

		JsonObject carAfterBeingUpdated = new JsonObject();

		carAfterBeingUpdated.addProperty("id", "101");
		carAfterBeingUpdated.addProperty("brand", "BMW");
		carAfterBeingUpdated.addProperty("model", "5.20");
		carAfterBeingUpdated.addProperty("constructionYear", "2019");
		carAfterBeingUpdated.addProperty("color", "BLUE");
		carAfterBeingUpdated.addProperty("fuelType", "Benzin");
		carAfterBeingUpdated.addProperty("fuelConsumption", 7);
		carAfterBeingUpdated.addProperty("numberOfDoors", 4);
		carAfterBeingUpdated.addProperty("numberOfSeats", 4);
		carAfterBeingUpdated.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carAfterBeingUpdated.add("infotainmentTypes", infotainmentTypes);

		String carAfterBeingUpdatedAsString = gson.toJson(carAfterBeingUpdated);

		given().
				contentType("application/json").
				body(carToBeAddedAsString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedAsString));

		String updatedCarID = "101";

		given().
				contentType("application/json").
				body(carAfterBeingUpdatedAsString).
				when().
				put("/cars/" + updatedCarID).
				then().
				assertThat().
				statusCode(NO_CONTENT.value());
	}

	@Test
	public void givenNonMatchingVINWhenCalling_UPDATE_endpoint_ThenRespondWithStatusNotFound() {
		Gson gson = new Gson();
		JsonObject carToBeAdded = new JsonObject();
		JsonArray infotainmentTypes = new JsonArray();

		carToBeAdded.addProperty("id", "101");
		carToBeAdded.addProperty("brand", "BMW");
		carToBeAdded.addProperty("model", "5.20");
		carToBeAdded.addProperty("constructionYear", "2019");
		carToBeAdded.addProperty("color", "GRAY");
		carToBeAdded.addProperty("fuelType", "Benzin");
		carToBeAdded.addProperty("fuelConsumption", 5);
		carToBeAdded.addProperty("numberOfDoors", 4);
		carToBeAdded.addProperty("numberOfSeats", 4);
		carToBeAdded.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carToBeAdded.add("infotainmentTypes", infotainmentTypes);

		String carToBeAddedAsString = gson.toJson(carToBeAdded);

		JsonObject carAfterBeingUpdated = new JsonObject();

		carAfterBeingUpdated.addProperty("id", "101");
		carAfterBeingUpdated.addProperty("brand", "BMW");
		carAfterBeingUpdated.addProperty("model", "5.20");
		carAfterBeingUpdated.addProperty("constructionYear", "2019");
		carAfterBeingUpdated.addProperty("color", "BLUE");
		carAfterBeingUpdated.addProperty("fuelType", "Benzin");
		carAfterBeingUpdated.addProperty("fuelConsumption", 7);
		carAfterBeingUpdated.addProperty("numberOfDoors", 4);
		carAfterBeingUpdated.addProperty("numberOfSeats", 4);
		carAfterBeingUpdated.addProperty("numberOfCylinders", 8);

		infotainmentTypes.add("NAVIGATION");
		infotainmentTypes.add("AIRCONDITION");
		carAfterBeingUpdated.add("infotainmentTypes", infotainmentTypes);

		String carAfterBeingUpdatedAsString = gson.toJson(carAfterBeingUpdated);

		given().
				contentType("application/json").
				body(carToBeAddedAsString).
				when().
				post("/cars").
				then().
				assertThat().
				body(equalTo(carToBeAddedAsString));

		String nonMatchingCarID = "202";

		given().
				contentType("application/json").
				body(carAfterBeingUpdatedAsString).
				when().
				put("/cars/" + nonMatchingCarID).
				then().
				assertThat().
				statusCode(NOT_FOUND.value());
	}
}