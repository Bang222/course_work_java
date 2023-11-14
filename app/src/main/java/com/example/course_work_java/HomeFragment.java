package com.example.course_work_java;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.hikeAdapter.HikeAdapter;
import com.example.course_work_java.interfaces.OnItemClickListener;
import com.example.course_work_java.models.HikeEntity;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment {
    private AppDatabase appDatabase;
    RecyclerView recyclerViewHike;
    Button buttonResetDatabase;
    View homeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        appDatabase = Room.databaseBuilder(homeView.getContext(), AppDatabase.class, "course_java").allowMainThreadQueries().build();
        recyclerViewHike = homeView.findViewById(R.id.recycler_view_hike);
        buttonResetDatabase = homeView.findViewById(R.id.ButtonResetDatabase);
        RecyclerViewData();
        pressFunction();
        return homeView;
    }

    private void customToast(String message) {
        Toast.makeText(homeView.getContext(),
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }
    private void customIntent(Class<?> activityClass) {
        Intent intent = new Intent(homeView.getContext(),activityClass);
        startActivity(intent);
    }
    private void tranformDataHike(Class<?> activityClass,HikeEntity hike,String key) {
        Intent intent = new Intent(homeView.getContext(),activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, hike);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void pressFunction() {
        buttonResetDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertMakeSureDelete();
            }
        });
    }
    public void displayDeleteAlert (String title) {
        new AlertDialog.Builder(homeView.getContext())
                .setTitle("Notification")
                .setMessage(
                        title
                ).show();
    }
    public void displayDetailAlert(HikeEntity hike) {

        new AlertDialog.Builder(homeView.getContext())
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
    public void displayAlertMakeSureDelete() {
        new AlertDialog.Builder(homeView.getContext())
                .setTitle("Make sure Delete")
//                .setMessage("New hike will be"
//                )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the "Cancel" button click
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllHike();
                        RecyclerViewData();
                        customToast("Reset database Success");
                    }
                })
                .show();
    }
    public void displayAlertObservationAndEditHike (HikeEntity hike) {
        new AlertDialog.Builder(homeView.getContext())
                .setTitle("Activity")
//                .setMessage("New hike will be"
//                )
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the "Cancel" button click
                    }
                })
                .setNeutralButton("Observation", new DialogInterface.OnClickListener() {
                    String key = "hikeDataToObservation";
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tranformDataHike(ObservationActivity.class,hike,key);
                    }
                })
                .setNegativeButton("Edit Hike", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String key = "hikeDataToUpdate";
                        tranformDataHike(UpdateHikeActivity.class,hike,key);
                    }
                })
                .show();
    }
    private void deleteAllHike() {
        appDatabase.observationDao().deleteAllObservations();
        appDatabase.hikeDao().deleteAllHikes();
    }
    private void RecyclerViewData() {
        List<HikeEntity> hikeList = appDatabase.hikeDao().getAllHikes();
        HikeAdapter hikeAdapter = new HikeAdapter(hikeList, new OnItemClickListener() {
            @Override
            public void onDetailButtonClick(HikeEntity hike) {
                displayDetailAlert(hike);
            }
            @Override
            public void onMoreButtonClick(HikeEntity hike) {
                displayAlertObservationAndEditHike(hike);
            }
            @Override
            public void onDeleteButtonClick(long hikeId) {
                appDatabase.hikeWithObservationsDao().deleteObservationsByHikeId(hikeId);
                appDatabase.hikeDao().deleteHikeById(hikeId);
                RecyclerViewDataUpdate();
                displayDeleteAlert("Delete Success");

            }
        });
        recyclerViewHike.setAdapter(hikeAdapter);
        recyclerViewHike.setLayoutManager(new LinearLayoutManager(homeView.getContext()));
    }
    private void RecyclerViewDataUpdate() {
        List<HikeEntity> hikeList = appDatabase.hikeDao().getAllHikes();
        HikeAdapter hikeAdapter = new HikeAdapter(hikeList, new OnItemClickListener() {
            @Override
            public void onDetailButtonClick(HikeEntity hike) {
                displayDetailAlert(hike);
            }

            @Override
            public void onMoreButtonClick(HikeEntity hike) {
                displayAlertObservationAndEditHike(hike);
            }
            @Override
            public void onDeleteButtonClick(long hikeId) {
                appDatabase.hikeDao().deleteHikeById(hikeId);
            }
        });
        recyclerViewHike.setAdapter(hikeAdapter);
        recyclerViewHike.setLayoutManager(new LinearLayoutManager(homeView.getContext()));
    }
}