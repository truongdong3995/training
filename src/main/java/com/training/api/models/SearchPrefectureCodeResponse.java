package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;
import lombok.Getter;
import lombok.Setter;

public class SearchPrefectureCodeResponse {

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

    public SearchPrefectureCodeResponse(TblCity tblCity) {
        TblPrefecture tblPrefecture = tblCity.getTblPrefecture();
        this.code = tblCity.getCode();
        this.city = tblCity.getCity();
        this.cityKana = tblCity.getCityKana();
        this.prefecture = tblPrefecture.getPrefecture();
        this.prefectureKana = tblPrefecture.getPrefectureKana();
        this.prefectureCode = tblPrefecture.getPrefectureCode();
    }
}
