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

public class WorkoutActivity extends AppCompatActivity {

    private TextView textViewWorkoutTimeRemaining;
    private ProgressBar progressBarWorkout;
    private Button buttonStopWorkout;

    private CountDownTimer countDownTimer;
    private long workoutDuration;
    private long timeLeft;
    private long restDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        // Initialize UI components
        textViewWorkoutTimeRemaining = findViewById(R.id.textViewWorkoutTimeRemaining);
        progressBarWorkout = findViewById(R.id.progressBarWorkout);
        buttonStopWorkout = findViewById(R.id.buttonStopWorkout);
        // Get the workout and rest durations from Intent
        workoutDuration = getIntent().getLongExtra("workoutDuration", 0) * 1000;
        restDuration = getIntent().getLongExtra("restDuration", 0) * 1000;
        // Start the workout timer
        startWorkoutTimer();

        buttonStopWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the countdown timer if it exists and go to the RestActivity
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                goToRestActivity(workoutDuration);
            }
        });
    }

    private void startWorkoutTimer() {
        timeLeft = workoutDuration;
        // Initialize a new countdown timer
        countDownTimer = new CountDownTimer(workoutDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the time left and progress bar on each tick
                timeLeft -= 1000;
                updateProgressBarAndText();
            }

            @Override
            public void onFinish() {
                // Go to the RestActivity when the timer finishes
                goToRestActivity(workoutDuration);
            }
        }.start();
    }

    private void goToRestActivity(long workoutDuration) {
        // Vibrate the device
        vibrateDevice();
        // Create an Intent to start the RestActivity
        Intent intent = new Intent(WorkoutActivity.this, RestActivity.class);
        intent.putExtra("workoutDuration", workoutDuration);
        intent.putExtra("restDuration", restDuration);
        // Start the RestActivity and finish the current activity
        startActivity(intent);
        finish();
    }

    // Method to update the progress bar and time remaining text
    private void updateProgressBarAndText() {
        textViewWorkoutTimeRemaining.setText(String.valueOf(timeLeft / 1000));
        int progress = (int) (((float) timeLeft / workoutDuration) * 100);
        progressBarWorkout.setProgress(progress);
    }

    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }
}
