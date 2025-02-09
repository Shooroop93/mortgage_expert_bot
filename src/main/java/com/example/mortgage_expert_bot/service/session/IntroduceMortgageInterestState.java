package com.example.mortgage_expert_bot.service.session;

import java.math.BigDecimal;

public class IntroduceMortgageInterestState implements MortgageCalculatorState {

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        try {
            BigDecimal rate = new BigDecimal(input);
            session.setRate(rate);
            session.setState(new EnterAMonthlyPaymentState());
            return session.getState();
        } catch (NumberFormatException e) {
            return this;
        }
    }

    @Override
    public String getPrompt() {
        return "Введите процент по ипотеки. Например: 1.23";
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
