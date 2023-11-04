package com.example.course_work_java.SearchAdapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    Button buttonDetailSearchHike;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        buttonDetailSearchHike = itemView.findViewById(R.id.ButtonDetailObservationSearch);

    }
}
