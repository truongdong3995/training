package com.training.api.model.cities;

import com.training.api.model.prefectures.Prefecture;

/**
 * Fixtures for {@link City}.
 *
 */
public class CityFixtures {
	
	public static City createCity() {
		City city = new City("01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
				new Prefecture(1, "ﾎｯｶｲﾄﾞｳ", "北海道", "01"));
		city.setCityId(13265);
		
		return city;
	}
}
