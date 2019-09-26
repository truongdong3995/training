package com.training.api.repositorys;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.fixtures.TblAreaFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AreaRepositoryTest {
    @Autowired
    AreaRepository sut;

    @Test
    public  void testFindById() {
        TblArea tblArea = TblAreaFixtures.createArea();

    }
}
