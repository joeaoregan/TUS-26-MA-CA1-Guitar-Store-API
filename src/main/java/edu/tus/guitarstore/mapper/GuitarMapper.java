package edu.tus.guitarstore.mapper;

import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Guitar;

public class GuitarMapper {
	// Convert Guitar Entity to GuitarDto
	public static GuitarDto mapToGuitarDto(Guitar guitar, GuitarDto guitarDto) {
		guitarDto.setModelName(guitar.getModelName());
		guitarDto.setPrice(guitar.getPrice());
		guitarDto.setManufactureDate(guitar.getManufactureDate());

		if (guitar.getBrand() != null) {
			guitarDto.setBrandName(guitar.getBrand().getName());
		}

		return guitarDto;
	}

	// Convert GuitarDto to Guitar Entity
	public static Guitar mapToGuitar(GuitarDto guitarDto, Guitar guitar) {
		guitar.setModelName(guitarDto.getModelName());
		guitar.setPrice(guitarDto.getPrice());
		guitar.setManufactureDate(guitarDto.getManufactureDate());
		return guitar;
	}

}
