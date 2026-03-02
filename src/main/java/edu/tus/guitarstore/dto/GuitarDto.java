package edu.tus.guitarstore.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuitarDto {
	private String modelName;
	private double price;
    private LocalDate manufactureDate;
    private String brandName;
}
