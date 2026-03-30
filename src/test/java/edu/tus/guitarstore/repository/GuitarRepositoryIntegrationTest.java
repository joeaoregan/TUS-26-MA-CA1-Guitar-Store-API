package edu.tus.guitarstore.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import edu.tus.guitarstore.config.TestJpaAuditingConfig;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.entity.Guitar;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
@DisplayName("GuitarRepository Integration Tests")
public class GuitarRepositoryIntegrationTest {

	@Autowired
	private GuitarRepository guitarRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private TestEntityManager entityManager;

//	@BeforeEach
//	void setUp() {
//		// Clear all data before each test
//		guitarRepository.deleteAll();
//		brandRepository.deleteAll();
//		entityManager.flush();
//
//		// Create fresh test data
//		Brand fender = new Brand();
//		fender.setName("Fender");
//		fender.setCountry("USA");
//		fender = brandRepository.save(fender);
//
//		Guitar strat = new Guitar();
//		strat.setModelName("Stratocaster");
//		strat.setPrice(999.99);
//		strat.setManufactureDate(LocalDate.of(2010, 1, 5));
//		strat.setBrand(fender);
//		guitarRepository.save(strat);
//
//		entityManager.flush();
//		entityManager.clear();
//	}


	private Brand fenderBrand;
	private Brand gibsonBrand;
	private Guitar stratocasterGuitar;
	private Guitar telecasterGuitar;

	@BeforeEach
	void setUp() {
		// Clear all data before each test
		guitarRepository.deleteAll();
		brandRepository.deleteAll();
		entityManager.flush();
		
		// Create and persist Brand entities
		fenderBrand = new Brand();
		fenderBrand.setName("Fender");
		fenderBrand.setCountry("USA");
		fenderBrand = brandRepository.save(fenderBrand);

		gibsonBrand = new Brand();
		gibsonBrand.setName("Gibson");
		gibsonBrand.setCountry("USA");
		gibsonBrand = brandRepository.save(gibsonBrand);

		// Create and persist Guitar entities with Fender brand
		stratocasterGuitar = new Guitar();
		stratocasterGuitar.setModelName("Stratocaster");
		stratocasterGuitar.setPrice(999.99);
		stratocasterGuitar.setManufactureDate(LocalDate.of(2010, 1, 5));
		stratocasterGuitar.setBrand(fenderBrand);
		stratocasterGuitar = guitarRepository.save(stratocasterGuitar);

		telecasterGuitar = new Guitar();
		telecasterGuitar.setModelName("Telecaster");
		telecasterGuitar.setPrice(799.99);
		telecasterGuitar.setManufactureDate(LocalDate.of(2015, 6, 10));
		telecasterGuitar.setBrand(fenderBrand);
		telecasterGuitar = guitarRepository.save(telecasterGuitar);

		// Flush to ensure database operations complete
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@DisplayName("Should find Guitar by exact modelName match")
	void testFindByModelNameExactMatch() {
		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");

		// Assert
		assertTrue(result.isPresent(), "Guitar should be found");
		Guitar foundGuitar = result.get();
		assertEquals("Stratocaster", foundGuitar.getModelName(), "Model name should match");
		assertEquals(999.99, foundGuitar.getPrice(), "Price should match");
		assertEquals(LocalDate.of(2010, 1, 5), foundGuitar.getManufactureDate(), 
			"Manufacture date should match");
		assertNotNull(foundGuitar.getBrand(), "Brand should not be null");
		assertEquals("Fender", foundGuitar.getBrand().getName(), "Brand name should be Fender");
	}

	@Test
	@DisplayName("Should return empty Optional when modelName does not exist")
	void testFindByModelNameNotFound() {
		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("NonExistentModel");

		// Assert
		assertFalse(result.isPresent(), "Optional should be empty");
		assertTrue(result.isEmpty(), "No guitar should be found");
	}

	@Test
	@DisplayName("Should find correct Guitar among multiple records")
	void testFindByModelNameAmongMultipleRecords() {
		// Act - Search for Telecaster when multiple guitars exist
		Optional<Guitar> result = guitarRepository.findByModelName("Telecaster");

		// Assert - Should return Telecaster, not Stratocaster
		assertTrue(result.isPresent(), "Telecaster should be found");
		Guitar foundGuitar = result.get();
		assertEquals("Telecaster", foundGuitar.getModelName(), "Model name should be Telecaster");
		assertEquals(799.99, foundGuitar.getPrice(), "Price should be 799.99");
		assertNotNull(foundGuitar.getId(), "Guitar ID should be set");
		assertEquals(stratocasterGuitar.getId() + 1, foundGuitar.getId(), 
			"Telecaster ID should be different from Stratocaster");
	}

	@Test
	@DisplayName("Should preserve Brand relationship when finding Guitar")
	void testFindByModelNamePreservesBrandRelationship() {
		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");

		// Assert
		assertTrue(result.isPresent(), "Guitar should be found");
		Guitar foundGuitar = result.get();
		
		// Verify brand is properly loaded
		assertNotNull(foundGuitar.getBrand(), "Brand should not be null");
		assertEquals(fenderBrand.getId(), foundGuitar.getBrand().getId(), 
			"Brand ID should match");
		assertEquals("Fender", foundGuitar.getBrand().getName(), 
			"Brand name should be Fender");
		assertEquals("USA", foundGuitar.getBrand().getCountry(), 
			"Brand country should be USA");
	}

	@Test
	@DisplayName("Should handle case-sensitive modelName search")
	void testFindByModelNameCaseSensitivity() {
		// Act - Search with different case
		Optional<Guitar> resultLowerCase = guitarRepository.findByModelName("stratocaster");
		Optional<Guitar> resultUpperCase = guitarRepository.findByModelName("STRATOCASTER");
		Optional<Guitar> resultMixedCase = guitarRepository.findByModelName("Stratocaster");

		// Assert
		assertFalse(resultLowerCase.isPresent(), 
			"Lowercase search should not find the guitar (case-sensitive)");
		assertFalse(resultUpperCase.isPresent(), 
			"Uppercase search should not find the guitar (case-sensitive)");
		assertTrue(resultMixedCase.isPresent(), 
			"Exact case match should find the guitar");
	}

	@Test
	@DisplayName("Should retrieve all Guitar attributes correctly from database")
	void testFindByModelNameRetrievesAllAttributes() {
		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");

		// Assert
		assertTrue(result.isPresent(), "Guitar should be found");
		Guitar foundGuitar = result.get();

		// Verify all attributes are correctly mapped from database
		assertNotNull(foundGuitar.getId(), "ID should be set");
		assertEquals("Stratocaster", foundGuitar.getModelName(), "Model name should be set");
		assertEquals(999.99, foundGuitar.getPrice(), "Price should be correctly persisted");
		assertEquals(LocalDate.of(2010, 1, 5), foundGuitar.getManufactureDate(), 
			"Manufacture date should be correctly persisted");
		assertNotNull(foundGuitar.getBrand(), "Brand should be loaded");
		assertTrue(foundGuitar.getBrand().getId() > 0, "Brand ID should be positive");
	}

	@Test
	@DisplayName("Should return independent Optional instances for separate queries")
	void testFindByModelNameReturnsIndependentOptionals() {
		// Act - Execute same query twice
		Optional<Guitar> firstResult = guitarRepository.findByModelName("Stratocaster");
		Optional<Guitar> secondResult = guitarRepository.findByModelName("Stratocaster");

		// Assert - Both should return present optionals with same data
		assertTrue(firstResult.isPresent(), "First query should find guitar");
		assertTrue(secondResult.isPresent(), "Second query should find guitar");
		assertEquals(firstResult.get().getId(), secondResult.get().getId(), 
			"Both results should have same ID");
		assertEquals(firstResult.get().getModelName(), secondResult.get().getModelName(), 
			"Both results should have same model name");
	}

	@Test
	@DisplayName("Should only return Guitar with matching modelName, not similar names")
	void testFindByModelNameDoesNotReturnSimilarNames() {
		// Arrange - Add a guitar with similar name
		Guitar lesPaulGuitar = new Guitar();
		lesPaulGuitar.setModelName("StratocasterPlus");
		lesPaulGuitar.setPrice(1299.99);
		lesPaulGuitar.setManufactureDate(LocalDate.of(2018, 3, 15));
		lesPaulGuitar.setBrand(fenderBrand);
		guitarRepository.save(lesPaulGuitar);
		entityManager.flush();
		entityManager.clear();

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");

		// Assert - Should find exact match only, not "StratocasterPlus"
		assertTrue(result.isPresent(), "Exact match should be found");
		assertEquals("Stratocaster", result.get().getModelName(), 
			"Should return exact model name match");
		assertNotNull(result.get().getId(), "Should return correct guitar ID");
	}

	@Test
	@DisplayName("Should work with special characters in modelName")
	void testFindByModelNameWithSpecialCharacters() {
		// Arrange - Create guitar with special characters
		Guitar specialGuitar = new Guitar();
		specialGuitar.setModelName("Stratocaster-USA");
		specialGuitar.setPrice(1199.99);
		specialGuitar.setManufactureDate(LocalDate.of(2019, 5, 20));
		specialGuitar.setBrand(fenderBrand);
		guitarRepository.save(specialGuitar);
		entityManager.flush();
		entityManager.clear();

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster-USA");

		// Assert
		assertTrue(result.isPresent(), "Guitar with special characters should be found");
		assertEquals("Stratocaster-USA", result.get().getModelName(), 
			"Special characters should be preserved");
	}

	@Test
	@DisplayName("Should verify unique constraint on modelName at database level")
	void testFindByModelNameVerifiesUniqueConstraint() {
		// Arrange - Create duplicate guitar
		Guitar duplicateGuitar = new Guitar();
		duplicateGuitar.setModelName("Stratocaster");
		duplicateGuitar.setPrice(850.00);
		duplicateGuitar.setManufactureDate(LocalDate.of(2020, 7, 25));
		duplicateGuitar.setBrand(gibsonBrand);

		// Act & Assert - Attempting to save duplicate should violate unique constraint
		try {
			guitarRepository.save(duplicateGuitar);
			entityManager.flush();
			assertTrue(false, "Should have thrown exception for duplicate modelName");
		} catch (Exception e) {
			// Expected: Unique constraint violation
			assertTrue(e.getMessage().contains("UNIQUE constraint failed") || 
				e.getMessage().contains("Duplicate") ||
				e.getMessage().contains("unique"),
				"Exception should indicate unique constraint violation");
		}
	}

	@Test
	@DisplayName("Should correctly handle NULL modelName field")
	void testFindByModelNameWithNullValue() {
		// Arrange - Create guitar with null modelName
		Guitar nullModelGuitar = new Guitar();
		nullModelGuitar.setModelName(null);
		nullModelGuitar.setPrice(500.00);
		nullModelGuitar.setManufactureDate(LocalDate.of(2010, 1, 1));
		nullModelGuitar.setBrand(fenderBrand);
		guitarRepository.save(nullModelGuitar);
		entityManager.flush();
		entityManager.clear();

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName(null);

		// Assert
		assertFalse(result.isPresent(), "Searching for null should not return anything");
	}
	
	@Test
	@DisplayName("Should verify lazy loading of Brand relationship")
	void testFindByModelNameBrandLazyLoading() {
		// Act
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");

		// Assert
		assertTrue(result.isPresent(), "Guitar should be found");
		Guitar foundGuitar = result.get();

		// Access brand after query (tests lazy loading)
		assertNotNull(foundGuitar.getBrand(), "Brand should be accessible");
		assertEquals("Fender", foundGuitar.getBrand().getName(), 
			"Brand name should be loaded lazily");
	}
}