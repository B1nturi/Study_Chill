package edu.ewubd.studychill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    private CardView timerCard, statsCard, moodCard, musicCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        timerCard = findViewById(R.id.timerCard);
        statsCard = findViewById(R.id.statsCard);
        moodCard = findViewById(R.id.moodCard);
        musicCard = findViewById(R.id.musicCard);

        timerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to TimerActivity
                Intent i = new Intent(DashboardActivity.this, TimerActivity.class);
                startActivity(i);
            }
        });

        statsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to StatsActivity
                Toast.makeText(DashboardActivity.this, "Stats card clicked (Not Implemented)", Toast.LENGTH_SHORT).show();
            }
        });

        moodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to MoodChallenge
                Intent i = new Intent(DashboardActivity.this, MoodChallenge.class);
                startActivity(i);
            }
        });

        musicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to Music Player
                Intent i = new Intent(DashboardActivity.this, Player.class);
                startActivity(i);
            }
        });
    }
}