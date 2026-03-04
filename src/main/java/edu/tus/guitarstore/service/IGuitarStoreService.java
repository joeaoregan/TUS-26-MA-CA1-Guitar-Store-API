package edu.tus.guitarstore.service;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

public interface IGuitarStoreService {
	void createGuitar(GuitarDto guitarDto);

	void createBrand(BrandDto brandDto);

	GuitarDto fetchGuitar(String modelName);

	List<GuitarDto> fetchAllGuitars();

	List<BrandDto> fetchAllBrands();

	boolean updateGuitar(GuitarDto guitarDto);

	boolean updateBrand(BrandDto brandDto);

	boolean deleteGuitar(String modelName);

	boolean deleteBrand(String brandName);

	Page<GuitarDto> fetchAllGuitarsPaginated(int page, int size);
	
	List<GuitarDto> fetchGuitarsByDateRange(LocalDate start, LocalDate end);
}
