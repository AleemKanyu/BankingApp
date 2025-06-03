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

public class Pin extends AppCompatActivity {

    EditText editTextNumber3, editTextText4, editTextNumberPassword4;
    Button button4;
    FloatingActionButton floatingActionButton10;

    AppDatabase db;
    CustomerDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pin2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNumber3 = findViewById(R.id.editTextNumber3);
        editTextText4 = findViewById(R.id.editTextText4);
        editTextNumberPassword4 = findViewById(R.id.editTextNumberPassword4);
        button4 = findViewById(R.id.button4);
        floatingActionButton10 = findViewById(R.id.floatingActionButton10);

        // Initialize Room
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button4.setOnClickListener(v -> resetPin());
    }

    public void resetPin() {
        String accountStr = editTextNumber3.getText().toString();
        String favouriteColour = editTextText4.getText().toString();
        String newPinStr = editTextNumberPassword4.getText().toString();

        if (accountStr.isEmpty() || favouriteColour.isEmpty() || newPinStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int accountNumber = Integer.parseInt(accountStr);
        int newPin = Integer.parseInt(newPinStr);

        Customer customer = dao.getCustomerByAccountNumber(accountNumber);

        if (customer != null && customer.getColour().equalsIgnoreCase(favouriteColour)) {
            customer.setPin(newPin);
            dao.update(customer);
            Toast.makeText(this, "PIN changed successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Wrong Account Number or Favourite Colour", Toast.LENGTH_SHORT).show();
        }
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
