package edu.ewubd.studychill;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BreathCircle extends AppCompatActivity {

    private Button backButton;
    private Button logoutButton;
    private FrameLayout circleContainer;
    private View viewCircleOuter;
    private View viewCircleInner;
    private TextView txtBreathe;
    private Button btnStartExercise;

    private boolean isAnimating = false;
    private final long animationDuration = 3000L; // 3 seconds for inhale/exhale
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable breathRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_circle);

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        circleContainer = findViewById(R.id.circleContainer);
        viewCircleOuter = findViewById(R.id.viewCircleOuter);
        viewCircleInner = findViewById(R.id.viewCircleInner);
        txtBreathe = findViewById(R.id.txtBreathe);
        btnStartExercise = findViewById(R.id.btnStartExercise);

        // --- Button Click Listeners ---
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back - you might want to use NavController or finish()
                finish(); // Simple way to go back to the previous activity
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logout logic here
                // For example, navigate to a LoginActivity and clear user session
                // Intent intent = new Intent(BreathCircle.this, LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // startActivity(intent);
                // finish();
                // For now, let's just finish the activity
                finish();
            }
        });

        btnStartExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    stopBreathingAnimation();
                } else {
                    startBreathingAnimation();
                }
            }
        });

        circleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    stopBreathingAnimation();
                } else {
                    startBreathingAnimation();
                }
            }
        });

        // --- Breathing Animation Runnable ---
        breathRunnable = new Runnable() {
            private boolean isBreathingIn = true;

            @Override
            public void run() {
                if (!isAnimating) return;

                if (isBreathingIn) {
                    txtBreathe.setText("Breathe Out...");
                    animateCircles(1.0f, 0.7f); // Scale down
                } else {
                    txtBreathe.setText("Breathe In...");
                    animateCircles(0.7f, 1.0f); // Scale up
                }
                isBreathingIn = !isBreathingIn;
                handler.postDelayed(this, animationDuration + 500); // Add a slight pause
            }
        };
    }

    private void startBreathingAnimation() {
        isAnimating = true;
        btnStartExercise.setText("Stop Exercise");
        txtBreathe.setText("Breathe In...");
        // Initial animation state before the runnable loop starts
        animateCircles(0.7f, 1.0f); // Start by breathing in (expanding)
        handler.postDelayed(breathRunnable, animationDuration + 500);
    }

    private void stopBreathingAnimation() {
        isAnimating = false;
        handler.removeCallbacks(breathRunnable);
        btnStartExercise.setText("Start Exercise");
        txtBreathe.setText("Breathe In...");
        // Reset circles to a default state (optional)
        viewCircleInner.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        viewCircleOuter.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
    }

    private void animateCircles(float startScale, float endScale) {
        // Inner Circle Animation
        ObjectAnimator innerScaleX = ObjectAnimator.ofFloat(viewCircleInner, "scaleX", startScale, endScale);
        ObjectAnimator innerScaleY = ObjectAnimator.ofFloat(viewCircleInner, "scaleY", startScale, endScale);

        // Outer Circle Animation
        ObjectAnimator outerScaleX = ObjectAnimator.ofFloat(viewCircleOuter, "scaleX", startScale, endScale);
        ObjectAnimator outerScaleY = ObjectAnimator.ofFloat(viewCircleOuter, "scaleY", startScale, endScale);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(innerScaleX, innerScaleY, outerScaleX, outerScaleY);
        animatorSet.setDuration(animationDuration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop animation and remove callbacks to prevent memory leaks
        if (isAnimating) {
            stopBreathingAnimation();
        }
    }
}
