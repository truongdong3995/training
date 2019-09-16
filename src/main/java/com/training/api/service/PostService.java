package com.training.api.service;

import com.training.api.entity.TblArea;

import java.util.List;

public interface PostService {
    /**
     * Get information by postcode
     *
     * @param  postCode postcode;
     *
     * @return TblArea
     */
    List<TblArea> searchAreaByPostCode(String postCode);

    /**
     * Search address by post code 2
     *
     * @param postCode post code
     * @return List of {@link TblArea}
     */
    List<TblArea> findByTblPostPostcode(String postCode);
}
