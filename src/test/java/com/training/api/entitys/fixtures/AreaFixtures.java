package com.training.api.entitys.fixtures;

import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.entitys.OldPost;
import com.training.api.entitys.Post;
import com.training.api.entitys.Prefecture;

/**
 * Fixtures for {@link Area}
 *
 */
public class AreaFixtures {
	
	public static Area createArea() {
		
		return new Area("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ", "以下に掲載がない場合",
				new City(1073, "01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
						new Prefecture(258, "ﾎｯｶｲﾄﾞｳ", "北海道", "01")),
				0, 0, 0,
				new Post("0010000", 0, 0, 0), new OldPost("001"));
	}
}
