package com.training.api.web.postoffices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.cities.City;
import com.training.api.model.prefectures.Prefecture;
import com.training.api.utils.Common;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Search by prefecture code Response.
 *
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class SearchPrefectureCodeResponse {
	
	/**
	 * City code
	 */
	@JsonProperty("code")
	private String cityCode;
	
	/**
	 * City name
	 */
	@JsonProperty("city")
	private String cityName;
	
	/**
	 * City kana
	 */
	@JsonProperty("city_kana")
	private String cityKana;
	
	/**
	 * Prefecture
	 */
	@JsonProperty("prefecture")
	private String prefectureName;
	
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
		Common.checkNotNull(city.getPrefecture(), "Prefecture must be not null");
		Prefecture prefecture = city.getPrefecture();
		this.cityCode = city.getCityCode();
		this.cityName = city.getCityName();
		this.cityKana = city.getCityKana();
		this.prefectureName = prefecture.getPrefectureName();
		this.prefectureKana = prefecture.getPrefectureKana();
		this.prefectureCode = prefecture.getPrefectureCode();
	}
}
