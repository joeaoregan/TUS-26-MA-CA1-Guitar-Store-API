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
	@Schema(description = "Status code in the response", example = "201")
	private String statusCode;
	
	@Schema(description = "Status message in the response", example = "Guitar created successfully")
	private String statusMsg;
}
