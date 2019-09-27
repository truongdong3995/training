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
public class RegisterCityRequest implements Supplier<City> {
	
	@Getter
	@Setter
	@Length(max = 5)
	@NotNull
	@JsonProperty("code")
	private String code;
	
	@Getter
	@Setter
	@Length(max = 100)
	@NotNull
	@JsonProperty("city_kana")
	private String cityKana;
	
	@Getter
	@Setter
	@Length(max = 100)
	@NotNull
	@JsonProperty("city")
	private String city;
	
	@Getter
	@Setter
	@NotNull
	@JsonProperty("tblPrefecture")
	private Prefecture tblPrefecture;
	
	
	@Override
	public City get() {
		City tblCity = new City();
		tblCity.setCode(code);
		tblCity.setCityKana(cityKana);
		tblCity.setCity(city);
		tblCity.setTblPrefecture(tblPrefecture);
		
		return tblCity;
	}
}
