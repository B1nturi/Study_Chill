package edu.ewubd.studychill;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast; // For a simple back button action

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    // --- Timer UI Elements ---
    private TextView timerDisplay;
    private TextView timerPhase;
    private ProgressBar timerCircle; // The progress circle
    private Button startBtn;
    private Button resetBtn;
    private Button preset25;
    private Button preset45;
    private Button preset60;

    // --- Timer State Variables ---
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis; // Default to 25 minutes
    private boolean timerRunning;
    private boolean isFocusPhase = true; // Start with focus phase
    private long currentPhaseTotalDuration; // To store the total duration of the current phase for progress bar

    // --- Time Constants ---
    private static final long FOCUS_TIME_DURATION_25 = 25 * 60 * 1000; // 25 minutes
    private static final long FOCUS_TIME_DURATION_45 = 45 * 60 * 1000; // 45 minutes
    private static final long FOCUS_TIME_DURATION_60 = 60 * 60 * 1000; // 60 minutes
    private static final long SHORT_BREAK_DURATION = 5 * 60 * 1000;   // 5 minutes
    // private static final long LONG_BREAK_DURATION = 15 * 60 * 1000;  // For future enhancement

    // --- Music Player UI Elements (from your previous code) ---
    private LinearLayout musicPlayerLayout; // Renamed to avoid confusion with a potential MediaPlayer object
    private Button playMusicBtn;
    private Button closeMusicPlayerBtn;
    private boolean musicPlayerVisible = false;

    // --- Other UI Elements ---
    private Button backButton;
    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // --- Initialize Timer UI Elements ---
        timerDisplay = findViewById(R.id.timerDisplay);
        timerPhase = findViewById(R.id.timerPhase);
        timerCircle = findViewById(R.id.timerCircle); // Make sure this ID is correct for the progress circle
        startBtn = findViewById(R.id.startBtn);
        resetBtn = findViewById(R.id.resetBtn);
        preset25 = findViewById(R.id.preset25);
        preset45 = findViewById(R.id.preset45);
        preset60 = findViewById(R.id.preset60);

        // --- Initialize Music Player UI Elements ---
        musicPlayerLayout = findViewById(R.id.musicPlayer);
        playMusicBtn = findViewById(R.id.playMusicBtn);
        closeMusicPlayerBtn = findViewById(R.id.closeMusicPlayerBtn);

        // --- Initialize Other UI Elements ---
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);


        // --- Set Initial Timer State ---
        setTimerDuration(FOCUS_TIME_DURATION_25, true); // Default to 25 min focus

        // --- Setup Button Click Listeners ---
        setupTimerButtonClickListeners();
        setupMusicPlayerButtonListeners();
        setupOtherButtonListeners();

        // --- Initial UI Update ---
        updateTimerDisplay();
        updatePhaseDisplay();
        updateProgressBar();
    }

    private void setupTimerButtonClickListeners() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    startTimer();
                }
                else {
                    pauseTimer();
                }
            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        preset25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerDuration(FOCUS_TIME_DURATION_25, true);
            }
        });

        preset45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerDuration(FOCUS_TIME_DURATION_45, true);
            }
        });

        preset60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerDuration(FOCUS_TIME_DURATION_60, true);
            }
        });
    }

    private void setupMusicPlayerButtonListeners() {
        playMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayerVisible = !musicPlayerVisible;
                musicPlayerLayout.setVisibility(musicPlayerVisible ? View.VISIBLE : View.GONE);
            }
        });

        closeMusicPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayerVisible = false;
                musicPlayerLayout.setVisibility(View.GONE);
            }
        });
        // TODO: Add listeners for your actual music control buttons (prevTrack, playPauseMusic, etc.)
    }

    private void setupOtherButtonListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Go back or finish activity
                // If you have a DashboardActivity:
                Intent i = new Intent(TimerActivity.this, DashboardActivity.class);
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
                Toast.makeText(TimerActivity.this, "Logout button clicked (Not Implemented)", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setTimerDuration(long durationMillis, boolean isFocus) {
        timeLeftInMillis = durationMillis;
        currentPhaseTotalDuration = durationMillis; // Store the total duration for this phase
        isFocusPhase = isFocus;
        resetTimerInternals(); // Reset timer state but keep new duration
    }

    private void startTimer() {
        if (timeLeftInMillis <= 0) { // Don't start if no time left
            updateTimerDisplay(); // Ensure display is 00:00
            // Optionally auto-switch to next phase here if desired upon trying to start a finished timer
            return;
        }

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Update every second
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerDisplay();
                updateProgressBar();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                timeLeftInMillis = 0; // Ensure it's exactly 0
                updateTimerDisplay();
                updateProgressBar(); // Show 0% progress or 100% depending on your drawable

                // TODO: Add sound notification or vibration

                // Switch to the next phase
                if (isFocusPhase) {
                    setTimerDuration(SHORT_BREAK_DURATION, false); // Switch to short break
                } else {
                    setTimerDuration(FOCUS_TIME_DURATION_25, true); // Switch back to default focus
                }
                // Optionally auto-start the next phase:
                startTimer();
                // Or update button text to "Start Break" / "Start Focus"
                // startBtn.setText("▶Start");
            }
        }.start();

        timerRunning = true;
        startBtn.setText("|| Pause"); // Or disable, or change icon
        resetBtn.setEnabled(true);
        updatePhaseDisplay();
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
        startBtn.setText("▶Resume");
    }

    private void resetTimer() {
        long resetDuration;
        if (isFocusPhase) {
            // Reset to the beginning of the current focus phase's total duration
            resetDuration = currentPhaseTotalDuration;
        } else {
            // If on a break, reset to a default focus session
            // Or you could store the last focus duration to reset to that
            resetDuration = FOCUS_TIME_DURATION_25;
        }
        setTimerDuration(resetDuration, true); // Resetting usually means going back to a focus phase
    }

    private void resetTimerInternals() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
        updateTimerDisplay();
        updatePhaseDisplay();
        updateProgressBar();
        startBtn.setText("▶Start");
        resetBtn.setEnabled(true);
    }

    private void updateTimerDisplay() {
        long minutes = (timeLeftInMillis / 1000) / 60;
        long seconds = (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerDisplay.setText(timeFormatted);
    }

    private void updatePhaseDisplay() {
        timerPhase.setText(isFocusPhase ? "Focus Time" : "Break Time");
        // You could also change the progress bar color based on the phase here
        // e.g., if (isFocusPhase) { timerCircle.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.circle_progress_pink)); }
        // else { timerCircle.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.circle_progress_green)); }
    }

    private void updateProgressBar() {
        if (currentPhaseTotalDuration > 0) {
            int progress = (int) (((double) timeLeftInMillis / currentPhaseTotalDuration) * 100);
            timerCircle.setProgress(progress);
        } else {
            timerCircle.setProgress(isFocusPhase ? 100 : 0); // Default if total duration is 0 (e.g., before first set)
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Prevent memory leaks
        }
    }

    // --- XML onClick handlers ---
    // It's generally better to remove android:onClick from XML and use setOnClickListener as shown above.
    // If you keep them, ensure these methods are public.

    public void showDashboard(View view) {
        // This is called if you have android:onClick="showDashboard" on the backButton in XML
        Toast.makeText(this, "Back to Dashboard (Not Implemented via XML onClick)", Toast.LENGTH_SHORT).show();
        // Consider removing android:onClick from XML and relying on the listener set in setupOtherButtonListeners()
        // finish();
    }

    // The startTimer, pauseTimer, resetTimer XML onClicks should be removed from your XML
    // as their functionality is now handled by the listeners set in setupTimerButtonClickListeners().

    public void openMusicCard(View view) {
        // This is called if you have android:onClick="openMusicCard" on playMusicBtn in XML
        musicPlayerVisible = !musicPlayerVisible;
        musicPlayerLayout.setVisibility(musicPlayerVisible ? View.VISIBLE : View.GONE);
        // Consider removing android:onClick from XML and relying on the listener set in setupMusicPlayerButtonListeners()
    }

    // --- You had these onClick methods in your XML but they don't have corresponding Java methods yet ---
    // --- It's better to implement them via setOnClickListener as done for other buttons ---
    // public void startTimer(View view) { /* ... */ }
    // public void pauseTimer(View view) { /* ... */ }
    // public void resetTimer(View view) { /* ... */ }
}
