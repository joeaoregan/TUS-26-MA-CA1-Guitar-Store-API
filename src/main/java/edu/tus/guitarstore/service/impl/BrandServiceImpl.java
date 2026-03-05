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
	private BrandRepository brandRepository;

	@Override
	public void createBrand(BrandDto brandDto) {
	    Optional<Brand> existingBrand = brandRepository.findByName(brandDto.getName());	    
	    if(existingBrand.isPresent()) {
	        // Possible to throw Custom Exception ...but no time to implement
	        throw new RuntimeException("Brand already exists with name: " + brandDto.getName());
	    }
	    
		Brand brand = BrandMapper.mapToBrand(brandDto, new Brand());
		brandRepository.save(brand);
	}

	@Override
	public BrandDto fetchBrand(String name) {
		Brand brand = brandRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", name));
		return BrandMapper.mapToBrandDto(brand, new BrandDto());
	}

	@Override
	public List<BrandDto> fetchAllBrands() {
		return brandRepository.findAll().stream().map(brand -> BrandMapper.mapToBrandDto(brand, new BrandDto()))
				.collect(Collectors.toList());
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
	public boolean deleteBrand(String brandName) {
		Brand brand = brandRepository.findByName(brandName)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "name", brandName));
		brandRepository.delete(brand);
		return true;
	}

}
