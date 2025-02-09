package com.example.mortgage_expert_bot.service.session;

public class EnterAdditionalMonthlyPaymentState implements MortgageCalculatorState{
    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        return null;
    }

    @Override
    public String getPrompt() {
        return "";
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
