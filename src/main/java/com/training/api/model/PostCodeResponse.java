package com.training.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entity.TblArea;

public class PostCodeResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("city")
    private String city;

    @JsonProperty("city_kana")
    private String cityKana;

    @JsonProperty("prefecture")
    private String prefecture;

    @JsonProperty("prefecture_kana")
    private String prefectureKana;

    @JsonProperty("prefecture_code")
    private String prefectureCode;

    @JsonProperty("area")
    private String area;

    @JsonProperty("area_kana")
    private String areaKana;

    @JsonProperty("multi_post_area")
    private int multiPostArea;

    @JsonProperty("koaza_area")
    private int koazaArea;

    @JsonProperty("chome_area")
    private int chomeArea;

    @JsonProperty("old_post_code")
    private String oldPostCode;

    @JsonProperty("post_code")
    private String postCode;

    @JsonProperty("multi_area")
    private int multiArea;

    @JsonProperty("update_show")
    private int updateShow;

    @JsonProperty("change_reason")
    private int changeReason;

    public PostCodeResponse(TblArea tblArea) {
        this.code = tblArea.getTblCity().getCode();
        this.prefecture = tblArea.getTblCity().getTblPrefecture().getPrefecture();
        this.city = tblArea.getTblCity().getCity();
        this.area = tblArea.getArea();
        this.oldPostCode = tblArea.getTblOldPost().getOldPostCode();
        this.postCode = tblArea.getTblPost().getPostCode();
        this.prefectureKana = tblArea.getTblCity().getTblPrefecture().getPrefectureKana();
        this.cityKana = tblArea.getTblCity().getCityKana();
        this.areaKana = tblArea.getAreaKana();
        this.multiArea = tblArea.getTblPost().getMultiArea();
        this.koazaArea = tblArea.getKoazaArea();
        this.chomeArea = tblArea.getChomeArea();
        this.multiPostArea = tblArea.getMultiPostArea();
        this.updateShow = tblArea.getTblPost().getUpdateShow();
        this.changeReason = tblArea.getTblPost().getChangeReason();
        this.prefectureCode = tblArea.getTblCity().getTblPrefecture().getPrefectureCode();
    }
}
