package com.example.beginnerbanking;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class Customer {


    @PrimaryKey
    private int AccountNumber;
    String Name;
    int Age;
    int Balance;
     int Pin;
    String colour;
    // Required no-arg constructor for Room
    public Customer() {
    }


    // Constructor
    public Customer(String Name, int AccountNumber, int Age, int random, String Answer) {
        int j = 8888;
        this.Name = Name;
        this.AccountNumber = AccountNumber;
        this.Age = Age;
        this.Balance = 1000;
        this.Pin = (j - random + AccountNumber);
        this.colour = Answer;
    }

    // Getters and Setters

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.AccountNumber = accountNumber;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        this.Balance = balance;
    }

    public int getPin() {
        return Pin;
    }

    public void setPin(int pin) {
        this.Pin = pin;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
