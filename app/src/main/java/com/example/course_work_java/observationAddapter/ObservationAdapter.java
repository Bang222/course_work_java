package com.example.course_work_java.observationAddapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;
import com.example.course_work_java.interfaces.OnItemsClickObservation;
import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationViewHolder> {
    private final OnItemsClickObservation onItemsClickObservation;
    private final List<ObservationEntity> observationList;


    public ObservationAdapter(List<ObservationEntity> observationList, OnItemsClickObservation listener) {
        this.observationList = observationList;
        this.onItemsClickObservation = listener;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_item, parent, false);

        return new ObservationViewHolder(view);
    }
    // duoc lap lai nhieu lan va rang buoc cai noi dung vao tung cai item myViewHolder position la vi tri so vong lap
    // lay hoder tu onCreateViewHolder
    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        ObservationEntity observation = observationList.get(position);
        holder.buttonDetailObservation.setText(observation.title);
        holder.buttonDetailObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClickObservation.onDetailObservationButtonClick(observation);
            }
        });
        holder.buttonDeleteObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClickObservation.onDeleteObservationButtonClick(observation.observationId);
            }
        });
        holder.buttonEditObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClickObservation.onEditObservationButtonClick(observation);
            }
        });

    }

    // cai vong for nay cho cai ham onBindViewHolder like ( for position =0 position < name.length ; position++)
    @Override
    public int getItemCount() {
        return observationList.size();
    }
}
