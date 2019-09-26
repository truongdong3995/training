package com.training.api.entitys.fixtures;

import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;

/**
 * Fixtures for {@link TblCity}
 *
 */
public class TblCityFixtures {

    public static TblCity createCity() {
        TblCity tblCity = new TblCity(1073,"01102","ｻｯﾎﾟﾛｼｷﾀ","札幌市北区",
                        new TblPrefecture(258, "ﾎｯｶｲﾄﾞｳ","北海道", "01"));

        return tblCity;
    }
}
