package com.example.workouttimerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RestActivity extends AppCompatActivity {

    private TextView textViewRestTimeRemaining;
    private ProgressBar progressBarRest;
    private Button buttonStopRest;
    private CountDownTimer countDownTimer;
    private long restDuration;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        // Initialize UI components
        textViewRestTimeRemaining = findViewById(R.id.textViewRestTimeRemaining);
        progressBarRest = findViewById(R.id.progressBarRest);
        buttonStopRest = findViewById(R.id.buttonStopRest);
        // Get the rest duration from Intent
        restDuration = getIntent().getLongExtra("restDuration", 0);
        // Start the rest timer
        startRestTimer();

        buttonStopRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the countdown timer if it exists and go to the MainActivity
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                goToMainActivity();
            }
        });
    }

    private void startRestTimer() {
        timeLeft = restDuration;
        // Initialize a new countdown timer
        countDownTimer = new CountDownTimer(restDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the time left and progress bar on each tick
                timeLeft -= 1000;
                updateProgressBarAndText();
            }

            @Override
            public void onFinish() {
                // Go to the MainActivity when the timer finishes
                goToMainActivity();
            }
        }.start();
    }

    private void goToMainActivity() {
        // Vibrate the device
        vibrateDevice();
        // Create an Intent to start the MainActivity
        Intent intent = new Intent(RestActivity.this, MainActivity.class);
        // Start the MainActivity and finish the current activity
        startActivity(intent);
        finish();
    }
    // Method to update the progress bar and time remaining text
    private void updateProgressBarAndText() {
        textViewRestTimeRemaining.setText(String.valueOf(timeLeft / 1000));
        int progress = (int) (((float) timeLeft / restDuration) * 100);
        progressBarRest.setProgress(progress);
    }

    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }
}
