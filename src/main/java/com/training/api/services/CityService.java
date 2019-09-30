package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflictException;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.Common;
import javassist.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for {@link City}.
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CityService {
	
	private final CityRepository cityRepository;
	
	private final AreaRepository areaRepository;

	private final ApiMessage apiMessage;


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
	 * @throws ConflictException if create failed
	 * @throws NullPointerException if argument is null
	 */
	public City create(City createCity) {
		Common.checkNotNull(createCity, "City must not be null");
		City createdCity;
		try {
			createdCity = cityRepository.save(createCity);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException(apiMessage.getMessageError("service.city.create.conflict"));
		}
		
		return createdCity;
	}
	
	/**
	 * Update existing {@link City}
	 *
	 * @param updateCity {@link City}
	 * @return updatedCity updated area
	 * @throws NullPointerException if cityId is null
	 * @throws ConflictException if update failed
	 */
	public City update(City updateCity) {
		Common.checkNotNull(updateCity, "City must not be null");
		
		City updatedCity;
		try {
			updatedCity = cityRepository.save(updateCity);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException(apiMessage.getMessageError("service.city.update.conflict"));
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
	@Transactional(rollbackOn = Exception.class)
	public City deleteCity(City deleteCity) {
		List<Area> tblAreaList = areaRepository.findByCity_CityId(Integer.valueOf(deleteCity.getCityId()));
		if (tblAreaList.size() > 0) {
			areaRepository.deleteAll(tblAreaList);
		}
		cityRepository.delete(deleteCity);
		
		return deleteCity;
	}
}
