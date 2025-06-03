package com.example.beginnerbanking;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    CustomerDao dao;


    ImageButton imageButton2, imageButton3, imageButton4, imageButton5,imageButton6,imageButton17,imageButton,imageButton7;


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

        db = AppDatabase.getInstance(this);
        dao = db.customerDao();


    }

    public void openActivity(View v) {
        imageButton2 = findViewById(R.id.imageButton2);
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
    public void deleteActivity(View v) {
        imageButton3 = findViewById(R.id.imageButton3);
        Intent intent = new Intent(this, DeleteAccount.class);
        startActivity(intent);
    }

    public void balanceActivity(View v) {
        imageButton2 = findViewById(R.id.imageButton2);
        Intent intent = new Intent(this, Balance.class);
        startActivity(intent);
    }

    public void pinActivity(View v) {
        imageButton6 = findViewById(R.id.imageButton6);
        Intent intent = new Intent(this, Pin.class);
        startActivity(intent);
    }
    public void sendActivity(View v) {
        imageButton5 = findViewById(R.id.imageButton5);
        Intent intent = new Intent(this, SendMoney.class);
        startActivity(intent);
    }
    public void contactActivity(View v) {
        imageButton17 = findViewById(R.id.imageButton17);
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }
    public void depositActivity(View v) {
        imageButton = findViewById(R.id.imageButton);
        Intent intent = new Intent(this, Deposit.class);
        startActivity(intent);
    }

    public void withdrawActivity(View v) {
        imageButton7 = findViewById(R.id.imageButton7);
        Intent intent = new Intent(this, withdraw.class);
        startActivity(intent);
    }


}

