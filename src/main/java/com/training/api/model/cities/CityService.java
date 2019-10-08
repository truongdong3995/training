package com.training.api.model.cities;

import com.amazonaws.util.CollectionUtils;
import com.training.api.model.areas.Area;
import com.training.api.utils.ApiMessage;
import com.training.api.model.AlreadyExistsException;
import com.training.api.model.areas.AreaRepository;
import com.training.api.utils.Common;
import com.training.api.validators.ModelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
	
	private final ModelValidator modelValidator;
	
	
	/**
	 * Create new {@link City}.
	 *
	 * @param createCity The City to post
	 * @return created City
	 * @throws AlreadyExistsException if create failed
	 * @throws NullPointerException if argument is null
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public City create(City createCity) {
		Common.checkNotNull(createCity, "City must not be null");
		City city = createCity.toBuilder().build();

		City createdCity;
		try {
			modelValidator.validate(city);
			createdCity = cityRepository.save(city);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.city.common.area_already_exists", "create",
							city.getCityCode()));
		}
		
		return createdCity;
	}
	
	/**
	 * Find single {@link City}.
	 *
	 * @param code city code
	 *
	 * @return Found {@link City}
	 * @throws IllegalArgumentException if cityId invalid
	 */
	public Optional<City> find(String code) {
		if (Common.checkValidNumber(code) == false) {
			throw new IllegalArgumentException(apiMessage.getMessageError("service.city.find.code_invalid", code));
		}
		
		return cityRepository.findByCityCode(code);
	}
	
	/**
	 * Update existing {@link City}.
	 *
	 * @param updateCity {@link City}
	 * @return updatedCity updated area
	 * @throws NullPointerException if cityId is null
	 * @throws AlreadyExistsException if update failed
	 */
	public City update(City updateCity) {
		Common.checkNotNull(updateCity, "City must not be null");
		City city = updateCity.toBuilder().build();

		City updatedCity;
		try {
			modelValidator.validate(city);
			updatedCity = cityRepository.save(city);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException(
					apiMessage.getMessageError("service.city.common.area_already_exists", "update",
							city.getCityCode()));
		}
		
		return updatedCity;
	}
	
	/**
	 * Delete existing {@link City}.
	 *
	 * @param deleteCity {@link City} delete
	 * @return deleted {@link City}
	 */
	@Transactional
	public City delete(City deleteCity) {
		Common.checkNotNull(deleteCity, "City must not be null");
		List<Area> areaList = areaRepository.findByCityCityId(deleteCity.getCityId());
		if (CollectionUtils.isNullOrEmpty(areaList) == false) {
			areaRepository.deleteAll(areaList);
		}
		cityRepository.delete(deleteCity);
		
		return deleteCity;
	}
}
