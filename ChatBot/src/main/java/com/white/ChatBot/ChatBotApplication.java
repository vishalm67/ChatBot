package com.white.ChatBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ChatBotApplication
{

	public static void main(String[] args)
	{

		SpringApplication.run(ChatBotApplication.class, args);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Chatbot Backend Started Successfully!");
		System.out.println("Server running at: http://localhost:8080");
		System.out.println("API Docs: http://localhost:8080/api/chat/health");

	}

}
