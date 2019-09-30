package com.training.api.repositorys;

import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@TestExecutionListeners(value = FlywayTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
@ContextConfiguration(classes = {
	DataSourceAutoConfiguration.class,
	FlywayAutoConfiguration.class,
	ValidationAutoConfiguration.class
}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("unittest")
@ExtendWith(SpringExtension.class)
public class CityRepositoryTest {
	
	@Autowired
	CityRepository sut;
	
	
	/**
	 * Test find city by prefecture code
	 *
	 */
	@Test
	@FlywayTest
	public void testFindByPrefecture_PrefectureCode() {
		// setup
		City city = CityFixtures.createCity();
		sut.save(city);
		// exercise
		List<City> actual = sut.findByPrefecture_PrefectureCode(city.getPrefecture().getPrefectureCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		City cityActual = actual.get(0);
		assertThat(cityActual.getCityId()).isEqualTo(city.getCityId());
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCode()).isEqualTo(city.getCode());
	}
	
	/**
	 * Test find city by city code
	 *
	 */
	@Test
	@FlywayTest
	public void testFindByCode() {
		// setup
		City city = CityFixtures.createCity();
		sut.save(city);
		// exercise
		Optional<City> actual = sut.findByCode(city.getCode());
		// verify
		assertThat(actual).isPresent();
		City cityActual = actual.get();
		assertThat(cityActual.getCityId()).isEqualTo(city.getCityId());
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCode()).isEqualTo(city.getCode());
	}
	
	/**
	 * Test insert/update city
	 *
	 */
	@Test
	@FlywayTest
	public void testSave() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		City registerCity = sut.save(city);
		City actual = sut.findByCode(city.getCode()).get();
		// verify
		assertThat(actual).isNotNull();
		assertThat(actual.getCityId()).isEqualTo(registerCity.getCityId());
		assertThat(actual.getCityKana()).isEqualTo(registerCity.getCityKana());
		assertThat(actual.getCityName()).isEqualTo(registerCity.getCityName());
		assertThat(actual.getCode()).isEqualTo(registerCity.getCode());
	}
	
	/**
	 * Test save/update city throws DataIntegrityViolationException
	 *
	 */
	@Test
	@FlywayTest
	public void testCreateThrowsDIVE() {
		// setup
		City city = CityFixtures.createCity();
		sut.save(city);
		// exercise & verify
		assertThatThrownBy(() -> sut.save(city))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test save/update city throws DataIntegrityViolationException
	 *
	 */
	@Test
	@FlywayTest
	public void testUpdateThrowsDIVE() {
		// setup
		City city1 = CityFixtures.createCity();
		city1.setCode("1111");
		sut.save(city1);
		City city2 = CityFixtures.createCity();
		city2.setCode("2222");
		sut.save(city2);
		city2.setCode(city1.getCode());
		// exercise & verify
		assertThatThrownBy(() -> sut.save(city2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test delete
	 *
	 */
	@Test
	@FlywayTest
	public void testDelete() {
		// setup
		City city = CityFixtures.createCity();
		sut.save(city);
		// exercise
		Optional<City> actual = sut.findByCode(city.getCode());
		sut.delete(actual.get());
		//verify
		assertThat(sut.findByCode(actual.get().getCode())).isEmpty();
	}
}
