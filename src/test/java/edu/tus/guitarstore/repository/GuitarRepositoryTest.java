package edu.tus.guitarstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.entity.Guitar;

@ExtendWith(MockitoExtension.class)
@DisplayName("GuitarRepository Unit Tests")
public class GuitarRepositoryTest {

	@Mock
	private GuitarRepository guitarRepository;

	private Guitar guitar;
	private Brand brand;

	@BeforeEach
	void setUp() {
		// Initialize test data
		brand = new Brand();
		brand.setId(1L);
		brand.setName("Fender");

		guitar = new Guitar();
		guitar.setId(1L);
		guitar.setModelName("Stratocaster");
		guitar.setPrice(999.99);
		guitar.setManufactureDate(LocalDate.of(2010, 1, 5));
		guitar.setBrand(brand);
	}

	@Test
	@DisplayName("Should return Optional containing Guitar when model name is found")
	void testFindByModelNameSuccess() {
		// Arrange
		String modelName = "Stratocaster";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.of(guitar));

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName(modelName);

		// Assert
		assertTrue(result.isPresent(), "Optional should contain a guitar");
		assertEquals(guitar.getModelName(), result.get().getModelName(), "Model name should match");
		assertEquals(guitar.getPrice(), result.get().getPrice(), "Price should match");
		assertEquals(guitar.getManufactureDate(), result.get().getManufactureDate(), "Manufacture date should match");
		assertEquals(guitar.getBrand().getName(), result.get().getBrand().getName(), "Brand name should match");
	}

	@Test
	@DisplayName("Should return empty Optional when model name is not found")
	void testFindByModelNameNotFound() {
		// Arrange
		String modelName = "NonExistentModel";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.empty());

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName(modelName);

		// Assert
		assertFalse(result.isPresent(), "Optional should be empty");
		assertTrue(result.isEmpty(), "Result should be empty when guitar not found");
	}

	@Test
	@DisplayName("Should correctly handle multiple different model names")
	void testFindByModelNameMultipleCalls() {
		// Arrange
		String modelName1 = "Stratocaster";
		String modelName2 = "Telecaster";

		Guitar guitar2 = new Guitar();
		guitar2.setId(2L);
		guitar2.setModelName("Telecaster");
		guitar2.setPrice(799.99);
		guitar2.setManufactureDate(LocalDate.of(2015, 6, 10));
		guitar2.setBrand(brand);

		when(guitarRepository.findByModelName(modelName1)).thenReturn(Optional.of(guitar));
		when(guitarRepository.findByModelName(modelName2)).thenReturn(Optional.of(guitar2));

		// Act
		Optional<Guitar> result1 = guitarRepository.findByModelName(modelName1);
		Optional<Guitar> result2 = guitarRepository.findByModelName(modelName2);

		// Assert
		assertTrue(result1.isPresent(), "First guitar should be found");
		assertTrue(result2.isPresent(), "Second guitar should be found");
		assertEquals("Stratocaster", result1.get().getModelName(), "First model name should be Stratocaster");
		assertEquals("Telecaster", result2.get().getModelName(), "Second model name should be Telecaster");
		assertEquals(999.99, result1.get().getPrice(), "First price should be 999.99");
		assertEquals(799.99, result2.get().getPrice(), "Second price should be 799.99");
	}

	@Test
	@DisplayName("Should return Optional with correct Guitar entity attributes")
	void testFindByModelNameEntityIntegrity() {
		// Arrange
		String modelName = "Stratocaster";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.of(guitar));

		// Act
		Optional<Guitar> result = guitarRepository.findByModelName(modelName);

		// Assert
		assertTrue(result.isPresent(), "Guitar should be found");
		Guitar foundGuitar = result.get();
		assertEquals(1L, foundGuitar.getId(), "Guitar ID should be 1");
		assertEquals("Stratocaster", foundGuitar.getModelName(), "Model name should match");
		assertEquals(999.99, foundGuitar.getPrice(), "Price should match");
		assertEquals(LocalDate.of(2010, 1, 5), foundGuitar.getManufactureDate(), "Manufacture date should match");
		assertEquals("Fender", foundGuitar.getBrand().getName(), "Brand should be Fender");
	}
}