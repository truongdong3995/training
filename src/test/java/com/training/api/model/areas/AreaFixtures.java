package com.training.api.model.areas;

import com.training.api.model.cities.City;
import com.training.api.model.oldposts.OldPost;
import com.training.api.model.posts.Post;
import com.training.api.model.prefectures.Prefecture;

/**
 * Fixtures for {@link Area}.
 *
 */
public class AreaFixtures {
	
	public static Area createArea() {
		
		return new Area("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ", "以下に掲載がない場合",
				new City("01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
						new Prefecture(2288, "ﾎｯｶｲﾄﾞｳ", "北海道", "01")),
				0, 0, 0,
				new Post("0010000", 0, 0, 0), new OldPost("001"));
	}
}
