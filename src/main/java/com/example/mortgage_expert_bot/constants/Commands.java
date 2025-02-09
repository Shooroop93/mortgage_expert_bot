package com.example.mortgage_expert_bot.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@RequiredArgsConstructor
public enum Commands {

    HELP("/help", "Команда показывающая все команды доступные для пользователя", ""),
    START("/start", "Команда для начала взаимодействия с ботом", ""),
    CALC("/calc", "Команда ипотечного калькулятора", "");

    private final String command;
    private final String description;
    private final String example;

    public static Optional<Commands> getEnum(String text) {
        return Arrays.stream(Commands.values())
                .filter(commands -> commands.getCommand().equals(text))
                .findFirst();
    }

    public static String getListCommands() {
        AtomicInteger count = new AtomicInteger();
        StringBuilder result = new StringBuilder();

        result.append("*Доступные команды для бота:*\n\n");
        Arrays.stream(Commands.values())
                .forEach(command ->
                        result.append(count.incrementAndGet())
                                .append(") `")
                                .append(command.getCommand())
                                .append("` – ")
                                .append(command.getDescription())
                                .append("\n")
                );
        return result.toString();
    }
}