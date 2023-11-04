package com.example.course_work_java.SearchAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_java.R;
import com.example.course_work_java.interfaces.OnItemClickListener;
import com.example.course_work_java.interfaces.SearchOnClicked;
import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;
import com.example.course_work_java.observationAddapter.ObservationViewHolder;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
    private SearchOnClicked onItemClickListener;
    private List<HikeEntity> hikeList;



    public SearchAdapter(List<HikeEntity> hikeList,SearchOnClicked onItemClickListener) {
        this.hikeList = hikeList;
        this.onItemClickListener = onItemClickListener;
    }
    public void updateData(List<HikeEntity> dataUpdate) {
        this.hikeList = dataUpdate;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        HikeEntity hike = hikeList.get(position);
        holder.buttonDetailSearchHike.setText(hike.name);
        System.out.println("data" + hike.name);

        holder.buttonDetailSearchHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDetailHikeButtonClick(hike);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }
}
