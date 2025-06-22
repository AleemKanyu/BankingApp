package com.example.beginnerbanking;



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
     String question;
    String answer;
    // Required no-arg constructor for Room
    public Customer() {
    }

    public Customer(String Name, int AccountNumber, int Age, int random,String question, String Answer) {
        int j = 8888;
        this.Name = Name;
        this.AccountNumber = AccountNumber;
        this.Age = Age;
        this.Balance = 1000;
        this.Pin = (j - random + AccountNumber);
        this.question = question;
        this.answer = Answer;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFavouriteColour() {return answer;}


}

