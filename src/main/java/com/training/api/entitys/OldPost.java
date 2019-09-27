package com.training.api.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * OldPost model
 *
 */
@Entity
@Table(name = "tbl_old_post")
public class OldPost {
	
	@Id
	@Column(name = "old_post_id")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int oldPostId;
	
	@Column(name = "old_post_code", unique = true)
	@JsonProperty("old_post_code")
	@NotNull
	@Length(max = 5)
	@Getter
	@Setter
	private String oldPostCode;
	
	
	/**
	 * Create instance
	 *
	 */
	public OldPost() {
	}
	
	/**
	 * Create instance
	 *
	 * @param oldPostCode old post code
	 */
	public OldPost(String oldPostCode) {
		this.oldPostCode = oldPostCode;
	}
}
