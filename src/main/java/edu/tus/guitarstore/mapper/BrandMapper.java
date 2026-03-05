package edu.tus.guitarstore.mapper;

import java.util.stream.Collectors;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Brand;

public class BrandMapper {
	public static Brand mapToBrand(BrandDto brandDto, Brand brand) {
		brand.setName(brandDto.getName());
		brand.setCountry(brandDto.getCountry());
		return brand;
	}

	public static BrandDto mapToBrandDto(Brand brand, BrandDto brandDto) {
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