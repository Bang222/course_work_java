package com.example.course_work_java.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.course_work_java.models.HikeWithObservationEntity;

import java.util.List;

@Dao
public interface HikeWithObservationsDao {
    @Transaction
    @Query("SELECT * FROM Hike")
    public List<HikeWithObservationEntity> getHikeWithObservations();

    @Query("DELETE FROM Observation WHERE hikeIdCreated = :hikeId")
    void deleteObservationsByHikeId(long hikeId);
}
