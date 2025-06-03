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

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    Button button;
    EditText editTextText, editTextText2, editTextText3;
    AppDatabase db;
    CustomerDao dao;

    public static final String EXTRA_NAME = "name.com";
    public static final String EXTRA_AGE = "age.com";
    public static final String EXTRA_COLOUR = "colour.com";
    public static final String EXTRA_ACCOUNTNUMBER = "accountnumber.com";
    public static final String EXTRA_PIN = "pin.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);


        editTextText = findViewById(R.id.editTextText);
        editTextText2 = findViewById(R.id.editTextText2);
        editTextText3 = findViewById(R.id.editTextText3);
        button = findViewById(R.id.button);

        // Initialize Room DB
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button.setOnClickListener(v -> createAccount());
    }

    public void createAccount() {
        String name = editTextText.getText().toString();
        String ageStr = editTextText2.getText().toString();
        String colour = editTextText3.getText().toString();

        if (name.isEmpty() || ageStr.isEmpty() || colour.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        int random = (age <= 18) ? 4321 : 1234;

        // Get next AccountNumber (auto increment logic)
        List<Customer> allCustomers = dao.getAllCustomers();
        int nextAccountNumber = allCustomers.size();

        Customer newCustomer = new Customer(name, nextAccountNumber, age, random, colour);
        dao.insert(newCustomer);

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        // Retrieve the saved customer to pass via intent
        Customer saved = dao.getCustomerByAccountNumber(nextAccountNumber);

        Intent intent = new Intent(this, Details.class);
        intent.putExtra(EXTRA_NAME, saved.getName());
        intent.putExtra(EXTRA_AGE, String.valueOf(saved.getAge()));
        intent.putExtra(EXTRA_COLOUR, saved.getColour());
        intent.putExtra(EXTRA_ACCOUNTNUMBER, String.format("%04d", saved.getAccountNumber()));
        intent.putExtra(EXTRA_PIN, String.valueOf(saved.getPin()));
        startActivity(intent);
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
