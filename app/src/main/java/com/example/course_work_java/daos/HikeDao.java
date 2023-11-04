package com.example.course_work_java.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_work_java.models.HikeEntity;

import java.util.List;
@Dao
public interface HikeDao {
    @Insert
    long saveHike(HikeEntity hike);
    @Query("SELECT * FROM hike")
    List<HikeEntity> getAllHikes();
    @Query("SELECT * FROM Hike WHERE hikeId = :hikeId")
    HikeEntity getHikeById(long hikeId);
    @Update
    void updateHikeById(HikeEntity updatedHike);
    @Query("SELECT * FROM Hike WHERE name LIKE '%' || :name || '%'")
    List<HikeEntity> searchHikeByName(String name);
    @Query("DELETE FROM Hike")
    void deleteAllHikes();
    @Query("DELETE FROM Hike WHERE hikeId = :hikeId")
    void deleteHikeById(long hikeId);
}
