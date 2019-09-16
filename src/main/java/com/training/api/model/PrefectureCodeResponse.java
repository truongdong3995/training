package com.training.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entity.TblCity;

public class PrefectureCodeResponse {

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

    public PrefectureCodeResponse(TblCity tblCity) {
        this.code = tblCity.getCode();
        this.city = tblCity.getCity();
        this.cityKana = tblCity.getCityKana();
        this.prefecture = tblCity.getTblPrefecture().getPrefecture();
        this.prefectureKana = tblCity.getTblPrefecture().getPrefectureKana();
        this.prefectureCode = tblCity.getTblPrefecture().getPrefectureCode();
    }
}
