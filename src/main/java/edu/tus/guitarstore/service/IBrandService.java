package edu.tus.guitarstore.service;

import java.util.List;

import edu.tus.guitarstore.dto.BrandDto;

public interface IBrandService {

    /**
     * Business logic to create a new brand.
      * It checks if a brand with the same name already exists
     * @param brandDto
     */
    void createBrand(BrandDto brandDto);

    /**
     * fetches a brand by its name. If the brand does not exist,
     * it throws a ResourceNotFoundException.
     * @param name
     * @return BrandDto fetched brand details as BrandDto
     */
    BrandDto fetchBrand(String name);

    /**
     * fetches all brands from the database and
     * returns them as a list of BrandDto objects.
     * @return list of BrandDto
     */
    List<BrandDto> fetchAllBrands();

    /**
     * updates an existing brand's details.
     * It first checks if the brand exists, and if it does,
     * it updates the brand's information and saves it back to the database.
     * If the brand does not exist, it throws a ResourceNotFoundException.
     * @param brandDto
     * @return update status as boolean
     */
    boolean updateBrand(BrandDto brandDto);

    /**
     * deletes a brand based on its name. It checks if the brand
     * exists, and if it does, it deletes the brand from the database.
     * If the brand does not exist, it throws a ResourceNotFoundException.
     * @param brandName
     * @return deletion status as boolean
     */
    boolean deleteBrand(String brandName);
}
