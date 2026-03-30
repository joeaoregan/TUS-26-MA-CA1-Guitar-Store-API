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

	@BeforeEach
	void setUp() {
		// Clear all data before each test
		guitarRepository.deleteAll();
		brandRepository.deleteAll();
		entityManager.flush();

		// Create fresh test data
		Brand fender = new Brand();
		fender.setName("Fender");
		fender.setCountry("USA");
		fender = brandRepository.save(fender);

		Guitar strat = new Guitar();
		strat.setModelName("Stratocaster");
		strat.setPrice(999.99);
		strat.setManufactureDate(LocalDate.of(2010, 1, 5));
		strat.setBrand(fender);
		guitarRepository.save(strat);

		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@DisplayName("Should find Guitar by modelName")
	void testFindByModelName() {
		Optional<Guitar> result = guitarRepository.findByModelName("Stratocaster");
		assertTrue(result.isPresent());
		assertEquals("Stratocaster", result.get().getModelName());
		assertEquals(999.99, result.get().getPrice());
		assertEquals("Fender", result.get().getBrand().getName());
	}

	@Test
	@DisplayName("Should return empty when not found")
	void testNotFound() {
		Optional<Guitar> result = guitarRepository.findByModelName("NonExistent");
		assertFalse(result.isPresent());
	}
}