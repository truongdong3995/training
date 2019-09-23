package com.training.api.services;

import com.training.api.entitys.TblCity;

import java.util.List;

public interface CityService {
    /**
     * Seatch City information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return List of {@link TblCity}
     */
    List<TblCity> searchCityByPrefectureCode(String prefectureCode);

    /**
     * Get all records of table tbl_city
     *
     * @return List of {@link TblCity}
     */
    List<TblCity> findAll();

    /**
     * Find record mapping by id
     *
     * @param id city id
     *
     * @return {@link TblCity}
     */
    TblCity findCityById(int id);

    /**
     * Create record in table tbl_city
     *
     * @param tblCity object {@link TblCity}
     */
    TblCity create(TblCity tblCity);

    /**
     * Update record in table tbl_city
     *
     * @param tblCity object {@link TblCity}
     */
    TblCity update(int cityId, TblCity tblCity);

    /**
     * Delete record in table tbl_city
     *
     * @param cityId Object {@link TblCity}
     * @return
     */
    TblCity deleteCity(int cityId);
}
