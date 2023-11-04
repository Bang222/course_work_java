package com.example.course_work_java.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Observation")
public class ObservationEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long observationId;
    public long hikeIdCreated;
    public String title;

    public String timeObservation;

    @Nullable
    public String comment;

}
