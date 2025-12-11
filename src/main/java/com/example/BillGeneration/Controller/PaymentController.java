package com.example.BillGeneration.Controller;

import com.example.BillGeneration.Model.Order;
import com.example.BillGeneration.Model.Payment;
import com.example.BillGeneration.Repository.OrderRepo;
import com.example.BillGeneration.Repository.PaymentRepo;
import com.example.BillGeneration.Repository.ProductRepo;
import com.example.BillGeneration.Service.PaymentService;
import com.example.BillGeneration.Service.ProductService;
import com.example.BillGeneration.Service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    WhatsAppService whatsAppService;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/pay")
    public String payment(@RequestParam int orderId) {
        Order oid;
        String phone;

        try {
            // Fetch order
            oid = orderRepo.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order Not Found.."));

            phone = "+91" + oid.getCustomer().getMob();

            System.out.println(phone);
            // Process payment
            String result = paymentService.processPayment(oid.getId());

            if (result.contains("Successful")) {

                // 1️⃣ Send text message
                whatsAppService.sendMessage(phone,
                        "Payment Successful for Order " + orderId + " 🎉");

                System.out.println(phone+orderId);
                // 2️⃣ Save payment
                Payment payment = new Payment();
                payment.setCustomer(oid.getCustomer());
                payment.setOrder(oid);
                payment.setPid(oid.getProduct_id());
                paymentRepo.save(payment);

                // 3️⃣ Generate PDF invoice
                // Update stock
                productService.updateStock(oid.getProduct_id(), oid.getCustomer().getQty());
            }

            return result;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
