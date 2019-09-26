package com.training.api.models.fixtures;

import com.training.api.entitys.TblPrefecture;
import com.training.api.models.RegisterCityRequest;

/**
 * Fixtures for {@link RegisterCityRequest}
 *
 */
public class RegisterCityRequestFixtures {
    public static RegisterCityRequest creatRequest(){
        RegisterCityRequest request = new RegisterCityRequest();
        request.setCode("47313");
        request.setCityKana("ｱﾏｸﾞﾝｶﾆｴﾁｮｳ");
        request.setCity("海部郡蟹江町");
        request.setTblPrefecture(new TblPrefecture(258, "ｵｷﾅﾜｹﾝ", "沖縄県",  "47"));

        return request;
    }
}
