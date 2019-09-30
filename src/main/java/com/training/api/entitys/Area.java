package com.training.api.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
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
@Entity
@Table(name = "tbl_area")
public class Area implements Serializable {
	
	@Id
	@Column(name = "area_id")
	@JsonIgnore
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int areaId;
	
	@Column(name = "area_kana")
	@NotNull
	@Getter
	@Setter
	@JsonProperty("area_kana")
	private String areaKana;
	
	@Column(name = "area")
	@NotNull
	@Getter
	@Setter
	@JsonProperty("area")
	private String area;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	@Getter
	@Setter
	private City city;
	
	@Column(name = "chome_area")
	@NotNull
	@Getter
	@Setter
	@JsonProperty("chome_area")
	private int chomeArea;
	
	@Column(name = "koaza_area")
	@NotNull
	@Getter
	@Setter
	@JsonProperty("koaza_area")
	private int koazaArea;
	
	@Column(name = "multi_post_area")
	@JsonProperty("multi_post_area")
	@NotNull
	@Getter
	@Setter
	private int multiPostArea;
	
	@ManyToOne()
	@JoinColumn(name = "post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Getter
	@Setter
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "old_post_id")
	@Getter
	@Setter
	private OldPost oldPost;
	
	
	/**
	 * Create instance.
	 */
	public Area() {
	}
	
	/**
	 * Create instance.
	 *
	 * @param areaKana area kana
	 * @param area area
	 * @param city {@link City}
	 * @param chomeArea chome area
	 * @param koazaArea koaza area
	 * @param multiPostArea multi post area
	 * @param post {@link Post}
	 * @param oldPost {@link OldPost}
	 */
	public Area(String areaKana, String area, City city, int chomeArea, int koazaArea,
			int multiPostArea, Post post, OldPost oldPost) {
		this.areaKana = areaKana;
		this.area = area;
		this.city = city;
		this.chomeArea = chomeArea;
		this.koazaArea = koazaArea;
		this.multiPostArea = multiPostArea;
		this.post = post;
		this.oldPost = oldPost;
	}
}
