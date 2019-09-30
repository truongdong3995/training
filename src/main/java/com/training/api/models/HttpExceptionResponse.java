package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Response error
 */
public class HttpExceptionResponse {
	
	@JsonProperty("error")
	@Getter
	@Setter
	private String error;
	
	@JsonProperty("error_descreption")
	@Getter
	@Setter
	private String errorDescreption;
	
	
	public HttpExceptionResponse(String error, String errorDescreption) {
		this.error = error;
		this.errorDescreption = errorDescreption;
	}
}
