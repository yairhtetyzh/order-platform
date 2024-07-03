package com.assignment.foodorder.demo.service;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;

import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.PromotionDTO;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.vo.PromotionVo;

public interface PromotionService {

	PromotionDTO register(@Valid PromotionDTO promotionDTO) throws RdpException;

	PageDTO<PromotionVo> getAll(Pageable pageable) throws RdpException;

	PromotionDTO update(@Valid PromotionDTO promotionDTO);

	PromotionVo findById(String id);

}
