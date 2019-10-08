package com.training.api.web.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.cities.City;
import com.training.api.model.prefectures.Prefecture;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

/**
 * Request to Register {@link City}.
 *
 */
@Getter
@Setter
public class RegisterCityRequest implements Supplier<City> {
	
	/**
	 * City code.
	 */
	@Length(max = 5)
	@NotNull
	@JsonProperty("code")
	private String cityCode;
	
	/**
	 * City kana.
	 */
	@Length(max = 100)
	@NotNull
	@JsonProperty("city_kana")
	private String cityKana;
	
	/**
	 * City name
	 */
	@Length(max = 100)
	@NotNull
	@JsonProperty("city")
	private String cityName;
	
	/**
	 * Prefecture
	 */
	@NotNull
	@JsonProperty("prefecture")
	private Prefecture prefecture;
	
	
	@Override
	public City get() {
		City city = new City();
		city.setCityCode(cityCode);
		city.setCityKana(cityKana);
		city.setCityName(cityName);
		city.setPrefecture(prefecture);
		
		return city;
	}
}
