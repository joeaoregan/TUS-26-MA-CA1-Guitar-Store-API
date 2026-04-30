package edu.tus.guitarstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Response", description = "Schema to hold successful response information")
public class ResponseDto {

    /**
     * Status code indicating the result of the operation.
     * For example, "201" for successful creation.
     */
	@Schema(description = "Status code in the response", example = "201")
	private String statusCode;

    /**
     * Message providing additional information about the status of the operation.
     * For example, "Guitar created successfully" for a successful creation.
     */
    @Schema(description = "Status message in the response", example = "Guitar created successfully")
	private String statusMsg;
}
