package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

/**
 * Request to Register {@link Post}.
 *
 */
public class RegisterPostRequest implements Supplier<Post> {
	
	@JsonProperty("post_code")
	@NotNull
	@Getter
	@Setter
	@NotNull
	@Length(max = 7)
	private String postCode;
	
	@NotNull
	@Getter
	@Setter
	@NotNull
	@JsonProperty("update_show")
	private int updateShow;
	
	@JsonProperty("change_reason")
	@NotNull
	@Getter
	@Setter
	private int changeReason;
	
	@JsonProperty("multi_area")
	@NotNull
	@Getter
	@Setter
	private int multiArea;
	
	
	@Override
	public Post get() {
		Post tblPost = new Post(postCode, updateShow, changeReason, multiArea);
		
		return tblPost;
	}
}
