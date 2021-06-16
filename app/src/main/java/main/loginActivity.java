package main;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.Main.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton, registerButton;
    private EditText userText, passwordText;
    private TextView biometricText;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        userText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.passwordText);

        mAuth = FirebaseAuth.getInstance();

        biometricText = findViewById(R.id.biometricLabel);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(loginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull @NotNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: "+errString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull @NotNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), R.string.authsuccess, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), R.string.authfailed, Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText("cancel")
                .build();

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                biometricPrompt.authenticate(promptInfo);
                break;
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }

    private void userLogin() {
        String email = userText.getText().toString().trim();
        String passwd = passwordText.getText().toString().trim();

        if (email.isEmpty()) {
            userText.setError("Email is required");
            userText.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userText.setError("Please provide a valid email address!");
            userText.requestFocus();
        }

        if (passwd.isEmpty()) {
            passwordText.setError("Password is required to login");
            passwordText.requestFocus();
        }

        if (passwd.length() < 8) {
            passwordText.setError("Password length must be at least 8 chars long");
            passwordText.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
