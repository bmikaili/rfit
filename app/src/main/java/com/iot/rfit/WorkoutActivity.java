package com.iot.rfit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

  ArrayList exercises = new ArrayList<Exercise>();
  public static final String EXTRA_EXERCISE = "com.example.android.rfit.extra.EXERCISE";
  public static final int EXERCISE_REQUEST = 1;
  private TextView textViewWorkout1;
  private TextView textViewWorkout2;
  private TextView textViewWorkout3;
  private Exercise exercise1;
  private Exercise exercise2;
  private Exercise exercise3;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workout);

    textViewWorkout1 = findViewById(R.id.textview_workout1);
    textViewWorkout2 = findViewById(R.id.textview_workout2);
    textViewWorkout3 = findViewById(R.id.textview_workout3);

    DBHelper db = new DBHelper(this);
    Intent intent = getIntent();
    String workout = intent.getStringExtra(MainActivity.EXTRA_WORKOUT);
    setTitle(workout);
    exercises = db.getAllWorkouts();
    exercise1 = (Exercise) exercises.get(0);
    exercise2 = (Exercise) exercises.get(1);
    exercise3 = (Exercise) exercises.get(2);

    textViewWorkout1.setText(exercise1.exercise);
    textViewWorkout2.setText(exercise2.exercise);
    textViewWorkout3.setText(exercise3.exercise);

    // Die 3 Klick listener durch ein Asynctask ersetzen, welches auf RFID wartet
    textViewWorkout1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent2 = new Intent(view.getContext(), ExerciseActivity.class);
        intent2.putExtra(EXTRA_EXERCISE, exercise1.id);
        startActivityForResult(intent2, EXERCISE_REQUEST);
      }
    });

    textViewWorkout2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent2 = new Intent(view.getContext(), ExerciseActivity.class);
        intent2.putExtra(EXTRA_EXERCISE, exercise2.id);
        startActivityForResult(intent2, EXERCISE_REQUEST);
      }
    });

    textViewWorkout3.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent2 = new Intent(view.getContext(), ExerciseActivity.class);
        intent2.putExtra(EXTRA_EXERCISE, exercise3.id);
        startActivityForResult(intent2, EXERCISE_REQUEST);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (textViewWorkout1.getVisibility() == View.INVISIBLE
        && textViewWorkout2.getVisibility() == View.INVISIBLE
        && textViewWorkout3.getVisibility() == View.INVISIBLE) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("Workout Done!")
          .setCancelable(false)
          .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
              startActivity(intent);
            }
          });
      AlertDialog alert = builder.create();
      alert.show();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == EXERCISE_REQUEST) {
      if (resultCode == RESULT_OK) {
        String reply = data.getStringExtra(ExerciseActivity.EXTRA_REPLY);
        if (reply.equals(exercise1.exercise)) {
          textViewWorkout1.setVisibility(View.INVISIBLE);
        } else if (reply.equals(exercise2.exercise)) {
          textViewWorkout2.setVisibility(View.INVISIBLE);
        } else {
          textViewWorkout3.setVisibility(View.INVISIBLE);
        }
      }
    }

  }
}
