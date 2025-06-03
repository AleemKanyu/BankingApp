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

public class SendMoney extends AppCompatActivity {

    EditText editTextNumber4, editTextNumber5, editTextNumber6, editTextNumber7;
    Button button5;
    FloatingActionButton floatingActionButton11;

    AppDatabase db;
    CustomerDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_money);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        editTextNumber4 = findViewById(R.id.editTextNumber4); // Receiver
        editTextNumber5 = findViewById(R.id.editTextNumber5); // Sender
        editTextNumber6 = findViewById(R.id.editTextNumber6); // PIN
        editTextNumber7 = findViewById(R.id.editTextNumber7); // Amount
        button5 = findViewById(R.id.button5);
        floatingActionButton11 = findViewById(R.id.floatingActionButton11);

        // DB and DAO
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();

        button5.setOnClickListener(v -> sendMoney());
    }

    public void sendMoney() {
        String senderStr = editTextNumber5.getText().toString();
        String receiverStr = editTextNumber4.getText().toString();
        String pinStr = editTextNumber6.getText().toString();
        String amountStr = editTextNumber7.getText().toString();

        if (senderStr.isEmpty() || receiverStr.isEmpty() || pinStr.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int senderAcc = Integer.parseInt(senderStr);
            int receiverAcc = Integer.parseInt(receiverStr);
            int pin = Integer.parseInt(pinStr);
            int amount = Integer.parseInt(amountStr);

            if (senderAcc == receiverAcc) {
                Toast.makeText(this, "Sender and Receiver cannot be same", Toast.LENGTH_SHORT).show();
                return;
            }

            Customer sender = dao.getCustomerByAccountNumber(senderAcc);
            Customer receiver = dao.getCustomerByAccountNumber(receiverAcc);

            if (sender == null || receiver == null) {
                Toast.makeText(this, "Invalid account number(s)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sender.getPin() != pin) {
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sender.getBalance() < amount) {
                Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                return;
            }

            // Perform transaction
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            dao.update(sender);
            dao.update(receiver);

            Toast.makeText(this, "Money Sent Successfully", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid numeric input", Toast.LENGTH_SHORT).show();
        }
    }

    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
