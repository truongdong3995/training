package com.training.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.training.api.entity.TblCity;
import com.training.api.model.PrefectureCodeResponse;
import com.training.api.service.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import com.training.api.utils.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * Function request processing when call url mapping
     *
     * @param prefectureCode prefecture code
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/prefectures/{prefectureCode}", method = RequestMethod.GET)
    public ResponseEntity searchByPrefectureCode(@PathVariable("prefectureCode") String prefectureCode) {
        if (Common.checkValidNumber(prefectureCode) == false) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }
        List<TblCity> cityList = cityService.searchCityByPrefectureCode(prefectureCode);

        if (cityList.size() == 0) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new RestData(getListResponseFromCity(cityList)), HttpStatus.OK);
    }

    /**
     * Get all records in table city
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<TblCity> newList = cityService.findAll();
        return new ResponseEntity(new RestData(newList), HttpStatus.OK);
    }

    /**
     * Delete record when id mapping
     *
     * @param id city id
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/city/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable("id") int id){
        Optional<TblCity> tblCity = cityService.findCityById(id);
        if (tblCity.isPresent() == false) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        cityService.deleteCity(tblCity.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find city by city id
     *
     * @param id city id
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/city/find/{id}", method = RequestMethod.GET)
    public ResponseEntity findCityById(@PathVariable("id") int id){
        Optional<TblCity> tblCity = cityService.findCityById(id);

        if (tblCity.isPresent() == false) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tblCity.get(), HttpStatus.OK);
    }

    /**
     * Create record into table city
     *
     * @param tblCity
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/city/create", method = RequestMethod.PUT)
    public ResponseEntity createCity(@RequestBody TblCity tblCity){
        try {
            cityService.create(tblCity);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT, HttpStatus.OK);
    }

    /**
     * Get list response from city
     *
     * @param cityList List of {@link TblCity}
     *
     * @return List of{@link PrefectureCodeResponse}
     */
    private List<PrefectureCodeResponse> getListResponseFromCity(List<TblCity> cityList) {
        List<PrefectureCodeResponse> searchByPrefectureCodeResponseList = cityList.stream().map(tblCity->
                new PrefectureCodeResponse(tblCity)).collect(Collectors.toList());

        return searchByPrefectureCodeResponseList;
    }
}
