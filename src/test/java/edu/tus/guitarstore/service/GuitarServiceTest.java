package edu.tus.guitarstore.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.entity.Guitar;
import edu.tus.guitarstore.exception.GuitarAlreadyExistsException;
import edu.tus.guitarstore.exception.ResourceNotFoundException;
import edu.tus.guitarstore.repository.BrandRepository;
import edu.tus.guitarstore.repository.GuitarRepository;
import edu.tus.guitarstore.service.impl.GuitarServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("GuitarServiceImpl Unit Tests")
public class GuitarServiceTest {

	@Mock
	private GuitarRepository guitarRepository;

	@Mock
	private BrandRepository brandRepository;

	@InjectMocks
	private GuitarServiceImpl guitarService;

	private Guitar guitar;
	private GuitarDto guitarDto;
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

		guitarDto = new GuitarDto();
		guitarDto.setModelName("Stratocaster");
		guitarDto.setPrice(999.99);
		guitarDto.setManufactureDate(LocalDate.of(2010, 1, 5));
		guitarDto.setBrandName("Fender");
	}

	@Test
	@DisplayName("Should return GuitarDto when guitar is found by model name")
	void testFetchGuitarSuccess() {
		// Arrange
		String modelName = "Stratocaster";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.of(guitar));

		// Act
		GuitarDto result = guitarService.fetchGuitar(modelName);

		// Assert
		assertNotNull(result, "GuitarDto should not be null");
		assertEquals(guitarDto.getModelName(), result.getModelName(), "Model names should match");
		assertEquals(guitarDto.getPrice(), result.getPrice(), "Prices should match");
		assertEquals(guitarDto.getManufactureDate(), result.getManufactureDate(), "Manufacture dates should match");
		assertEquals(guitarDto.getBrandName(), result.getBrandName(), "Brand names should match");

		// Verify the mock was called
		verify(guitarRepository).findByModelName(modelName);
	}

	@Test
	@DisplayName("Should throw ResourceNotFoundException when guitar is not found by model name")
	void testFetchGuitarNotFound() {
		// Arrange
		String modelName = "NonExistentGuitar";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> guitarService.fetchGuitar(modelName),
				"Should throw ResourceNotFoundException when guitar is not found");

		// Verify the mock was called
		verify(guitarRepository).findByModelName(modelName);
	}

	@Test
	@DisplayName("Should correctly map guitar entity to GuitarDto with all fields")
	void testFetchGuitarMappingAllFields() {
		// Arrange
		String modelName = "Stratocaster";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.of(guitar));

		// Act
		GuitarDto result = guitarService.fetchGuitar(modelName);

		// Assert
		assertNotNull(result, "GuitarDto should not be null");
		assertEquals("Stratocaster", result.getModelName(), "Model name should be correctly mapped");
		assertEquals(999.99, result.getPrice(), "Price should be correctly mapped");
		assertEquals(LocalDate.of(2010, 1, 5), result.getManufactureDate(),
				"Manufacture date should be correctly mapped");
		assertEquals("Fender", result.getBrandName(), "Brand name should be correctly mapped from related entity");
	}

	@Test
	@DisplayName("Should return empty Optional when searching for non-existent model")
	void testFetchGuitarEmptyOptional() {
		// Arrange
		String modelName = "InvalidModel";
		when(guitarRepository.findByModelName(modelName)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> guitarService.fetchGuitar(modelName));
		verify(guitarRepository).findByModelName(modelName);
		verify(guitarRepository, never()).findAll();
	}

	// Example 2: Original
	@Test
	@DisplayName("Should successfully create guitar when brand is found and guitar does not already exist")
	void testCreateGuitarSuccess() {
		// Arrange
		GuitarDto newGuitarDto = new GuitarDto();
		newGuitarDto.setModelName("Telecaster");
		newGuitarDto.setPrice(799.99);
		newGuitarDto.setManufactureDate(LocalDate.of(2015, 6, 10));
		newGuitarDto.setBrandName("Fender");

		// Mock: Guitar does not already exist
		when(guitarRepository.findByModelName("Telecaster")).thenReturn(Optional.empty());

		// Mock: Brand is found
		when(brandRepository.findByName("Fender")).thenReturn(Optional.of(brand));

		// Mock: Save operation completes successfully
		Guitar savedGuitar = new Guitar();
		savedGuitar.setId(2L);
		savedGuitar.setModelName("Telecaster");
		savedGuitar.setPrice(799.99);
		savedGuitar.setManufactureDate(LocalDate.of(2015, 6, 10));
		savedGuitar.setBrand(brand);
		when(guitarRepository.save(any(Guitar.class))).thenReturn(savedGuitar);

		// Act
		guitarService.createGuitar(newGuitarDto);

		// Assert
		verify(guitarRepository).findByModelName("Telecaster");
		verify(brandRepository).findByName("Fender");
		verify(guitarRepository).save(any(Guitar.class));
	}

	// Example 2: Modified
	@Test
	@DisplayName("Should throw GuitarAlreadyExistsException when guitar model already exists")
	void testCreateGuitarAlreadyExists() {
		// Arrange
		GuitarDto duplicateGuitarDto = new GuitarDto();
		duplicateGuitarDto.setModelName("Stratocaster");
		duplicateGuitarDto.setPrice(999.99);
		duplicateGuitarDto.setManufactureDate(LocalDate.of(2010, 1, 5));
		duplicateGuitarDto.setBrandName("Fender");

		// Mock: Guitar already exists
		when(guitarRepository.findByModelName("Stratocaster")).thenReturn(Optional.of(guitar));

		// Act & Assert
		assertThrows(GuitarAlreadyExistsException.class, () -> guitarService.createGuitar(duplicateGuitarDto),
				"Should throw GuitarAlreadyExistsException when guitar already exists");

		// Verify that we only checked for existing guitar and did not proceed further
		verify(guitarRepository).findByModelName("Stratocaster");
		verify(brandRepository, never()).findByName(anyString());
		verify(guitarRepository, never()).save(any(Guitar.class));
	}

	@Test
	@DisplayName("Should throw ResourceNotFoundException when brand name is not found")
	void testCreateGuitarBrandNotFound() {
		// Arrange
		GuitarDto newGuitarDto = new GuitarDto();
		newGuitarDto.setModelName("Les Paul");
		newGuitarDto.setPrice(1299.99);
		newGuitarDto.setManufactureDate(LocalDate.of(2012, 3, 20));
		newGuitarDto.setBrandName("Gibson");

		// Mock: Guitar does not already exist
		when(guitarRepository.findByModelName("Les Paul")).thenReturn(Optional.empty());

		// Mock: Brand is not found
		when(brandRepository.findByName("Gibson")).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> guitarService.createGuitar(newGuitarDto),
				"Should throw ResourceNotFoundException when brand is not found");

		// Verify the sequence of calls
		verify(guitarRepository).findByModelName("Les Paul");
		verify(brandRepository).findByName("Gibson");
		verify(guitarRepository, never()).save(any(Guitar.class));
	}
}
