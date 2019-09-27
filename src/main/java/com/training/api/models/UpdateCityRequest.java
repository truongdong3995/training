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
public class UpdateCityRequest implements UnaryOperator<City> {
	
	@Getter
	@Setter
	@Length(max = 7)
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
	public City apply(City tblCity) {
		Optional.ofNullable(getCode()).ifPresent(tblCity::setCode);
		Optional.ofNullable(getCityKana()).ifPresent(tblCity::setCityKana);
		Optional.ofNullable(getCity()).ifPresent(tblCity::setCity);
		Optional.ofNullable(getTblPrefecture()).ifPresent(tblCity::setTblPrefecture);
		
		return tblCity;
	}
}
