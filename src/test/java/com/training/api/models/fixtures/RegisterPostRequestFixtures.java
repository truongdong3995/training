package com.training.api.models.fixtures;

import com.training.api.models.RegisterPostRequest;

/**
 * Fixtures for {@link RegisterPostRequest}
 *
 */
public class RegisterPostRequestFixtures {
	
	public static RegisterPostRequest createRequest() {
		RegisterPostRequest request = new RegisterPostRequest();
		request.setPostCode("0010000");
		request.setChangeReason(0);
		request.setMultiArea(0);
		request.setUpdateShow(0);
		
		return request;
	}
}
