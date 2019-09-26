package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.TblCity;
import com.training.api.models.RegisterCityRequest;
import com.training.api.models.UpdateCityRequest;
import com.training.api.services.CityService;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    /**
     * Request processing when call url mapping search address by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return List of {@link SearchPrefectureCodeResponse} found
     */
    @RequestMapping(value = "/post_offices/prefectures/{prefectureCode}", method = RequestMethod.GET)
    public ResponseEntity searchAddressByPrefectureCode(@PathVariable("prefectureCode") String prefectureCode) {
        try {
            List<SearchPrefectureCodeResponse> prefectureCodeResponseList =
                    cityService.searchAddressByPrefectureCode(prefectureCode);

            return new ResponseEntity<>(new RestData(prefectureCodeResponseList), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all {@link TblCity}
     *
     * @return List of {@link TblCity}
     */
    @RequestMapping(value = "/city/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<TblCity> newList = cityService.findAll();

        return new ResponseEntity(new RestData(newList), HttpStatus.OK);
    }

    /**
     * Delete city {@link TblCity}
     *
     * @param cityId City id
     * @return deleted {@link TblCity}
     */
    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCity(@PathVariable("cityId") String cityId){
        try {
            TblCity deleteCity = cityService.deleteCity(cityId);

            return new ResponseEntity<>(deleteCity, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get city {@link TblCity}.
     *
     * @param cityId City id
     *
     * @return {@link TblCity} found
     */
    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET)
        public ResponseEntity getCity(@PathVariable("cityId") String cityId){
        try {
            TblCity tblCity = cityService.findCityById(cityId).orElseThrow(() -> new NotFoundException("City not found"));

            return new ResponseEntity<>(tblCity, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create new City {@link TblCity}
     *
     * @param request The request to register {@link RegisterCityRequest}
     *
     * @return registed {@link TblCity}
     */
    @RequestMapping(value = "/city/", method = RequestMethod.PUT)
    public ResponseEntity registerCity(@Validated @RequestBody RegisterCityRequest request){
        try {
            TblCity cityToRegister = request.get();
            TblCity tblCityCreate = cityService.create(cityToRegister);

            return new ResponseEntity<>(tblCityCreate, HttpStatus.OK);
        } catch (ConflicException e) {
            return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a existing {@link TblCity}
     *
     * @param request The request to update {@link UpdateCityRequest}
     *
     * @param cityId City id
     * @return
     */
    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.PUT)
    public ResponseEntity updateCity(@Validated @RequestBody UpdateCityRequest request, @PathVariable("cityId") String cityId){
        try {
            TblCity updateTblCity = cityService.update(cityId, request);

            return new ResponseEntity<>(updateTblCity, HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (ConflicException e) {
            return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }
}
