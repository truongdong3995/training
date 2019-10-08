package com.training.api.web.postoffices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.areas.Area;
import com.training.api.model.cities.City;
import com.training.api.model.posts.Post;
import com.training.api.model.prefectures.Prefecture;
import com.training.api.utils.Common;
import lombok.Getter;
import lombok.Setter;

/**
 * Search by post code response.
 * 
 */
@Getter
@Setter
public class SearchPostCodeResponse {
	
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
	private String areaName;
	
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
		Common.checkNotNull(area.getCity(), "City must be not null");
		City city = area.getCity();
		Common.checkNotNull(city.getPrefecture(), "Prefecture must be not null");
		Prefecture prefecture = city.getPrefecture();
		Common.checkNotNull(area.getPost(), "Post must be not null");
		Post post = area.getPost();
		this.cityCode = city.getCityCode();
		this.cityName = city.getCityName();
		this.cityKana = city.getCityKana();
		this.prefectureName = prefecture.getPrefectureName();
		this.prefectureKana = prefecture.getPrefectureKana();
		this.prefectureCode = prefecture.getPrefectureCode();
		this.areaName = area.getAreaName();
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
