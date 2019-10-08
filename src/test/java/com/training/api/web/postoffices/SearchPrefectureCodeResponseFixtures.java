package com.training.api.web.postoffices;

import com.training.api.model.cities.City;

/**
 * Fixtures for search by prefecture code
 */
class SearchPrefectureCodeResponseFixtures {
	
	static SearchPrefectureCodeResponse createResponse(City tblCity) {
		return new SearchPrefectureCodeResponse(tblCity);
	}
}
