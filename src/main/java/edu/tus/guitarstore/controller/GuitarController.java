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
import edu.tus.guitarstore.dto.ErrorResponseDto;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IGuitarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Guitar Controller", description = "CRUD operations for Guitar Inventory")
@RestController
@RequestMapping(path = "/api/guitarstore/v1/guitars", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GuitarController {

    private IGuitarService iGuitarService;

    @Operation(summary = "Create New Guitar", description = "REST API to create a new Guitar record in the inventory")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "HTTP Status Created", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request - Validation Failed", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping
    public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody GuitarDto guitarDto) {
        iGuitarService.createGuitar(guitarDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(GuitarStoreConstants.STATUS_201, GuitarStoreConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Guitar Details", description = "Fetch a specific guitar by its unique model name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "404", description = "Guitar not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping("/{modelName}")
    public ResponseEntity<GuitarDto> fetchGuitar(
            @Parameter(description = "The unique model name of the guitar", example = "Stratocaster") @PathVariable String modelName) {
        GuitarDto guitarDto = iGuitarService.fetchGuitar(modelName);
        return ResponseEntity.status(HttpStatus.OK).body(guitarDto);
    }

    @Operation(summary = "Fetch All Guitars", description = "Retrieve the complete list of all guitars in the store")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK")})
    @GetMapping
    public ResponseEntity<List<GuitarDto>> fetchAllGuitars() {
        List<GuitarDto> allGuitars = iGuitarService.fetchAllGuitars();
        return ResponseEntity.status(HttpStatus.OK).body(allGuitars);
    }

    @Operation(summary = "Update Guitar Details", description = "Update price or details for an existing guitar record")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Expectation Failed - Update Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request (Validation)", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PutMapping
    public ResponseEntity<ResponseDto> updateGuitar(@Valid @RequestBody GuitarDto guitarDto) {
        boolean isUpdated = iGuitarService.updateGuitar(guitarDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete Guitar", description = "Remove a guitar from inventory using its model name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Expectation Failed - Delete Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Guitar not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @DeleteMapping("/{modelName}")
    public ResponseEntity<ResponseDto> deleteGuitar(
            @Parameter(description = "The unique model name of the guitar to delete", example = "Telecaster") @PathVariable String modelName) {
        boolean isDeleted = iGuitarService.deleteGuitar(modelName);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(summary = "Fetch Paginated Guitars", description = "Retrieve guitars using server-side pagination for optimized performance")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK")})
    @GetMapping("/paginated")
    public ResponseEntity<Page<GuitarDto>> fetchGuitarsPaginated(
            @Parameter(description = "Zero-based page index (0..N)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "The size of the page to be returned", example = "5") @RequestParam(defaultValue = "5") int size) {
        Page<GuitarDto> guitarPage = iGuitarService.fetchAllGuitarsPaginated(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(guitarPage);
    }

    @Operation(summary = "Filter Guitars by Date", description = "Retrieve guitars manufactured within a specific date range")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "400", description = "Invalid Date Format", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping("/filter")
    public ResponseEntity<List<GuitarDto>> filterGuitarsByDate(
            @Parameter(description = "Start date in ISO format (YYYY-MM-DD)", example = "2020-01-01") @RequestParam LocalDate start,
            @Parameter(description = "End date in ISO format (YYYY-MM-DD)", example = "2025-12-31") @RequestParam LocalDate end) {
        List<GuitarDto> guitars = iGuitarService.fetchGuitarsByDateRange(start, end);
        return ResponseEntity.ok(guitars);
    }
}
