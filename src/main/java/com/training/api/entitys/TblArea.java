package com.training.api.entitys;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "tbl_area")
public class TblArea implements Serializable{
	
	@Id
	@Column(name = "area_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int areaId;
	
	@Column(name = "area_kana")
	private String areaKana;
	
	@Column(name = "area")
	private String area;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private TblCity tblCity;
	
	@Column(name = "chome_area")
	private int chomeArea;
	
	@Column(name = "koaza_area")
	private int koazaArea;
	
	@Column(name = "multi_post_area")
	private int multiPostArea;
	
	@ManyToOne()
	@JoinColumn(name = "post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TblPost tblPost;
	
	@ManyToOne
	@JoinColumn(name = "old_post_id")
	private TblOldPost tblOldPost;
	
	
	public TblArea() {
	}
	
	public TblArea(int areaId, String areaKana, String area, TblCity tblCity, int chomeArea, int koazaArea,
			int multiPostArea, TblPost tblPost, TblOldPost tblOldPost) {
		this.areaId = areaId;
		this.areaKana = areaKana;
		this.area = area;
		this.tblCity = tblCity;
		this.chomeArea = chomeArea;
		this.koazaArea = koazaArea;
		this.multiPostArea = multiPostArea;
		this.tblPost = tblPost;
		this.tblOldPost = tblOldPost;
	}
	
	public int getAreaId() {
		return areaId;
	}
	
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaKana() {
		return areaKana;
	}
	
	public void setAreaKana(String areaKana) {
		this.areaKana = areaKana;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public TblCity getTblCity() {
		return tblCity;
	}
	
	public void setTblCity(TblCity tblCity) {
		this.tblCity = tblCity;
	}
	
	public int getChomeArea() {
		return chomeArea;
	}
	
	public void setChomeArea(int chomeArea) {
		this.chomeArea = chomeArea;
	}
	
	public int getKoazaArea() {
		return koazaArea;
	}
	
	public void setKoazaArea(int koazaArea) {
		this.koazaArea = koazaArea;
	}
	
	public int getMultiPostArea() {
		return multiPostArea;
	}
	
	public void setMultiPostArea(int multiPostArea) {
		this.multiPostArea = multiPostArea;
	}
	
	public TblPost getTblPost() {
		return tblPost;
	}
	
	public void setTblPost(TblPost tblPost) {
		this.tblPost = tblPost;
	}
	
	public TblOldPost getTblOldPost() {
		return tblOldPost;
	}
	
	public void setTblOldPost(TblOldPost tblOldPost) {
		this.tblOldPost = tblOldPost;
	}
}
