package com.training.api.controllers;

import java.util.List;

import com.training.api.entitys.City;
import com.training.api.models.HttpExceptionResponse;
import com.training.api.models.RegisterCityRequest;
import com.training.api.models.UpdateCityRequest;
import com.training.api.services.CityService;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import com.training.api.utils.exceptions.InvalidModelException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CityController {
	
	private final CityService cityService;
	
	private final ApiMessage apiMessage;
	
	
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
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Get all {@link City}
	 *
	 * @return List of {@link City}
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public ResponseEntity getAll() {
		List<City> newList = cityService.findAll();
		
		return new ResponseEntity(new RestData(newList), HttpStatus.OK);
	}
	
	/**
	 * Get city {@link City}.
	 *
	 * @param code City code
	 *
	 * @return {@link City} found
	 */
	@RequestMapping(value = "/citys/{code}", method = RequestMethod.GET)
	public ResponseEntity getCity(@PathVariable("code") String code) {
		try {
			City city = cityService.findCityByCode(code).orElseThrow(
					() -> new NotFoundException(apiMessage.getMessageError("service.city.find.city_not_exist")));
			
			return new ResponseEntity<>(city, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Create new City {@link City}
	 *
	 * @param request The request to register {@link RegisterCityRequest}
	 *
	 * @return register {@link City}
	 */
	@RequestMapping(value = "/citys", method = RequestMethod.POST)
	public ResponseEntity registerCity(@Validated @RequestBody RegisterCityRequest request) {
		try {
			City cityToRegister = request.get();
			City cityCreate = cityService.create(cityToRegister);
			
			return new ResponseEntity<>(cityCreate, HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (InvalidModelException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Update a existing {@link City}
	 *
	 * @param request The request to update {@link UpdateCityRequest}
	 *
	 * @param code City code
	 * @return
	 */
	@RequestMapping(value = "/citys/{code}", method = RequestMethod.POST)
	public ResponseEntity updateCity(@Validated @RequestBody UpdateCityRequest request,
			@PathVariable("code") String code) {
		try {
			City updateCity = cityService.findCityByCode(code)
				.map(request)
				.map(city -> cityService.update(city))
				.orElseThrow(
						() -> new NotFoundException(apiMessage.getMessageError("service.city.find.city_not_exist")));
			
			return new ResponseEntity<>(updateCity, HttpStatus.OK);
		} catch (IllegalArgumentException | InvalidModelException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("409", e.getMessage()), HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Delete city {@link City}
	 *
	 * @param code City code
	 * @return deleted {@link City}
	 */
	@RequestMapping(value = "/citys/{code}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCity(@PathVariable("code") String code) {
		try {
			City deleteCity = cityService.findCityByCode(code)
				.map(city -> cityService.deleteCity(city))
				.orElseThrow(
						() -> new NotFoundException(apiMessage.getMessageError("service.city.delete.city_not_exist")));
			
			return new ResponseEntity<>(deleteCity, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
