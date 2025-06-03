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

public class Deposit extends AppCompatActivity {

    Button button7;
    EditText editTextNumber8, editTextNumber9;
    AppDatabase db;
    CustomerDao dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button7 = findViewById(R.id.button7);
        editTextNumber8 = findViewById(R.id.editTextNumber8);
        editTextNumber9 = findViewById(R.id.editTextNumber9);

        // Initialize Room
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = editTextNumber8.getText().toString().trim();
                String accountStr = editTextNumber9.getText().toString().trim();

                if (amountStr.isEmpty() || accountStr.isEmpty()) {
                    Toast.makeText(Deposit.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int deposit = Integer.parseInt(amountStr);
                int accNo = Integer.parseInt(accountStr);

                Customer customer = dao.getCustomerByAccountNumber(accNo);

                if (customer != null) {
                    int newBalance = customer.getBalance() + deposit;
                    customer.setBalance(newBalance);
                    dao.update(customer);
                    Toast.makeText(Deposit.this, "Amount Credited Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Deposit.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    FloatingActionButton floatingActionButton13;

    public void homeActivity(View v) {
        floatingActionButton13 = findViewById(R.id.floatingActionButton13);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
