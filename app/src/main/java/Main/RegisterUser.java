package Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.Main.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText editFname, editPasswd, editEmail, editConPasswd;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        mAuth = FirebaseAuth.getInstance();

        TextView apptitle = findViewById(R.id.BioSecure);
        editFname = findViewById(R.id.full_nameText);
        editPasswd = findViewById(R.id.passwordText);
        editConPasswd = findViewById(R.id.confirmPasswordText);
        editEmail = findViewById(R.id.emailText);
        genderGroup = findViewById(R.id.genderGroup);
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
        String email = editEmail.getText().toString().trim();
        String passwd = editPasswd.getText().toString().trim();
        String conPasswd = editConPasswd.getText().toString().trim();
        int selectedID = genderGroup.getCheckedRadioButtonId();
        RadioButton genderButton = findViewById(selectedID);
        String gender = genderButton.getText().toString().trim();

        if (full_name.isEmpty()) {
            editFname.setError("Full name is required!");
            editFname.requestFocus();
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
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
                            UserAccount user = new UserAccount(full_name, email, gender);
                            FirebaseDatabase.getInstance().getReference("UserAccount")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"User has been registered successfully!", Toast.LENGTH_LONG).show();
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