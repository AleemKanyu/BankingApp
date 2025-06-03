package com.example.beginnerbanking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class withdraw extends AppCompatActivity {

    EditText editTextNumber10, editTextNumber11, editTextNumber12;
    Button button8;

    AppDatabase db;
    CustomerDao dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_withdraw);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button8 = findViewById(R.id.button8);
        editTextNumber10 = findViewById(R.id.editTextNumber10);
        editTextNumber11 = findViewById(R.id.editTextNumber11);
        editTextNumber12 = findViewById(R.id.editTextNumber12);

        // Initialize Room
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button8.setOnClickListener(v -> {
            String accountStr = editTextNumber10.getText().toString().trim();
            String pinStr = editTextNumber11.getText().toString().trim();
            String amountStr = editTextNumber12.getText().toString().trim();

            if (accountStr.isEmpty() || pinStr.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(withdraw.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int account = Integer.parseInt(accountStr);
            int pin = Integer.parseInt(pinStr);
            int amount = Integer.parseInt(amountStr);

            Customer customer = dao.getCustomerByAccountNumber(account);

            if (customer != null && customer.getPin() == pin) {
                if (customer.getBalance() < amount) {
                    Toast.makeText(withdraw.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                } else {
                    customer.setBalance(customer.getBalance() - amount);
                    dao.update(customer);
                    Toast.makeText(withdraw.this, "Amount Withdrawn Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(withdraw.this, "Invalid Account or PIN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    FloatingActionButton floatingActionButton2;

    public void openActivity(View v) {
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
