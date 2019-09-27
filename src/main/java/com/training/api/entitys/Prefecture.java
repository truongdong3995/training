package com.training.api.entitys;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Prefecture model
 *
 */
@Entity
@Table(name = "tbl_prefecture", uniqueConstraints = @UniqueConstraint(columnNames = {
	"prefecture_kana",
	"prefecture"
}))
public class Prefecture {
	
	@Id
	@Column(name = "prefecture_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("prefecture_id")
	@Getter
	@Setter
	private int prefectureId;
	
	@Column(name = "prefecture_kana")
	@JsonProperty("prefecture_kana")
	@NotNull
	@Length(max = 100)
	@Getter
	@Setter
	private String prefectureKana;
	
	@Column(name = "prefecture")
	@NotNull
	@JsonProperty("prefecture")
	@Length(max = 100)
	@Getter
	@Setter
	private String prefecture;
	
	@Column(name = "prefecture_code", unique = true)
	@NotNull
	@JsonProperty("prefecture_code")
	@Length(max = 2)
	@Getter
	@Setter
	private String prefectureCode;
	
	
	/**
	 * Create instance
	 *
	 */
	public Prefecture() {
	}
	
	/**
	 * Create instance
	 *
	 * @param prefectureId prefecture id
	 * @param prefectureKana prefecture kana
	 * @param prefecture prefecture
	 * @param prefectureCode prefecture code
	 */
	public Prefecture(int prefectureId, String prefectureKana, String prefecture, String prefectureCode) {
		this.prefectureId = prefectureId;
		this.prefectureKana = prefectureKana;
		this.prefecture = prefecture;
		this.prefectureCode = prefectureCode;
	}
}
