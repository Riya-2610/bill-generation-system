package com.example.BillGeneration.Controller;

import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.ProductRepo;
import com.opencsv.CSVWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@RestController
public class CsvController {

    @Autowired
    ProductRepo productRepo;


    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/export-csv")
    public void export_csv(HttpServletResponse response) {
        // 1️⃣ Set response headers for download
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"stock_report.csv\"");

        // 2️⃣ Fetch data from DB
        List<Product> products = productRepo.findAll();

        try {
            // 3️⃣ Create CSV in memory
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos, "UTF-8"))) {
                // Header
                writer.writeNext(new String[]{"ID", "NAME", "PRICE", "STOCK", "THRESHOLD"});

                // Data
                for (Product p : products) {
                    writer.writeNext(new String[]{
                            String.valueOf(p.getId()),
                            p.getPname(),
                            String.valueOf(p.getPrice()),
                            String.valueOf(p.getStock()),
                            String.valueOf(p.getThreshold())
                    });
                }
                writer.flush();
            }

            // 4️⃣ Write CSV to browser for download
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();

            // 5️⃣ Send CSV to admin email
            sendEmailWithAttachment("ptlriya2612@gmail.com", baos.toByteArray());

        } catch (IOException | MessagingException e) {
            throw new RuntimeException("Error while exporting CSV or sending email", e);
        }
    }

    // Method to send email
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
