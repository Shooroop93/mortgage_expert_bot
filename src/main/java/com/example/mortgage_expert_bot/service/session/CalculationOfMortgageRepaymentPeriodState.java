package com.example.mortgage_expert_bot.service.session;

import com.example.mortgage_expert_bot.service.utils.MortgageCalculatorUtil;

import java.math.BigDecimal;

import static java.lang.String.format;

public class CalculationOfMortgageRepaymentPeriodState implements MortgageCalculatorState {

    private final StringBuilder result = new StringBuilder();

    public CalculationOfMortgageRepaymentPeriodState(MortgageCalculatorBotSession session) {
        BigDecimal debt = session.getDebt();
        BigDecimal rate = session.getRate();
        BigDecimal monthlyPayment = session.getMonthlyPayment();
        BigDecimal additionalPayment = session.getAdditionalPayment();

        BigDecimal monthRate = MortgageCalculatorUtil.calculateTheMonthlyInterestRate(rate);

        int baseMonths = MortgageCalculatorUtil.calculateNumberOfMonths(debt, monthlyPayment, monthRate);
        int monthsWithAdditional = MortgageCalculatorUtil.calculateNumberOfMonthsWithAdditionalPayment(
                debt, monthlyPayment, monthRate, additionalPayment);

        result.append(format("Без дополнительных платежей: ~%d мес. (%.1f лет)%n",
                baseMonths, baseMonths / 12.0));
        result.append(format("С дополнительными платежами: ~%d мес. (%.1f лет)%n",
                monthsWithAdditional, monthsWithAdditional / 12.0));
    }

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        return null;
    }

    @Override
    public String getPrompt() {
        return result.toString();
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}