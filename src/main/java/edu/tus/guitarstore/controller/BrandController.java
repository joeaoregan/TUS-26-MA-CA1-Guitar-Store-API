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
import edu.tus.guitarstore.dto.ErrorResponseDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Brand Controller", description = "CRUD operations for Guitar Brands")
@RestController
@RequestMapping(path = "/api/guitarstore/v1/brands", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class BrandController {

	private IBrandService iBrandService;

	@Operation(summary = "Create Brand", description = "REST API to create a new Guitar Brand")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "HTTP Status Created", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "HTTP Status Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PostMapping
	public ResponseEntity<ResponseDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
		iBrandService.createBrand(brandDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_201, "Brand created successfully"));
	}

	@Operation(summary = "Fetch All Brands", description = "REST API to fetch all available Guitar Brands")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK") })
	@GetMapping
	public ResponseEntity<List<BrandDto>> fetchAllBrands() {
		List<BrandDto> allBrands = iBrandService.fetchAllBrands();
		return ResponseEntity.status(HttpStatus.OK).body(allBrands);
	}

	@Operation(summary = "Fetch Brand Details", description = "REST API to fetch Brand details based on a unique brand name")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/{brandName}")
	public ResponseEntity<BrandDto> fetchBrand(
			@Parameter(description = "The unique name of the brand", example = "Gibson") @PathVariable String brandName) {
		BrandDto brandDto = iBrandService.fetchBrand(brandName);
		return ResponseEntity.status(HttpStatus.OK).body(brandDto);
	}

	@Operation(summary = "Update Brand", description = "REST API to update existing Brand details")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "417", description = "Expectation Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request (Validation)", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PutMapping
	public ResponseEntity<ResponseDto> updateBrand(@Valid @RequestBody BrandDto brandDto) {
		boolean isUpdated = iBrandService.updateBrand(brandDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_200, "Brand updated successfully"));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_417, "Update failed"));
	}

	@Operation(summary = "Delete Brand", description = "REST API to delete a Brand and all its associated guitar inventory")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "417", description = "Expectation Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))) })
	@DeleteMapping("/{brandName}")
	public ResponseEntity<ResponseDto> deleteBrand(
			@Parameter(description = "The unique name of the brand to delete", example = "Ibanez") @PathVariable String brandName) {
		boolean isDeleted = iBrandService.deleteBrand(brandName);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(GuitarStoreConstants.STATUS_200,
					"Brand and associated guitars deleted successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_417, "Delete operation failed"));
		}
	}
}
