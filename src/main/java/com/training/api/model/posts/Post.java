package com.training.api.model.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
 * Post model.
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_post")
@Builder(toBuilder = true)
public class Post {
	
	/**
	 * Post ID
	 */
	@Id
	@Column(name = "post_id")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int postId;
	
	/**
	 * Post code
	 */
	@Column(name = "post_code", unique = true)
	@JsonProperty("post_code")
	@NotNull
	@Length(max = 7)
	@Getter
	@Setter
	private String postCode;
	
	/**
	 * Update show
	 */
	@Column(name = "update_show")
	@JsonProperty("update_show")
	@Getter
	@Setter
	private int updateShow;
	
	/**
	 * Change reason
	 */
	@Column(name = "change_reason")
	@JsonProperty("change_reason")
	@Getter
	@Setter
	private int changeReason;
	
	/**
	 * Multi area
	 */
	@Column(name = "multi_area")
	@JsonProperty("multi_area")
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
