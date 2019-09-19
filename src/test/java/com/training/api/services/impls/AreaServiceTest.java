package com.training.api.services.impls;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblCity;
import com.training.api.entitys.TblOldPost;
import com.training.api.entitys.TblPost;
import com.training.api.entitys.TblPrefecture;
import com.training.api.repositorys.AreaRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;


@RunWith(SpringRunner.class)
public class AreaServiceTest {

    @InjectMocks
    private AreaServiceImpl areaService;

    @MockBean
    AreaRepository areaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchAreaByPostCode1(){
        TblArea tblArea = new TblArea(102854,"ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ","以下に掲載がない場合",
                new TblCity(8190,"01102","ｻｯﾎﾟﾛｼｷﾀ","札幌市北区",
                        new TblPrefecture(255,"ﾎｯｶｲﾄﾞｳ","北海道", "01")),0,0,0,
                new TblPost(262141,"001000",0,0,0),new TblOldPost(8192,"001"));
        List<TblArea> actualList = new ArrayList<>();
        actualList.add(tblArea);

        Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(actualList);
        Assert.assertThat(areaService.searchAreaByPostCode("0010000"),is(actualList));
    }

    @Test
    public void testSearchAreaByPostCode2(){
        Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(new ArrayList<>());
        Assert.assertEquals(0,areaService.searchAreaByPostCode("0010000").size());
    }
}