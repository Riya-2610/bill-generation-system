package com.example.BillGeneration.Service;

import com.example.BillGeneration.DTO.ProductDTO;
import com.example.BillGeneration.Model.Product;
import com.example.BillGeneration.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public ResponseEntity<List<Product>> save(List<Product> product)
    {
        try{
            productRepo.saveAll(product);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
    public ResponseEntity<List<Product>> getAll(){
        try{
            return new ResponseEntity<>(productRepo.findAll(),HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Product findProductByPname(String pname)
    {
        try{
            return productRepo.findProductByPname(pname);        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String updateStock( int productId,  long qty)
    {
        Product p=productRepo.findById(productId).orElseThrow(()->new RuntimeException("product Not Found.."));
        if (p.getStock()<qty)
        {
            return "Not Enough Stock";
        }
        p.setStock(p.getStock()-qty);
        productRepo.save(p);
        return "Stock Updated..";
    }

}
