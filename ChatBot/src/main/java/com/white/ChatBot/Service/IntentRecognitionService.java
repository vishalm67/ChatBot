package com.white.ChatBot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
@Service
public class IntentRecognitionService {

    private final Map<String, Pattern> intentPatterns;
    private final Map<String, Double> intentConfidence;

    public IntentRecognitionService() {
        intentPatterns = new HashMap<>();
        intentConfidence = new HashMap<>();
        initializeIntents();
    }

    private void initializeIntents() {
        // Greeting patterns
        intentPatterns.put("GREETING", Pattern.compile(
                "(?i).*(hello|hi|hey|greetings|good morning|good afternoon|good evening|howdy|sup).*"));

        // Farewell patterns
        intentPatterns.put("FAREWELL", Pattern.compile(
                "(?i).*(bye|goodbye|see you|take care|farewell|catch you later|good night).*"));

        // Help patterns
        intentPatterns.put("HELP", Pattern.compile(
                "(?i).*(help|assist|support|guide|how to|can you help).*"));

        // Name query patterns
        intentPatterns.put("NAME_QUERY", Pattern.compile(
                "(?i).*(what is your name|who are you|your name|what are you called).*"));

        // Time query patterns
        intentPatterns.put("TIME_QUERY", Pattern.compile(
                "(?i).*(what time|current time|time now|tell me the time).*"));

        // Date query patterns
        intentPatterns.put("DATE_QUERY", Pattern.compile(
                "(?i).*(what date|today's date|current date|what day|todays date).*"));

        // Weather patterns
        intentPatterns.put("WEATHER_QUERY", Pattern.compile(
                "(?i).*(weather|temperature|forecast|how hot|how cold|raining).*"));

        // Joke patterns
        intentPatterns.put("JOKE", Pattern.compile(
                "(?i).*(joke|funny|make me laugh|tell me something funny|humor).*"));

        // Thanks patterns
        intentPatterns.put("THANKS", Pattern.compile(
                "(?i).*(thank|thanks|appreciate|grateful|thx).*"));

        // Capability query
        intentPatterns.put("CAPABILITY_QUERY", Pattern.compile(
                "(?i).*(what can you do|your capabilities|what do you know|can you|are you able).*"));
    }

    public IntentResult recognizeIntent(String message) {
        for (Map.Entry<String, Pattern> entry : intentPatterns.entrySet()) {
            if (entry.getValue().matcher(message).matches()) {
                return new IntentResult(entry.getKey(), 0.95);
            }
        }
        return new IntentResult("UNKNOWN", 0.3);
    }

    public static class IntentResult {
        private String intent;
        private Double confidence;

        public IntentResult(String intent, Double confidence) {
            this.intent = intent;
            this.confidence = confidence;
        }

        public String getIntent() { return intent; }
        public Double getConfidence() { return confidence; }
    }
}
