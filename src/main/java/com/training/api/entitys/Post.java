package com.training.api.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Post model
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "tbl_post")
public class Post {
	
	@Id
	@Column(name = "post_id")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int postId;
	
	@Column(name = "post_code", unique = true)
	@JsonProperty("post_code")
	@NotNull
	@Length(max = 7)
	@Getter
	@Setter
	private String postCode;
	
	@Column(name = "update_show")
	@NotNull
	@JsonProperty("update_show")
	@Getter
	@Setter
	private int updateShow;
	
	@Column(name = "change_reason")
	@JsonProperty("change_reason")
	@NotNull
	@Getter
	@Setter
	private int changeReason;
	
	@Column(name = "multi_area")
	@JsonProperty("multi_area")
	@NotNull
	@Getter
	@Setter
	private int multiArea;

	
	/**
	 * Create instance
	 *
	 * @param postCode post code
	 * @param updateShow update show
	 * @param changeReason change reason
	 * @param multiArea multi area
	 */
	public Post(String postCode, int updateShow, int changeReason, int multiArea) {
		this.postCode = postCode;
		this.updateShow = updateShow;
		this.changeReason = changeReason;
		this.multiArea = multiArea;
	}
}
