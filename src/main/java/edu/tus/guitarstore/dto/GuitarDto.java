package edu.tus.guitarstore.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Guitar", description = "Schema to hold Guitar")
public class GuitarDto {

	@Schema(description = "Name of the guitar model", example = "Stratocaster")
	@NotEmpty(message = "Model name cannot be null or empty")
	@Size(min = 2, max = 50, message = "Model name should be between 2 and 50 characters")
	private String modelName;

	@Schema(description = "Price of the guitar", example = "999.99")
	@Positive(message = "Price must be greater than zero")
	private double price;

	@Schema(description = "Date guitar was manufactured", example = "2010-01-05")
	@NotNull(message = "Manufacture date is required, format YYYY-MM-DD")
	@PastOrPresent(message = "Manufacture date cannot be in the future")
	private LocalDate manufactureDate;

	@Schema(description = "Brand name of the Guitar", example = "Fender")
	@NotEmpty(message = "Brand name is required")
	private String brandName;
}
