package com.training.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_old_post")
public class TblOldPost {
	
	@Id
	@Column(name = "old_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oldPostId;
	
	@Column(name = "old_post_code")
	private String oldPostCode;
	
	
	public TblOldPost() {
	}
	
	public TblOldPost(int oldPostId, String oldPostCode) {
		this.oldPostId = oldPostId;
		this.oldPostCode = oldPostCode;
	}
	
	public int getOldPostId() {
		return oldPostId;
	}
	
	public void setOldPostId(int oldPostId) {
		this.oldPostId = oldPostId;
	}
	
	public String getOldPostCode() {
		return oldPostCode;
	}
	
	public void setOldPostCode(String oldPostCode) {
		this.oldPostCode = oldPostCode;
	}
}
