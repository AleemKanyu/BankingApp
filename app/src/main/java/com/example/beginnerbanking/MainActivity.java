package com.example.beginnerbanking;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    LinearLayout f;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

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
        checkBiometricAndAuthenticate();
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

    private void checkBiometricAndAuthenticate() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "Biometric not available or not set up", Toast.LENGTH_SHORT).show();
                return;
        }

        biometricPrompt = new BiometricPrompt(MainActivity.this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getApplicationContext(), "Authentication Success", Toast.LENGTH_SHORT).show();

                        // Only open SendMoney activity
                        Intent intent = new Intent(MainActivity.this, SendMoney.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(getApplicationContext(), "Error: " + errString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Use your face, fingerprint or device PIN")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

}



