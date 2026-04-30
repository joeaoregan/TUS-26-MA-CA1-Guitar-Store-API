package edu.tus.guitarstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.tus.guitarstore.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * Find a brand by name.
     * @param name
     * @return Optional<Brand> findByName(String name)
     */
    Optional<Brand> findByName(String name);

    /**
     * Find a brand by name, ignoring case sensitivity.
     * @param name
     * @return findByNameIgnoreCase(String name)
     */
    Optional<Brand> findByNameIgnoreCase(String name);
}
