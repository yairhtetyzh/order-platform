package com.assignment.foodorder.demo.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "shop")
public class Shop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5778394106757983615L;

	@Id
    private String id;

	@Field(name = "name")
    private String name;
	
	@Field(name = "avatarImage")
    private String avatarImage;
	
	@Field(name = "location")
	private GeoJsonPoint location;
	
	@Field(name = "shopTypes")
	private List<String> shopTypes;
	
	@Field(name = "rating")
    private String rating;
	
	@Field(name = "status")
    private Integer status;
}
