package com.training.api.service;

import com.training.api.entity.TblCity;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CityService {
    /**
     * Seatch City information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return TblCity
     */
    List<TblCity> searchCityByPrefectureCode(String prefectureCode);

    List<TblCity> findAll();

    Optional<TblCity> findCityById(int id);

    void create(TblCity tblCity);

    void deleteCity(TblCity tblCity);


}
