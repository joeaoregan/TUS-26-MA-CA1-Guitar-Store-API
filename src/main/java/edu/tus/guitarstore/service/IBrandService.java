package edu.tus.guitarstore.service;

import java.util.List;

import edu.tus.guitarstore.dto.BrandDto;

public interface IBrandService {

	void createBrand(BrandDto brandDto);

	BrandDto fetchBrand(String name);

	List<BrandDto> fetchAllBrands();

	boolean updateBrand(BrandDto brandDto);

	boolean deleteBrand(String brandName);
}
