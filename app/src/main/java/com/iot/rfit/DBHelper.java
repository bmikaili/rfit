package com.iot.rfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "WorkoutDB.db";
  public static final String WORKOUTS_TABLE_NAME = "workouts";
  public static final String WORKOUTS_COLUMN_ID = "id";
  public static final String WORKOUTS_COLUMN_WEIGHT = "weight";
  public static final String WORKOUTS_COLUMN_EXERCISE = "exercise";
  public static final String WORKOUTS_COLUMN_BODYPART = "bodypart";
  public static final String WORKOUTS_COLUMN_SETS = "sets";
  public static final String WORKOUTS_COLUMN_REPS = "reps";
  public static final String WORKOUTS_COLUMN_REST = "rest";
  private HashMap hp;

  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(
        "create table workouts" +
            "(id integer primary key, exercise text, weight text, bodypart text, sets text, reps text, rest text)"
    );
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS workouts");
    onCreate(db);
  }

  public boolean insertWorkout (Integer id, String weight, String exercise, String bodypart, String sets, String reps, String rest) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("id", id);
    contentValues.put("bodypart", bodypart);
    contentValues.put("exercise", exercise);
    contentValues.put("weight", weight);
    contentValues.put("sets", sets);
    contentValues.put("reps", reps);
    contentValues.put("rest", rest);
    db.insert("workouts", null, contentValues);
    return true;
  }

  public Cursor getData(Integer id) {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("select * from workouts where id="+id+"", null);
  }

  public int numberOfRows() {
    SQLiteDatabase db = this.getReadableDatabase();
    return (int) DatabaseUtils.queryNumEntries(db, WORKOUTS_TABLE_NAME);
  }

  public boolean updateWorkout (Integer id, String exercise, String weight, String bodypart, String sets, String reps, String rest) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("id", id);
    contentValues.put("exercise", exercise);
    contentValues.put("weight", weight);
    contentValues.put("bodypart", bodypart);
    contentValues.put("sets", sets);
    contentValues.put("reps", reps);
    contentValues.put("rest", rest);
    db.update("workouts", contentValues, "id = ? ", new String[] { Integer.toString(id) });
    return true;
  }

  public Integer deleteWorkout (Integer id) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(
        "workouts",
        "id = ? ",
        new String[] { Integer.toString(id) });
  }

  public ArrayList<Exercise> getAllWorkouts() {
    ArrayList<Exercise> arrayList = new ArrayList<>();

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor res = db.rawQuery("select * from workouts", null);
    res.moveToFirst();

    while (!res.isAfterLast()) {
      Exercise tmp = new Exercise(res.getString(res.getColumnIndex(WORKOUTS_COLUMN_ID)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_WEIGHT)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_EXERCISE)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_BODYPART)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_SETS)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_REPS)),
          res.getString(res.getColumnIndex(WORKOUTS_COLUMN_REST)));
      arrayList.add(tmp);
      res.moveToNext();
    }

    return arrayList;
  }
}
