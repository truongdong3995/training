package com.training.api.models.fixtures;

import com.training.api.models.UpdatePostRequest;

/**
 * Fixtures for {@link UpdatePostRequest}
 *
 */
public class UpdatePostRequestFixtures {
	
	public static UpdatePostRequest createRequest() {
		UpdatePostRequest request = new UpdatePostRequest();
		request.setChangeReason(0);
		request.setMultiArea(0);
		request.setUpdateShow(0);
		
		return request;
	}
}
