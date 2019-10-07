package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Response http exception
 */
public class HttpExceptionResponse {

	/**
	 * Error
	 */
	@JsonProperty("error")
	@Getter
	@Setter
	private String error;
	
	@JsonProperty("error_description")
	@Getter
	@Setter
	private String errorDescription;

	/**
	 * Create instance
	 *
	 * @param error error
	 * @param errorDescription error description
	 */
	public HttpExceptionResponse(String error, String errorDescription) {
		this.error = error;
		this.errorDescription = errorDescription;
	}
}
