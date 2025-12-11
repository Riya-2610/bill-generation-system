package com.example.BillGeneration.Repository;

import com.example.BillGeneration.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer> {
    @Query("SELECT MAX(p.id) FROM Payment p")
    Integer getLastPaymentId();
}
