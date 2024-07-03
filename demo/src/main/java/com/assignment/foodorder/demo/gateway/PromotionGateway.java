package com.assignment.foodorder.demo.gateway;

import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.PromotionDTO;
import com.assignment.foodorder.demo.vo.PromotionVo;

@MessagingGateway
public interface PromotionGateway {

	@Gateway(requestChannel = "registerPromotionChannel")
	PromotionDTO register(@Payload PromotionDTO promotionDTO);

	@Gateway(requestChannel = "updatePromotionChannel")
	PromotionDTO update(@Payload PromotionDTO shopDTO);

	@Gateway(requestChannel = "getAllPromotionChannel")
	PageDTO<PromotionVo> getAll(@Payload Pageable pageable);

	@Gateway(requestChannel = "findByIdPromotionChannel")
	PromotionVo findById(@Payload String id);
}
