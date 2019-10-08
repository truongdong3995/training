package com.training.api.web.cities;

import com.training.api.model.prefectures.Prefecture;

/**
 * Fixtures for {@link UpdateCityRequest}.
 *
 */
public class UpdateCityRequestFixtures {
	
	public static UpdateCityRequest creatRequest() {
		UpdateCityRequest request = new UpdateCityRequest();
		request.setCityKana("ｱﾏｸﾞﾝｶﾆｴﾁｮｳupdate");
		request.setCityName("海部郡蟹江町update");
		request.setPrefecture(new Prefecture(2288, "ｵｷﾅﾜｹﾝ", "沖縄県", "47"));
		
		return request;
	}
}
