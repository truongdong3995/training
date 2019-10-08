package com.training.api.web.postoffices;

import com.training.api.model.areas.Area;

/**
 * Fixtures for search by post code
 *
 */
class SearchPostCodeResponseFixtures {
	
	static SearchPostCodeResponse createResponse(Area tblArea) {
		return new SearchPostCodeResponse(tblArea);
	}
}
