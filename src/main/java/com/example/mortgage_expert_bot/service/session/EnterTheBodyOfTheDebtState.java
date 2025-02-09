package com.example.mortgage_expert_bot.service.session;

import java.math.BigDecimal;

public class EnterTheBodyOfTheDebtState implements MortgageCalculatorState{

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        try {
            BigDecimal debt = new BigDecimal(input.trim());
            session.setDebt(debt);
            session.setState(new IntroduceMortgageInterestState());
            return session.getState();
        } catch (NumberFormatException ex) {
            return this;
        }
    }

    @Override
    public String getPrompt() {
        return "Введите основную сумму кредита (тело долга). Например: 123456.13";
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
