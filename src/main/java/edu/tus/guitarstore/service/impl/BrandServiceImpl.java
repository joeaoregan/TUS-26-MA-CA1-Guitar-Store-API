package edu.tus.guitarstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import edu.tus.guitarstore.dto.BrandDto;
import edu.tus.guitarstore.entity.Brand;
import edu.tus.guitarstore.exception.ResourceNotFoundException;
import edu.tus.guitarstore.mapper.BrandMapper;
import edu.tus.guitarstore.repository.BrandRepository;
import edu.tus.guitarstore.service.IBrandService;
import jakarta.transaction.Transactional;

public class BrandServiceImpl implements IBrandService {
	private BrandRepository brandRepository;

	@Override
	public void createBrand(BrandDto brandDto) {
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
