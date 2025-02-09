package com.example.mortgage_expert_bot.dto;

import lombok.Data;

@Data
public class CalculationRequest {

    private double principal;
    private double annualRate;
    private double scheduledPayment;
    private double[] extraPayments;
}