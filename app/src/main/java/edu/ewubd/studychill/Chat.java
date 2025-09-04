package edu.ewubd.studychill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {

    private Button backButton, logoutButton, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        btnSend = findViewById(R.id.btnSend);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Go back or finish activity
                Intent i = new Intent(Chat.this, MoodChallenge.class);
                startActivity(i);
                // finish(); // Optional
                //Toast.makeText(TimerActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                // If you just want to finish this activity:
                // finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Implement logout logic
                // Intent intent = new Intent(TimerActivity.this, LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                // startActivity(intent);
                // finish();
                Toast.makeText(Chat.this, "Logout button clicked (Not Implemented)", Toast.LENGTH_SHORT).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Implement logout logic
                // Intent intent = new Intent(TimerActivity.this, LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                // startActivity(intent);
                // finish();
                Toast.makeText(Chat.this, "Send button clicked (Not Implemented)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}