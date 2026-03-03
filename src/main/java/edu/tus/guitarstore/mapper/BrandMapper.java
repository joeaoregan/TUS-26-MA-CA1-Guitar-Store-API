package edu.tus.guitarstore.mapper;

import edu.tus.guitarstore.dto.BrandDto;
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
        return brandDto;
    }
}