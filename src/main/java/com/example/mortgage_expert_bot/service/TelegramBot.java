package com.example.mortgage_expert_bot.service;

import com.example.mortgage_expert_bot.config.BotConfig;
import com.example.mortgage_expert_bot.constants.Commands;
import com.example.mortgage_expert_bot.service.session.MortgageCalculatorBotSession;
import com.example.mortgage_expert_bot.service.session.MortgageCalculatorState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    private final ConcurrentHashMap<Long, MortgageCalculatorBotSession> session = new ConcurrentHashMap<>();

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            log.info("Получено сообщение от {}: {}", chatId, text);

            MortgageCalculatorBotSession mortgageCalculatorBotSession =
                    this.session.computeIfAbsent(chatId, id -> new MortgageCalculatorBotSession());

            Optional<Commands> anEnum = Commands.getEnum(text);
            Commands commands;

            if (anEnum.isEmpty()) {
                commands = mortgageCalculatorBotSession.getCommands() == null ?
                        Commands.getEnum(text).orElse(null) : mortgageCalculatorBotSession.getCommands();
            } else {
                commands = anEnum.get();
            }

            if (Objects.nonNull(commands)) {
                switch (commands) {
                    case HELP -> sendMessage(chatId, Commands.getListCommands());
                    case CALC -> {
                        if (text.equalsIgnoreCase("/calc")) {
                            MortgageCalculatorBotSession newSession = new MortgageCalculatorBotSession();
                            newSession.setCommands(Commands.CALC);
                            this.session.put(chatId, newSession);
                            sendMessage(chatId, newSession.getState().getPrompt());
                        } else {
                            mortgageCalculatorBotSession.setCommands(Commands.CALC);
                            MortgageCalculatorState currentState = mortgageCalculatorBotSession.getState();
                            MortgageCalculatorState nextState = currentState.processInput(text, mortgageCalculatorBotSession);
                            mortgageCalculatorBotSession.setState(nextState);

                            if (nextState.isTerminal()) {
                                sendMessage(chatId, "Расчет закончен. Начните заново.");
                                session.remove(chatId);
                            }

                            sendMessage(chatId, nextState.getPrompt());
                        }
                    }
                    default -> log.info("Unexpected value: " + commands);
                }
            }

        }
    }

    private void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    private void sendMessage(Long chatId, String text, String parseMode) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setParseMode(parseMode);
        try {
            execute(message);
        } catch (Exception e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
}