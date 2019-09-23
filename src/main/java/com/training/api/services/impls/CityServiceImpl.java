package com.training.api.services.impls;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.services.CityService;
import com.training.api.utils.Common;
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
     * @return PrefectureCodeResponse
     */
    @Override
    public List<TblCity> searchCityByPrefectureCode(String prefectureCode) {
        if (Common.checkValidNumber(Common.replaceData(prefectureCode)) == false) {
            throw new InvalidInputException();
        }

        List<TblCity> tblCityList = cityRepository.findByTblPrefecture_PrefectureCode(prefectureCode);
        if (tblCityList.size() == 0) {
            throw new NoExistResourcesException();
        }

        return tblCityList;
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
     * @param cityId city id
     * @return tblCity
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public TblCity deleteCity(int cityId) {
        TblCity tblCity = findCityById(cityId);
        List<TblArea> tblAreaList= areaRepository.findByTblCity_CityId(cityId);
        areaRepository.deleteAll(tblAreaList);
        cityRepository.delete(tblCity);

        return tblCity;
    }

    /**
     * Find record mapping by id
     *
     * @param id city id
     *
     * @return Optional of{@link TblCity}
     */
    @Override
    public TblCity findCityById(int id) throws  IllegalArgumentException{
        Optional<TblCity> tblCity = cityRepository.findById(id);

        if (tblCity.isPresent() == false) {
            throw new NoExistResourcesException();
        }

        return tblCity.get();
    }

    /**
     * Create record in table city
     *
     * @param tblCity object city
     */
    @Override
    public TblCity create(TblCity tblCity){
        if (cityRepository.findById(tblCity.getCityId()).isPresent()){
            throw  new ConflicException();
        }
        TblCity createCity = cityRepository.save(tblCity);

        return createCity;
    }

    /**
     * Create record in table city
     *
     * @param cityId city id
     * @param request
     *
     * @return {@link TblCity}
     */
    @Override
    public TblCity update(int cityId, TblCity request) {
        TblCity tblCity = findCityById(cityId);
        tblCity.setCity(request.getCity());
        tblCity.setCityKana(request.getCityKana());
        TblCity updateCity = cityRepository.save(tblCity);

        return updateCity;
    }
}
