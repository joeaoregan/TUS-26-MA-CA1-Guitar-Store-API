package edu.tus.guitarstore.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import edu.tus.guitarstore.entity.Guitar;
import jakarta.transaction.Transactional;

public interface GuitarRepository extends JpaRepository<Guitar, Long> {

	/**
	 * Find a guitar by model name.
	 * @param modelName
	 * @return Optional<Guitar> findByModelName(String modelName)
	 */
	Optional<Guitar> findByModelName(String modelName);

	/**
	 * Find guitars by brand ID.
	 * @param brandId
	 * @return List<Guitar> findByBrandId(Long brandId)
	 */
	List<Guitar> findByBrandId(Long brandId);

	/**
	 * Find a guitar by model name, ignoring case sensitivity.
	 * @param modelName
	 * @return Optional<Guitar> findByModelNameIgnoreCase(String modelName)
	 */
	Optional<Guitar> findByModelNameIgnoreCase(String modelName);

	/**
	 * Delete a guitar by model name.
	 * @param modelName
	 */
	@Transactional
	@Modifying
	void deleteByModelName(String modelName);

	/**
	 * Find guitars with price less than a specified value.
	 * @param price
	 * @return List<Guitar> findByPriceLessThan(Double price)
	 */
	List<Guitar> findByPriceLessThan(Double price);

	/**
	 * Find guitars manufactured between two dates.
	 * @param start
	 * @param end
	 * @return List<Guitar> findByManufactureDateBetween(LocalDate start, LocalDate end)
	 */
	List<Guitar> findByManufactureDateBetween(LocalDate start, LocalDate end);
}
