package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.City;
import com.training.api.entitys.Prefecture;
import lombok.Getter;
import lombok.Setter;

/**
 * Search by prefecture code Response
 * 
 */
@Getter
@Setter
public class SearchPrefectureCodeResponse {
	
	/**
	 * Code
	 */
	@JsonProperty("code")
	private String code;
	
	/**
	 * City
	 */
	@JsonProperty("city")
	private String city;
	
	/**
	 * City kana
	 */
	@JsonProperty("city_kana")
	private String cityKana;
	
	/**
	 * Prefecture
	 */
	@JsonProperty("prefecture")
	private String prefecture;
	
	/**
	 * Prefecture_kana kana
	 */
	@JsonProperty("prefecture_kana")
	private String prefectureKana;
	
	/**
	 * Prefecture code
	 */
	@JsonProperty("prefecture_code")
	private String prefectureCode;
	
	
	public SearchPrefectureCodeResponse(City city) {
		Prefecture tblPrefecture = city.getPrefecture();
		this.code = city.getCode();
		this.city = city.getCityName();
		this.cityKana = city.getCityKana();
		this.prefecture = tblPrefecture.getPrefecture();
		this.prefectureKana = tblPrefecture.getPrefectureKana();
		this.prefectureCode = tblPrefecture.getPrefectureCode();
	}
}
