package com.iot.rfit;

public class Exercise {

  public final String id;
  public final String weight;
  public final String exercise;
  public final String bodypart;
  public final String sets;
  public final String reps;
  public final String rest;

  public Exercise(String id, String weight, String exercise, String bodypart, String sets, String reps, String rest) {
    this.id = id;
    this.weight = weight;
    this.exercise = exercise;
    this.bodypart = bodypart;
    this.sets = sets;
    this.reps = reps;
    this.rest = rest;
  }
}
