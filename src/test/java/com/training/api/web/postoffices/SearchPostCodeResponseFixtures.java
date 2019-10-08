package com.training.api.web.postoffices;

import com.training.api.model.areas.Area;

/**
 * Fixtures for {@link SearchPostCodeResponse}
 *
 */
class SearchPostCodeResponseFixtures {
	
	static SearchPostCodeResponse createResponse(Area tblArea) {
		return new SearchPostCodeResponse(tblArea);
	}
}
