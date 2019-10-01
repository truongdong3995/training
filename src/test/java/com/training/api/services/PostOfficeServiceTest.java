package com.training.api.services;

import com.training.api.entitys.Area;
import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.AreaFixtures;
import com.training.api.entitys.fixtures.CityFixtures;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.fixtures.SearchPostCodeResponseFixtures;
import com.training.api.models.fixtures.SearchPrefectureCodeResponseFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.ApiMessage;
import javassist.NotFoundException;
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
	 * Test search address by post code
	 *
	 */
	@Test
	public void searchAddressByPostCode() throws NotFoundException {
		// setup
		Area tblArea = AreaFixtures.createArea();
		SearchPostCodeResponse searchPostCodeResponse =
				SearchPostCodeResponseFixtures.createResponse(tblArea);
		List<Area> tblAreaList = new ArrayList<>();
		tblAreaList.add(tblArea);
		Mockito.when(areaRepository.findByPost_PostCode(anyString())).thenReturn(tblAreaList);
		// exercise
		List<SearchPostCodeResponse> actual =
				sut.searchAddressByPostCode(tblArea.getPost().getPostCode());
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
		assertThatThrownBy(() -> sut.searchAddressByPostCode(postCode))
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
		Mockito.when(areaRepository.findByPost_PostCode(anyString())).thenReturn(new ArrayList<>());
		// exercise
		assertThatThrownBy(() -> sut.searchAddressByPostCode(tblArea.getPost().getPostCode()))
			.isInstanceOf(NotFoundException.class);
	}
}
