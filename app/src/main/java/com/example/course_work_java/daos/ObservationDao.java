package com.example.course_work_java.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;

import java.util.List;

@Dao
public interface ObservationDao {
    @Insert
    long insertObservation(ObservationEntity observation);
    @Query("SELECT * FROM Observation WHERE hikeIdCreated = :hikeId")
    List<ObservationEntity> getObservationsForHike(long hikeId);
    @Update
    void updateObservation(ObservationEntity updatedObservation);
    @Query("DELETE FROM Observation")
    void deleteAllObservations();
    @Query("DELETE FROM Observation WHERE observationId = :observationId")
    void deleteObservationById(long observationId);
}
