package com.training.api.repository;

import com.training.api.entity.TblCity;

import java.util.List;

public interface CityRepositoryCustom {
    /**
     * Search city information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return Object {@link TblCity}
     */
    List<TblCity> searchCityByPrefectureCode(String prefectureCode);
}
