package edu.tus.guitarstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.exception.ResourceNotFoundException;
import edu.tus.guitarstore.mapper.BrandMapper;
import edu.tus.guitarstore.repository.BrandRepository;
import edu.tus.guitarstore.service.IBrandService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements IBrandService {

    /**
     * BrandRepository is injected to perform
     * CRUD operations on Brand entities.
     */
    private BrandRepository brandRepository;

    /**
     * Creates a new Brand based on the provided BrandDto.
     * It first checks if a brand with the same name already exists
     * in the system. If it does, a RuntimeException is thrown.
     * @param brandDto the BrandDto containing brand details
     */
    @Override
    public void createBrand(final BrandDto brandDto) {
        Optional<Brand> existingBrand = brandRepository.findByName(brandDto.getName());	    
	    if (existingBrand.isPresent()) {
	        // Possible to throw Custom Exception here
            throw new RuntimeException("Brand already exists with name: " + brandDto.getName());
	    }

		Brand brand = BrandMapper.mapToBrand(brandDto, new Brand());
		brandRepository.save(brand);
	}

    /**
     * Fetches a Brand based on the provided brand name.
     * If no brand is found with the given name,
     * a ResourceNotFoundException is thrown.
     * Brand details are returned as a BrandDto.
     * @param name the name of the brand to fetch
     * @return BrandDto containing brand details
     */
	@Override
	public BrandDto fetchBrand(final String name) {
		Brand brand = brandRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", name));
		return BrandMapper.mapToBrandDto(brand, new BrandDto());
	}

	/**
	 * Fetches all available brands in the system and
	 * returns them as a list of BrandDto.
	 * @return List of BrandDto containing details of all brands
	 */
    @Override
    public List<BrandDto> fetchAllBrands() {
        return brandRepository.findAll().stream().map(brand -> BrandMapper.mapToBrandDto(brand, new BrandDto()))
            .collect(Collectors.toList());
	}

    /**
     * Updates the details of an existing Brand based on the provided BrandDto.
     * @param brandDto the BrandDto containing updated brand details
     * @return true if the update was successful, false otherwise
     */
	@Override
	public boolean updateBrand(final BrandDto brandDto) {
		Brand brand = brandRepository.findByName(brandDto.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", brandDto.getName()));

		BrandMapper.mapToBrand(brandDto, brand);

		brandRepository.save(brand);
		return true;
	}

	/**
	 * Deletes a Brand and all its associated guitar
	 * inventory based on the unique brand name.
	 * If no brand is found with the given name,
	 * a ResourceNotFoundException is thrown.
	 * @param brandName the unique name of the brand to delete
	 * @return true if the delete operation was successful, false otherwise
	 */
	@Override
	@Transactional
	public boolean deleteBrand(final String brandName) {
		Brand brand = brandRepository.findByName(brandName)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", brandName));
		brandRepository.delete(brand);
		return true;
	}

}
