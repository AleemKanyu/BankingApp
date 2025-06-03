package com.example.beginnerbanking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contact extends AppCompatActivity {
EditText editTextText5, editTextText6;
Button button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextText5 = findViewById(R.id.editTextText5);
        editTextText6 = findViewById(R.id.editTextText6);
        button6 = findViewById(R.id.button6);

        button6.setOnClickListener(new View.OnClickListener() {
            String[] addresses={"kanyualeem416@gmail.com","ifham2077@gmail.com"};
            @Override
            public void onClick(View v) {
                String haha = editTextText5.getText().toString();
                String hehe=editTextText6.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, haha);
                intent.putExtra(Intent.EXTRA_TEXT, hehe);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
    FloatingActionButton floatingActionButton12;

    public void homeActivity(View v) {
        floatingActionButton12 = findViewById(R.id.floatingActionButton12);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}