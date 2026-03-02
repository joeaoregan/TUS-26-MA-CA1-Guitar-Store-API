package edu.tus.guitarstore.service;

import edu.tus.guitarstore.dto.GuitarDto;
import java.util.List;

public interface IGuitarStoreService {
	void createGuitar(GuitarDto guitarDto);

	GuitarDto fetchGuitar(String modelName);

	List<GuitarDto> fetchAllGuitars();

	boolean updateGuitar(GuitarDto guitarDto);

	boolean deleteGuitar(String modelName);
}
