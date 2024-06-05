package com.example.cookingbook.neuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для разделения текста на предложения и шаги.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class TextDivider {

    /**
     * Разделяет текст на шаги с указанным количеством предложений в каждом шаге.
     *
     * @param text Текст для разделения.
     * @return Список шагов.
     */
    public static List<String> splitTextIntoSteps(String text) {
        List<String> steps = new ArrayList<>();
        List<String> sentences = splitTextIntoSentences(text);

        for (int i = 0; i < sentences.size(); i += 5) {
            int end = Math.min(i + 5, sentences.size());
            List<String> stepSentences = sentences.subList(i, end);
            steps.add(String.join(" ", stepSentences));
        }

        return steps;
    }

    /**
     * Разделяет текст на предложения.
     *
     * @param text Текст для разделения.
     * @return Список предложений.
     */
    public static List<String> splitTextIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();

        // Регулярное выражение для разделения текста на предложения
        Pattern pattern = Pattern.compile("[^.!?\\s][^.!?]*(?:\\.(?!\\s|$)|[.!?](?=\\s|$))");
        Matcher matcher = pattern.matcher(text);

        // Добавляем найденные предложения в список
        while (matcher.find()) {
            sentences.add(matcher.group());
        }

        return sentences;
    }
}