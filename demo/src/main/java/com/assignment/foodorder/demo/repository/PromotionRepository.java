package com.assignment.foodorder.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.foodorder.demo.model.Promotion;

@Repository
public interface PromotionRepository extends MongoRepository<Promotion, String> {

	@Query("{ 'shopIds': ?0 }")
    List<Promotion> findByShopId(String shopId);
	
	@Query("{ 'shopIds': ?0, 'fromDate': { $lte: ?1 }, 'toDate': { $gte: ?1 } }")
    List<Promotion> findValidPromotions(String shopId, Date date);
}
