package com.example.mortgage_expert_bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {

    @Value("${telegrambots.bots.mortgage_expert_bot.token}")
    private String botToken;

    @Value("${telegrambots.bots.mortgage_expert_bot.userName}")
    private String botUsername;
}