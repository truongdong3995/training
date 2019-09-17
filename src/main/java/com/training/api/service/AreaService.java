package com.training.api.service;

import com.training.api.entity.TblArea;

import java.util.List;

public interface AreaService {
    /**
     * Get information by post code
     *
     * @param  postCode post code;
     *
     * @return TblArea
     */
    List<TblArea> searchAreaByPostCode(String postCode);


}
