package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.City;
import com.training.api.models.RegisterCityRequest;
import com.training.api.models.UpdateCityRequest;
import com.training.api.services.CityService;
import com.training.api.utils.exceptions.ConflictException;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Validated
@RestController
@Transactional
@Slf4j
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
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Get all {@link City}
	 *
	 * @return List of {@link City}
	 */
	@RequestMapping(value = "/city/", method = RequestMethod.GET)
	public ResponseEntity getAll() {
		List<City> newList = cityService.findAll();
		
		return new ResponseEntity(new RestData(newList), HttpStatus.OK);
	}
	
	/**
	 * Get city {@link City}.
	 *
	 * @param cityId City id
	 *
	 * @return {@link City} found
	 */
	@RequestMapping(value = "/citys/{cityId}", method = RequestMethod.GET)
	public ResponseEntity getCity(@PathVariable("cityId") String cityId) {
		try {
			City city = cityService.findCityById(cityId).orElseThrow(() -> new NotFoundException("City not found"));
			
			return new ResponseEntity<>(city, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Create new City {@link City}
	 *
	 * @param request The request to register {@link RegisterCityRequest}
	 *
	 * @return register {@link City}
	 */
	@RequestMapping(value = "/citys", method = RequestMethod.PUT)
	public ResponseEntity registerCity(@Validated @RequestBody RegisterCityRequest request) {
		try {
			City cityToRegister = request.get();
			City cityCreate = cityService.create(cityToRegister);
			
			return new ResponseEntity<>(cityCreate, HttpStatus.OK);
		} catch (ConflictException e) {
			return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * Update a existing {@link City}
	 *
	 * @param request The request to update {@link UpdateCityRequest}
	 *
	 * @param cityId City id
	 * @return
	 */
	@RequestMapping(value = "/citys/{cityId}", method = RequestMethod.POST)
	public ResponseEntity updateCity(@Validated @RequestBody UpdateCityRequest request,
			@PathVariable("cityId") String cityId) {
		try {
			City updateCity = cityService.findCityById(cityId)
				.map(request)
				.map(city -> cityService.update(city))
				.orElseThrow(() -> new NotFoundException("City no found"));
			
			return new ResponseEntity<>(updateCity, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new ApiMessage("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ConflictException e) {
			return new ResponseEntity<>(new ApiMessage("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Delete city {@link City}
	 *
	 * @param cityId City id
	 * @return deleted {@link City}
	 */
	@RequestMapping(value = "/citys/{cityId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCity(@PathVariable("cityId") String cityId) {
		try {
			City deleteCity = cityService.findCityById(cityId)
				.map(city -> cityService.deleteCity(city))
				.orElseThrow(() -> new NotFoundException("City no found"));
			
			return new ResponseEntity<>(deleteCity, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ApiMessage("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
