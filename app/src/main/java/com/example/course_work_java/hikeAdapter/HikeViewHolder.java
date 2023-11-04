package com.example.course_work_java.hikeAdapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;

public class HikeViewHolder extends RecyclerView.ViewHolder {
    Button buttonDetailHike,buttonMore,buttonDelete;
    public HikeViewHolder(@NonNull View itemView) {
        super(itemView);
        buttonDetailHike = itemView.findViewById(R.id.ButtonDetailHike);
        buttonMore = itemView.findViewById(R.id.ButtonMore);
        buttonDelete = itemView.findViewById(R.id.ButtonDelete);
    }
}
