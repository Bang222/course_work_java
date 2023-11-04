package com.example.course_work_java.interfaces;

import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;

public interface OnItemClickListener {
    void onDetailButtonClick(HikeEntity hike);
    void onMoreButtonClick(HikeEntity hike);
    void onDeleteButtonClick(long hikeId);
}
