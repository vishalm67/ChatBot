package com.white.ChatBot.Service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class ResponseGeneratorService {

    private final Random random = new Random();

    public String generateResponse(String intent, String userMessage) {
        switch (intent) {
            case "GREETING":
                return getGreetingResponse();
            case "FAREWELL":
                return getFarewellResponse();
            case "HELP":
                return getHelpResponse();
            case "NAME_QUERY":
                return "I'm ChatBot, your friendly AI assistant! I'm here to help you with various queries.";
            case "TIME_QUERY":
                return "The current time is: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
            case "DATE_QUERY":
                return "Today's date is: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
            case "WEATHER_QUERY":
                return "I don't have real-time weather access yet, but you can check weather.com or your local weather service for accurate forecasts!.In Future I will be prepared for It.";
            case "JOKE":
                return getJoke();
            case "THANKS":
                return getThankYouResponse();
            case "CAPABILITY_QUERY":
                return getCapabilitiesResponse();
            case "ADMIN_NAME":
                return getMyName();
            default:
                return getDefaultResponse(userMessage);
        }
    }

    private String getMyName() {
        String[] tells = {"Vishal is Created Me for his Learning purpose",
        "Vishal is Create Me, I am very glad to say i am one of the part of his college Projects!",
          "Vishal From Final year student at VSB ENGINEERING College"};

        return tells[random.nextInt(tells.length)];
    }

    private String getGreetingResponse() {
        String[] greetings = {
                "Hello! How can I assist you today?",
                "Hi there! What can I do for you?",
                "Hey! Nice to meet you. How may I help?",
                "Greetings! I'm here to help you.",
                "Hello! Ready to chat. What's on your mind?"
        };
        return greetings[random.nextInt(greetings.length)];
    }

    private String getFarewellResponse() {
        String[] farewells = {
                "Goodbye! Have a wonderful day!",
                "See you later! Take care!",
                "Bye! Feel free to come back anytime!",
                "Farewell! It was nice talking to you!",
                "Take care! Chat with you soon!"
        };
        return farewells[random.nextInt(farewells.length)];
    }

    private String getHelpResponse() {
        return "I can help you with:\n" +
                "• Answering general questions\n" +
                "• Telling you the current time and date\n" +
                "• Sharing jokes to brighten your day\n" +
                "• Having casual conversations\n" +
                "• Providing information on various topics\n\n" +
                "Just ask me anything!";
    }

    private String getJoke() {
        String[] jokes = {
                "Why do programmers prefer dark mode? Because light attracts bugs!",
                "Why did the developer go broke? Because he used up all his cache!",
                "How many programmers does it take to change a light bulb? None, that's a hardware problem!",
                "Why do Java developers wear glasses? Because they don't C#!",
                "What's a programmer's favorite hangout place? The Foo Bar!",
                "Why did the programmer quit his job? Because he didn't get arrays!"
        };
        return jokes[random.nextInt(jokes.length)];
    }

    private String getThankYouResponse() {
        String[] responses = {
                "You're welcome! Happy to help!",
                "My pleasure! Anything else I can do for you?",
                "No problem at all! Feel free to ask more questions.",
                "Glad I could help! What else would you like to know?"
        };
        return responses[random.nextInt(responses.length)];
    }

    private String getCapabilitiesResponse() {
        return "I'm a conversational AI chatbot with the following capabilities:\n" +
                "✓ Natural language understanding\n" +
                "✓ Context-aware responses\n" +
                "✓ Information retrieval\n" +
                "✓ Time and date queries\n" +
                "✓ Entertainment (jokes, casual chat)\n" +
                "✓ Conversation history tracking\n\n" +
                "I'm constantly learning and improving. What would you like to try?";
    }

    private String getDefaultResponse(String userMessage) {
        if (userMessage.contains("?")) {
            return "That's an interesting question! While I'm still learning about that topic, " +
                    "I'd be happy to help with something else. Try asking me about the time, date, or a joke!";
        }
        return "I understand you're saying: '" + userMessage + "'. " +
                "I'm still learning to handle that, but I'm here to help! " +
                "Try asking me for help to see what I can do.";
    }
}