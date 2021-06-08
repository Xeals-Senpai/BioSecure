package Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.Main.R;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    Button loginButton, registerButton;
    EditText userText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        userText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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
