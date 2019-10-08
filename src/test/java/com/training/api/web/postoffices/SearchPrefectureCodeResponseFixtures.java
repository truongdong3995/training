package com.training.api.web.postoffices;

import com.training.api.model.cities.City;

class SearchPrefectureCodeResponseFixtures {
	
	static SearchPrefectureCodeResponse createResponse(City tblCity) {
		return new SearchPrefectureCodeResponse(tblCity);
	}
}
