package com.example.BillGeneration.Controller;

import com.example.BillGeneration.Service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsappService;

    @GetMapping("/send")
    public String send(@RequestParam String phone, @RequestParam String message) {
        return whatsappService.sendMessage(phone, message);
    }
}

