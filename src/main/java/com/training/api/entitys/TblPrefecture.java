package com.training.api.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_prefecture")
public class TblPrefecture {
	
	@Id
	@Column(name = "prefecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prefectureId;
	
	@Column(name = "prefecture_kana")
	private String prefectureKana;
	
	@Column(name = "prefecture")
	private String prefecture;
	
	@Column(name = "prefecture_code")
	private String prefectureCode;
	
	public TblPrefecture() {
	}
	
	public TblPrefecture(int prefectureId, String prefectureKana, String prefecture, String prefectureCode) {
		this.prefectureId = prefectureId;
		this.prefectureKana = prefectureKana;
		this.prefecture = prefecture;
		this.prefectureCode = prefectureCode;
	}
	
	public int getPrefectureId() {
		return prefectureId;
	}
	
	public void setPrefectureId(int prefectureId) {
		this.prefectureId = prefectureId;
	}
	
	public String getPrefectureKana() {
		return prefectureKana;
	}
	
	public void setPrefectureKana(String prefectureKana) {
		this.prefectureKana = prefectureKana;
	}
	
	public String getPrefecture() {
		return prefecture;
	}
	
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	
	public String getPrefectureCode() {
		return prefectureCode;
	}
	
	public void setPrefectureCode(String prefectureCode) {
		this.prefectureCode = prefectureCode;
	}
}
