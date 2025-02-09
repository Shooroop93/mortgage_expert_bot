package com.example.mortgage_expert_bot.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalculationResponse {

    private String message;
    private List<String> details;
    private int additionalMonths;
}