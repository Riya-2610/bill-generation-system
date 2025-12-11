package com.example.BillGeneration.Configuration;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String from;

    @Value("${admin.whatsapp}")
    private String admin;

    @Value("${twilio.prefix}")
    private String prefix;

    public static String FROM_NUMBER;

    public static String PREFIX;



    @PostConstruct
    public void initTwilio()
    {
        Twilio.init(accountSid,authToken);

        FROM_NUMBER=from;
        PREFIX=prefix;
        System.out.println("✔ Twilio Initialized");

    }
}

