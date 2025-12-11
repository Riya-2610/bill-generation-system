package com.example.BillGeneration.Controller;

import com.example.BillGeneration.Model.Customer;
import com.example.BillGeneration.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @PostMapping("/buyProduct")
    public ResponseEntity<?> buyProduct(@RequestBody Customer customer)
    {
        return customerService.buyProduct(customer);
    }


}
