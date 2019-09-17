package com.training.api.service.impl;

import com.training.api.entity.TblArea;
import com.training.api.entity.TblCity;
import com.training.api.repository.AreaRepository;
import com.training.api.repository.CityRepository;
import com.training.api.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AreaRepository areaRepository;

    /**
     * Search City information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return TblCity
     */
    @Override
    public List<TblCity> searchCityByPrefectureCode(String prefectureCode) {
        return cityRepository.findByTblPrefecture_PrefectureCode(prefectureCode);
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
    @Transactional(rollbackOn = Exception.class)
    public void deleteCity(TblCity tblCity) throws IllegalArgumentException {
            List<TblArea> tblAreaList= areaRepository.findByTblCity_CityId(tblCity.getCityId());
            areaRepository.deleteAll(tblAreaList);
            cityRepository.delete(tblCity);
    }

    /**
     * Find record mapping by id
     *
     * @param id city id
     *
     * @return Optional of{@link TblCity}
     */
    @Override
    public Optional<TblCity> findCityById(int id) {

        return cityRepository.findById(id);
    }

    /**
     * Save record in table city
     *
     * @param tblCity object city
     */
    @Override
    public TblCity save(TblCity tblCity){
        return cityRepository.save(tblCity);
    }
}
