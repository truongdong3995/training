package com.training.api.models.fixtures;

import com.training.api.entitys.Prefecture;
import com.training.api.models.RegisterCityRequest;

/**
 * Fixtures for {@link RegisterCityRequest}
 *
 */
public class RegisterCityRequestFixtures {
	
	public static RegisterCityRequest creatRequest(String code) {
		RegisterCityRequest request = new RegisterCityRequest();
		request.setCode(code);
		request.setCityKana("ｱﾏｸﾞﾝｶﾆｴﾁｮｳnew");
		request.setCity("海部郡蟹江町new");
		request.setPrefecture(new Prefecture(2288, "ｵｷﾅﾜｹﾝ", "沖縄県", "47"));
		
		return request;
	}
}
