package com.training.api.model.cities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.prefectures.Prefecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * City model.
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_city", uniqueConstraints = @UniqueConstraint(columnNames = {
	"city_kana",
	"city",
	"prefecture_id"
}))
@Getter
@Setter
@Builder(toBuilder = true)
public class City implements Serializable {
	
	/**
	 * City id
	 */
	@Id
	@Column(name = "city_id")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityId;
	
	/**
	 * City code
	 */
	@Column(name = "code", unique = true)
	@NotNull
	@Length(max = 5)
	@JsonProperty("code")
	private String cityCode;
	
	/**
	 * City kana
	 */
	@Column(name = "city_kana")
	@NotNull
	@JsonProperty("city_kana")
	@Length(max = 100)
	private String cityKana;
	
	/**
	 * City name
	 */
	@Column(name = "city")
	@JsonProperty("city")
	@NotNull
	@Length(max = 100)
	private String cityName;
	
	/**
	 * Prefecture {@link Prefecture}
	 */
	@ManyToOne
	@JoinColumn(name = "prefecture_id")
	private Prefecture prefecture;
	
	
	/**
	 * Create instance
	 * @param cityCode city code
	 * @param cityKana city kana
	 * @param cityName city name
	 * @param prefecture {@link Prefecture}
	 */
	public City(String cityCode, String cityKana, String cityName, Prefecture prefecture) {
		this.cityCode = cityCode;
		this.cityKana = cityKana;
		this.cityName = cityName;
		this.prefecture = prefecture;
	}
}
