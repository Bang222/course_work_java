package com.example.course_work_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.hikeAdapter.HikeAdapter;
import com.example.course_work_java.interfaces.OnItemClickListener;
import com.example.course_work_java.interfaces.OnItemsClickObservation;
import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;
import com.example.course_work_java.observationAddapter.ObservationAdapter;

import java.util.List;

public class ObservationActivity extends AppCompatActivity {
    Button backObservation, addObservation;
    AppDatabase appDatabase;
    RecyclerView recycleViewObservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_observation);
        backObservation = findViewById(R.id.backObservation);
        addObservation = findViewById(R.id.addNewObservationButton);
        recycleViewObservation = findViewById(R.id.recycler_view_observations);
        assert bundle != null;
        HikeEntity hikeData = (HikeEntity) bundle.get("hikeDataToObservation");
        assert hikeData != null;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "course_java").allowMainThreadQueries().build();
        setRecycleViewData(hikeData);
        pressFunction(hikeData);
    }

    private void customToast(String message) {
        Toast.makeText(this,
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }

    private void DetailObservation(ObservationEntity observation) {
        new AlertDialog.Builder(this)
                .setTitle("Detail Hike")
                .setMessage(
                        "Title: " + observation.title + "\n" +
                        "Time : " + observation.timeObservation + "\n" +
                        "Comment: " + observation.comment + "\n"
                )
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void setRecycleViewData(HikeEntity hikeData) {
        List<ObservationEntity> getObservationByHikeId = appDatabase.observationDao().getObservationsForHike(hikeData.hikeId);
        ObservationAdapter observationAdapter = new ObservationAdapter(getObservationByHikeId, new OnItemsClickObservation() {
            @Override
            public void onDetailObservationButtonClick(ObservationEntity observation) {
                DetailObservation(observation);
            }

            @Override
            public void onEditObservationButtonClick(ObservationEntity observation) {
                String key = "observationDataToUpdate";
                tranformDataObservation(EditObservation.class,observation,key);
            }
            @Override
            public void onDeleteObservationButtonClick(long observationId) {
                appDatabase.observationDao().deleteObservationById(observationId);
                customToast("DeleteSuccess");
                UpdateRecycleViewData(hikeData);
            }
        });
        recycleViewObservation.setAdapter(observationAdapter);
        recycleViewObservation.setLayoutManager(new LinearLayoutManager(this));
    }

    private void UpdateRecycleViewData(HikeEntity hikeData) {
        List<ObservationEntity> getObservationByHikeId = appDatabase.observationDao().getObservationsForHike(hikeData.hikeId);
        ObservationAdapter observationAdapter = new ObservationAdapter(getObservationByHikeId, new OnItemsClickObservation() {
            @Override
            public void onDetailObservationButtonClick(ObservationEntity observation) {
                DetailObservation(observation);
            }

            @Override
            public void onEditObservationButtonClick(ObservationEntity observation) {
                String key = "observationDataToUpdate";
                tranformDataObservation(EditObservation.class,observation,key);
            }
            @Override
            public void onDeleteObservationButtonClick(long observationId) {
                appDatabase.observationDao().deleteObservationById(observationId);
                customToast("DeleteSuccess");
                UpdateRecycleViewData(hikeData);
            }
        });
        recycleViewObservation.setAdapter(observationAdapter);
        recycleViewObservation.setLayoutManager(new LinearLayoutManager(this));
    }

    private void tranformDataHike(Class<?> activityClass, HikeEntity hike, String key) {
        Intent intent = new Intent(this, activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, hike);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void customIntent(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
    private void tranformDataObservation(Class<?> activityClass,ObservationEntity observation,String key) {
        Intent intent = new Intent(this,activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, observation);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void pressFunction(HikeEntity hike) {
        backObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObservationActivity.this, MainActivity.class);

                // Add an extra to indicate that you want to show the HomeFragment
                intent.putExtra("showHomeFragment", true);

                // Start the MainActivity and clear any previous activities in the back stack
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        addObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "passHikeDataToAddObservation";
                tranformDataHike(AddObservation.class, hike, key);
            }
        });
    }
}