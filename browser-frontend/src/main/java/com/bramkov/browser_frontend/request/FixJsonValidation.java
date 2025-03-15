package com.bramkov.browser_frontend.request;

import org.json.simple.parser.JSONParser;

public class FixJsonValidation {

    // Метод для экранирования строки
    public static String escapeJsonString(String input) {
        return input
                .replace("\\", "\\\\")  // Экранируем обратный слэш
                .replace("\"", "\\\"") // Экранируем кавычки
                .replace("\n", "\\n")  // Экранируем переносы строк
                .replace("\r", "\\r")  // Экранируем возврат каретки
                .replace("\t", "\\t");  // Экранируем табуляцию
    }

    // Метод для проверки валидности JSON
    public static boolean isValidJson(String json) {
        try {
            new JSONParser().parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}