package com.training.api.web.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.cities.City;
import com.training.api.model.prefectures.Prefecture;
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
	public City apply(City city) {
		Optional.ofNullable(getCityKana()).ifPresent(city::setCityKana);
		Optional.ofNullable(getCityName()).ifPresent(city::setCityName);
		Optional.ofNullable(getPrefecture()).ifPresent(city::setPrefecture);
		
		return city;
	}
}
