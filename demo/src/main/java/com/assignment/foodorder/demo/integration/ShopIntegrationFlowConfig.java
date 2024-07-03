package com.assignment.foodorder.demo.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;

import com.assignment.foodorder.demo.service.ShopService;

@Configuration
public class ShopIntegrationFlowConfig {

    @Bean
    public IntegrationFlow registerFlow(ShopService shopService) {
        return IntegrationFlows.from("registerChannel")
                .handle(shopService, "register")
                .get();
    }

    @Bean
    public IntegrationFlow updateFlow(ShopService shopService) {
        return IntegrationFlows.from("updateChannel")
                .handle(shopService, "update")
                .get();
    }

    @Bean
    public IntegrationFlow getShopNearFlow(ShopService shopService) {
    	return IntegrationFlows.from("getShopNearChannel")
                .handle(new GenericHandler<Object[]>() {
                    @Override
                    public Object handle(Object[] payload, MessageHeaders headers) {
                        String latitude = (String) payload[0];
                        String longitude = (String) payload[1];
                        Pageable pageable = (Pageable) payload[2];
                        return shopService.getAllShopNearLocation(latitude, longitude, pageable);
                    }
                })
                .get();
    }

    @Bean
    public IntegrationFlow getAllFlow(ShopService shopService) {
        return IntegrationFlows.from("getAllChannel")
                .handle(shopService, "getAll")
                .get();
    }

    @Bean
    public IntegrationFlow findByIdFlow(ShopService shopService) {
        return IntegrationFlows.from("findByIdChannel")
                .handle(shopService, "getById")
                .get();
    }
}