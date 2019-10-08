package com.training.api.model.postoffices;

import com.training.api.model.areas.Area;
import com.training.api.model.areas.AreaRepository;
import com.training.api.model.cities.City;
import com.training.api.model.areas.AreaFixtures;
import com.training.api.model.cities.CityFixtures;
import com.training.api.model.cities.CityRepository;
import com.training.api.web.postoffices.SearchPostCodeResponse;
import com.training.api.web.postoffices.SearchPrefectureCodeResponse;
import com.training.api.utils.ApiMessage;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link PostOfficeService}.
 */
@RunWith(SpringRunner.class)
public class PostOfficeServiceTest {
	
	private PostOfficeService sut;
	
	@Mock
	AreaRepository areaRepository;
	
	@Mock
	ApiMessage apiMessage;
	
	@Mock
	CityRepository cityRepository;

	@Before
	public void setUp() {
		sut = new PostOfficeService(areaRepository, apiMessage, cityRepository);
	}
	
	/**
	 * Test search address by prefecture code.
	 *
	 */
	@Test
	public void searchAddressByPrefectureCode() throws NotFoundException {
		// setup
		City city = CityFixtures.createCity();
		List<City> cityList = new ArrayList<>();
		cityList.add(city);
		Mockito.when(cityRepository.findByPrefecturePrefectureCode(anyString())).thenReturn(cityList);
		// exercise
		List<SearchPrefectureCodeResponse> actual =
				sut.searchByPrefectureCode(city.getPrefecture().getPrefectureCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		verify(cityRepository, times(1))
			.findByPrefecturePrefectureCode(city.getPrefecture().getPrefectureCode());
	}
	
	/**
	 * Test search address by prefecture code throws IllegalArgumentException.
	 *
	 */
	@Test
	public void searchCityByPrefectureCodeThrowIAE() {
		// setup
		String prefectureCode = "TEST";
		// exercise
		assertThatThrownBy(() -> sut.searchByPrefectureCode(prefectureCode))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test search address by prefecture code throws NotFoundException.
	 *
	 */
	@Test
	public void searchCityByPrefectureCodeThrowNFE() {
		// setup
		City city = CityFixtures.createCity();
		Mockito.when(cityRepository.findByPrefecturePrefectureCode(anyString())).thenReturn(new ArrayList<>());
		// exercise
		assertThatThrownBy(() -> sut.searchByPrefectureCode(city.getPrefecture().getPrefectureCode()))
			.isInstanceOf(NotFoundException.class);
	}
	
	/**
	 * Test search address by post code
	 *
	 */
	@Test
	public void searchAddressByPostCode() throws NotFoundException {
		// setup
		Area tblArea = AreaFixtures.createArea();
		List<Area> tblAreaList = new ArrayList<>();
		tblAreaList.add(tblArea);
		Mockito.when(areaRepository.findByPostPostCode(anyString())).thenReturn(tblAreaList);
		// exercise
		List<SearchPostCodeResponse> actual =
				sut.searchByPostCode(tblArea.getPost().getPostCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
	}
	
	/**
	 * Test search address by post code throws IllegalArgumentException
	 *
	 */
	@Test
	public void searchSearchAddressByPostCodeThrowIAE() {
		String postCode = "POST_CODE_TEST";
		// exercise
		assertThatThrownBy(() -> sut.searchByPostCode(postCode))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	/**
	 * Test search address by post code throws NotFoundException
	 *
	 */
	@Test
	public void searchSearchAddressByPostCodeThrowNFE() {
		// setup
		Area tblArea = AreaFixtures.createArea();
		Mockito.when(areaRepository.findByPostPostCode(anyString())).thenReturn(new ArrayList<>());
		// exercise
		assertThatThrownBy(() -> sut.searchByPostCode(tblArea.getPost().getPostCode()))
			.isInstanceOf(NotFoundException.class);
	}
}
