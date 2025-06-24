package com.example.beginnerbanking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity {
    ImageView userProfile;
    TextView accountnumber1, password1, favouritecolour1, balance3,usernametext,genderanswer;
    AppDatabase db;
    CustomerDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailsactivity2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accountnumber1 = findViewById(R.id.accountnumber1);
        password1 = findViewById(R.id.password1);
        favouritecolour1 = findViewById(R.id.favouritecolour1);
        balance3 = findViewById(R.id.balance10);
        usernametext = findViewById(R.id.usernametext);
        userProfile=findViewById(R.id.userProfile);
        genderanswer=findViewById(R.id.genderanswer);
        db = AppDatabase.getInstance(this);
        dao = db.customerDao();
        Intent intent = getIntent();
        handleIntent(intent);





    }
    private void handleIntent(Intent intent) {
        String source = intent.getStringExtra("source");
        if ("first".equals(source)) {

            String number = intent.getStringExtra(MainActivity.EXTRA_NUMBER);

            int accnumber = Integer.parseInt(number);


            Customer customer = dao.getCustomerByAccountNumber(accnumber);
            userProfile.setImageResource(R.drawable.profile);
            if(customer.getGender().equals("Female")){
                userProfile.setImageResource(R.drawable.profile2);
            }
            String userName = customer.getName();
            String displayName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();
            usernametext.setText(displayName);
            accountnumber1.setText(String.valueOf(customer.getAccountNumber()));
            password1.setText(String.valueOf(customer.getPin()));
            favouritecolour1.setText(customer.getFavouriteColour());
            balance3.setText(String.valueOf(customer.getBalance()));
            genderanswer.setText(String.valueOf(customer.getGender()));
        } else if ("second".equals(source)) {

            String number = intent.getStringExtra(CreateAccount.EXTRA_ACCOUNTNUMBER);

            int accnumber = Integer.parseInt(number);


            Customer customer = dao.getCustomerByAccountNumber(accnumber);
            userProfile.setImageResource(R.drawable.profile);
            if(customer.getGender().equals("Female")){
                userProfile.setImageResource(R.drawable.profile2);
            }
            String userName = customer.getName();
            String displayName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();
            usernametext.setText(displayName);
            accountnumber1.setText(String.valueOf(customer.getAccountNumber()));
            password1.setText(String.valueOf(customer.getPin()));
            favouritecolour1.setText(customer.getFavouriteColour());
            balance3.setText(String.valueOf(customer.getBalance()));
            genderanswer.setText(String.valueOf(customer.getGender()));
        }
    }
    public void homeActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}