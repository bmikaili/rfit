package com.iot.rfit;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  public static final String EXTRA_WORKOUT = "com.example.android.rfit.extra.WORKOUT";

  @Override
  //Test
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().hide();

    DBHelper db = new DBHelper(this);
    db.insertWorkout(0, "60kg", "Bench Press", "Chest", "3", "10", "3");
    db.insertWorkout(1, "30kg", "Dumbbell Curl", "Bicep", "3", "15", "2");
    db.insertWorkout(2, "50kg", "Tricep Extension", "Tricep", "3", "15", "2");
  }

  public void startWorkout(View view) {
    Intent intent = new Intent(this, WorkoutActivity.class);
    intent.putExtra(EXTRA_WORKOUT, ((Button) view).getText().toString());
    startActivity(intent);
  }
}
