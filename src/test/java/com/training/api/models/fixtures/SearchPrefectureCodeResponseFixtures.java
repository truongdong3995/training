package com.training.api.models.fixtures;

import com.training.api.entitys.City;
import com.training.api.models.SearchPrefectureCodeResponse;

public class SearchPrefectureCodeResponseFixtures {
	
	public static SearchPrefectureCodeResponse createResponse(City tblCity) {
		return new SearchPrefectureCodeResponse(tblCity);
	}
}
