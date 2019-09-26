package com.training.api.models.fixtures;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.fixtures.TblAreaFixtures;
import com.training.api.models.SearchPostCodeResponse;

/**
 * Fixtures for {@link SearchPostCodeResponse}
 *
 */
public class SearchPostCodeResponseFixtures {
    public static SearchPostCodeResponse createReponse(TblArea tblArea ) {
        SearchPostCodeResponse response = new SearchPostCodeResponse(tblArea);

        return response;
    }
}
