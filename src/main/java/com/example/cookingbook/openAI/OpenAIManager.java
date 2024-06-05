package com.example.cookingbook.openAI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIManager {

    private static final String API_KEY = "sk-proj-CmnpZCxfzvdGNHN19YART3BlbkFJXNb8PVSIP58ZvwHAqWIM";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/davinci/completions";

    public static String getCompletion(String text) {
        try {
            URL url = new URL(OPENAI_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            con.setDoOutput(true);

            String requestBody = "{\"prompt\": \"" + text + "\", \"max_tokens\": 200}";
            con.getOutputStream().write(requestBody.getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при отправке запроса: " + e.getMessage());
            return null;
        }
    }
}
