package com.training.api.model.cities;

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
	 * Test find city by prefecture code.
	 *
	 */
	@Test
	public void testFindByPrefecturePrefectureCode() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		List<City> actual = sut.findByPrefecturePrefectureCode(city.getPrefecture().getPrefectureCode());
		// verify
		assertThat(actual.size()).isEqualTo(2);
		City cityActual = actual.get(0);
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCityCode()).isEqualTo(city.getCityCode());
	}
	
	/**
	 * Test find city by city code.
	 *
	 */
	@Test
	public void testFindByCode() {
		// setup
		City city = CityFixtures.createCity();
		// exercise
		Optional<City> actual = sut.findByCityCode(city.getCityCode());
		// verify
		assertThat(actual).isPresent();
		City cityActual = actual.get();
		assertThat(cityActual.getCityKana()).isEqualTo(city.getCityKana());
		assertThat(cityActual.getCityName()).isEqualTo(city.getCityName());
		assertThat(cityActual.getCityCode()).isEqualTo(city.getCityCode());
	}
	
	/**
	 * Test insert/update city.
	 *
	 */
	@Test
	public void testSave() {
		// setup
		City city = CityFixtures.createCity();
		city.setCityName("update");
		city.setCityCode("11111");
		// exercise
		City registerCity = sut.save(city);
		City actual = sut.findByCityCode(city.getCityCode()).get();
		// verify
		assertThat(actual).isNotNull();
		assertThat(actual.getCityName()).isEqualTo(registerCity.getCityName());
		assertThat(actual.getCityCode()).isEqualTo(registerCity.getCityCode());
	}

	/**
	 * Test save city throws DataIntegrityViolationException.
	 *
	 */
	@Test
	public void testSaveThrowsDIVE() {
		// setup
		City city = CityFixtures.createCity();
		// exercise & verify
		assertThatThrownBy(() -> sut.save(city))
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	/**
	 * Test delete.
	 *
	 */
	@Test
	public void testDelete() {
		// setup
		String cityCode = "01103";
		// exercise
		Optional<City> actual = sut.findByCityCode(cityCode);
		sut.delete(actual.get());
		//verify
		assertThat(sut.findByCityCode(actual.get().getCityCode())).isEmpty();
	}
}
