package com.assignment.foodorder.demo.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.assignment.foodorder.demo.dto.NearestShopRespDTO;
import com.assignment.foodorder.demo.dto.PageDTO;
import com.assignment.foodorder.demo.dto.ShopDTO;
import com.assignment.foodorder.demo.enums.CommonStatus;
import com.assignment.foodorder.demo.exception.RdpException;
import com.assignment.foodorder.demo.model.Promotion;
import com.assignment.foodorder.demo.model.Shop;
import com.assignment.foodorder.demo.repository.PromotionRepository;
import com.assignment.foodorder.demo.repository.ShopRepository;
import com.assignment.foodorder.demo.service.ShopService;
import com.assignment.foodorder.demo.utils.CommonConstant;
import com.assignment.foodorder.demo.utils.CommonUtil;
import com.assignment.foodorder.demo.utils.ErrorCode;
import com.assignment.foodorder.demo.vo.PromotionVo;
import com.assignment.foodorder.demo.vo.ShopVo;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ShopDTO register(@Valid ShopDTO shopDTO) {
		checkAlreadyExist(shopDTO);
		Shop shop = new Shop();
		shop.setName(shopDTO.getName());
		shop.setAvatarImage(shopDTO.getAvatarImage());
		shop.setRating(shopDTO.getRating());
		shop.setStatus(CommonStatus.ACTIVE.getCode());
		GeoJsonPoint location = new GeoJsonPoint(shopDTO.getLongitude(), shopDTO.getLatitude());
		shop.setLocation(location);
		shop.setShopTypes(shopDTO.getShopTypes());
		shop = shopRepository.save(shop);
		shopDTO.setId(shop.getId());
		return shopDTO;
	}

	private void checkAlreadyExist(@Valid ShopDTO shopDTO) {
		Optional<Shop> shopOpt = Optional.ofNullable(shopRepository.findByName(shopDTO.getName()));
		if (shopOpt.isPresent()) {
			if (!shopOpt.get().getId().equals(shopDTO.getId())) {
				throw new RdpException(ErrorCode.ERRORCODE_020131.getCode(),
						String.format(ErrorCode.ERRORCODE_020131.getDesc(), shopDTO.getName()));
			}
		}
	}

	@Override
	public PageDTO<NearestShopRespDTO> getAllShopNearLocation(String latitude, String longitude, Pageable pageable) {
		PageDTO<NearestShopRespDTO> pageDTO = new PageDTO<>();

		GeoJsonPoint currentlocation = new GeoJsonPoint(Double.parseDouble(longitude), Double.parseDouble(latitude));
		
        double longi = Double.parseDouble(longitude);
        double lati = Double.parseDouble(latitude);
        Point point = new Point(longi, lati);
		Query query = new Query();
        query.addCriteria(Criteria.where("status").is(CommonStatus.ACTIVE.getCode()));
        query.addCriteria(Criteria.where("location").nearSphere(point).maxDistance(CommonConstant.DISTANCE_IN_METER));

		long total = mongoTemplate.count(query, Shop.class);
		int skip = pageable.getPageNumber() * pageable.getPageSize();
        query.skip(skip);
        query.limit(pageable.getPageSize());

		List<Shop> shopList = mongoTemplate.find(query, Shop.class);
		Page<Shop> page = new PageImpl<>(shopList, pageable, total);

		List<NearestShopRespDTO> shopVoList = new ArrayList<>();
		for (Shop shop : shopList) {
			NearestShopRespDTO resp = convertToResp(shop, currentlocation);
			setEligiblePromotion(resp);
			shopVoList.add(resp);
		}

		pageDTO.setContent(shopVoList);
		pageDTO.setNumberofElements(page.getNumberOfElements());
		pageDTO.setPage(page.getNumber());
		pageDTO.setSize(page.getSize());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		return pageDTO;
	}

	private void setEligiblePromotion(NearestShopRespDTO resp) {
		List<Promotion> promotionList = promotionRepository.findValidPromotions(resp.getId(), new Date());
		List<PromotionVo> promotionVoList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(promotionList)) {
			for (Promotion p : promotionList) {
				PromotionVo vo = new PromotionVo();
				vo.setName(p.getName());
				vo.setDiscountAmount(p.getDiscountAmount());
				promotionVoList.add(vo);
			}
			resp.setPromotionList(promotionVoList);
		}
	}

	private NearestShopRespDTO convertToResp(Shop shop, GeoJsonPoint currentLocation) {
		NearestShopRespDTO shopVo = new NearestShopRespDTO();
		shopVo.setId(shop.getId());
		shopVo.setName(shop.getName());
		shopVo.setAvatarImage(shop.getAvatarImage());
		shopVo.setRating(shop.getRating());
		shopVo.setLatitude(shop.getLocation().getCoordinates().get(0));
		shopVo.setLongitude(shop.getLocation().getCoordinates().get(1));
		shopVo.setShopTypes(shop.getShopTypes());
		setDistanceAndArriveTime(shopVo, shop, currentLocation);

		return shopVo;
	}

	private void setDistanceAndArriveTime(NearestShopRespDTO shopVo, Shop shop, GeoJsonPoint currentLocation) {
		double distance = CommonUtil.calculateDistance(currentLocation.getCoordinates().get(0),
				currentLocation.getCoordinates().get(1), shop.getLocation().getCoordinates().get(0),
				shop.getLocation().getCoordinates().get(1));
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format(distance);
		shopVo.setDistance(formatted + " km");

		setTimeToArrive(distance, shopVo);
		setDeliveryFee(distance, shopVo);
	}

	private void setDeliveryFee(double distance, NearestShopRespDTO shopVo) {
		// calculate fee 1 RM Per 1km
		int result = (int)distance / CommonConstant.DELI_FEE_PER_KILOMETER;
		if(result < CommonConstant.MIN_DELI_FEE)shopVo.setDeliveryFee(CommonConstant.MIN_DELI_FEE + " RM");
		else shopVo.setDeliveryFee(result + " RM");
	}

	private void setTimeToArrive(double distance, NearestShopRespDTO shopVo) {
		int timeToArrive = (int) CommonUtil.calculateTimeToArrive(distance, CommonConstant.AVERAGE_SPEED);
		shopVo.setTimeToArrive(timeToArrive + " min");
	}

	@Override
	public PageDTO<ShopVo> getAll(Pageable pageable) {
		PageDTO<ShopVo> pageDTO = new PageDTO<>();
		Page<Shop> page = shopRepository.findAll(pageable);
		List<Shop> shopList = page.getContent();
		List<ShopVo> shopVoList = new ArrayList<>();
		for (Shop shop : shopList) {
			shopVoList.add(new ShopVo(shop));
		}

		pageDTO.setContent(shopVoList);
		pageDTO.setNumberofElements(page.getNumberOfElements());
		pageDTO.setPage(page.getNumber());
		pageDTO.setSize(page.getSize());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		return pageDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ShopDTO update(@Valid ShopDTO shopDTO) {
		Shop shop = validate(shopDTO.getId());
		checkAlreadyExist(shopDTO);
		shop.setName(shopDTO.getName());
		shop.setAvatarImage(shopDTO.getAvatarImage());
		shop.setRating(shopDTO.getRating());
		shop.setStatus(shopDTO.getStatus());
		GeoJsonPoint location = new GeoJsonPoint(shopDTO.getLongitude(), shopDTO.getLatitude());
		shop.setShopTypes(shopDTO.getShopTypes());
		shop.setLocation(location);
		shop = shopRepository.save(shop);
		return shopDTO;
	}

	private Shop validate(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new RdpException(ErrorCode.ERRORCODE_000211.getCode(), ErrorCode.ERRORCODE_000211.getDesc());
		}
		Optional<Shop> shopOpt = shopRepository.findById(id);
		if (!shopOpt.isPresent()) {
			throw new RdpException(ErrorCode.ERRORCODE_020127.getCode(),
					String.format(ErrorCode.ERRORCODE_020127.getDesc(), "shop"));
		}
		return shopOpt.get();
	}

	@Override
	public ShopVo getById(String id) throws RdpException {
		Shop shop = validate(id);
		ShopVo shopvo = new ShopVo(shop);
		return shopvo;
	}

}
