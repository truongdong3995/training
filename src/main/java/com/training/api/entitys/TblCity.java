package com.training.api.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_city")
public class TblCity implements Serializable {
	
	@Id
	@Column(name = "city_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "city_kana")
	private String cityKana;
	
	@Column(name = "city")
	private String city;
	
	@ManyToOne
	@JoinColumn(name = "prefecture_id")
	private TblPrefecture tblPrefecture;

    public TblCity() {
    }

    public TblCity(int cityId, String code, String cityKana, String city, TblPrefecture tblPrefecture) {
        this.cityId = cityId;
        this.code = code;
        this.cityKana = cityKana;
        this.city = city;
        this.tblPrefecture = tblPrefecture;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityKana() {
        return cityKana;
    }

    public void setCityKana(String cityKana) {
        this.cityKana = cityKana;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TblPrefecture getTblPrefecture() {
        return tblPrefecture;
    }

    public void setTblPrefecture(TblPrefecture tblPrefecture) {
        this.tblPrefecture = tblPrefecture;
    }
}
