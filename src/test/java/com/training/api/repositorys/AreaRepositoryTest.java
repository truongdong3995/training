package com.training.api.repositorys;

import com.training.api.entitys.Area;
import com.training.api.entitys.fixtures.AreaFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link AreaRepository}.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AreaRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	AreaRepository sut;
	
	
	/**
	 * Test find area by city id
	 *
	 */
	@Test
	public void testFindByCity_CityId() {
		// setup
		int cityId = 413134;
		// exercise
		List<Area> actual = sut.findByCity_CityId(cityId);
		// verify
		assertThat(actual.size()).isEqualTo(1);
		Area areaActual = actual.get(0);
		
		assertThat(areaActual.getCity().getCityId()).isEqualTo(cityId);
	}
	
	/**
	 * Test find by post code
	 *
	 */
	@Test
	public void testFindByPost_PostCode() {
		// setup
		Area area = AreaFixtures.createArea();
		// exercise
		List<Area> actual = sut.findByPost_PostCode(area.getPost().getPostCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		Area areaActual = actual.get(0);
		assertThat(areaActual.getPost().getPostCode()).isEqualTo(area.getPost().getPostCode());
	}
	
	/**
	 * Test find by post id
	 *
	 */
	@Test
	public void testFindByPost_PostId() {
		// setup
		int postId = 393221;
		// exercise
		List<Area> actual = sut.findByPost_PostId(postId);
		// verify
		assertThat(actual.size()).isEqualTo(1);
		Area areaActual = actual.get(0);
		assertThat(areaActual.getPost().getPostId()).isEqualTo(postId);
	}
}
