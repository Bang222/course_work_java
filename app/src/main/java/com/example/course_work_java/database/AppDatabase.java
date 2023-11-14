package com.example.course_work_java.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.course_work_java.daos.HikeDao;
import com.example.course_work_java.daos.HikeWithObservationsDao;
import com.example.course_work_java.daos.ObservationDao;
import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;

@Database(entities = { HikeEntity.class, ObservationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
    public abstract ObservationDao observationDao();
    public abstract HikeWithObservationsDao hikeWithObservationsDao();


}
