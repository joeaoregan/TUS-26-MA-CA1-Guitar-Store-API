package edu.tus.guitarstore.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import edu.tus.guitarstore.constants.GuitarStoreConstants;
import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IGuitarStoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/guitarstore/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class BrandController {
	private IGuitarStoreService iGuitarStoreService;

	@PostMapping("/brands")
	public ResponseEntity<ResponseDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
		iGuitarStoreService.createBrand(brandDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_201, "Brand created successfully"));
	}

	@GetMapping("/brands")
	public ResponseEntity<List<BrandDto>> fetchAllBrands() {
		List<BrandDto> allBrands = iGuitarStoreService.fetchAllBrands();
		return ResponseEntity.status(HttpStatus.OK).body(allBrands);
	}

	@GetMapping("/brands/{brandName}")
	public ResponseEntity<BrandDto> fetchBrand(@PathVariable String brandName) {
		BrandDto brandDto = iGuitarStoreService.fetchBrand(brandName);
		return ResponseEntity.status(HttpStatus.OK).body(brandDto);
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

}
