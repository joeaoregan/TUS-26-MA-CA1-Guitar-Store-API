package edu.tus.guitarstore.service;

import edu.tus.guitarstore.dto.GuitarDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

public interface IGuitarService {

    /**
     * Create a new guitar in the system.
     * @param guitarDto
     */
    void createGuitar(GuitarDto guitarDto);

    /**
     * Fetch a guitar by its model name.
     * @param modelName
     * @return GuitarDto fetched guitar details as GuitarDto
     */
    GuitarDto fetchGuitar(String modelName);

    /**
     * Fetch all guitars in the system.
     * @return list of GuitarDto
     */
    List<GuitarDto> fetchAllGuitars();

    /**
     * Update an existing guitar in the system.
     * @param guitarDto
     * @return boolean indicating success or failure
     */
    boolean updateGuitar(GuitarDto guitarDto);

    /**
     * Delete a guitar by its model name.
     * @param modelName
     * @return boolean indicating success or failure of delete operation
     */
    boolean deleteGuitar(String modelName);

    /**
     * Fetch all guitars in a paginated manner.
     * @param page the page number to fetch
     * @param size the number of items per page
     * @return a page of GuitarDto
     */
    Page<GuitarDto> fetchAllGuitarsPaginated(int page, int size);

    /**
     * Fetch guitars that were manufactured within a specified date range.
     * @param start
     * @param end the end date of the range
     * @return a list of GuitarDto within the specified date range
     */
    List<GuitarDto> fetchGuitarsByDateRange(LocalDate start, LocalDate end);
}
