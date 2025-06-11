package com.example.beginnerbanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.concurrent.Executors;

public class Pin extends AppCompatActivity {

    EditText editTextText4, editTextNumberPassword4, accountnumberinput1;
    Button button4;
    FloatingActionButton floatingActionButton10;
    String account;
    AppDatabase db;
    CustomerDao dao;
    Customer currentCustomer; // To hold the current customer object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pin2); // This avoids crash, but will be replaced after popup

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        showaccountPopup(); // show account popup after layout is loaded
    }

    public void showaccountPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.accountinput, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        view.setAlpha(0f);
        view.setScaleX(0.8f);
        view.setScaleY(0.8f);
        view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(300).start();

        accountnumberinput1 = view.findViewById(R.id.accountnumberinput1);
        Button changepin = view.findViewById(R.id.changepin);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);

        changepin.setOnClickListener(v -> {
            account = accountnumberinput1.getText().toString().trim();
            if (account.isEmpty()) {
                Toast.makeText(this, "Enter account number", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int accountNumber = Integer.parseInt(account);
                Executors.newSingleThreadExecutor().execute(() -> {
                    Customer customer = dao.getCustomerByAccountNumber(accountNumber);
                    runOnUiThread(() -> {
                        if (customer != null) {
                            currentCustomer = customer; // Save the customer for use in resetPin()
                            // now switch to pin2 layout after verifying account
                            setContentView(R.layout.activity_pin2);

                            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v2, insets) -> {
                                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                                v2.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                                return insets;
                            });

                            editTextText4 = findViewById(R.id.editTextText4);
                            editTextNumberPassword4 = findViewById(R.id.editTextNumberPassword4);
                            button4 = findViewById(R.id.button4);
                            floatingActionButton10 = findViewById(R.id.floatingActionButton10);

                            String question = customer.getQuestion();
                            if (question != null && !question.isEmpty()) {
                                editTextText4.setHint(question);
                            } else {
                                editTextText4.setHint("Your Favourite Colour ?");
                            }

                            button4.setOnClickListener(btn -> resetPin());

                            if (floatingActionButton10 != null) {
                                floatingActionButton10.setOnClickListener(this::homeActivity);
                            }

                            dialog.dismiss();
                        } else {
                            Toast.makeText(this, "No such account", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid account number", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_btn.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void resetPin() {
        String answer = editTextText4.getText().toString().trim();
        String newPinStr = editTextNumberPassword4.getText().toString().trim();

        if (account == null || account.isEmpty() || answer.isEmpty() || newPinStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int newPin = Integer.parseInt(newPinStr);

            Executors.newSingleThreadExecutor().execute(() -> {
                // Use the already fetched customer object
                if (currentCustomer != null && currentCustomer.getAnswer().equalsIgnoreCase(answer)) {
                    currentCustomer.setPin(newPin);
                    dao.update(currentCustomer);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "PIN changed successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Wrong Account Number or Security Answer", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid PIN format", Toast.LENGTH_SHORT).show();
        }
    }
}
