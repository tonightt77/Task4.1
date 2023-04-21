package com.example.workouttimerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWorkoutDuration;
    private EditText editTextRestDuration;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextWorkoutDuration = findViewById(R.id.editTextWorkoutDuration);
        editTextRestDuration = findViewById(R.id.editTextRestDuration);
        buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the WorkoutActivity when the start button is clicked
                startWorkoutActivity();
            }
        });
    }

    private void startWorkoutActivity() {
        // Create an Intent to start the WorkoutActivity
        Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
        // Get the workout and rest durations in seconds
        long workoutDuration = getDurationInSeconds(editTextWorkoutDuration);
        long restDuration = getDurationInSeconds(editTextRestDuration);
        // Pass the workout and rest durations to the WorkoutActivity
        intent.putExtra("workoutDuration", workoutDuration);
        intent.putExtra("restDuration", restDuration);

        startActivity(intent);
    }

    //get the duration in seconds from an EditText
    private long getDurationInSeconds(EditText editText) {
        // Get the text from the EditText
        String input = editText.getText().toString();
        // If the input is not empty, parse it to long and return it
        if (!input.isEmpty()) {
            return Long.parseLong(input);
        }
        // If the input is empty, return 0
        return 0;
    }
}
