package com.training.api.services.impls;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CityServiceTest {
    @InjectMocks
    private CityServiceImpl cityService;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    AreaRepository areaRepository;

    private TblCity tblCity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tblCity = new TblCity(8655,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
    }

    @Test
    public void findAll(){
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);

        Mockito.when(cityService.findAll()).thenReturn(tblCityList);
        Assert.assertThat(tblCityList, is(cityService.findAll()));
    }

    @Test
    public void searchCityByPrefectureCode(){
        List<TblCity> actualList = new ArrayList<>();
        actualList.add(tblCity);

        Mockito.when(cityRepository.findByTblPrefecture_PrefectureCode("09")).thenReturn(actualList);
        Assert.assertThat(actualList,is(cityService.searchCityByPrefectureCode("09")));
    }

    @Test(expected = NoExistResourcesException.class)
    public void searchCityByPrefectureCodeThrowNERE(){
        // exercise
        Mockito.when(cityRepository.findByTblPrefecture_PrefectureCode(anyString())).thenReturn(new ArrayList<>());
        cityService.searchCityByPrefectureCode("01");
    }

    @Test(expected = InvalidInputException.class)
    public void searchCityByPrefectureCodeThrowIIE(){
        // exercise
        cityService.searchCityByPrefectureCode("test01");
    }

    @Test
    public void findCityById(){
        // setup
        Optional<TblCity> expectedCity = Optional.of(tblCity);
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(expectedCity);

        // exercise
        TblCity actualCity = cityService.findCityById(1);

        //verify
        Assert.assertThat(expectedCity.get(),is(actualCity));
    }

    @Test(expected = NoExistResourcesException.class)
    public void findCityByIdThrowNERE(){
        // exercise
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.empty());
        cityService.findCityById(0);
    }


    @Test
    public void create() {
        // exercise
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.empty());
        Mockito.when(cityRepository.save(any(TblCity.class))).thenReturn(tblCity);
        TblCity actual = cityService.create(tblCity);

        // verify
        assertThat(actual).isEqualTo(tblCity);
    }

    @Test(expected = ConflicException.class)
    public void createThrowCE() {
        // exercise
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));
        cityService.create(tblCity);
    }

    @Test
    public void update() {
        // setup
        TblCity actual = new TblCity(8655,"09201","ｳﾂﾉﾐﾔｼupdate","宇都宮市update",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));
        Mockito.when(cityRepository.save(any(TblCity.class))).thenReturn(actual);

        // exercise
        TblCity updateCity = cityService.update(tblCity.getCityId(), tblCity);

        // verify
        assertThat(actual.getCity()).isEqualTo(updateCity.getCity());
        assertThat(actual.getCityKana()).isEqualTo(updateCity.getCityKana());
        verify(cityRepository, times(1)).findById(anyInt());
    }

    @Test
    public void delete() {
        Mockito.when(cityRepository.findById(anyInt())).thenReturn(Optional.of(tblCity));
        TblCity actual = cityService.deleteCity(tblCity.getCityId());

        //verify
        verify(areaRepository, times(1)).findByTblCity_CityId(anyInt());
        verify(areaRepository, times(1)).deleteAll(anyList());
        assertThat(actual).isEqualTo(tblCity);
    }
}
