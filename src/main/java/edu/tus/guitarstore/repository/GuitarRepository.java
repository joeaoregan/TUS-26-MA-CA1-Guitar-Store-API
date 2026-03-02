package edu.tus.guitarstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import edu.tus.guitarstore.entity.Guitar;
import jakarta.transaction.Transactional;

public interface GuitarRepository extends JpaRepository<Guitar, Long> {
	Optional<Guitar> findByModelName(String modelName); // prevents NullPointerException
	
	java.util.List<Guitar> findByBrandId(Long brandId);
	
	Optional<Guitar> findByModelNameIgnoreCase(String modelName);

	@Transactional
	@Modifying
	void deleteByModelName(String modelName);
}
