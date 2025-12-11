package com.example.BillGeneration.Service;

import com.example.BillGeneration.DTO.ProductDTO;
import com.example.BillGeneration.Model.Customer;
import com.example.BillGeneration.Model.Order;
import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.CustomerRepo;
import com.example.BillGeneration.Repository.OrderRepo;
import com.example.BillGeneration.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;
    public ResponseEntity<?> buyProduct(Customer customer)
    {
        RestTemplate restTemplate=new RestTemplate();


        Product product=productService.findProductByPname(customer.getPname());

        if (product==null)
        {
            return new ResponseEntity<>("Product is Not Found", HttpStatus.NOT_FOUND);
        }

        else if (product.getStock()<customer.getQty())
        {
            return new ResponseEntity<>("Out Of Stock", HttpStatus.NOT_FOUND);
        }
        Customer cust= customerRepo.save(customer);

        Order order=new Order();
        order.setCustomer(cust);
        order.setProduct_id(product.getId());
        double tot_price=(product.getPrice()  * cust.getQty());
        double gst_price=(tot_price*18)/100;
        order.setTotal_price(tot_price+gst_price);

        orderRepo.save(order);
        return new ResponseEntity<Order>(order,HttpStatus.OK);

    }
}
