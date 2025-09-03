package edu.ewubd.studychill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MoodChallenge extends AppCompatActivity {

    private Button backButton, logoutButton, btnBreathingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_challenge);

        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        btnBreathingCircle = findViewById(R.id.btnBreathingCircle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Go back or finish activity
                // If you have a DashboardActivity:
                Intent i = new Intent(MoodChallenge.this, DashboardActivity.class);
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
                Toast.makeText(MoodChallenge.this, "Logout button clicked (Not Implemented)", Toast.LENGTH_SHORT).show();
            }
        });

        btnBreathingCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Go back or finish activity
                // If you have a BreathCircle:
                Intent i = new Intent(MoodChallenge.this, BreathCircle.class);
                startActivity(i);
                // finish(); // Optional
                //Toast.makeText(TimerActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                // If you just want to finish this activity:
                // finish();
            }
        });
    }
}