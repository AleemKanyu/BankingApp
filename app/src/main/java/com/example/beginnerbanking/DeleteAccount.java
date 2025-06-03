package com.example.beginnerbanking;

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

public class DeleteAccount extends AppCompatActivity {

    EditText editTextNumber, editTextNumberPassword;
    Button button2;
    FloatingActionButton floatingActionButton8;

    AppDatabase db;
    CustomerDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumberPassword = findViewById(R.id.editTextNumberPassword);
        button2 = findViewById(R.id.button2);
        floatingActionButton8 = findViewById(R.id.floatingActionButton8);

        // Initialize Room database and DAO
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button2.setOnClickListener(v -> deleteActivity());
    }

    public void deleteActivity() {
        String accountNumberStr = editTextNumber.getText().toString();
        String pinStr = editTextNumberPassword.getText().toString();

        if (accountNumberStr.isEmpty() || pinStr.isEmpty()) {
            Toast.makeText(this, "Please enter both Account Number and PIN", Toast.LENGTH_SHORT).show();
            return;
        }

        int accountNumber = Integer.parseInt(accountNumberStr);
        int pin = Integer.parseInt(pinStr);

        Customer customer = dao.getCustomerByAccountNumber(accountNumber);

        if (customer != null && customer.getPin() == pin) {
            dao.delete(customer);
            Toast.makeText(this, "Account Deleted Successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to previous activity
        } else {
            Toast.makeText(this, "Invalid Account Number or PIN!", Toast.LENGTH_SHORT).show();
        }
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
