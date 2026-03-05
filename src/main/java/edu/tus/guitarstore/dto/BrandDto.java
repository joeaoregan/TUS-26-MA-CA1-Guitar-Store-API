package edu.tus.guitarstore.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BrandDto {
	@NotEmpty(message = "Brand name cannot be null or empty")
	private String name;

	@NotEmpty(message = "Country cannot be null or empty")
	private String country;

	private List<GuitarDto> guitars; // holds nested list of guitars, returned when searching by brand
}
