package main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.example.Main.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText editFname, editAge, editPasswd, editEmail, editConPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        mAuth = FirebaseAuth.getInstance();

        TextView apptitle = findViewById(R.id.BioSecure);
        editFname = findViewById(R.id.full_nameText);
        editAge = findViewById(R.id.ageText);
        editPasswd = findViewById(R.id.passwordText);
        editConPasswd = findViewById(R.id.confirmPasswordText);
        editEmail = findViewById(R.id.emailText);

        Button registerButton = findViewById(R.id.registerButton);

        apptitle.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BioSecure:
                startActivity(new Intent(this, loginActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String full_name = editFname.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String passwd = editPasswd.getText().toString().trim();
        String conPasswd = editConPasswd.getText().toString().trim();

        if (full_name.isEmpty()) {
            editFname.setError("Full name is required!");
            editFname.requestFocus();
        }

        if (age.isEmpty()) {
            editAge.setError("Age is required");
            editAge.requestFocus();
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please provide a valid email");
            editEmail.requestFocus();
        }

        if (passwd.isEmpty()) {
            editPasswd.setError("Password is required");
            editPasswd.requestFocus();
        }

        if (conPasswd.isEmpty()) {
            editConPasswd.setError("Please confirm your password!");
            editConPasswd.requestFocus();
        }

        if (!passwd.equals(conPasswd)) {
            editPasswd.setError("Both password input must match!");
            editConPasswd.setError("Both password input must match!");
            editConPasswd.requestFocus();
        }

        if (passwd.length() < 8) {
            editPasswd.setError("Minimum password length should be 8 characters");
            editPasswd.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userAccount user = new userAccount(full_name, email, age);
                            FirebaseDatabase.getInstance().getReference("userAccount")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(registerUser.this, loginActivity.class));
                                    } else
                                        Toast.makeText(getApplicationContext(),"User has been registered unsuccessfully! Please try again!", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(),"User has been registered unsuccessfully! Please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}