package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.City;
import com.training.api.entitys.Prefecture;
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
	private String code;

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
	private String city;

	/**
	 * Prefecture
	 */
	@NotNull
	@JsonProperty("prefecture")
	private Prefecture prefecture;
	
	
	@Override
	public City get() {
		City tblCity = new City();
		tblCity.setCode(code);
		tblCity.setCityKana(cityKana);
		tblCity.setCityName(city);
		tblCity.setPrefecture(prefecture);
		
		return tblCity;
	}
}
