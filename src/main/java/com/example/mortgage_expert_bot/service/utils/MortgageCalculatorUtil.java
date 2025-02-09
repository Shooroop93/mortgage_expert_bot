package com.example.mortgage_expert_bot.service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MortgageCalculatorUtil {

    public static BigDecimal calculateTheMonthlyInterestRate(BigDecimal rate) {
        return rate.divide(new BigDecimal("1200"), 10, RoundingMode.HALF_UP);
    }

    /**
     * Вычисляет количество месяцев, необходимое для погашения кредита (без дополнительных платежей).
     * <p>
     * Формула:
     * n = ln(M / (M - P * r)) / ln(1 + r)
     *
     * @param loanAmount          сумма кредита (P)
     * @param monthlyPayment      ежемесячный платеж (M)
     * @param monthlyInterestRate месячная процентная ставка (r) в виде десятичной дроби
     * @return количество месяцев, округленное вверх до целого
     * @throws IllegalArgumentException если ежемесячный платеж недостаточен для погашения процентов
     */
    public static int calculateNumberOfMonths(BigDecimal loanAmount,
                                              BigDecimal monthlyPayment,
                                              BigDecimal monthlyInterestRate) {
        return calculateNumberOfMonthsWithAdditionalPayment(loanAmount, monthlyPayment, monthlyInterestRate, BigDecimal.ZERO);
    }

    /**
     * Вычисляет количество месяцев, необходимое для погашения кредита,
     * если к регулярному платежу (M) каждый месяц добавляется
     * дополнительная сумма (additionalPayment).
     * <p>
     * Формула:
     * n = ln(Meff / (Meff - P * r)) / ln(1 + r),
     * где Meff = M + additionalPayment
     *
     * @param loanAmount          сумма кредита (P)
     * @param monthlyPayment      аннуитетный ежемесячный платеж (M)
     * @param monthlyInterestRate месячная процентная ставка (r) (в десятичной форме, например 0.01)
     * @param additionalPayment   дополнительная сумма, вносимая каждый месяц
     * @return количество месяцев, округленное вверх
     * @throws IllegalArgumentException если эффективный платеж Meff
     *                                  недостаточен для погашения процентов
     */
    public static int calculateNumberOfMonthsWithAdditionalPayment(
            BigDecimal loanAmount,
            BigDecimal monthlyPayment,
            BigDecimal monthlyInterestRate,
            BigDecimal additionalPayment) {

        BigDecimal effectivePayment = monthlyPayment.add(additionalPayment);

        if (effectivePayment.compareTo(loanAmount.multiply(monthlyInterestRate)) <= 0) {
            throw new IllegalArgumentException("Даже с дополнительным платежом покрыть проценты не удается.");
        }

        double M = effectivePayment.doubleValue();
        double P = loanAmount.doubleValue();
        double r = monthlyInterestRate.doubleValue();

        double numerator = Math.log(M / (M - P * r));

        double denominator = Math.log(1 + r);

        return (int) Math.ceil(numerator / denominator);
    }
}