package com.example.BillGeneration.Repository;

import com.example.BillGeneration.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    public Product findProductByPname(String pname);
    @Query(value = "select count(id) from Product",nativeQuery = true)
    public int countProducts();
}
