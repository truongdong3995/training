package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.Common;
import com.training.api.validators.ModelValidator;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for {@link City}.
 *
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CityService {
	
	private final CityRepository cityRepository;
	
	private final AreaRepository areaRepository;

	private final ApiMessage apiMessage;

	private final ModelValidator modelValidator;


	/**
	 * Get address information by prefecture code
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return searchPrefectureCodeResponseList List of {@link SearchPrefectureCodeResponse}
	 * @throws IllegalArgumentException if input invalid
	 * @throws NotFoundException if not found
	 */
	public List<SearchPrefectureCodeResponse> searchAddressByPrefectureCode(String prefectureCode)
			throws NotFoundException {
		if (Common.checkValidNumber(prefectureCode) == false) {
			throw new IllegalArgumentException(apiMessage.getMessageError("service.city.search.invalid_input"));
		}

		List<SearchPrefectureCodeResponse> searchPrefectureCodeResponseList =
				cityRepository.findByPrefecture_PrefectureCode(prefectureCode)
					.stream().map(SearchPrefectureCodeResponse::new).collect(Collectors.toList());
		if (searchPrefectureCodeResponseList.size() == 0) {
			throw new NotFoundException(apiMessage.getMessageError("service.city.search.not_found"));
		}
		
		return searchPrefectureCodeResponseList;
	}
	
	/**
	 * Get all {@link City}
	 *
	 * @return List of {@link City}
	 */
	public List<City> findAll() {
		return cityRepository.findAll();
	}
	
	/**
	 * Find single {@link City}.
	 *
	 * @param code city code
	 *
	 * @return Found {@link City}
	 * @throws IllegalArgumentException if cityId invalid
	 */
	public Optional<City> findCityByCode(String code) {
		if (Common.checkValidNumber(code) == false) {
			throw new IllegalArgumentException(apiMessage.getMessageError("service.city.find.code_invalid"));
		}
		
		return cityRepository.findByCode(code);
	}
	
	/**
	 * Create new {@link City}
	 *
	 * @param createCity The City to post
	 * @return created City
	 * @throws AlreadyExistsException if create failed
	 * @throws NullPointerException if argument is null
	 */
	@Transactional
	public City create(City createCity) {
		Common.checkNotNull(createCity, "City must not be null");
		City createdCity;
		try {
			modelValidator.validate(createCity);
			createdCity = cityRepository.save(createCity);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(apiMessage.getMessageError("service.city.create.conflict"));
		}
		
		return createdCity;
	}
	
	/**
	 * Update existing {@link City}
	 *
	 * @param updateCity {@link City}
	 * @return updatedCity updated area
	 * @throws NullPointerException if cityId is null
	 * @throws AlreadyExistsException if update failed
	 */
	@Transactional
	public City update(City updateCity) {
		Common.checkNotNull(updateCity, "City must not be null");
		
		City updatedCity;
		try {
			modelValidator.validate(updateCity);
			updatedCity = cityRepository.save(updateCity);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(apiMessage.getMessageError("service.city.update.conflict"));
		}
		
		return updatedCity;
	}
	
	/**
	 * Delete existing {@link City}
	 *
	 * @param deleteCity {@link City} delete
	 * @return deleted {@link City}
	 * @throws NullPointerException if cityId is null
	 * @throws NotFoundException if city is not found
	 */
	@Transactional
	public City deleteCity(City deleteCity) {
		List<Area> tblAreaList = areaRepository.findByCity_CityId(Integer.valueOf(deleteCity.getCityId()));
		if (tblAreaList.size() > 0) {
			areaRepository.deleteAll(tblAreaList);
		}
		cityRepository.delete(deleteCity);
		
		return deleteCity;
	}
}
