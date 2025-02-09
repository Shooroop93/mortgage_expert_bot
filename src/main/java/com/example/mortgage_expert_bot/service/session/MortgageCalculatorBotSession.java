package com.example.mortgage_expert_bot.service.session;

import com.example.mortgage_expert_bot.constants.Commands;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MortgageCalculatorBotSession {

    private MortgageCalculatorState state;
    private Commands commands;

    private BigDecimal debt;
    private BigDecimal rate;
    private BigDecimal monthlyPayment;
    private BigDecimal additionalPayment;
    private PrepaymentType prepaymentType;

    public MortgageCalculatorBotSession() {
        this.state = new EnterTheBodyOfTheDebtState();
    }
}