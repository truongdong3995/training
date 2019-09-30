package com.training.api.repositorys;

import com.training.api.entitys.Area;
import com.training.api.entitys.fixtures.AreaFixtures;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@TestExecutionListeners(value = FlywayTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
@ContextConfiguration(classes = {
	DataSourceAutoConfiguration.class,
	FlywayAutoConfiguration.class,
	ValidationAutoConfiguration.class
}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("unittest")
@ExtendWith(SpringExtension.class)
public class AreaRepositoryTest {
	
	@Autowired
	AreaRepository sut;
	
	
	/**
	 * Test find area by city id
	 *
	 */
	@Test
	@FlywayTest
	public void testFindByCity_CityId() {
		// setup
		Area area = AreaFixtures.createArea();
		sut.save(area);
		// exercise
		List<Area> actual = sut.findByCity_CityId(area.getCity().getCityId());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		Area areaActual = actual.get(0);
		assertThat(areaActual.getArea()).isEqualTo(area.getArea());
		assertThat(areaActual.getAreaKana()).isEqualTo(area.getAreaKana());
		assertThat(areaActual.getChomeArea()).isEqualTo(area.getChomeArea());
		assertThat(areaActual.getCity().getCityId()).isEqualTo(area.getCity().getCityId());
	}
	
	/**
	 * Test find by post code
	 *
	 */
	@Test
	@FlywayTest
	public void testFindByPost_PostCode() {
		// setup
		Area area = AreaFixtures.createArea();
		sut.save(area);
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
	@FlywayTest
	public void testFindByPost_PostId() {
		// setup
		Area area = AreaFixtures.createArea();
		sut.save(area);
		// exercise
		List<Area> actual = sut.findByPost_PostId(area.getPost().getPostId());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		Area areaActual = actual.get(0);
		assertThat(areaActual.getPost().getPostId()).isEqualTo(area.getPost().getPostId());
	}
}
