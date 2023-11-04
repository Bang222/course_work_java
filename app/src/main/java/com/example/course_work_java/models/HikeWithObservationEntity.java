package com.example.course_work_java.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HikeWithObservationEntity {
    @Embedded
    public HikeEntity hike;
    @Relation(parentColumn = "hikeId", entityColumn = "hikeIdCreated")
    public List<ObservationEntity> observationList;
}
