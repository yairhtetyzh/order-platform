package com.assignment.foodorder.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "promotion")
public class Promotion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045082410361237142L;

	@Id
	private String id;
	
	@Field(name = "name")
	private String name;
	
	@Field(name = "fromDate")
	private Date fromDate;
	
	@Field(name = "toDate")
	private Date toDate;
	
	@Field(name = "status")
	private Integer status;
	
	@Field(name = "discountAmount")
	private BigDecimal discountAmount;
	
	@Field(name = "shopIds")
	private List<String> shopIds;
}
