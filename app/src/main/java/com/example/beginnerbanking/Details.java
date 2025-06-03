package com.example.beginnerbanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Details extends AppCompatActivity {

    TextView textViewName, textViewAccount, textViewColor, textViewPin;
    FloatingActionButton floatingActionButton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        textViewName = findViewById(R.id.textView4);
        textViewAccount = findViewById(R.id.textView5);
        textViewColor = findViewById(R.id.textView6);
        textViewPin = findViewById(R.id.textView7);
        floatingActionButton7 = findViewById(R.id.floatingActionButton7);

        // Receive data from Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra(CreateAccount.EXTRA_NAME);
        String accountNumber = intent.getStringExtra(CreateAccount.EXTRA_ACCOUNTNUMBER);
        String colour = intent.getStringExtra(CreateAccount.EXTRA_COLOUR);
        String pin = intent.getStringExtra(CreateAccount.EXTRA_PIN);

        // Set text
        textViewName.setText("Name: " + name);
        textViewAccount.setText("Account Number: " + accountNumber);
        textViewColor.setText("Your Favourite Colour: " + colour);
        textViewPin.setText("PIN: " + pin);
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
