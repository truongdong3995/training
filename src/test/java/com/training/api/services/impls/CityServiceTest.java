package com.training.api.services.impls;

import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblPrefecture;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.CityRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class CityServiceTest {
    @InjectMocks
    private CityServiceImpl cityService;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    AreaRepository areaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll(){
        TblCity tblCity = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
        List<TblCity> tblCityList = new ArrayList<>();
        tblCityList.add(tblCity);

        Mockito.when(cityService.findAll()).thenReturn(tblCityList);
        Assert.assertThat(tblCityList, is(cityService.findAll()));
    }

    @Test
    public void testSearchCityByPrefectureCode(){
        TblCity tblCity = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
        List<TblCity> actualList = new ArrayList<>();
        actualList.add(tblCity);

        Mockito.when(cityRepository.findByTblPrefecture_PrefectureCode("09")).thenReturn(actualList);
        Assert.assertThat(actualList,is(cityService.searchCityByPrefectureCode("09")));
    }

    @Test
    public void findCityById(){
        TblCity tblCity = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));
        Optional<TblCity> acutalCity = Optional.of(tblCity);

        Mockito.when(cityRepository.findById(any())).thenReturn(acutalCity);
        Assert.assertThat(acutalCity,is(cityService.findCityById(8652)));
    }

    @Test
    public void testSave() {
        TblCity tblCity = new TblCity(8652,"09201","ｳﾂﾉﾐﾔｼ","宇都宮市",
                new TblPrefecture(263,"ﾄﾁｷﾞｹﾝ","栃木県","09"));

        Mockito.when(cityRepository.save(tblCity)).thenReturn(tblCity);
        Assert.assertThat(tblCity, is(cityRepository.save(tblCity)));
    }
}
