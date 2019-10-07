package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.Post;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Request to Update {@link Post}.
 */
@Getter
@Setter
public class UpdatePostRequest implements UnaryOperator<Post> {
	
	/**
	 * Update show
	 */
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
	public Post apply(Post tblPost) {
		
		return tblPost;
	}
}
