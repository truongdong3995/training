package com.training.api.model.cities;

import com.training.api.model.areas.AreaRepository;
import com.training.api.utils.ApiMessage;
import com.training.api.model.AlreadyExistsException;
import com.training.api.model.InvalidModelException;
import com.training.api.validators.ModelValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
	
	@Mock
	private ApiMessage apiMessage;
	
	
	@Before
	public void setUp() {
		sut = new CityService(cityRepository, areaRepository, apiMessage, modelValidator);
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
		doNothing().when(modelValidator).validate(any(City.class));
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
		doNothing().when(modelValidator).validate(any(City.class));
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
	 * Test find City by id.
	 *
	 */
	@Test
	public void find() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(cityRepository.findByCityCode(anyString())).thenReturn(Optional.of(city));
		
		// exercise
		Optional<City> actual = sut.find(city.getCityCode());
		
		//verify
		assertThat(actual).isPresent();
		assertThat(actual.get()).isEqualTo(city);
		verify(cityRepository, times(1)).findByCityCode(city.getCityCode());
	}
	
	/**
	 * Test find City by code throws IllegalArgumentException.
	 *
	 */
	@Test
	public void findThrowIAE() {
		String code = "TEST";
		// exercise
		assertThatThrownBy(() -> sut.find(code))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test update City if exist.
	 *
	 */
	@Test
	public void update() {
		// setup
		City city = CityFixtures.createCity();
		doNothing().when(modelValidator).validate(any(City.class));
		when(cityRepository.save(any(City.class))).thenReturn(city);
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
		doNothing().when(modelValidator).validate(any(City.class));
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
		Mockito.when(areaRepository.findByCityCityId(anyInt())).thenReturn(new ArrayList<>());
		// exercise
		City actual = sut.delete(city);
		// verify
		verify(cityRepository, times(1)).delete(city);
		assertThat(actual).isEqualTo(city);
	}
	
	/**
	 * Test delete City if exist throws NullPointerException.
	 *
	 */
	@Test
	public void deleteCityThrowNPE() {
		// exercise
		assertThatThrownBy(() -> sut.delete(null))
			.isInstanceOf(NullPointerException.class);
	}
}
