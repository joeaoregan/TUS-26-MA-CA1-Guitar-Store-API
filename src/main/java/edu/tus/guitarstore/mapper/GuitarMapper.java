package edu.tus.guitarstore.mapper;

import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Guitar;

public class GuitarMapper {

    /**
     * Convert Guitar Entity to GuitarDto.
     * @param guitar
     * @param guitarDto
     * @return The updated GuitarDto
     */
    public static GuitarDto mapToGuitarDto(final Guitar guitar, final GuitarDto guitarDto) {
        guitarDto.setModelName(guitar.getModelName());
        guitarDto.setPrice(guitar.getPrice());
        guitarDto.setManufactureDate(guitar.getManufactureDate());

        if (guitar.getBrand() != null) {
            guitarDto.setBrandName(guitar.getBrand().getName());
        }

        return guitarDto;
    }


    /**
     * Convert GuitarDto to Guitar Entity.
     * @param guitarDto The source GuitarDto containing the values.
     * @param guitar The target Guitar entity to be updated.
     * @return The updated Guitar entity.
     */
    public static Guitar mapToGuitar(final GuitarDto guitarDto, final Guitar guitar) {
        guitar.setModelName(guitarDto.getModelName());
        guitar.setPrice(guitarDto.getPrice());
        guitar.setManufactureDate(guitarDto.getManufactureDate());
        return guitar;
    }

}
