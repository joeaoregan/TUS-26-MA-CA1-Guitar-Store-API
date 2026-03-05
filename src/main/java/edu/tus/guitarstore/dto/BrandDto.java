package edu.tus.guitarstore.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(name = "Brand", description = "Schema to hold Brand and its associated Guitars")
public class BrandDto {
	@Schema(description = "Name of the Brand", example = "Fender")
	@NotEmpty(message = "Brand name cannot be null or empty")
	private String name;

	@Schema(description = "Country of origin", example = "USA")
	@NotEmpty(message = "Country cannot be null or empty")
	private String country;

	@Schema(description = "List of guitars associated with this brand")
	private List<GuitarDto> guitars;
}
