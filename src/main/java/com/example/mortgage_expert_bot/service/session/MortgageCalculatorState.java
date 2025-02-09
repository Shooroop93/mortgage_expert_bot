package com.example.mortgage_expert_bot.service.session;


public interface MortgageCalculatorState {

    MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session);

    String getPrompt();

    boolean isTerminal();
}
