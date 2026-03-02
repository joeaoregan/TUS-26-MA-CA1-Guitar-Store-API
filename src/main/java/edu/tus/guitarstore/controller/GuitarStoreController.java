package edu.tus.guitarstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tus.guitarstore.constants.GuitarStoreConstants;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.dto.ResponseDto;
import edu.tus.guitarstore.service.IGuitarStoreService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GuitarStoreController {
	private IGuitarStoreService iGuitarStoreService;
	
	@PostMapping("/create")
    public ResponseEntity<ResponseDto> createGuitar(@RequestBody GuitarDto guitarDto) {
		iGuitarStoreService.createGuitar(guitarDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(GuitarStoreConstants.STATUS_201, GuitarStoreConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<GuitarDto> fetchGuitar(@RequestParam String modelName) {
        GuitarDto guitarDto = iGuitarStoreService.fetchGuitar(modelName);
        return ResponseEntity.status(HttpStatus.OK).body(guitarDto);
    }
    
    @GetMapping("/fetchAll")
    public ResponseEntity<List<GuitarDto>> fetchAllGuitars() {
        List<GuitarDto> allGuitars = iGuitarStoreService.fetchAllGuitars();
        return ResponseEntity.status(HttpStatus.OK).body(allGuitars);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateGuitar(@RequestBody GuitarDto guitarDto) {
        boolean isUpdated = iGuitarStoreService.updateGuitar(guitarDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteGuitar(@RequestParam String modelName) {
        boolean isDeleted = iGuitarStoreService.deleteGuitar(modelName);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_200, GuitarStoreConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GuitarStoreConstants.STATUS_417, GuitarStoreConstants.MESSAGE_417_DELETE));
        }
    }
}
