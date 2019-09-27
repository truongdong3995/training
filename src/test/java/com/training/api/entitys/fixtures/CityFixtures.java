package com.training.api.entitys.fixtures;

import com.training.api.entitys.City;
import com.training.api.entitys.Prefecture;

/**
 * Fixtures for {@link City}
 *
 */
public class CityFixtures {
	
	public static City createCity() {
		City city = new City(1073, "01102", "ｻｯﾎﾟﾛｼｷﾀ", "札幌市北区",
				new Prefecture(258, "ﾎｯｶｲﾄﾞｳ", "北海道", "01"));
		
		return city;
	}
}
