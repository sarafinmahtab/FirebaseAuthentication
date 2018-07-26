package com.sarafinmahtab.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.email_edit);
        displayNameEditText = findViewById(R.id.display_name_edit);
        phoneEditText = findViewById(R.id.number_edit);
        hobbyEditText = findViewById(R.id.hobby_edit);
        passwordEditText = findViewById(R.id.password_edit);

        registerButton = findViewById(R.id.register_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = emailEditText.getText().toString().trim();
                final String displayName = displayNameEditText.getText().toString().trim();
                final String phone = phoneEditText.getText().toString().trim();
                final String hobby = hobbyEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName)
                                    .build();

                            if (user != null) {

                                DatabaseReference userChild = databaseReference.child("users")
                                        .child(user.getUid());

                                Map<String, String> userData = new HashMap<>();

                                userData.put("email", email);
                                userData.put("name", displayName);
                                userData.put("phone", phone);
                                userData.put("hobby", hobby);

                                userChild.setValue(userData);

                                user.updateProfile(profileUpdates);

                                Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);

                                homeIntent.putExtra("display_name", displayName);

                                startActivity(homeIntent);
                                finish();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error Result: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private EditText emailEditText;
    private EditText displayNameEditText;
    private EditText phoneEditText;
    private EditText hobbyEditText;
    private EditText passwordEditText;

    private Button registerButton;
}
