package edu.tus.guitarstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public boolean updateGuitar(GuitarDto guitarDto) {
		Guitar guitar = guitarRepository.findByModelName(guitarDto.getModelName())
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", guitarDto.getModelName()));
		GuitarMapper.mapToGuitar(guitarDto, guitar);
		guitarRepository.save(guitar);
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
	public boolean deleteGuitar(String modelName) {
		Guitar guitar = guitarRepository.findByModelName(modelName)
				.orElseThrow(() -> new ResourceNotFoundException("Guitar", "modelName", modelName));
		guitarRepository.deleteById(guitar.getId());
		return true;
	}
}
