package edu.ewubd.studychill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class GetStartedActivity extends AppCompatActivity {

    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("first_time", false);

        // Navigate to DashboardActivity
        if (isFirstTime) {
            Intent i = new Intent(GetStartedActivity.this, DashboardActivity.class);
            startActivity(i);
            finish(); // Close GetStartedActivity
            return;
        }

        btnGetStarted.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("first_time", true);
                editor.apply();

                // Navigate to DashboardActivity
                Intent i = new Intent(GetStartedActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });
    }

}