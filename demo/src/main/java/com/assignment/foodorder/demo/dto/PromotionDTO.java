package com.assignment.foodorder.demo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.assignment.foodorder.demo.enums.CommonStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102622321515523652L;
	
	private String id;

	@NotEmpty(message = "Name must not be empty")
	private String name;

	@NotNull(message = "Latitude must not be empty")
	private String fromDate;

	@NotNull(message = "Latitude must not be empty")
	private String toDate;

	private Integer status = CommonStatus.ACTIVE.getCode();

	@NotNull(message = "Discount must not be empty")
	private BigDecimal discountAmount;

	@NotNull(message = "Shop List must not be empty")
	private List<String> shopIds;
}
