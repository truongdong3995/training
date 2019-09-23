package com.training.api.services;

import com.training.api.entitys.TblArea;
import com.training.api.utils.exceptions.InvalidInputException;

import java.util.List;

public interface AreaService {
    /**
     * Get information by post code
     *
     * @param  postCode post code;
     *
     * @return TblArea
     */
    List<TblArea> searchAreaByPostCode(String postCode) throws InvalidInputException;
}
