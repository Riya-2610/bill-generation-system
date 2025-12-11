package com.example.BillGeneration.Service;

import com.example.BillGeneration.Configuration.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

@Service
public class WhatsAppService {

    @Autowired
    TwilioConfig twilioConfig;


    @Value("${admin.whatsapp}")   // your admin mobile
    private String adminNumber;
    public String sendMessage(String to, String text) {
        try {
            String finalTo= TwilioConfig.PREFIX+to;
            Message message = Message.creator(
                    new PhoneNumber( finalTo),
                    new PhoneNumber(TwilioConfig.FROM_NUMBER),
                    text
            ).create();

            return "Message Sent: " + message.getSid();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    // ⭐ UPDATED METHOD — Accepts ByteArrayInputStream PDF
    public void sendWhatsAppAlert(String text) {

        // No config file — direct initialization
        twilioConfig.initTwilio();

        Message.creator(
                new PhoneNumber(adminNumber),
                new PhoneNumber(TwilioConfig.FROM_NUMBER),
                text
        ).create();

        System.out.println("WhatsApp Alert Sent!");
    }


    public String sendPdf(String to, String pdfUrl) {
        try {
            String finalTo= TwilioConfig.PREFIX+to;

            Message message = Message.creator(
                    new PhoneNumber(finalTo),
                    new PhoneNumber(TwilioConfig.FROM_NUMBER),
                    "Here is your Invoice PDF 📄"
            ).setMediaUrl(
                    java.util.List.of(URI.create(pdfUrl))
            ).create();

            return "PDF Sent: " + message.getSid();
        } catch (Exception e) {
            return "Error sending PDF: " + e.getMessage();
        }
    }

}
