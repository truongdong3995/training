package com.training.api.repositorys;

import com.training.api.entitys.City;
import com.training.api.entitys.fixtures.CityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for {@link CityRepository}.
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	CityRepository sut;
	
	
	/**
	 * Test find city by prefecture code
	 *
	 */
	@Test
	public void testFindByPrefecture_PrefectureCode() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		List<City> actual = sut.findByPrefecture_PrefectureCode(city.getPrefecture().getPrefectureCode());
		// verify
		assertThat(actual.size()).isEqualTo(1);
		City cityActual = actual.get(0);
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCode()).isEqualTo(city.getCode());
	}
	
	/**
	 * Test find city by city code
	 *
	 */
	@Test
	public void testFindByCode() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		Optional<City> actual = sut.findByCode(city.getCode());
		// verify
		assertThat(actual).isPresent();
		City cityActual = actual.get();
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCode()).isEqualTo(city.getCode());
	}
	
	/**
	 * Test insert/update city
	 *
	 */
	@Test
	public void testSave() {
		// setup
		City city = CityFixtures.createCity();
		city.setCityName("update");
		city.setCode("11111");
		// exercise
		City registerCity = sut.save(city);
		City actual = sut.findByCode(city.getCode()).get();
		// verify
		assertThat(actual).isNotNull();
		assertThat(actual.getCityName()).isEqualTo(registerCity.getCityName());
		assertThat(actual.getCode()).isEqualTo(registerCity.getCode());
	}
	
	/**
	 * Test save/update city throws DataIntegrityViolationException
	 *
	 */
	@Test
	public void testCreateThrowsDIVE() {
		// setup
		City city = CityFixtures.createCity();
		// exercise & verify
		assertThatThrownBy(() -> sut.save(city))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test delete
	 *
	 */
	@Test
	public void testDelete() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		Optional<City> actual = sut.findByCode(city.getCode());
		sut.delete(actual.get());
		//verify
		assertThat(sut.findByCode(actual.get().getCode())).isEmpty();
	}
}
