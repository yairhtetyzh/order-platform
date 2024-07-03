package com.assignment.foodorder.demo.service;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;

import com.assignment.foodorder.demo.dto.NearestShopRespDTO;
import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.ShopDTO;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.vo.ShopVo;

public interface ShopService {

	ShopDTO register(@Valid ShopDTO shopDTO) throws RdpException;

	PageDTO<NearestShopRespDTO> getAllShopNearLocation(String latitude, String longitude, Pageable pageable);

	PageDTO<ShopVo> getAll(Pageable pageable);

	ShopDTO update(@Valid ShopDTO shopDTO);
	
	ShopVo getById(String id) throws RdpException;

}
