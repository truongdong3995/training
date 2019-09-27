package com.training.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CommonTest {
	
	@Test
	public void replaceDataTest() {
		String dataActual = "aa-bb";
		String resultExpect = "aabb";
		
		String resultActual = Common.replaceData(dataActual);
		Assert.assertEquals(resultExpect, resultActual);
	}
	
	@Test
	public void checkValidNumberTest() {
		String dataActucal = "aa_bb";
		Assert.assertEquals(false, Common.checkValidNumber(dataActucal));
		
	}
}
