package com.assignment.foodorder.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.foodorder.demo.model.Shop;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {
	
	@Query("{'status': ?0, 'location': {$near: {$geometry: {type: 'Point', coordinates: [?1, ?2]}, $maxDistance: ?3}}}")
    Page<Shop> findShopsNearLocation(int status, double longitude, double latitude, double distance, Pageable pageable);

	List<Shop> findByIdIn(List<String> shopIds);

	@Query("{'name': ?0}")
	Shop findByName(String name);

}
