package com.assignment.foodorder.demo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.assignment.foodorder.demo.model.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopVo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2981047143538451118L;
	
	private String id;

	private String name;

	private String avatarImage;

	private Double latitude;

	private Double longitude;
	
	private List<String> shopTypes = new ArrayList<>();

	private String rating;
	
	public ShopVo(Shop shop) {
		this.id = shop.getId();
		this.name = shop.getName();
		this.avatarImage = shop.getAvatarImage();
		this.latitude = shop.getLocation().getY();
		this.longitude = shop.getLocation().getX();
		this.shopTypes = shop.getShopTypes();
		this.rating = shop.getRating();
	}
}
