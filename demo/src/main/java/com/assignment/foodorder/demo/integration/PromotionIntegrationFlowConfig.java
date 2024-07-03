package com.assignment.foodorder.demo.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import com.assignment.foodorder.demo.service.PromotionService;

@Configuration
public class PromotionIntegrationFlowConfig {

	@Bean
    public IntegrationFlow registerPromotionFlow(PromotionService promotionService) {
        return IntegrationFlows.from("registerPromotionChannel")
                .handle(promotionService, "register")
                .get();
    }

    @Bean
    public IntegrationFlow updatePromotionFlow(PromotionService promotionService) {
        return IntegrationFlows.from("updatePromotionChannel")
                .handle(promotionService, "update")
                .get();
    }

    @Bean
    public IntegrationFlow getAllPromotionFlow(PromotionService promotionService) {
        return IntegrationFlows.from("getAllPromotionChannel")
                .handle(promotionService, "getAll")
                .get();
    }

    @Bean
    public IntegrationFlow findByIdPromotionFlow(PromotionService promotionService) {
        return IntegrationFlows.from("findByIdPromotionChannel")
                .handle(promotionService, "findById")
                .get();
    }
}
