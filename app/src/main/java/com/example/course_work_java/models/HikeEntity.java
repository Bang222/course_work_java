package com.example.course_work_java.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.Nullable;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "Hike")
public class HikeEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long hikeId;

    public String name;
    public String location;
    public String dateOfTheHike;
    public int lengthOfTheHike;
    public String statusParking;

    public String difficultyLevel;
    @Nullable
    public String description;

}
