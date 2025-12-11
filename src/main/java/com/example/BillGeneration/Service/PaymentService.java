package com.example.BillGeneration.Service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    public String processPayment(int orderId) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        boolean[] steps = {true,true,true,true,false};
        Random random = new Random();

        for (boolean step : steps) {
            if (step == false) {
                int chance = random.nextInt(5);
                if (steps[chance] == false ) {
                    sb.append("Payment Failed...\n");
                    return sb.toString();
                }
            }
            sb.append("Payment " + " completed... \n");
            Thread.sleep(1000);
        }
        sb.append("Payment Successful! 🎉\n");



        return sb.toString();
    }
}
