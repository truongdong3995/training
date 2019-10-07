package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.entitys.Post;
import com.training.api.entitys.Prefecture;
import lombok.Getter;
import lombok.Setter;

/**
 * Search by post code response
 * 
 */
@Getter
@Setter
public class SearchPostCodeResponse {
	
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
	 * Prefecture kana
	 */
	@JsonProperty("prefecture_kana")
	private String prefectureKana;
	
	/**
	 * Prefecture code
	 */
	@JsonProperty("prefecture_code")
	private String prefectureCode;
	
	/**
	 * Area
	 */
	@JsonProperty("area")
	private String area;
	
	/**
	 * Area kana
	 */
	@JsonProperty("area_kana")
	private String areaKana;
	
	/**
	 * Multi post area
	 */
	@JsonProperty("multi_post_area")
	private int multiPostArea;
	
	/**
	 * Koaza area
	 */
	@JsonProperty("koaza_area")
	private int koazaArea;
	
	/**
	 * Chome area
	 */
	@JsonProperty("chome_area")
	private int chomeArea;
	
	/**
	 * Old post code
	 */
	@JsonProperty("old_post_code")
	private String oldPostCode;
	
	/**
	 * Post code
	 */
	@JsonProperty("post_code")
	private String postCode;
	
	/**
	 * Multi area
	 */
	@JsonProperty("multi_area")
	private int multiArea;
	
	/**
	 * Update show
	 */
	@JsonProperty("update_show")
	private int updateShow;
	
	/**
	 * Change reason
	 */
	@JsonProperty("change_reason")
	private int changeReason;
	
	
	public SearchPostCodeResponse(Area area) {
		City city = area.getCity();
		Prefecture prefecture = city.getPrefecture();
		Post post = area.getPost();
		this.code = city.getCode();
		this.city = city.getCityName();
		this.cityKana = city.getCityKana();
		this.prefecture = prefecture.getPrefecture();
		this.prefectureKana = prefecture.getPrefectureKana();
		this.prefectureCode = prefecture.getPrefectureCode();
		this.area = area.getArea();
		this.areaKana = area.getAreaKana();
		this.multiPostArea = area.getMultiPostArea();
		this.koazaArea = area.getKoazaArea();
		this.chomeArea = area.getChomeArea();
		this.oldPostCode = area.getOldPost().getOldPostCode();
		this.postCode = post.getPostCode();
		this.multiArea = post.getMultiArea();
		this.updateShow = post.getUpdateShow();
		this.changeReason = post.getChangeReason();
	}
}
