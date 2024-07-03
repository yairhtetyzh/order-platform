package com.assignment.foodorder.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.PromotionDTO;
import com.assignment.foodorder.demo.enums.CommonStatus;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.model.Promotion;
import com.assignment.foodorder.demo.model.Shop;
import com.assignment.foodorder.demo.repository.PromotionRepository;
import com.assignment.foodorder.demo.repository.ShopRepository;
import com.assignment.foodorder.demo.service.PromotionService;
import com.assignment.foodorder.demo.utils.CommonConstant;
import com.assignment.foodorder.demo.utils.CommonUtil;
import com.assignment.foodorder.demo.utils.ErrorCode;
import com.assignment.foodorder.demo.vo.PromotionVo;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	ShopRepository shopRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionDTO register(@Valid PromotionDTO promotionDTO) throws RdpException {
		checkValidateShops(promotionDTO.getShopIds());
		Promotion promotion = new Promotion();
		promotion.setName(promotionDTO.getName());
		promotion.setFromDate(CommonUtil.stringToDate(CommonConstant.STD_DATE_FORMAT, promotionDTO.getFromDate()));
		promotion.setToDate(CommonUtil.stringToDate(CommonConstant.STD_DATE_FORMAT, promotionDTO.getToDate()));
		promotion.setDiscountAmount(promotionDTO.getDiscountAmount());
		promotion.setStatus(CommonStatus.ACTIVE.getCode());
		promotion.setShopIds(promotionDTO.getShopIds());
		promotion = promotionRepository.save(promotion);
		return promotionDTO;
	}

	private void checkValidateShops(List<String> shopIds) {
		if (!CollectionUtils.isEmpty(shopIds)) {
			List<Shop> shopList = shopRepository.findByIdIn(shopIds);
			if (shopList.size() != shopIds.size()) {
				StringBuilder sb = new StringBuilder("");
				for (String s : shopIds) {
					Optional<Shop> shOpt = shopList.stream().filter(obj -> obj.getId().equals(s)).findFirst();
					if (!shOpt.isPresent())
						sb.append(s + ",");
				}
				throw new RdpException(ErrorCode.ERRORCODE_020133.getCode(),
						String.format(ErrorCode.ERRORCODE_020133.getDesc(), sb.toString()));
			}
		} else {
			throw new RdpException(ErrorCode.ERRORCODE_020132.getCode(), ErrorCode.ERRORCODE_020132.getDesc());
		}
	}

	@Override
	public PageDTO<PromotionVo> getAll(Pageable pageable) {
		PageDTO<PromotionVo> pageDTO = new PageDTO<>();

		Page<Promotion> page = promotionRepository.findAll(pageable);
		List<Promotion> promotionList = page.getContent();
		List<PromotionVo> promotionVoList = new ArrayList<>();
		for (Promotion promotion : promotionList) {
			promotionVoList.add(convertToPromotionVo(promotion));
		}

		pageDTO.setContent(promotionVoList);
		pageDTO.setNumberofElements(page.getNumberOfElements());
		pageDTO.setPage(page.getNumber());
		pageDTO.setSize(page.getSize());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		return pageDTO;
	}

	private PromotionVo convertToPromotionVo(Promotion promotion) {
		PromotionVo promotionVo = new PromotionVo();
		promotionVo.setId(promotion.getId());
		promotionVo.setName(promotion.getName());
		promotionVo.setFromDate(CommonUtil.dateToString(CommonConstant.STD_DATE_FORMAT, promotion.getFromDate()));
		promotionVo.setToDate(CommonUtil.dateToString(CommonConstant.STD_DATE_FORMAT, promotion.getToDate()));
		promotionVo.setDiscountAmount(promotion.getDiscountAmount());
		promotionVo.setStatus(CommonStatus.getDescription(promotion.getStatus()));
		promotionVo.setShopIds(promotion.getShopIds());
		return promotionVo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionDTO update(@Valid PromotionDTO promotionDTO) {
		Promotion promotion = validatePromotion(promotionDTO.getId());
		promotion.setName(promotionDTO.getName());
		promotion.setFromDate(CommonUtil.stringToDate(CommonConstant.STD_DATE_FORMAT, promotionDTO.getFromDate()));
		promotion.setToDate(CommonUtil.stringToDate(CommonConstant.STD_DATE_FORMAT, promotionDTO.getToDate()));
		promotion.setDiscountAmount(promotionDTO.getDiscountAmount());
		promotion.setStatus(promotionDTO.getStatus());
		promotion.setShopIds(promotionDTO.getShopIds());
		promotion = promotionRepository.save(promotion);
		return promotionDTO;
	}

	private Promotion validatePromotion(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new RdpException(ErrorCode.ERRORCODE_000211.getCode(), ErrorCode.ERRORCODE_000211.getDesc());
		}
		Optional<Promotion> promotionOpt = promotionRepository.findById(id);
		if (!promotionOpt.isPresent()) {
			throw new RdpException(ErrorCode.ERRORCODE_020127.getCode(),
					String.format(ErrorCode.ERRORCODE_020127.getDesc(), "promotion"));
		}
		return promotionOpt.get();
	}

	@Override
	public PromotionVo findById(String id) {
		Promotion promotion = validatePromotion(id);
		PromotionVo promotionVo = convertToPromotionVo(promotion);
		return promotionVo;
	}

}
