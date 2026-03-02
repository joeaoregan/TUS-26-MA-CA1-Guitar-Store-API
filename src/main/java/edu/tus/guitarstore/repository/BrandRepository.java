package edu.tus.guitarstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.tus.guitarstore.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	Optional<Brand> findByName(String name);
	
	Optional<Brand> findByNameIgnoreCase(String name);
}
