package edu.tus.guitarstore.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuitarDto {

	@NotEmpty(message = "Model name cannot be null or empty")
	@Size(min = 2, max = 50, message = "Model name should be between 2 and 50 characters")
	private String modelName;

	@Positive(message = "Price must be greater than zero")
	private double price;

	@PastOrPresent(message = "Manufacture date cannot be in the future")
	private LocalDate manufactureDate;

	@NotEmpty(message = "Brand name is required")
	private String brandName;
}
