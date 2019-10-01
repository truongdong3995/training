package com.training.api.entitys.fixtures;

import com.training.api.entitys.Prefecture;

/**
 * Fixtures for {@link Prefecture}
 *
 */
public class PrefectureFixtures {
	
	public static Prefecture createPrefecture() {
		Prefecture tblPrefecture = new Prefecture(2288, "ﾎｯｶｲﾄﾞｳ", "北海道", "01");
		
		return tblPrefecture;
	}
}
