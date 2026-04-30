package edu.tus.guitarstore.mapper;

import java.util.stream.Collectors;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Brand;

/**
 * Mapper class to convert between Brand entity and BrandDto.
 */
public class BrandMapper {
    /**
     * Maps a BrandDto to a Brand entity.
     * This method updates the provided Brand entity
     * with values from the BrandDto.
     * @param brandDto The source BrandDto containing the updated values.
     * @param brand The target Brand entity to be updated.
     * @return The updated Brand entity.
     */
    public static Brand mapToBrand(final BrandDto brandDto, final Brand brand) {
        brand.setName(brandDto.getName());
        brand.setCountry(brandDto.getCountry());
        return brand;
	}

    /**
     * Maps a Brand entity to a BrandDto.
     * This method updates the provided BrandDto
     * with values from the Brand entity.
     * @param brand The source Brand entity containing the values.
     * @param brandDto The target BrandDto to be updated.
     * @return The updated BrandDto.
     */
    public static BrandDto mapToBrandDto(final Brand brand, final BrandDto brandDto) {
		brandDto.setName(brand.getName());
		brandDto.setCountry(brand.getCountry());

		// Nested guitars
		if (brand.getGuitars() != null) {
			brandDto.setGuitars(brand.getGuitars().stream()
                .map(guitar -> GuitarMapper.mapToGuitarDto(guitar, new GuitarDto())).collect(Collectors.toList()));
		}

		return brandDto;
	}
}
