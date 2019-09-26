package com.training.api.services;

import com.training.api.entitys.TblCity;
import com.training.api.entitys.fixtures.TblCityFixtures;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.models.fixtures.SearchPrefectureCodeResponseFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.exceptions.ConflicException;
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

    @Before
    public void setUp() {
        sut = new CityService(cityRepository,areaRepository);
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
        assertThat(actual.size()).isEqualTo(1);
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
        String cityId = "TEST";
        // exercise
        assertThatThrownBy(() -> sut.findCityById(cityId))
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
        assertThatThrownBy(() -> sut.create(tblCity)).isInstanceOf(ConflicException.class);
    }

    /**
     * Test update City if exist.
     *
     */
    @Test
    public void update() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        Mockito.when(cityRepository.save(any(TblCity.class))).thenReturn(tblCity);
        // exercise
        TblCity actual = sut.update(tblCity);
        // verify
        assertThat(actual).isEqualTo(tblCity);
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
     * Test update City if exist throws ConflicException.
     *
     */
    @Test
    public void updateThrowsCE() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        doThrow(DataIntegrityViolationException.class).when(cityRepository).save(any(TblCity.class));
        // exercise
        assertThatThrownBy(() -> sut.update(tblCity)).isInstanceOf(ConflicException.class);
    }

    /**
     * Test delete City if exist.
     *
     */
    @Test
    public void deleteCity() {
        // setup
        TblCity tblCity = TblCityFixtures.createCity();
        Mockito.when(areaRepository.findByTblCity_CityId(anyInt())).thenReturn(new ArrayList<>());
        // exercise
        TblCity actual = sut.deleteCity(tblCity);
        // verify
        verify(cityRepository, times(1)).delete(tblCity);
        assertThat(actual).isEqualTo(tblCity);
    }
}
