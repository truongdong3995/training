package com.training.api.utils;

public class RestData<T> {
	private T data;
	
	public RestData(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
}
