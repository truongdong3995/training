package com.training.api.services;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.UpdateCityRequest;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.Common;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for {@link TblCity}.
 *
 */
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final AreaRepository areaRepository;

    /**
     * Get address information by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return searchPrefectureCodeResponseList List of {@link SearchPrefectureCodeResponse}
     * @throws IllegalArgumentException if input invalid
     * @throws NotFoundException if not found
     */
    public List<SearchPrefectureCodeResponse> searchAddressByPrefectureCode(String prefectureCode) throws NotFoundException {
        if (Common.checkValidNumber(prefectureCode) == false) {
            throw new IllegalArgumentException("Prefecture code must be halfsize number");
        }

        List<SearchPrefectureCodeResponse> searchPrefectureCodeResponseList =
                cityRepository.findByTblPrefecture_PrefectureCode(prefectureCode)
                        .stream().map(SearchPrefectureCodeResponse::new).collect(Collectors.toList());
        if (searchPrefectureCodeResponseList.size() == 0) {
            throw new NotFoundException("Not found result");
        }

        return searchPrefectureCodeResponseList;
    }


    /**
     * Get all {@link TblCity}
     *
     * @return List of {@link TblCity}
     */
    public List<TblCity> findAll() {
        return cityRepository.findAll();
    }


    /**
     * Find single {@link TblCity}.
     *
     * @param cityId city id
     *
     * @return Found @link TblCity}
     * @throws IllegalArgumentException if cityId invalid
     */
    public Optional<TblCity> findCityById(String cityId) {
        if (Common.checkValidNumber(cityId) == false) {
            throw new IllegalArgumentException("City id must be halfsize number");
        }

        return cityRepository.findById(Integer.valueOf(cityId));
    }

    /**
     * Delete existing {@link TblCity}
     *
     * @param cityId city id
     * @return deleted {@link TblCity}
     * @throws NullPointerException if cityId is null
     * @throws NotFoundException if city is not found
     */
    @Transactional(rollbackOn = Exception.class)
    public TblCity deleteCity(String cityId) throws NotFoundException {
        if (Common.checkValidNumber(cityId) == false) {
            throw new IllegalArgumentException("City id must be halfsize number");
        }
        TblCity tblCity = findCityById(cityId).orElseThrow(()-> new NotFoundException("City not found"));
        List<TblArea> tblAreaList= areaRepository.findByTblCity_CityId(Integer.valueOf(cityId));
        if (tblAreaList.size() > 0) {
            areaRepository.deleteAll(tblAreaList);
        }
        cityRepository.delete(tblCity);

        return tblCity;
    }

    /**
     * Create new {@link TblCity}
     *
     * @param createCity The City to post
     * @return created City
     * @throws ConflicException if create failed
     * @throws NullPointerException if argument is null
     */
    public TblCity create(TblCity createCity){
        Common.checkNotNull(createCity, "City must not be null");
        TblCity createdCity;
        try {
            createdCity = cityRepository.save(createCity);
        } catch (DataIntegrityViolationException e) {
            throw new ConflicException("City has been exist");
        }

        return createdCity;
    }

    /**
     * Update existing {@link TblCity}
     *
     * @param cityId city id
     * @param request The request to update
     * @return updatedCity updated area
     * @throws NotFoundException if city is not found
     * @throws NullPointerException if cityId is null
     * @throws ConflicException if update failed
     */
    public TblCity update(String cityId, UpdateCityRequest request) throws NotFoundException {
        if (Common.checkValidNumber(cityId) == false) {
            throw new IllegalArgumentException("City id must be halfsize number");
        }
        Common.checkNotNull(request, "City must not be null");
        TblCity tblCity = findCityById(cityId).map(request).orElseThrow(()-> new NotFoundException("City not found"));

        TblCity updatedCity;
        try {
            updatedCity = cityRepository.save(tblCity);
        } catch (DataIntegrityViolationException e) {
            throw new ConflicException("City has been exist");
        }

        return updatedCity;
    }
}
