package com.training.api.web.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.posts.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

/**
 * Request to Register {@link Post}.
 *
 */
@Getter
@Setter
public class RegisterPostRequest implements Supplier<Post> {
	
	/**
	 * Post code
	 */
	@JsonProperty("post_code")
	@NotNull
	@Length(max = 7)
	private String postCode;
	
	/**
	 * Update show
	 */
	@NotNull
	@NotNull
	@JsonProperty("update_show")
	private int updateShow;
	
	/**
	 * Change reason
	 */
	@JsonProperty("change_reason")
	@NotNull
	private int changeReason;
	
	/**
	 * Multi area
	 */
	@JsonProperty("multi_area")
	@NotNull
	private int multiArea;
	
	
	@Override
	public Post get() {
		return new Post(postCode, updateShow, changeReason, multiArea);
	}
}
