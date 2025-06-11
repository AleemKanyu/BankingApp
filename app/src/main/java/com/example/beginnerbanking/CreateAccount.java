package com.example.beginnerbanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Locale;

public class CreateAccount extends AppCompatActivity {

    Button button;
    EditText editTextText, editTextText2, editTextText3;
    AppDatabase db;
    CustomerDao dao;
    String question;

    public static final String EXTRA_NAME = "name.com";
    public static final String EXTRA_COLOUR = "colour.com";
    public static final String EXTRA_ACCOUNTNUMBER = "accountnumber.com";
    public static final String EXTRA_PIN = "pin.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        // Linking UI elements
        editTextText = findViewById(R.id.editTextText);
        editTextText2 = findViewById(R.id.editTextText2);
        editTextText3 = findViewById(R.id.editTextText3);
        button = findViewById(R.id.button);
        Spinner mySpinner = findViewById(R.id.mySpinner);

        // Spinner setup with security questions
        String[] secuirityQuestions = {
                "Select Secuirity Question",
                "What's your favourite Colour?",
                "What's your favourite Sport?",
                "What's your favourite Food?"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, secuirityQuestions);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mySpinner.setAdapter(adapter);

        // Initialize Room database instance
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        // Apply padding to avoid overlap with system UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Save selected security question from spinner
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no action needed
            }
        });

        // On clicking "Create Account" button
        button.setOnClickListener(v -> createAccount());
    }

    // Handles account creation and saving customer in DB
    public void createAccount() {
        String name = editTextText.getText().toString();
        String ageStr = editTextText2.getText().toString();
        String colour = editTextText3.getText().toString();

        // Validate input fields
        if (name.isEmpty() || ageStr.isEmpty() || colour.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        // Set default PIN based on age group
        int random = (age <= 18) ? 4321 : 1234;

        // Generate account number (based on number of customers already in DB)
        List<Customer> allCustomers = dao.getAllCustomers();
        int nextAccountNumber = allCustomers.size();

        // Create and insert new customer into DB
        Customer newCustomer = new Customer(name, nextAccountNumber, age, random, question, colour);
        dao.insert(newCustomer);

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        // Fetch saved customer and open details screen
        Customer saved = dao.getCustomerByAccountNumber(nextAccountNumber);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(EXTRA_ACCOUNTNUMBER, String.format(Locale.US, "%04d", saved.getAccountNumber())); // pad with 0s
        intent.putExtra("source", "second");
        startActivity(intent);
    }

    // Navigates back to home screen and clears activity stack
    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
