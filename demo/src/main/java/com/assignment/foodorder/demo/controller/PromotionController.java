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
import org.springframework.web.bind.annotation.RestController;

import com.assignment.foodorder.demo.config.ResponeModel;
import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.PromotionDTO;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.gateway.PromotionGateway;
import com.assignment.foodorder.demo.utils.ErrorCode;
import com.assignment.foodorder.demo.vo.PromotionVo;

@RestController
@RequestMapping(value = "promotion")
public class PromotionController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PromotionGateway promotionGateway;

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponeModel register(@Valid @RequestBody PromotionDTO promotionDTO) {
		logger.debug("Start promotion register request : [{}] ", promotionDTO);
		try {
			promotionGateway.register(promotionDTO);

			logger.debug("success promotion register ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("End promotion register ... ");
		return ResponeModel.ok();
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public ResponeModel update(@Valid @RequestBody PromotionDTO promotionDTO) {
		logger.debug("Start promotion update request : [{}] ", promotionDTO);
		try {
			promotionGateway.update(promotionDTO);

			logger.debug("success promotion update ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("End promotion update ... ");
		return ResponeModel.ok();
	}

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponeModel getAll(@PageableDefault(size = 10) Pageable pageable) {
		logger.debug("Start get all near promotion by paging.....");
		PageDTO<PromotionVo> promotionPage = null;
		try {
			promotionPage = promotionGateway.getAll(pageable);

			logger.debug("success get all promotion  ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		ResponeModel responeModel = new ResponeModel();
		responeModel.setCount(promotionPage.getTotalElements());
		responeModel.setData(promotionPage.getContent());

		logger.debug("End get all promotion.....");
		return responeModel;
	}

	@RequestMapping(value = "get-by-id/{id}", method = RequestMethod.GET)
	public ResponeModel findById(@PathVariable(required = true, name = "id") String id) {
		logger.debug("Start get Promotion By Id.....");

		PromotionVo promotionVo = null;
		try {
			promotionVo = promotionGateway.findById(id);

			logger.debug("success get all promotion  ... ");
		} catch (RdpException e) {
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}
		logger.debug("end get promotion By Id......");
		return ResponeModel.ok(promotionVo);
	}
}
