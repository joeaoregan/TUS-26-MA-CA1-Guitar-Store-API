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
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IGuitarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/guitarstore/v1/guitars", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class GuitarController {
	private IGuitarService iGuitarService;

	@PostMapping
	public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody GuitarDto guitarDto) {
		iGuitarService.createGuitar(guitarDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarStoreConstants.STATUS_201, GuitarStoreConstants.MESSAGE_201));
	}

	@GetMapping("/{modelName}")
	public ResponseEntity<GuitarDto> fetchGuitar(@PathVariable String modelName) {
		GuitarDto guitarDto = iGuitarService.fetchGuitar(modelName);
		return ResponseEntity.status(HttpStatus.OK).body(guitarDto);
	}

	@GetMapping
	public ResponseEntity<List<GuitarDto>> fetchAllGuitars() {
		List<GuitarDto> allGuitars = iGuitarService.fetchAllGuitars();
		return ResponseEntity.status(HttpStatus.OK).body(allGuitars);
	}

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

	@DeleteMapping("/{modelName}")
	public ResponseEntity<ResponseDto> deleteGuitar(@PathVariable String modelName) {
		boolean isDeleted = iGuitarService.deleteGuitar(modelName);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_DELETE));
		}
	}

	@GetMapping("/paginated")
	public ResponseEntity<Page<GuitarDto>> fetchGuitarsPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Page<GuitarDto> guitarPage = iGuitarService.fetchAllGuitarsPaginated(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(guitarPage);
	}

	@GetMapping("/guitars/filter")
	public ResponseEntity<List<GuitarDto>> filterGuitarsByDate(@RequestParam LocalDate start,
			@RequestParam LocalDate end) {
		List<GuitarDto> guitars = iGuitarService.fetchGuitarsByDateRange(start, end);
		return ResponseEntity.ok(guitars);
	}
}
