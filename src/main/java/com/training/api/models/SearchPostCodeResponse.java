package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPost;
import com.training.api.entitys.TblPrefecture;
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

    public SearchPostCodeResponse(TblArea tblArea) {
        TblCity tblCity = tblArea.getTblCity();
        TblPrefecture tblPrefecture = tblCity.getTblPrefecture();
        TblPost tblPost = tblArea.getTblPost();
        this.code = tblCity.getCode();
        this.city = tblCity.getCity();
        this.cityKana = tblCity.getCityKana();
        this.prefecture = tblPrefecture.getPrefecture();
        this.prefectureKana = tblPrefecture.getPrefectureKana();
        this.prefectureCode = tblPrefecture.getPrefectureCode();
        this.area = tblArea.getArea();
        this.areaKana = tblArea.getAreaKana();
        this.multiPostArea = tblArea.getMultiPostArea();
        this.koazaArea = tblArea.getKoazaArea();
        this.chomeArea = tblArea.getChomeArea();
        this.oldPostCode = tblArea.getTblOldPost().getOldPostCode();
        this.postCode = tblPost.getPostCode();
        this.multiArea = tblPost.getMultiArea();
        this.updateShow = tblPost.getUpdateShow();
        this.changeReason = tblPost.getChangeReason();
    }
}
