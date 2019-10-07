package com.training.api.entitys.fixtures;

import com.training.api.entitys.City;
import com.training.api.entitys.Prefecture;

/**
 * Fixtures for {@link City}
 *
 */
public class CityFixtures {
	
	public static City createCity() {
		City city = new City("01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
				new Prefecture(2288, "ﾎｯｶｲﾄﾞｳ", "北海道", "01"));
		city.setCityId(13265);

		return city;
	}
}
