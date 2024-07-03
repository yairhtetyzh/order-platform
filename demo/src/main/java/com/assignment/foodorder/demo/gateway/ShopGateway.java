package com.assignment.foodorder.demo.gateway;

import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

import com.assignment.foodorder.demo.dto.NearestShopRespDTO;
import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.ShopDTO;
import com.assignment.foodorder.demo.vo.ShopVo;

@MessagingGateway
public interface ShopGateway {
    @Gateway(requestChannel = "registerChannel")
    ShopDTO register(@Payload ShopDTO shopDTO);

    @Gateway(requestChannel = "updateChannel")
    ShopDTO update(@Payload ShopDTO shopDTO);

    @Gateway(requestChannel = "getShopNearChannel")
    PageDTO<NearestShopRespDTO> getShopNear(@Payload Object[] params);

    @Gateway(requestChannel = "getAllChannel")
    PageDTO<ShopVo> getAll(@Payload Pageable pageable);

    @Gateway(requestChannel = "findByIdChannel")
    ShopVo findById(@Payload String id);
}