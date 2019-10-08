package com.training.api.model.areas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.model.oldposts.OldPost;
import com.training.api.model.cities.City;
import com.training.api.model.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Area model.
 *
 */
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_area")
@Builder(toBuilder = true)
public class Area implements Serializable {
	
	/**
	 * Area id
	 */
	@Id
	@Column(name = "area_id")
	@JsonIgnore
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int areaId;
	
	/**
	 * Area kana
	 */
	@Column(name = "area_kana")
	@NotNull
	@Getter
	@Setter
	@JsonProperty("area_kana")
	private String areaKana;
	
	/**
	 * Area name
	 */
	@Column(name = "area")
	@Getter
	@Setter
	@JsonProperty("area")
	private String areaName;
	
	/**
	 * City {@link City}
	 */
	@ManyToOne
	@JoinColumn(name = "city_id")
	@Getter
	@Setter
	private City city;
	
	/**
	 * Chome area
	 */
	@Column(name = "chome_area")
	@Getter
	@Setter
	@JsonProperty("chome_area")
	private int chomeArea;
	
	/**
	 * Koaza area
	 */
	@Column(name = "koaza_area")
	@Getter
	@Setter
	@JsonProperty("koaza_area")
	private int koazaArea;
	
	/**
	 * Multi post area
	 */
	@Column(name = "multi_post_area")
	@JsonProperty("multi_post_area")
	@NotNull
	@Getter
	@Setter
	private int multiPostArea;
	
	/**
	 * Post {@link Post}
	 */
	@ManyToOne()
	@JoinColumn(name = "post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Getter
	@Setter
	private Post post;
	
	/**
	 * OldPost {@link OldPost}
	 */
	@ManyToOne
	@JoinColumn(name = "old_post_id")
	@Getter
	@Setter
	private OldPost oldPost;
	
	
	/**
	 * Create instance.
	 *
	 * @param areaKana area kana
	 * @param areaName area name
	 * @param city {@link City}
	 * @param chomeArea chome area
	 * @param koazaArea koaza area
	 * @param multiPostArea multi post area
	 * @param post {@link Post}
	 * @param oldPost {@link OldPost}
	 */
	public Area(String areaKana, String areaName, City city, int chomeArea, int koazaArea,
			int multiPostArea, Post post, OldPost oldPost) {
		this.areaKana = areaKana;
		this.areaName = areaName;
		this.city = city;
		this.chomeArea = chomeArea;
		this.koazaArea = koazaArea;
		this.multiPostArea = multiPostArea;
		this.post = post;
		this.oldPost = oldPost;
	}
}
