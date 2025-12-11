package com.example.BillGeneration.Controller;

import com.example.BillGeneration.DTO.ProductDTO;
import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.ProductRepo;
import com.example.BillGeneration.Service.ProductService;
import com.example.BillGeneration.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepo productRepo;


    @Autowired
    StockService stockService;
    @PostMapping("/save")
    public ResponseEntity<List<Product>> save(@RequestBody List<Product> product)
    {
        return productService.save(product);
    }

    @GetMapping("/getByPname/{pname}")
    public Product findByPname(@PathVariable String pname)
    {
        return productService.findProductByPname(pname);
    }
    @PutMapping("/updateStock")
    public String updateStock(@RequestParam int productId,@RequestParam long qty)
    {
        return productService.updateStock(productId,qty);
    }


    @GetMapping("/check-stock")
    public String check() {
        stockService.checkStock();
        return "Alerts Sent (If any)";
    }


}
