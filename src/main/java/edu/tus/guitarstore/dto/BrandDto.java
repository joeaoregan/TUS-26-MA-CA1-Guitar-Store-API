package edu.tus.guitarstore.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(name = "Brand",
description = "Schema to hold Brand and its associated Guitars")
public class BrandDto {

    /**
     * Name of the brand. This field is mandatory
     * and cannot be null or empty.
     */
    @Schema(description = "Name of the Brand", example = "Fender")
    @NotEmpty(message = "Brand name cannot be null or empty")
    private String name;

    /**
     * Country of origin for the brand. This field is mandatory
     * and cannot be null or empty.
     */
    @Schema(description = "Country of origin", example = "USA")
    @NotEmpty(message = "Country cannot be null or empty")
    private String country;

    /**
     * List of guitars associated with this brand.
     */
    @Schema(description = "List of guitars associated with this brand")
    private List<GuitarDto> guitars;
}
