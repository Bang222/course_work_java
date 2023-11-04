package com.example.course_work_java;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.course_work_java.SearchAdapter.SearchAdapter;
import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.interfaces.SearchOnClicked;
import com.example.course_work_java.models.HikeEntity;

import java.util.List;

public class SearchFragment extends Fragment {
    View viewSearch;
    AppDatabase appDatabase;
    RecyclerView recyclerViewSearch;
    EditText searchData;
    Button buttonSearchHike;
    String data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewSearch =  inflater.inflate(R.layout.fragment_search, container, false);
        recyclerViewSearch = viewSearch.findViewById(R.id.recycler_view_hike_Search);
        searchData = viewSearch.findViewById(R.id.editTextObservationSearch);
        buttonSearchHike = viewSearch.findViewById(R.id.ButtonSearchHike);
        appDatabase = Room.databaseBuilder(viewSearch.getContext(), AppDatabase.class, "course_java").allowMainThreadQueries().build();
        data = searchData.getText().toString();
        List<HikeEntity> allHikes = appDatabase.hikeDao().getAllHikes();
        SearchAdapter searchAdapter = new SearchAdapter(allHikes,new SearchOnClicked() {
            @Override
            public void onDetailHikeButtonClick(HikeEntity hike) {
                displayDetailAlert(hike);
            }
        });
        buttonSearchHike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                data = searchData.getText().toString();
                List<HikeEntity> hikeSearch = appDatabase.hikeDao().searchHikeByName(data);
                searchAdapter.updateData(hikeSearch);
                searchAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewSearch.setAdapter(searchAdapter);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(viewSearch.getContext()));
        return viewSearch;
    }
//    public void PressFunction(){
//        buttonSearchHike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                data = searchData.getText().toString();
//                List<HikeEntity> hikeSearch = (List<HikeEntity>) appDatabase.hikeDao().searchHikeByName("bang");
//                searchAdapter.set(hikeSearch);
//                searchAdapter.notifyDataSetChanged();
//            }
//        });
//    }
    public void displayDetailAlert(HikeEntity hike) {

        new AlertDialog.Builder(viewSearch.getContext())
                .setTitle("Detail Hike")
                .setMessage(
                        "Name: " + hike.name + "\n" +
                                "Location: " + hike.location + "\n" +
                                "Date Of Hike: " + hike.dateOfTheHike + "\n" +
                                "Parking available: " + hike.statusParking + "\n" +
                                "Length Of the hike: " + hike.lengthOfTheHike + "\n" +
                                "Difficultly Level: " + hike.difficultyLevel + "\n"+
                                "Description:" + "\n" + hike.description
                )
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}