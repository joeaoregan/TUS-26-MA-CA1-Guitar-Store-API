package edu.tus.guitarstore.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.tus.guitarstore.constants.GuitarStoreConstants;
import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IGuitarStoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/guitarstore/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class GuitarStoreController {
	private IGuitarStoreService iGuitarStoreService;

	@PostMapping("/guitars")
	public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody GuitarDto guitarDto) {
		iGuitarStoreService.createGuitar(guitarDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_201, GuitarStoreConstants.MESSAGE_201));
	}

	@PostMapping("/brands")
	public ResponseEntity<ResponseDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
		iGuitarStoreService.createBrand(brandDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_201, "Brand created successfully"));
	}

	@GetMapping("/guitars/{modelName}")
	public ResponseEntity<GuitarDto> fetchGuitar(@PathVariable String modelName) {
		GuitarDto guitarDto = iGuitarStoreService.fetchGuitar(modelName);
		return ResponseEntity.status(HttpStatus.OK).body(guitarDto);
	}

	@GetMapping("/guitars")
	public ResponseEntity<List<GuitarDto>> fetchAllGuitars() {
		List<GuitarDto> allGuitars = iGuitarStoreService.fetchAllGuitars();
		return ResponseEntity.status(HttpStatus.OK).body(allGuitars);
	}

	@GetMapping("/brands")
	public ResponseEntity<List<BrandDto>> fetchAllBrands() {
		List<BrandDto> allBrands = iGuitarStoreService.fetchAllBrands();
		return ResponseEntity.status(HttpStatus.OK).body(allBrands);
	}

	@PutMapping("/guitars")
	public ResponseEntity<ResponseDto> updateGuitar(@Valid @RequestBody GuitarDto guitarDto) {
		boolean isUpdated = iGuitarStoreService.updateGuitar(guitarDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_UPDATE));
		}
	}

	@PutMapping("/brands")
	public ResponseEntity<ResponseDto> updateBrand(@Valid @RequestBody BrandDto brandDto) {
		boolean isUpdated = iGuitarStoreService.updateBrand(brandDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_200, "Brand updated successfully"));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_417, "Update failed"));
	}

	@DeleteMapping("/guitars/{modelName}")
	public ResponseEntity<ResponseDto> deleteGuitar(@PathVariable String modelName) {
		boolean isDeleted = iGuitarStoreService.deleteGuitar(modelName);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_DELETE));
		}
	}

	@DeleteMapping("/brands/{brandName}")
	public ResponseEntity<ResponseDto> deleteBrand(@PathVariable String brandName) {
		boolean isDeleted = iGuitarStoreService.deleteBrand(brandName);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(GuitarStoreConstants.STATUS_200,
					"Brand and associated guitars deleted successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_417, "Delete operation failed"));
		}
	}

	@GetMapping("/guitars/paginated")
	public ResponseEntity<Page<GuitarDto>> fetchGuitarsPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Page<GuitarDto> guitarPage = iGuitarStoreService.fetchAllGuitarsPaginated(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(guitarPage);
	}

	@GetMapping("/guitars/filter")
	public ResponseEntity<List<GuitarDto>> filterGuitarsByDate(@RequestParam LocalDate start,
			@RequestParam LocalDate end) {
		List<GuitarDto> guitars = iGuitarStoreService.fetchGuitarsByDateRange(start, end);
		return ResponseEntity.ok(guitars);
	}
}
