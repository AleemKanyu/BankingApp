package com.example.beginnerbanking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Balance extends AppCompatActivity {

    EditText editTextNumber2, editTextNumberPassword2;

    Button button3;
    FloatingActionButton floatingActionButton9;

    AppDatabase db;
    CustomerDao dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_balance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNumber2 = findViewById(R.id.editTextNumber2);
        editTextNumberPassword2 = findViewById(R.id.editTextNumberPassword2);
        button3 = findViewById(R.id.button3);
        floatingActionButton9 = findViewById(R.id.floatingActionButton9);

        // Initialize Room
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button3.setOnClickListener(v -> checkBalance());
    }

    public void checkBalance() {
        String accountNumberStr = editTextNumber2.getText().toString();
        String pinStr = editTextNumberPassword2.getText().toString();

        if (accountNumberStr.isEmpty() || pinStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int accountNumber = Integer.parseInt(accountNumberStr);
        int pin = Integer.parseInt(pinStr);

        Customer customer = dao.getCustomerByAccountNumber(accountNumber);

        if (customer != null && customer.getPin() == pin) {

            View view = LayoutInflater.from(this).inflate(R.layout.balance, null);
            TextView balanceupdate = view.findViewById(R.id.balanceupdate);
            balanceupdate.setText("Your Balance is ₹" + customer.getBalance());

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .create();

// OPTIONAL: Add animation
            view.setAlpha(0f);
            view.setScaleX(0.8f);
            view.setScaleY(0.8f);
            view.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start();

            dialog.show();
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
