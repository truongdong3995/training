package com.training.api.web.cities;

import com.training.api.model.prefectures.Prefecture;

/**
 * Fixtures for {@link RegisterCityRequest}.
 *
 */
public class RegisterCityRequestFixtures {
	
	public static RegisterCityRequest creatRequest(String code) {
		RegisterCityRequest request = new RegisterCityRequest();
		request.setCityCode(code);
		request.setCityKana("ｱﾏｸﾞﾝｶﾆｴﾁｮｳnew");
		request.setCityName("海部郡蟹江町new");
		request.setPrefecture(new Prefecture(2288, "ﾎｯｶｲﾄﾞｳ", "北海道", "01"));
		return request;
	}
}
