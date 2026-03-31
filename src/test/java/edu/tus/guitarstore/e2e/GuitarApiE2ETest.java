package edu.tus.guitarstore.e2e;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GuitarApiE2ETest {
	private static final String BASE = "/api/guitarstore/v1";

	@Autowired
	private TestRestTemplate rest;

	// Test 1: Happy Path
	@Test
	void createBrand_thenCreateGuitar_thenFetchByModelName() {
		// Use unique values so we don't collide with data.sql / previous runs
		String brandName = "Fender-" + UUID.randomUUID();
		String modelName = "Stratocaster-" + UUID.randomUUID();

		// 1) Create brand
		BrandDto brand = new BrandDto();
		brand.setName(brandName);
		brand.setCountry("USA");

		ResponseEntity<String> brandCreate = rest.postForEntity(BASE + "/brands", brand, String.class);

		assertEquals(HttpStatus.CREATED, brandCreate.getStatusCode(), "Brand create failed: " + brandCreate.getBody());

		// 2) Create guitar
		GuitarDto guitar = new GuitarDto();
		guitar.setModelName(modelName);
		guitar.setPrice(999.99);
		guitar.setManufactureDate(LocalDate.of(2010, 1, 5)); // REQUIRED by @NotNull
		guitar.setBrandName(brandName);

		ResponseEntity<String> guitarCreate = rest.postForEntity(BASE + "/guitars", guitar, String.class);

		assertEquals(HttpStatus.CREATED, guitarCreate.getStatusCode(),
				"Guitar create failed: " + guitarCreate.getBody());

		// 3) Fetch guitar by model name
		ResponseEntity<GuitarDto> fetched = rest.getForEntity(BASE + "/guitars/{modelName}", GuitarDto.class,
				modelName);

		assertEquals(HttpStatus.OK, fetched.getStatusCode());
		assertNotNull(fetched.getBody());
		assertEquals(modelName, fetched.getBody().getModelName());
		assertEquals(999.99, fetched.getBody().getPrice());
		assertEquals(LocalDate.of(2010, 1, 5), fetched.getBody().getManufactureDate());
		assertEquals(brandName, fetched.getBody().getBrandName());
	}

	// Test #2: duplicate modelName should fail
	@Test
	void creatingDuplicateGuitarModelNameShouldReturnClientError() {
		String brandName = "Gibson-" + UUID.randomUUID();
		String modelName = "LesPaul-" + UUID.randomUUID();

		// Create brand
		BrandDto brand = new BrandDto();
		brand.setName(brandName);
		brand.setCountry("USA");

		ResponseEntity<String> brandCreate = rest.postForEntity(BASE + "/brands", brand, String.class);

		assertEquals(HttpStatus.CREATED, brandCreate.getStatusCode(), "Brand create failed: " + brandCreate.getBody());

		// Create guitar (first time)
		GuitarDto guitar = new GuitarDto();
		guitar.setModelName(modelName);
		guitar.setPrice(1499.99);
		guitar.setManufactureDate(LocalDate.of(2012, 6, 1));
		guitar.setBrandName(brandName);

		ResponseEntity<String> firstCreate = rest.postForEntity(BASE + "/guitars", guitar, String.class);

		assertEquals(HttpStatus.CREATED, firstCreate.getStatusCode(),
				"First guitar create failed: " + firstCreate.getBody());

		// Create guitar (duplicate modelName)
		GuitarDto duplicate = new GuitarDto();
		duplicate.setModelName(modelName); // same -> should fail
		duplicate.setPrice(1399.99);
		duplicate.setManufactureDate(LocalDate.of(2012, 6, 1));
		duplicate.setBrandName(brandName);

		ResponseEntity<String> secondCreate = rest.postForEntity(BASE + "/guitars", duplicate, String.class);

		// Your app might return 400 or 409 depending on exception handling
		assertTrue(secondCreate.getStatusCode().is4xxClientError(), "Expected 4xx for duplicate modelName but got "
				+ secondCreate.getStatusCode() + " body=" + secondCreate.getBody());
	}
	
	// Test #3: validation error returns 400 (missing manufactureDate)
	@Test
	void creatingGuitarWithoutManufactureDateShouldReturn400() {
	    String brandName = "Ibanez-" + UUID.randomUUID();
	    String modelName = "RG-" + UUID.randomUUID();

	    BrandDto brand = new BrandDto();
	    brand.setName(brandName);
	    brand.setCountry("Japan");

	    ResponseEntity<String> brandCreate =
	            rest.postForEntity(BASE + "/brands", brand, String.class);

	    assertEquals(HttpStatus.CREATED, brandCreate.getStatusCode(),
	            "Brand create failed: " + brandCreate.getBody());

	    GuitarDto invalid = new GuitarDto();
	    invalid.setModelName(modelName);
	    invalid.setPrice(899.99);
	    invalid.setManufactureDate(null);   // invalid
	    invalid.setBrandName(brandName);

	    ResponseEntity<String> response =
	            rest.postForEntity(BASE + "/guitars", invalid, String.class);

	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
	            "Expected 400 but got " + response.getStatusCode() + " body=" + response.getBody());
	}
}