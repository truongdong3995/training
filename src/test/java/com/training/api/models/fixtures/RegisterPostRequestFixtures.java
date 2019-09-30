package com.training.api.models.fixtures;

import com.training.api.models.RegisterPostRequest;

/**
 * Fixtures for {@link RegisterPostRequest}
 *
 */
public class RegisterPostRequestFixtures {
	
	public static RegisterPostRequest createRequest(String postCode) {
		RegisterPostRequest request = new RegisterPostRequest();
		request.setPostCode(postCode);
		request.setChangeReason(0);
		request.setMultiArea(0);
		request.setUpdateShow(0);
		
		return request;
	}
}
