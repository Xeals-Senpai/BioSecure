package Main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.Main.R;

public class loginActivity extends AppCompatActivity {
    Button loginButton;
    EditText userText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        loginButton = (Button) findViewById(R.id.loginButton);
        userText = (EditText) findViewById(R.id.userText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userText.getText().toString().equals("admin") &&
                passwordText.getText().toString().equals("1234")) {
                    Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), "Login Failed!\n Wrong credentials used!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
