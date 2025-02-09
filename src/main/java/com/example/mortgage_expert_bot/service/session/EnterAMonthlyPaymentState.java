package com.example.mortgage_expert_bot.service.session;

import java.math.BigDecimal;

public class EnterAMonthlyPaymentState implements MortgageCalculatorState {

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        try {
            BigDecimal monthlyPayment = new BigDecimal(input);
            session.setMonthlyPayment(monthlyPayment);
            session.setState(new ChoiceOfEarlyRepaymentState());
            return session.getState();
        } catch (NumberFormatException e) {
            return this;
        }
    }

    @Override
    public String getPrompt() {
        return "Введите ежемесячный платеж. Например: 12345.123";
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
