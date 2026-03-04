package edu.tus.guitarstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.dto.GuitarDto;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.entity.Guitar;
import edu.tus.guitarstore.exception.GuitarAlreadyExistsException;
import edu.tus.guitarstore.exception.ResourceNotFoundException;
import edu.tus.guitarstore.mapper.BrandMapper;
import edu.tus.guitarstore.mapper.GuitarMapper;
import edu.tus.guitarstore.repository.BrandRepository;
import edu.tus.guitarstore.repository.GuitarRepository;
import edu.tus.guitarstore.service.IGuitarStoreService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuitarStoreServiceImpl implements IGuitarStoreService {

	private GuitarRepository guitarRepository;
	private BrandRepository brandRepository;

	@Override
	public void createGuitar(GuitarDto guitarDto) {
		Optional<Guitar> optionalGuitar = guitarRepository.findByModelName(guitarDto.getModelName());
	    if(optionalGuitar.isPresent()){
	        throw new GuitarAlreadyExistsException("Guitar already registered with given modelName " 
	                + guitarDto.getModelName());
	    }
	    
		Guitar guitar = GuitarMapper.mapToGuitar(guitarDto, new Guitar());
		
		Brand brand = brandRepository.findByName(guitarDto.getBrandName())
	            .orElseThrow(() -> new ResourceNotFoundException("Brand", "name", guitarDto.getBrandName()));
	    guitar.setBrand(brand);

		guitarRepository.save(guitar);
	}

	@Override
	public void createBrand(BrandDto brandDto) {
		Brand brand = BrandMapper.mapToBrand(brandDto, new Brand());

		brandRepository.save(brand);
	}

	@Override
	public GuitarDto fetchGuitar(String modelName) {
		Guitar guitar = guitarRepository.findByModelName(modelName)
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", modelName));

		return GuitarMapper.mapToGuitarDto(guitar, new GuitarDto());
	}

	@Override
	public List<GuitarDto> fetchAllGuitars() {
		return guitarRepository.findAll().stream().map(g -> GuitarMapper.mapToGuitarDto(g, new GuitarDto()))
				.collect(Collectors.toList());
	}

	@Override
	public List<BrandDto> fetchAllBrands() {
		return brandRepository.findAll().stream().map(brand -> BrandMapper.mapToBrandDto(brand, new BrandDto()))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean updateGuitar(GuitarDto guitarDto) {
		Guitar guitar = guitarRepository.findByModelName(guitarDto.getModelName())
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", guitarDto.getModelName())); // Find existing guitar or throw 404 error
		GuitarMapper.mapToGuitar(guitarDto, guitar); // Map new data from DTO to Entity

	    Brand brand = brandRepository.findByName(guitarDto.getBrandName())
	            .orElseThrow(() -> new ResourceNotFoundException("Brand", "name", guitarDto.getBrandName())); // Referential integrity check, verify and set brand if changed
		guitar.setBrand(brand);

		guitarRepository.save(guitar); // Save updated guitar entity
		return true;
	}

	@Override
	public boolean updateBrand(BrandDto brandDto) {
		Brand brand = brandRepository.findByName(brandDto.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", brandDto.getName()));

		BrandMapper.mapToBrand(brandDto, brand);

		brandRepository.save(brand);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteGuitar(String modelName) {
		Guitar guitar = guitarRepository.findByModelName(modelName)
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", modelName)); // 404 if id doesn't exist
		guitarRepository.deleteById(guitar.getId()); // delete by ID
		return true;
	}
	
	@Override
	public Page<GuitarDto> fetchAllGuitarsPaginated() {
	    Pageable pageable = PageRequest.of(0, 3, Sort.by("modelName").ascending());
	    Page<Guitar> guitarPage = guitarRepository.findAll(pageable);
	    
	    return guitarPage.map(guitar -> GuitarMapper.mapToGuitarDto(guitar, new GuitarDto()));
	}
}
