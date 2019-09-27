package com.training.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class handle message of API
 *
 */
public class ApiMessage {
	
	@JsonProperty("error")
	@Getter
	@Setter
	private String error;
	
	@JsonProperty("error_description")
	@Getter
	@Setter
	private String errorDescription;
	
	
	public ApiMessage(String error, String errorDescription) {
		this.error = error;
		this.errorDescription = errorDescription;
	}
	
	public static ApiMessage error400() {
		return new ApiMessage("400",
				"Thiếu thông số bắt buộc, giá trị không hợp lệ hoặc request không đúng định dạng");
	}
	
	public static ApiMessage error404() {
		return new ApiMessage("404", "Cố gắng thao tác một tài nguyên không tồn tại");
	}
}
