package edu.tus.guitarstore.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.entity.Guitar;
import edu.tus.guitarstore.exception.GuitarAlreadyExistsException;
import edu.tus.guitarstore.exception.ResourceNotFoundException;
import edu.tus.guitarstore.mapper.GuitarMapper;
import edu.tus.guitarstore.repository.BrandRepository;
import edu.tus.guitarstore.repository.GuitarRepository;
import edu.tus.guitarstore.service.IGuitarService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuitarServiceImpl implements IGuitarService {

    /**
     * GuitarRepository is injected to perform CRUD operations on Guitar
     * entities, while BrandRepository is used to ensure referential
     * integrity when associating a Guitar with its Brand.
     */
    private final GuitarRepository guitarRepository;

    /**
     * BrandRepository is injected to perform CRUD operations on Brand entities,
     * ensuring referential integrity when associating a Guitar with its Brand.
     */
    private final BrandRepository brandRepository;

    /**
     * Creates a new Guitar record in the inventory based on the
     * provided GuitarDto.
     * @param guitarDto the GuitarDto containing guitar details to be created
     */
    @Override
    public void createGuitar(final GuitarDto guitarDto) {
        if (guitarDto.getModelName() == null || guitarDto.getModelName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Guitar", "modelName", "Empty");
        }

        Optional<Guitar> optionalGuitar = guitarRepository.findByModelName(guitarDto.getModelName());
        if (optionalGuitar.isPresent()) {
            throw new GuitarAlreadyExistsException(
					"Guitar already registered with given modelName " + guitarDto.getModelName());
		}

        Guitar guitar = GuitarMapper.mapToGuitar(guitarDto, new Guitar());

        Brand brand = brandRepository.findByName(guitarDto.getBrandName())
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", guitarDto.getBrandName()));
		guitar.setBrand(brand);

		guitarRepository.save(guitar);
	}

    /**
     * Fetch details of a specific guitar using its unique model name.
     * @param modelName the unique model name of the guitar to fetch
     * @return GuitarDto containing the guitar details
     */
	@Override
	public GuitarDto fetchGuitar(final String modelName) {
		Guitar guitar = guitarRepository.findByModelName(modelName)
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", modelName));

		return GuitarMapper.mapToGuitarDto(guitar, new GuitarDto());
	}


	/**
	 * Fetch the complete list of all guitars in the store and return them
	 * as a list of GuitarDto.
	 * @return List of GuitarDto representing all guitars in the inventory
	 */
	@Override
	public List<GuitarDto> fetchAllGuitars() {
        return guitarRepository.findAll().stream().map(g -> GuitarMapper.mapToGuitarDto(g, new GuitarDto()))
				.collect(Collectors.toList());
	}

	/**
     * Update details of an existing guitar, such as price or specifications,
     * using its unique model name. The method first checks if the guitar
       * exists, then updates its details based on the provided GuitarDto.
     * @param guitarDto the GuitarDto containing updated guitar details
     * @return true if the update was successful, false otherwise
     */
    @Override
    @Transactional
	public boolean updateGuitar(final GuitarDto guitarDto) {
        // Find existing guitar or throw 404 error
        Guitar guitar = guitarRepository.findByModelName(guitarDto.getModelName())
        		.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", guitarDto.getModelName()));
        GuitarMapper.mapToGuitar(guitarDto, guitar); // Map new data from DTO to Entity
		// Referential integrity check, verify and set brand if changed
       Brand brand = brandRepository.findByName(guitarDto.getBrandName())
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", guitarDto.getBrandName()));
		guitar.setBrand(brand);

		guitarRepository.save(guitar); // Save updated guitar entity
		return true;
	}

    /**
     * Delete guitar from the inventory using its unique model name. The method
     * first checks if the guitar exists, then deletes it by its ID.
     * @param modelName the unique model name of the guitar to delete
     * @return true if the delete operation was successful, false otherwise
     */
	@Override
	@Transactional
	public boolean deleteGuitar(final String modelName) {
		// 404 if ID doesn't exist, otherwise delete by ID
		Guitar guitar = guitarRepository.findByModelName(modelName)
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", modelName));
		guitarRepository.deleteById(guitar.getId()); // delete by ID
		return true;
	}

	/**
	 * Fetch a paginated list of guitars, allowing clients to specify the page number and size.
	 * The guitars are sorted by model name in ascending order for consistent pagination results.
	 * @param page the page number to retrieve (0-based index)
	 */
	@Override
    public Page<GuitarDto> fetchAllGuitarsPaginated(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modelName").ascending());
        Page<Guitar> guitarPage = guitarRepository.findAll(pageable);

		return guitarPage.map(guitar -> GuitarMapper.mapToGuitarDto(guitar, new GuitarDto()));
	}

	/**
	 * Fetch guitars that were manufactured within a specified date range.
	 * The method retrieves guitars whose manufacture date falls
	 * between the provided start and end dates,
	 * @param start the start date of the range
	 * @param end the end date of the range
	 * @return List of GuitarDto representing guitars within the specified
	 * date range
	 */
	@Override
    public List<GuitarDto> fetchGuitarsByDateRange(final LocalDate start, final LocalDate end) {
        List<Guitar> guitars = guitarRepository.findByManufactureDateBetween(start, end);
        return guitars.stream().map(g -> GuitarMapper.mapToGuitarDto(g, new GuitarDto())).collect(Collectors.toList());
	}
}
