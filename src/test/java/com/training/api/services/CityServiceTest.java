package com.training.api.services;

import com.training.api.entitys.TblCity;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.UpdateCityRequest;
import com.training.api.models.fixtures.SearchPrefectureCodeResponseFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.exceptions.ConflicException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @InjectMocks
    private CityService sut;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    AreaRepository areaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test get all City.
     *
     */
    @Test
    public void findAll(){
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);
        Mockito.when(cityRepository.findAll()).thenReturn(tblCityList);
        // exercise
        List<TblCity> actual = sut.findAll();
        // verify
        verify(cityRepository, times(1)).findAll(   );
        assertThat(actual).isEqualTo(tblCityList);
    }

    /**
     * Test search address by prefecture code.
     *
     */
    @Test
    public void searchAddressByPrefectureCode() throws NotFoundException {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        SearchPrefectureCodeResponse searchPrefectureCodeResponse =
                SearchPrefectureCodeResponseFixtures.createReponse(tblCity);
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);
        Mockito.when(cityRepository.findByTblPrefecture_PrefectureCode(anyString())).thenReturn(tblCityList);
        // exercise
        List<SearchPrefectureCodeResponse> actual =
                sut.searchAddressByPrefectureCode(tblCity.getTblPrefecture().getPrefectureCode());
        // verify
        assertThat(actual.get(0)).isEqualTo(searchPrefectureCodeResponse);
        verify(cityRepository, times(1))
                .findByTblPrefecture_PrefectureCode(tblCity.getTblPrefecture().getPrefectureCode());
    }

    /**
     * Test search address by prefecture code throws IllegalArgumentException.
     *
     */
    @Test
    public void searchCityByPrefectureCodeThrowIAE(){
        // exercise
        assertThatThrownBy(() -> sut.searchAddressByPrefectureCode(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test search address by prefecture code throws NotFoundException.
     *
     */
    @Test
    public void searchCityByPrefectureCodeThrowNFE(){
        Mockito.when(cityRepository.findByTblPrefecture_PrefectureCode(anyString())).thenReturn(new ArrayList<>());
        // exercise
        assertThatThrownBy(() -> sut.searchAddressByPrefectureCode(anyString()))
                .isInstanceOf(NotFoundException.class);
    }

    /**
     * Test find City by id.
     *
     */
    @Test
    public void findCityById(){
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));

        // exercise
        Optional<TblCity> actual = sut.findCityById(String.valueOf(tblCity.getCityId()));

        //verify
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(tblCity);
        verify(cityRepository, times(1)).findById(tblCity.getCityId());
    }

    /**
     * Test find City by id throws IllegalArgumentException.
     *
     */
    @Test
    public void findCityByIdThrowIAE(){
        // exercise
        assertThatThrownBy(() -> sut.findCityById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test creat new City.
     *
     */
    @Test
    public void create() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        when(cityRepository.save(any(TblCity.class))).thenReturn(tblCity);

        // exercise
        TblCity actual = sut.create(tblCity);
        // verify
        assertThat(actual).isEqualTo(tblCity);
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
     * Test creat new City throws ConflicException.
     *
     */
    @Test
    public void createThrowsCE() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(TblCity.class));
        // exercise
        assertThatThrownBy(() -> sut.create(null)).isInstanceOf(ConflicException.class);
    }

    /**
     * Test update City if exist.
     *
     */
    @Test
    public void update() throws NotFoundException {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        TblCity tblCity = TblCityFixtures.createCity();
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));
        Mockito.when(cityRepository.save(any(TblCity.class))).thenReturn(tblCity);
        // exercise
        TblCity actual = sut.update(String.valueOf(tblCity.getCityId()), request);
        // verify
        assertThat(actual).isEqualTo(tblCity);
    }

    /**
     * Test update City if exist throws NullPointerException.
     *
     */
    @Test
    public void updateThrowsNPE() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        // exercise
        assertThatThrownBy(() -> sut.update(String.valueOf(tblCity.getCityId()), null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Test update City if exist throws IllegalArgumentException.
     *
     */
    @Test
    public void updateThrowsIAE() {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        // exercise
        assertThatThrownBy(() -> sut.update(null, request)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test update City if exist throws ConflicException.
     *
     */
    @Test
    public void updateThrowsCE() {
        // setup
        UpdateCityRequest request = UpdateCityRequestFixtures.creatRequest();
        doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(TblCity.class));
        // exercise
        assertThatThrownBy(() -> sut.update(null, request)).isInstanceOf(ConflicException.class);
    }

    /**
     * Test delete City if exist.
     *
     */
    @Test
    public void deleteCity() throws NotFoundException {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));
        Mockito.when(areaRepository.findByTblCity_CityId(anyInt())).thenReturn(new ArrayList<>());
        // exercise
        TblCity actual = sut.deleteCity(String.valueOf(tblCity.getCityId()));
        // verify
        verify(cityRepository, times(1)).delete(tblCity);
        assertThat(actual).isEqualTo(tblCity);
    }

    /**
     * Test delete City if exist throws IllegalArgumentException.
     *
     */
    @Test
    public void deleteThrowsIAE() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        // exercise
        assertThatThrownBy(() -> sut.deleteCity(String.valueOf(tblCity.getCityId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test delete City if exist throws NotFoundException.
     *
     */
    @Test
    public void deleteThrowsNFE() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(NotFoundException.class).when(cityRepository).findById(anyInt());
        // exercise
        assertThatThrownBy(() -> sut.deleteCity(String.valueOf(tblCity.getCityId())))
                .isInstanceOf(NotFoundException.class);
    }
}
