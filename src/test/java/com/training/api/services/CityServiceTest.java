package com.training.api.services;

import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.fixtures.SearchPrefectureCodeResponseFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.exceptions.ConflictException;
import javassist.NotFoundException;
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
	
	ApiMessage apiMessage;
	
	
	@Before
	public void setUp() {
		sut = new CityService(cityRepository, areaRepository, apiMessage);
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
	 * Test search address by prefecture code.
	 *
	 */
	@Test
	public void searchAddressByPrefectureCode() throws NotFoundException {
		// setup
		City city = CityFixtures.createCity();
		SearchPrefectureCodeResponse searchPrefectureCodeResponse =
				SearchPrefectureCodeResponseFixtures.createResponse(city);
		List<City> cityList = new ArrayList<>();
		cityList.add(city);
		Mockito.when(cityRepository.findByPrefecture_PrefectureCode(anyString())).thenReturn(cityList);
		// exercise
		List<SearchPrefectureCodeResponse> actual =
				sut.searchAddressByPrefectureCode(city.getPrefecture().getPrefectureCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		verify(cityRepository, times(1))
			.findByPrefecture_PrefectureCode(city.getPrefecture().getPrefectureCode());
	}
	
	/**
	 * Test search address by prefecture code throws IllegalArgumentException.
	 *
	 */
	@Test
	public void searchCityByPrefectureCodeThrowIAE() {
		// exercise
		assertThatThrownBy(() -> sut.searchAddressByPrefectureCode(null))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test search address by prefecture code throws NotFoundException.
	 *
	 */
	@Test
	public void searchCityByPrefectureCodeThrowNFE() {
		Mockito.when(cityRepository.findByPrefecture_PrefectureCode(anyString())).thenReturn(new ArrayList<>());
		// exercise
		assertThatThrownBy(() -> sut.searchAddressByPrefectureCode(anyString()))
			.isInstanceOf(NotFoundException.class);
	}
	
	/**
	 * Test find City by id.
	 *
	 */
	@Test
	public void findCityById() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(city));
		
		// exercise
		Optional<City> actual = sut.findCityByCode(city.getCode());
		
		//verify
		assertThat(actual).isPresent();
		assertThat(actual.get()).isEqualTo(city);
		verify(cityRepository, times(1)).findById(city.getCityId());
	}
	
	/**
	 * Test find City by id throws IllegalArgumentException.
	 *
	 */
	@Test
	public void findCityByIdThrowIAE() {
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
	 * Test creat new City throws ConflictException.
	 *
	 */
	@Test
	public void createThrowsCE() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.create(city)).isInstanceOf(ConflictException.class);
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
	 * Test update City if exist throws ConflictException.
	 *
	 */
	@Test
	public void updateThrowsCE() {
		// setup
		City city = CityFixtures.createCity();
		doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(City.class));
		// exercise
		assertThatThrownBy(() -> sut.update(city)).isInstanceOf(ConflictException.class);
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
