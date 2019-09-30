package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.entitys.Post;
import com.training.api.entitys.Prefecture;
import lombok.Getter;
import lombok.Setter;

public class SearchPostCodeResponse {
	
	@JsonProperty("code")
	@Getter
	@Setter
	private String code;
	
	@JsonProperty("city")
	@Getter
	@Setter
	private String city;
	
	@JsonProperty("city_kana")
	@Getter
	@Setter
	private String cityKana;
	
	@JsonProperty("prefecture")
	@Getter
	@Setter
	private String prefecture;
	
	@JsonProperty("prefecture_kana")
	@Getter
	@Setter
	private String prefectureKana;
	
	@JsonProperty("prefecture_code")
	@Getter
	@Setter
	private String prefectureCode;
	
	@JsonProperty("area")
	@Getter
	@Setter
	private String area;
	
	@JsonProperty("area_kana")
	@Getter
	@Setter
	private String areaKana;
	
	@JsonProperty("multi_post_area")
	@Getter
	@Setter
	private int multiPostArea;
	
	@JsonProperty("koaza_area")
	@Getter
	@Setter
	private int koazaArea;
	
	@JsonProperty("chome_area")
	@Getter
	@Setter
	private int chomeArea;
	
	@JsonProperty("old_post_code")
	@Getter
	@Setter
	private String oldPostCode;
	
	@JsonProperty("post_code")
	@Getter
	@Setter
	private String postCode;
	
	@JsonProperty("multi_area")
	@Getter
	@Setter
	private int multiArea;
	
	@JsonProperty("update_show")
	@Getter
	@Setter
	private int updateShow;
	
	@JsonProperty("change_reason")
	@Getter
	@Setter
	private int changeReason;
	
	
	public SearchPostCodeResponse(Area tblArea) {
		City city = tblArea.getCity();
		Prefecture tblPrefecture = city.getPrefecture();
		Post tblPost = tblArea.getPost();
		this.code = city.getCode();
		this.city = city.getCityName();
		this.cityKana = city.getCityKana();
		this.prefecture = tblPrefecture.getPrefecture();
		this.prefectureKana = tblPrefecture.getPrefectureKana();
		this.prefectureCode = tblPrefecture.getPrefectureCode();
		this.area = tblArea.getArea();
		this.areaKana = tblArea.getAreaKana();
		this.multiPostArea = tblArea.getMultiPostArea();
		this.koazaArea = tblArea.getKoazaArea();
		this.chomeArea = tblArea.getChomeArea();
		this.oldPostCode = tblArea.getOldPost().getOldPostCode();
		this.postCode = tblPost.getPostCode();
		this.multiArea = tblPost.getMultiArea();
		this.updateShow = tblPost.getUpdateShow();
		this.changeReason = tblPost.getChangeReason();
	}
}
