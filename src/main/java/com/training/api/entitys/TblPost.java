package com.training.api.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_post")
public class TblPost {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "update_show")
    private int updateShow;

    @Column(name = "change_reason")
    private int changeReason;

    @Column(name = "multi_area")
    private int multiArea;

    public TblPost() {
    }

    public TblPost(int postId, String postCode, int updateShow, int changeReason, int multiArea) {
        this.postId = postId;
        this.postCode = postCode;
        this.updateShow = updateShow;
        this.changeReason = changeReason;
        this.multiArea = multiArea;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getUpdateShow() {
        return updateShow;
    }

    public void setUpdateShow(int updateShow) {
        this.updateShow = updateShow;
    }

    public int getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(int changeReason) {
        this.changeReason = changeReason;
    }

    public int getMultiArea() {
        return multiArea;
    }

    public void setMultiArea(int multiArea) {
        this.multiArea = multiArea;
    }
}
