package com.training.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.training.api.entitys.TblCity;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import com.training.api.models.PrefectureCodeResponse;
import com.training.api.services.CityService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * Request processing when call url mapping search address by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/prefectures/{prefectureCode}", method = RequestMethod.GET)
    public ResponseEntity searchByPrefectureCode(@PathVariable("prefectureCode") String prefectureCode) {
        try {
            List<PrefectureCodeResponse> prefectureCodeResponseList =
                    cityService.searchCityByPrefectureCode(prefectureCode).stream()
                            .map(PrefectureCodeResponse::new).collect(Collectors.toList());

            return new ResponseEntity<>(new RestData(prefectureCodeResponseList), HttpStatus.OK);
        } catch (InvalidInputException ex) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all records in table city
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/city/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<TblCity> newList = cityService.findAll();

        return new ResponseEntity(new RestData(newList), HttpStatus.OK);
    }

    /**
     * Delete record when id mapping
     *
     * @param cityId city id
     * @return ResponseEntity
     */
    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable("cityId") int cityId){
        try {
            TblCity deleteCity = cityService.deleteCity(cityId);

            return new ResponseEntity<>(deleteCity, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Find city by city id
     *
     * @param cityId city id
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET)
    public ResponseEntity findCityById(@PathVariable("cityId") int cityId){
        try {
            TblCity tblCity = cityService.findCityById(cityId);

            return new ResponseEntity<>(tblCity, HttpStatus.OK);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ApiMessage.error500(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create record into table city
     *
     * @param tblCity object TblCity
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/city/", method = RequestMethod.PUT)
    public ResponseEntity registerCity(@RequestBody TblCity tblCity){
        try {
            TblCity tblCityCreate = cityService.create(tblCity);

            return new ResponseEntity<>(tblCityCreate, HttpStatus.OK);
        } catch (ConflicException e) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update city in table city
     *
     * @param request TblCity
     * @param id city id
     * @return
     */
    @RequestMapping(value = "/city/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCity(@RequestBody TblCity request, @PathVariable("id") int id){
        try {
            TblCity updateTblCity = cityService.update(id, request);

            return new ResponseEntity<>(updateTblCity, HttpStatus.OK);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }
}
