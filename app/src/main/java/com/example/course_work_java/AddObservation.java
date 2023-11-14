package com.example.course_work_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.models.HikeEntity;
import com.example.course_work_java.models.ObservationEntity;

import java.util.Calendar;

public class AddObservation extends AppCompatActivity {
    Button buttonBackToListObservation,buttonAddObservation;
    EditText observation,timeObservation,comments;
    AppDatabase appDatabase;
    String observationData,commentsData,timeObservationData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        Bundle bundle = getIntent().getExtras();
//        if(bundle == null){
//            return;
//        }
        buttonBackToListObservation = findViewById(R.id.backObservation);
        buttonAddObservation = findViewById(R.id.AddNewObservationButton);
        observation = findViewById(R.id.editTextObservation);
        timeObservation = findViewById(R.id.editTextTimeOfObservation);
        comments= findViewById(R.id.AddCommentObservation);
        assert bundle != null;
        HikeEntity hikeData = (HikeEntity) bundle.get("passHikeDataToAddObservation");
        assert hikeData != null;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "course_java").allowMainThreadQueries().build();
        pressFunction(hikeData);
    }
    private void selectDateTimePickerDialog(EditText dateTimePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Handle the date selection
                        final int year = selectedYear;
                        final int month = selectedMonth;
                        final int day = selectedDayOfMonth;

                        // Create a TimePickerDialog for selecting time
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddObservation.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Handle the time selection
                                        String selectedDateTime = day + "/" + (month + 1) + "/" + year + " " + hourOfDay + ":" + minute;
                                        dateTimePicker.setText(selectedDateTime);
                                    }
                                }, hour, minute, true);

                        timePickerDialog.show();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    private void pressFunction(HikeEntity hike) {
        buttonAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(observation.getText().toString().isEmpty()){
                    customToast("can not null observation");
                    return;
                }
                else if(timeObservation.getText().toString().isEmpty()){
                    customToast("can not null time Observation");
                    return;
                }
                else {
                    submit(hike);
                    customToast("Add Observation Success");
                    resetForm();
                }
            }
        });
        timeObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateTimePickerDialog(timeObservation);
            }
        });
        buttonBackToListObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "hikeDataToObservation";
                tranformDataHike(ObservationActivity.class,hike,key);
            }
        });
    }
    private void tranformDataHike(Class<?> activityClass,HikeEntity hike,String key) {
        Intent intent = new Intent(this,activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, hike);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void customIntent(Class<?> activityClass) {
        Intent intent = new Intent(AddObservation.this,activityClass);
        startActivity(intent);
    }
    private void customToast(String message) {
        Toast.makeText(this,
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }
    private void submit(HikeEntity hike) {
        observationData = observation.getText().toString();
        timeObservationData = timeObservation.getText().toString();
        commentsData = comments.getText().toString();
        ObservationEntity observation = new ObservationEntity();
        observation.hikeIdCreated =hike.hikeId;
        observation.title = observationData;
        observation.timeObservation =timeObservationData;
        observation.comment=commentsData;
        long observationId = appDatabase.observationDao().insertObservation(observation);
    }
    private void resetForm() {
        observation.setText("");
        timeObservation.setText("");
        comments.setText("");
    }
}