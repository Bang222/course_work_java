package com.example.course_work_java.observationAddapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;

public class ObservationViewHolder extends RecyclerView.ViewHolder {
    Button buttonDetailObservation,buttonEditObservation,buttonDeleteObservation;
    public ObservationViewHolder(@NonNull View itemView) {
        super(itemView);
        buttonDetailObservation = itemView.findViewById(R.id.ButtonDetailObservation);
        buttonEditObservation = itemView.findViewById(R.id.ButtonEditObservation);
        buttonDeleteObservation = itemView.findViewById(R.id.ButtonDeleteObservation);
    }
}
