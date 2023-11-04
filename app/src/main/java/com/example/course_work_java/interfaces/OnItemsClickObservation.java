package com.example.course_work_java.interfaces;

import com.example.course_work_java.models.ObservationEntity;

public interface OnItemsClickObservation {
    void onDetailObservationButtonClick(ObservationEntity observation);
    void onEditObservationButtonClick(ObservationEntity observation);
    void onDeleteObservationButtonClick(long observationId);
}
