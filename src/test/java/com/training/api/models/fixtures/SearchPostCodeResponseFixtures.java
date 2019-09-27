package com.training.api.models.fixtures;

import com.training.api.entitys.Area;
import com.training.api.models.SearchPostCodeResponse;

/**
 * Fixtures for {@link SearchPostCodeResponse}
 *
 */
public class SearchPostCodeResponseFixtures {
	
	public static SearchPostCodeResponse createResponse(Area tblArea) {
		SearchPostCodeResponse response = new SearchPostCodeResponse(tblArea);
		
		return response;
	}
}
