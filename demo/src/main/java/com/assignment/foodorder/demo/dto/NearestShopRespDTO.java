package com.assignment.foodorder.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.assignment.foodorder.demo.vo.PromotionVo;

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
public class NearestShopRespDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6299145319672273513L;

	private String id;

	private String name;

	private String avatarImage;

	private Double latitude;

	private Double longitude;
	
	private List<String> shopTypes = new ArrayList<>();

	private String rating;
	
	private String distance;
	
	private String timeToArrive;
	
	private String deliveryFee;
	
	private List<PromotionVo> promotionList;
}
