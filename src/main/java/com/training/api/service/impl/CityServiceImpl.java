package com.training.api.service.impl;

import com.training.api.entity.TblCity;
import com.training.api.entity.TblPrefecture;
import com.training.api.repository.CityRepository;
import com.training.api.service.CityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Seatch City information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return TblCity
     */
    @Override
    public List<TblCity> searchCityByPrefectureCode(String prefectureCode) {
        return cityRepository.searchCityByPrefectureCode(prefectureCode);
    }

    /**
     * Get all records of Table City
     *
     * @return List of {@link TblCity}
     */
    @Override
    public List<TblCity> findAll() {
        return cityRepository.findAll();
    }

    /**
     * Delete record in table city
     *
     * @param tblCity
     */
    @Override
    public void deleteCity(TblCity tblCity) {
            cityRepository.delete(tblCity);
    }

    @Override
    public Optional<TblCity> findCityById(int id) {

        return cityRepository.findById(id);
    }

    @Override
    public void create(TblCity tblCity){
        cityRepository.save(tblCity);
    }
}
