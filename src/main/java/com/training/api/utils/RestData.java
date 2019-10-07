package com.training.api.utils;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RestData<T> {
	
	@Getter
	@JsonUnwrapped
	private T data;
}
