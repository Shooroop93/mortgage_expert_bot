package com.example.mortgage_expert_bot.service.session;

import java.math.BigDecimal;

public class EnterEarlyRepaymentAmountState implements MortgageCalculatorState {

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        try {
            BigDecimal additionalPayment = new BigDecimal(input);
            session.setAdditionalPayment(additionalPayment);
            session.setState(new CalculationOfMortgageRepaymentPeriodState(session));
            return session.getState();

        } catch (NumberFormatException e) {
            return this;
        }
    }

    @Override
    public String getPrompt() {
        return "Введите сумму дополнительного платежа, который вы планируете вносить ежемесячно для досрочного погашения кредита. Например: 123.123";
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
