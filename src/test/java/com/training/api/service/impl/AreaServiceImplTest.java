package com.training.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.api.entity.TblArea;
import com.training.api.repository.AreaRepository;
import com.training.api.service.AreaService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class AreaServiceImplTest {

    @TestConfiguration
    public static class AreaServiceImplTestConfiguration{

        @Bean
        AreaService areaService(){
            return  new AreaServiceImpl();
        }
    }

    @MockBean
    AreaRepository areaRepository;

    @Autowired
    private AreaService areaService;

    @Before
    public void setUp() {
        String actual ="[" +
                "{\"areaId\":102854," +
                "\"areaKana\":\"ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ\"," +
                "\"area\":\"以下に掲載がない場合\"," +
                "\"tblCity\":{" +
                "   \"cityId\":8190," +
                "   \"code\":\"01102\"," +
                "   \"cityKana\":\"ｻｯﾎﾟﾛｼｷﾀｸ\"," +
                "   \"city\":\"札幌市北区\"," +
                "   \"tblPrefecture\":{" +
                "       \"prefectureId\":255," +
                "       \"prefectureKana\":\"ﾎｯｶｲﾄﾞｳ\"," +
                "       \"prefecture\":\"北海道\"," +
                "       \"prefectureCode\":\"01\"}}," +
                "\"chomeArea\":0," +
                "\"koazaArea\":0," +
                "\"multiPostArea\":0," +
                "\"tblPost\":{" +
                "   \"postId\":262141," +
                "   \"postCode\":\"0010000\"," +
                "   \"updateShow\":0," +
                "   \"changeReason\":0," +
                "   \"multiArea\":0}," +
                "\"tblOldPost\":{" +
                "   \"oldPostId\":8192," +
                "   \"oldPostCode\":\"001\"}}" +
                "]";
        List<TblArea> tblAreaList = new ObjectMapper().readValues(actual,TblArea.class);
    }

    public void testSearchAreaByPostCode(){
        Mockito.when(areaRepository.findByTblPost_PostCode("02")).thenReturn();
    }
}
