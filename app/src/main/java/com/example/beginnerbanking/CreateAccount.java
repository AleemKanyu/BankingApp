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
    String question,GENDER;

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
        Spinner GenderSpinner=findViewById(R.id.GenderSpinner);

        // Spinner setup with security questions
        String[] secuirityQuestions = {
                "Select Secuirity Question",
                "What's your favourite Colour?",
                "What's your favourite Sport?",
                "What's your favourite Food?"};
        String[] ChooseGender = {
                "Choose Your Gender",
                "Male",
                "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, secuirityQuestions);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        mySpinner.setAdapter(adapter);
        ArrayAdapter<String> gender = new ArrayAdapter<>(this, R.layout.spinner_layout, ChooseGender);
        gender.setDropDownViewResource(R.layout.spinner_layout);
        GenderSpinner.setAdapter(gender);

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
        GenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GENDER = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        if (name.isEmpty() || ageStr.isEmpty() || colour.isEmpty()
                || GENDER.equals("Choose Your Gender")
                || question.equals("Select Secuirity Question")) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        int random = (age <= 18) ? 4321 : 1234;

        new Thread(() -> {
            List<Customer> allCustomers = dao.getAllCustomers();
            int nextAccountNumber = allCustomers.size();

            Customer newCustomer = new Customer(name, nextAccountNumber, age, random, question, colour, GENDER);
            dao.insert(newCustomer);

            Customer saved = dao.getCustomerByAccountNumber(nextAccountNumber);

            runOnUiThread(() -> {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra(EXTRA_ACCOUNTNUMBER, String.format(Locale.US, "%04d", saved.getAccountNumber()));
                intent.putExtra("source", "second");
                startActivity(intent);
            });
        }).start();
    }


    // Navigates back to home screen and clears activity stack
    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
