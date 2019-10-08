package com.training.api.model.prefectures;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
 * Prefecture model.
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "tbl_prefecture", uniqueConstraints = @UniqueConstraint(columnNames = {
	"prefecture_kana",
	"prefecture"
}))
@Builder(toBuilder = true)
public class Prefecture {
	/**
	 * Prefecture ID
	 */
	@Id
	@Column(name = "prefecture_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("prefecture_id")
	@Getter
	@Setter
	private int prefectureId;

	/**
	 * Prefecture code
	 */
	@Column(name = "prefecture_code", unique = true)
	@NotNull
	@JsonProperty("prefecture_code")
	@Length(max = 2)
	@Getter
	@Setter
	private String prefectureCode;

	/**
	 * Prefecture kana
	 */
	@Column(name = "prefecture_kana")
	@JsonProperty("prefecture_kana")
	@NotNull
	@Length(max = 100)
	@Getter
	@Setter
	private String prefectureKana;

	/**
	 * Prefecture name
	 */
	@Column(name = "prefecture")
	@NotNull
	@JsonProperty("prefecture")
	@Length(max = 100)
	@Getter
	@Setter
	private String prefectureName;
	
	
	/**
	 * Create instance.
	 *
	 * @param prefectureId prefecture id
	 * @param prefectureKana prefecture kana
	 * @param prefectureName prefecture
	 * @param prefectureCode prefecture code
	 */
	public Prefecture(int prefectureId, String prefectureKana, String prefectureName, String prefectureCode) {
		this.prefectureId = prefectureId;
		this.prefectureKana = prefectureKana;
		this.prefectureName = prefectureName;
		this.prefectureCode = prefectureCode;
	}
}
