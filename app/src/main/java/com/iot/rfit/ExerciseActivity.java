package com.iot.rfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

  public static final String EXTRA_REPLY = "com.example.android.rfit.extra.REPLY";
  ArrayList exercises = new ArrayList<Exercise>();
  private EditText editTextBodypart;
  private EditText editTextWeight;
  private EditText editTextSets;
  private EditText editTextReps;
  private EditText editTextRest;

  private Exercise exercise1;
  private Exercise exercise2;
  private Exercise exercise3;
  private Exercise active;

  private DBHelper db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise);

    db = new DBHelper(this);
    Intent intent = getIntent();
    String selected = intent.getStringExtra(WorkoutActivity.EXTRA_EXERCISE);
    exercises = db.getAllWorkouts();
    exercise1 = (Exercise) exercises.get(0);
    exercise2 = (Exercise) exercises.get(1);
    exercise3 = (Exercise) exercises.get(2);
    editTextBodypart = findViewById(R.id.edittext_bodypart);
    editTextWeight = findViewById(R.id.edittext_weight);
    editTextSets = findViewById(R.id.edittext_sets);
    editTextReps = findViewById(R.id.edittext_reps);
    editTextRest = findViewById(R.id.edittext_rest);


    if (exercise1.id.equals(selected)) {
      active = exercise1;
    } else if (exercise2.id.equals(selected)) {
      active = exercise2;
    } else {
      active = exercise3;
    }

    setTitle(active.exercise);
    editTextBodypart.setText(active.bodypart);
    editTextBodypart.setFocusable(false);
    editTextBodypart.setClickable(false);

    editTextWeight.setText(active.weight);
    editTextWeight.setFocusable(false);
    editTextWeight.setClickable(false);

    editTextSets.setText(active.sets);
    editTextSets.setFocusable(false);
    editTextSets.setClickable(false);

    editTextReps.setText(active.reps);
    editTextReps.setFocusable(false);
    editTextReps.setClickable(false);

    editTextRest.setText(active.rest);
    editTextRest.setFocusable(false);
    editTextRest.setClickable(false);
  }

  public void finishWorkout(View view) {
    db.updateWorkout(Integer.parseInt(active.id),
        active.exercise,
        editTextWeight.getText().toString(),
        active.bodypart,
        editTextSets.getText().toString(),
        editTextReps.getText().toString(),
        editTextRest.getText().toString());
    Intent replyIntent = new Intent();
    replyIntent.putExtra(EXTRA_REPLY, active.exercise);
    setResult(RESULT_OK, replyIntent);
    finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.exercise_menu, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);

    editTextWeight.setEnabled(true);
    editTextWeight.setFocusableInTouchMode(true);
    editTextWeight.setClickable(true);

    editTextSets.setEnabled(true);
    editTextSets.setFocusableInTouchMode(true);
    editTextSets.setClickable(true);

    editTextReps.setEnabled(true);
    editTextReps.setFocusableInTouchMode(true);
    editTextReps.setClickable(true);

    editTextRest.setEnabled(true);
    editTextRest.setFocusableInTouchMode(true);
    editTextRest.setClickable(true);

    return true;
  }
}
