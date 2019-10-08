package com.training.api.model.oldposts;

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
 * OldPost model.
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_old_post")
@Builder(toBuilder = true)
public class OldPost {
	
	/**
	 * Old post ID
	 */
	@Id
	@Column(name = "old_post_id")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int oldPostId;
	
	/**
	 * Old post code
	 */
	@Column(name = "old_post_code", unique = true)
	@JsonProperty("old_post_code")
	@NotNull
	@Length(max = 5)
	@Getter
	@Setter
	private String oldPostCode;
	
	
	/**
	 * Create instance.
	 *
	 * @param oldPostCode old post code
	 */
	public OldPost(String oldPostCode) {
		this.oldPostCode = oldPostCode;
	}
}
