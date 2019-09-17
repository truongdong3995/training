package com.training.api.service;

import com.training.api.entity.TblCity;

import java.util.List;
import java.util.Optional;

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
     * @return Optional of{@link TblCity}
     */
    Optional<TblCity> findCityById(int id);

    /**
     * Save record in table tbl_city
     *
     * @param tblCity object {@link TblCity}
     */
    TblCity save(TblCity tblCity);

    /**
     * Delete record in table tbl_city
     *
     * @param tblCity Object {@link TblCity}
     */
    void deleteCity(TblCity tblCity);
}
