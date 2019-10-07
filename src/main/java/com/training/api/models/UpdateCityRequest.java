package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.City;
import com.training.api.entitys.Prefecture;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Request to Update {@link City}.
 *
 */
@Getter
@Setter
public class UpdateCityRequest implements UnaryOperator<City> {
	
	/**
	 * City kana
	 */
	@Length(max = 100)
	@NotNull
	@JsonProperty("city_kana")
	private String cityKana;
	
	/**
	 * City
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
	public City apply(City tblCity) {
		Optional.ofNullable(getCityKana()).ifPresent(tblCity::setCityKana);
		Optional.ofNullable(getCity()).ifPresent(tblCity::setCityName);
		Optional.ofNullable(getPrefecture()).ifPresent(tblCity::setPrefecture);
		
		return tblCity;
	}
}
