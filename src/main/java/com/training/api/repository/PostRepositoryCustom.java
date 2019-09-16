package com.training.api.repository;

import com.training.api.entity.TblArea;

import java.util.List;

public interface PostRepositoryCustom {
    /**
     * Search area information by post code
     *
     * @param  postCode post code;
     *
     * @return Object {@link TblArea}
     */
    List<TblArea> searchAreaByPostCode(String postCode);
}
