package com.assignment.foodorder.demo.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.assignment.foodorder.demo.enums.CommonStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ShopDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7749100658638071737L;

	private String id;
	
	@NotEmpty(message = "Name must not be empty")
    private String name;
	
	@NotEmpty(message = "Image must not be empty")
    private String avatarImage;
	
	@NotNull(message = "Latitude must not be empty")
    private double latitude;
	
	@NotNull(message = "Longitude must not be empty")
    private double longitude;
	
	private List<String> shopTypes;
	
    private String rating;
	
    @Min(1)
    @Max(2)
    private Integer status = CommonStatus.ACTIVE.getCode();
}
