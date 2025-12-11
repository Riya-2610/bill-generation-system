package com.example.BillGeneration.Service;

import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    WhatsAppService whatsappService;

    public void checkStock() {
        List<Product> products = productRepo.findAll();

        for (Product p : products) {
            if (p.getStock() <= p.getThreshold()) {

                String msg = "⚠️ *Stock Alert* ⚠️\n"
                        + "Product: " + p.getPname() + "\n"
                        + "Stock Left: " + p.getStock() + "\n"
                        + "Threshold: " + p.getThreshold();

                whatsappService.sendWhatsAppAlert(msg);
            }
        }
    }
}
