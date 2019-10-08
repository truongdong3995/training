package com.training.api.web.cities;

import com.training.api.model.cities.City;
import com.training.api.model.cities.CityService;
import com.training.api.model.AlreadyExistsException;
import com.training.api.utils.ApiMessage;
import com.training.api.model.InvalidModelException;
import javassist.NotFoundException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpBadRequestException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpConflictException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC RESTful Controller to handle {@link City}s.
 *
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/cities")
public class CityController {
	
	private final CityService cityService;
	
	private final ApiMessage apiMessage;
	
	
	/**
	 * Create new {@link City}.
	 *
	 * @param request The request to create {@link City}
	 *
	 * @return register {@link City}
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity registerCity(
			@Validated @RequestBody RegisterCityRequest request) {
		try {
			City cityToRegister = request.get();
			City city = cityService.create(cityToRegister);
			
			return ResponseEntity.ok(city);
		} catch (AlreadyExistsException e) {
			throw new HttpConflictException(e.getMessage(), e);
		} catch (InvalidModelException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get city {@link City}.
	 *
	 * @param code code of City
	 *
	 * @return {@link City} found
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity getCity(
			@PathVariable("code") String code) {
		try {
			City city = cityService.find(code).orElseThrow(
					() -> new NotFoundException(
							apiMessage.getMessageError("controller.city.get.city_not_found", code)));
			
			return ResponseEntity.ok(city);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
	
	/**
	 * Update a existing {@link City}.
	 *
	 * @param request The request to update {@link City}
	 *
	 * @param code code of City
	 * @return updated {@link City}
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public ResponseEntity updateCity(
			@Validated @RequestBody UpdateCityRequest request,
			@PathVariable("code") String code) {
		try {
			City updatedCity = cityService.find(code)
				.map(request)
				.map(cityService::update)
				.orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.city.common.city_not_found", "update", code)));
			
			return ResponseEntity.ok(updatedCity);
		} catch (IllegalArgumentException | InvalidModelException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (AlreadyExistsException e) {
			throw new HttpConflictException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
	
	/**
	 * Delete {@link City}.
	 *
	 * @param code code of City
	 * @return deleted {@link City}
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCity(
			@PathVariable("code") String code) {
		try {
			City deletedCity = cityService.find(code)
				.map(cityService::delete)
				.orElseThrow(
						() -> new NotFoundException(
								apiMessage.getMessageError("controller.city.common.city_not_found", "delete", code)));
			
			return ResponseEntity.ok(deletedCity);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		}
	}
}
