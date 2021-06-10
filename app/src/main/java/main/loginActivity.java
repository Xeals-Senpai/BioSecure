package main;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.Main.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton, registerButton;
    private EditText userText, passwordText;
    private ImageView fprint;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        userText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.passwordText);
        fprint = findViewById(R.id.fingerprintImage);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(loginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull @NotNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull @NotNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        fprint.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                if (userText.getText().toString().equals("admin") &&
                        passwordText.getText().toString().equals("1234")) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Login Failed!\nWrong credentials used!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}
