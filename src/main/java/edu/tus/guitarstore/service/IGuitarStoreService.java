package edu.tus.guitarstore.service;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;
import java.util.List;

public interface IGuitarStoreService {
	void createGuitar(GuitarDto guitarDto);

	void createBrand(BrandDto brandDto);

	GuitarDto fetchGuitar(String modelName);

	List<GuitarDto> fetchAllGuitars();

	List<BrandDto> fetchAllBrands();

	boolean updateGuitar(GuitarDto guitarDto);

	boolean updateBrand(BrandDto brandDto);

	boolean deleteGuitar(String modelName);
}
