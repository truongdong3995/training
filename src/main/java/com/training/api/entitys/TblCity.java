package com.training.api.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
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
@Entity
@Table(name = "tbl_city", uniqueConstraints = @UniqueConstraint(columnNames = {"city_kana", "city", "prefecture_id"}))
public class TblCity implements Serializable {
	
	@Id
	@Column(name = "city_id")
    @JsonIgnore
    @Getter
    @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityId;
	
	@Column(name = "code", unique = true)
    @NotNull
    @Getter
    @Setter
    @Length(max = 5)
    @JsonProperty("code")
	private String code;
	
	@Column(name = "city_kana")
    @NotNull
    @Getter
    @Setter
    @JsonProperty("city_kana")
    @Length(max = 100)
	private String cityKana;
	
	@Column(name = "city")
    @JsonProperty("city")
    @NotNull
    @Getter
    @Setter
    @Length(max = 100)
	private String city;
	
	@ManyToOne
    @Getter
    @Setter
	@JoinColumn(name = "prefecture_id")
	private TblPrefecture tblPrefecture;

    /**
     * Create instance
     *
     */
    public TblCity() {
    }

    /**
     * Create instance
     * @param code code
     * @param cityKana city kana
     * @param city city
     * @param tblPrefecture {@link TblPrefecture}
     */
    public TblCity(int cityId, String code, String cityKana, String city, TblPrefecture tblPrefecture) {
        this.cityId = cityId;
        this.code = code;
        this.cityKana = cityKana;
        this.city = city;
        this.tblPrefecture = tblPrefecture;
    }
}
