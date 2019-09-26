package com.training.api.entitys.fixtures;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblOldPost;
import com.training.api.entitys.TblPost;
import com.training.api.entitys.TblPrefecture;

/**
 * Fixtures for {@link TblArea}
 *
 */
public class TblAreaFixtures {

    public static TblArea createArea() {
        TblArea tblArea = new TblArea("ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ","以下に掲載がない場合",
                new TblCity(1073,"01102","ｻｯﾎﾟﾛｼｷﾀ","札幌市北区",
                        new TblPrefecture(258, "ﾎｯｶｲﾄﾞｳ","北海道", "01")),0,0,0,
                new TblPost("0010000",0,0,0),new TblOldPost("001"));

        return tblArea;
    }
}
