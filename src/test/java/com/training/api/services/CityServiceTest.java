package com.training.api.services;

import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.AlreadyExistsException;
import com.training.api.utils.exceptions.InvalidModelException;
import com.training.api.validators.ModelValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test for {@link CityService}.
 */
@RunWith(SpringRunner.class)
public class CityServiceTest {
	
	private CityService sut;
	
	@Mock
	CityRepository cityRepository;
	
	@Mock
	AreaRepository areaRepository;
	
	@Mock
	ModelValidator modelValidator;
	
	ApiMessage apiMessage;
	
	
	@Before
	public void setUp() {
		sut = new CityService(cityRepository, areaRepository, apiMessage, modelValidator);
	}
	
	/**
	 * Test get all City.
	 *
	 */
	@Test
	public void findAll() {
		// setup
		City city = CityFixtures.createCity();
		List<City> cityList = new ArrayList<>();
		cityList.add(city);
		Mockito.when(cityRepository.findAll()).thenReturn(cityList);
		// exercise
		List<City> actual = sut.findAll();
		// verify
		verify(cityRepository, times(1)).findAll();
		assertThat(actual).isEqualTo(cityList);
	}
	
	/**
	 * Test find City by id.
	 *
	 */
	@Test
	public void findCityByCode() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(cityRepository.findByCode(anyString())).thenReturn(Optional.of(city));
		
		// exercise
		Optional<City> actual = sut.findCityByCode(city.getCode());
		
		//verify
		assertThat(actual).isPresent();
		assertThat(actual.get()).isEqualTo(city);
		verify(cityRepository, times(1)).findByCode(city.getCode());
	}
	
	/**
	 * Test find City by code throws IllegalArgumentException.
	 *
	 */
	@Test
	public void findCityByCodeThrowIAE() {
		String code = "TEST";
		// exercise
		assertThatThrownBy(() -> sut.findCityByCode(code))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test creat new City.
	 *
	 */
	@Test
	public void create() {
		// setup
		City city = CityFixtures.createCity();
		when(cityRepository.save(any(City.class))).thenReturn(city);
		
		// exercise
		City actual = sut.create(city);
		// verify
		assertThat(actual).isEqualTo(city);
	}
	
	/**
	 * Test creat new City throws NullPointerException.
	 *
	 */
	@Test
	public void createThrowsNPE() {
		// exercise
		assertThatThrownBy(() -> sut.create(null)).isInstanceOf(NullPointerException.class);
	}
	
	/**
	 * Test creat new City throws AlreadyExistsException.
	 *
	 */
	@Test
	public void createThrowsCE() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.create(city)).isInstanceOf(AlreadyExistsException.class);
	}
	
	/**
	 * Test creat new City throws InvalidModelException.
	 *
	 */
	@Test
	public void createThrowsIME() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(InvalidModelException.class).when(modelValidator).validate(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.create(city)).isInstanceOf(InvalidModelException.class);
	}
	
	/**
	 * Test update City if exist.
	 *
	 */
	@Test
	public void update() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(cityRepository.save(any(City.class))).thenReturn(city);
		// exercise
		City actual = sut.update(city);
		// verify
		assertThat(actual).isEqualTo(city);
	}
	
	/**
	 * Test update City if exist throws NullPointerException.
	 *
	 */
	@Test
	public void updateThrowsNPE() {
		// exercise
		assertThatThrownBy(() -> sut.update(null))
			.isInstanceOf(NullPointerException.class);
	}
	
	/**
	 * Test update City if exist throws AlreadyExistsException.
	 *
	 */
	@Test
	public void updateThrowsCE() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.update(city)).isInstanceOf(AlreadyExistsException.class);
	}
	
	/**
	 * Test update City if exist throws InvalidModelException.
	 *
	 */
	@Test
	public void updateThrowsIME() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(InvalidModelException.class).when(modelValidator).validate(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.update(city)).isInstanceOf(InvalidModelException.class);
	}
	
	/**
	 * Test delete City if exist.
	 *
	 */
	@Test
	public void deleteCity() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(areaRepository.findByCity_CityId(anyInt())).thenReturn(new ArrayList<>());
		// exercise
		City actual = sut.deleteCity(city);
		// verify
		verify(cityRepository, times(1)).delete(city);
		assertThat(actual).isEqualTo(city);
	}
}
