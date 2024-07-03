package com.assignment.foodorder.demo.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionVo {
	
	private String id;

	private String name;

	private String fromDate;

	private String toDate;

	private String status;

	private BigDecimal discountAmount;

	private List<String> shopIds;
}
