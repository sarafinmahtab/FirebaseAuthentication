package com.sarafinmahtab.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        displayNameTextView = findViewById(R.id.displayName);

        if (getIntent().getStringExtra("display_name") != null) {
            String displayName = getIntent().getStringExtra("display_name");
            displayNameTextView.setText(displayName);
        }
    }

    private TextView displayNameTextView;
}
