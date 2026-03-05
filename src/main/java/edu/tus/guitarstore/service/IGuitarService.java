package edu.tus.guitarstore.service;

import edu.tus.guitarstore.dto.GuitarDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

public interface IGuitarService {
	void createGuitar(GuitarDto guitarDto);

	GuitarDto fetchGuitar(String modelName);

	List<GuitarDto> fetchAllGuitars();

	boolean updateGuitar(GuitarDto guitarDto);

	boolean deleteGuitar(String modelName);

	Page<GuitarDto> fetchAllGuitarsPaginated(int page, int size);

	List<GuitarDto> fetchGuitarsByDateRange(LocalDate start, LocalDate end);
}
