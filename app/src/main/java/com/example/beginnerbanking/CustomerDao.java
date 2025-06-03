package com.example.beginnerbanking;

import androidx.room.*;
import java.util.List;

@Dao
public interface CustomerDao {

    @Insert
    void insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customers WHERE AccountNumber = :accNo")
    Customer getCustomerByAccountNumber(int accNo);

    @Query("SELECT * FROM customers")
    List<Customer> getAllCustomers();
}
