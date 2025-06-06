package com.example.beginnerbanking;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    CustomerDao dao;
    LinearLayout f;
    public static final String EXTRA_NUMBER = "accountnumberoftheaccount.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        f=findViewById(R.id.f);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginPopup();
            }
        });
    }

    public void openActivity(View v) {

        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
    public void deleteActivity(View v) {

        Intent intent = new Intent(this, DeleteAccount.class);
        startActivity(intent);
    }

    public void balanceActivity(View v) {

        Intent intent = new Intent(this, Balance.class);
        startActivity(intent);
    }

    public void pinActivity(View v) {

        Intent intent = new Intent(this, Pin.class);
        startActivity(intent);
    }
    public void sendActivity(View v) {

        Intent intent = new Intent(this, SendMoney.class);
        startActivity(intent);
    }
    public void contactActivity(View v) {

        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }

    public void depositActivity(View v) {
//        imageButton = findViewById(R.id.imageButton);
        Intent intent = new Intent(this, Deposit.class);
        startActivity(intent);
    }

    public void withdrawActivity(View v) {

        Intent intent = new Intent(this, withdraw.class);
        startActivity(intent);
    }

    public void showLoginPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.login, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        view.setAlpha(0f);
        view.setScaleX(0.8f);
        view.setScaleY(0.8f);
        view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .start();

        EditText accountEditText = view.findViewById(R.id.edit_account_number);
        EditText passwordEditText = view.findViewById(R.id.edit_password);
        Button loginButton = view.findViewById(R.id.btn_login);
        Button cancelButton = view.findViewById(R.id.btn_cancel);

        loginButton.setOnClickListener(v -> {
            String account = accountEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter account and password", Toast.LENGTH_SHORT).show();
                return;
            }

            CustomerDao dao = AppDatabase.getInstance(this).customerDao();
            Customer cus = dao.verifyLogin(Integer.parseInt(account), Integer.parseInt(password));
            if (cus == null) {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra(EXTRA_NUMBER, account);
                intent.putExtra("source", "first");
                startActivity(intent);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }



}

