package com.training.api.models.fixtures;

import com.training.api.entitys.TblCity;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.models.SearchPrefectureCodeResponse;

public class SearchPrefectureCodeResponseFixtures {
    public static SearchPrefectureCodeResponse createReponse(TblCity tblCity) {
        SearchPrefectureCodeResponse response = new SearchPrefectureCodeResponse(tblCity);

        return response;
    }
}
