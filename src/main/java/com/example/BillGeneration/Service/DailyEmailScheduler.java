package com.example.BillGeneration.Service;

import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.ProductRepo;
import com.opencsv.CSVWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;
@Service
public class DailyEmailScheduler {

    @Autowired
    ProductRepo productRepo;


    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 00 20 * * *")
    public void sendCsvToAdminScheduled() {
        try {
            List<Product> products = productRepo.findAll();

            // Create CSV in memory
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (
                    CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos, "UTF-8"))) {

                writer.writeNext(new String[]{"ID", "NAME", "PRICE", "STOCK", "THRESHOLD"});

                for (Product p : products) {
                    writer.writeNext(new String[]{
                            String.valueOf(p.getId()),
                            p.getPname(),
                            String.valueOf(p.getPrice()),
                            String.valueOf(p.getStock()),
                            String.valueOf(p.getThreshold())
                    });
                }
            }

            sendEmailWithAttachment("ptlriya2612@gmail.com", baos.toByteArray());

            System.out.println("Scheduled CSV Email Sent!");

        } catch (Exception e) {
            System.out.println("ERROR in scheduled CSV sending: " + e.getMessage());
        }
    }
    private void sendEmailWithAttachment(String toEmail, byte[] csvData) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Stock Report");
        helper.setText("Hi Admin,\n\nPlease find the latest stock report attached.\n\nRegards,");

        InputStreamSource attachment = new ByteArrayResource(csvData);
        helper.addAttachment("stock_report.csv", attachment);

        mailSender.send(message);
    }


}
