package com.example.course_work_java.hikeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;
import com.example.course_work_java.interfaces.OnItemClickListener;
import com.example.course_work_java.models.HikeEntity;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeViewHolder> {
    private OnItemClickListener onItemClickListener;
    private final List<HikeEntity> hikeList;


    public HikeAdapter(List<HikeEntity> hikeList,OnItemClickListener listener) {
        this.hikeList = hikeList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_item, parent, false);

        return new HikeViewHolder(view);
    }
    // duoc lap lai nhieu lan va rang buoc cai noi dung vao tung cai item myViewHolder position la vi tri so vong lap
    // lay hoder tu onCreateViewHolder
    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        HikeEntity hike = hikeList.get(position);
        holder.buttonDetailHike.setText(hike.name);
        holder.buttonDetailHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDetailButtonClick(hike);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteButtonClick(hike.hikeId);
            }
        });
        holder.buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onMoreButtonClick(hike);
            }
        });
    }

    // cai vong for nay cho cai ham onBindViewHolder like ( for position =0 position < name.length ; position++)
    @Override
    public int getItemCount() {
        return hikeList.size();
    }
}
