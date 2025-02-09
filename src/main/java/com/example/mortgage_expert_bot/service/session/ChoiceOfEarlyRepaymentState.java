package com.example.mortgage_expert_bot.service.session;

public class ChoiceOfEarlyRepaymentState implements MortgageCalculatorState {

    @Override
    public MortgageCalculatorState processInput(String input, MortgageCalculatorBotSession session) {
        try {
            int number = Integer.parseInt(input);

            switch (number) {
                case 1 -> {
                    session.setState(new EnterEarlyRepaymentAmountState());
                    session.setPrepaymentType(PrepaymentType.SHORTENING_OF_THE_LOAN_TERM);
                }
                case 2 -> {
                    session.setState(new EnterEarlyRepaymentAmountState());
                    session.setPrepaymentType(PrepaymentType.REDUCTION_OF_INTEREST);
                }
                default -> {
                    return this;
                }
            }
            return session.getState();
        } catch (NumberFormatException e) {
            return this;
        }
    }

    @Override
    public String getPrompt() {
        return
                """
                        Выберите тип досрочного погашения:
                        1 – Сокращение срока кредита.
                        2 – Сокращение процентов.
                                            
                        Нужно указать номер досрочно погашения, например 1
                        """;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
