package com.assignment.foodorder.demo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.foodorder.demo.config.ResponeModel;
import com.assignment.foodorder.demo.dto.NearestShopRespDTO;
import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.ShopDTO;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.gateway.ShopGateway;
import com.assignment.foodorder.demo.utils.ErrorCode;
import com.assignment.foodorder.demo.vo.ShopVo;

@RestController
@RequestMapping(value = "shop")
public class ShopController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ShopGateway shopGateway;

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponeModel register(@Valid @RequestBody ShopDTO shopDTO) {
		logger.debug("Start shop register request : [{}] ", shopDTO);
		try {
			shopGateway.register(shopDTO);

			logger.debug("success shop register ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("End update register ... ");
		return ResponeModel.ok();
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public ResponeModel update(@Valid @RequestBody ShopDTO shopDTO) {
		logger.debug("Start shop update request : [{}] ", shopDTO);
		try {
			shopGateway.update(shopDTO);

			logger.debug("success shop update ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("End shop update ... ");
		return ResponeModel.ok();
	}

	@RequestMapping(value = "getShopNear", method = RequestMethod.GET)
	public ResponeModel getAllShop(@RequestParam(name = "latitude", required = true) String latitude,
			@RequestParam(name = "longitude", required = true) String longitude,
			@PageableDefault(size = 10) Pageable pageable) {
		logger.debug("Start get all near shop by paging.....");
		PageDTO<NearestShopRespDTO> shopPage = null;
		try {
			Object[] params = { latitude, longitude, pageable };
			shopPage = shopGateway.getShopNear(params);

			logger.debug("success get near shop  ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		ResponeModel responeModel = new ResponeModel();
		responeModel.setCount(shopPage.getTotalElements());
		responeModel.setData(shopPage.getContent());

		logger.debug("End get all book.....");
		return responeModel;
	}

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponeModel getAllShop(@PageableDefault(size = 10) Pageable pageable) {
		logger.debug("Start get all shop by paging.....");
		PageDTO<ShopVo> shopPage = null;
		try {
			shopPage = shopGateway.getAll(pageable);

			logger.debug("success get all shop  ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		ResponeModel responeModel = new ResponeModel();
		responeModel.setCount(shopPage.getTotalElements());
		responeModel.setData(shopPage.getContent());

		logger.debug("End get all book.....");
		return responeModel;
	}

	@RequestMapping(value = "get-by-id/{id}", method = RequestMethod.GET)
	public ResponeModel findById(@PathVariable(required = true, name = "id") String id) {
		logger.debug("Start get Shop By Id.....");

		ShopVo shopvo = null;
		try {
			shopvo = shopGateway.findById(id);

			logger.debug("success get all shop  ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}
		logger.debug("end get shop By Id......");
		return ResponeModel.ok(shopvo);
	}
}
