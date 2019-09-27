package com.training.api.entitys.fixtures;

import com.training.api.entitys.Post;

/**
 * Fixtures for {@link Post}
 *
 */
public class PostFixtures {
	
	public static Post createPost() {
		Post tblPost = new Post("0010000", 0, 0, 0);
		
		return tblPost;
	}
}
