package com.training.api.models.fixtures;

import com.training.api.entitys.TblPrefecture;
import com.training.api.models.UpdateCityRequest;

/**
 * Fixtures for {@link UpdateCityRequest}
 *
 */
public class UpdateCityRequestFixtures {
    public static UpdateCityRequest creatRequest(){
        UpdateCityRequest request = new UpdateCityRequest();
        request.setCode("47313");
        request.setCityKana("ｱﾏｸﾞﾝｶﾆｴﾁｮｳ");
        request.setCity("海部郡蟹江町");
        request.setTblPrefecture(new TblPrefecture(258, "ｵｷﾅﾜｹﾝ", "沖縄県",  "47"));

        return request;
    }
}
