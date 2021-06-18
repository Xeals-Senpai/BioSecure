 package main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Main.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;

 public class userActivity extends AppCompatActivity {
    private Button logoutButton;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractivity);

        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(userActivity.this, loginActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("userAccount");
        userId = user.getUid();

        final TextView greetingView = findViewById(R.id.welcomeLabel);
        final TextView nameView = findViewById(R.id.userName);
        final TextView emailView = findViewById(R.id.userEmail);
        final TextView ageView = findViewById(R.id.userAge);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userAccount userProfile = snapshot.getValue(userAccount.class);

                if (userProfile != null) {
                    String fullName = userProfile.fullname;
                    String age = userProfile.age;
                    String email = userProfile.email;;

                    greetingView.setText(String.format("Welcome! %s!", fullName));
                    nameView.setText(String.format("Full name: %s", fullName));
                    ageView.setText(String.format("Age: %s", age));
                    emailView.setText(String.format("Email: %s", email));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error! An error has occurred!", Toast.LENGTH_LONG).show();
            }
        });

    }
}