package com.assignment.foodorder.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel registerChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel updateChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getShopNearChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getAllChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel findByIdChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public MessageChannel registerPromotionChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel updatePromotionChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getAllPromotionChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel findByIdPromotionChannel() {
        return new DirectChannel();
    }
}