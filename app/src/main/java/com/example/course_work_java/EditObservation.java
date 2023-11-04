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

public class EditObservation extends AppCompatActivity {
    AppDatabase appDatabase;
    Button buttonBackToListObservation,buttonAddObservation;
    EditText observation,timeObservation,comments;
    String observationUpdateData,commentsUpdateData,timeObservationUpdateData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);
        buttonBackToListObservation = findViewById(R.id.backEditObservation);
        buttonAddObservation= findViewById(R.id.UpdateAddNewObservationButton);
        observation = findViewById(R.id.UpdateEditTextObservation);
        timeObservation = findViewById(R.id.UpdateEditTextTimeOfObservation);
        comments = findViewById(R.id.UpdateAddCommentObservation);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        ObservationEntity observationData = (ObservationEntity) bundle.get("observationDataToUpdate");
        assert observationData != null;
        observation.setText(observationData.title);
        timeObservation.setText(observationData.timeObservation);
        comments.setText(observationData.comment);
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "course_java").allowMainThreadQueries().build();
        HikeEntity hike = appDatabase.hikeDao().getHikeById(observationData.hikeIdCreated);
        pressFunction(hike,observationData);
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
                        TimePickerDialog timePickerDialog = new TimePickerDialog(EditObservation.this,
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
    private void pressFunction(HikeEntity hike,ObservationEntity observationData) {
        buttonAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                submit(observationData);
                customToast("Update Observation Success");
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
//                Intent intent = new Intent(EditObservation.this, ObservationActivity.class);
//                startActivity(intent);
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
    private void submit(ObservationEntity observationData) {
        observationUpdateData = observation.getText().toString();
        timeObservationUpdateData = timeObservation.getText().toString();
        commentsUpdateData = comments.getText().toString();
        ObservationEntity observation = new ObservationEntity();
        observation.observationId = observationData.observationId;
        observation.hikeIdCreated =observationData.hikeIdCreated;
        observation.title = observationUpdateData;
        observation.timeObservation =timeObservationUpdateData;
        observation.comment=commentsUpdateData;
        appDatabase.observationDao().updateObservation(observation);
    }
    private void customToast(String message) {
        Toast.makeText(this,
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }
    private void validate() {
        if(observation.getText().toString().isEmpty()){
            customToast("can not null observation");
        }
        if(timeObservation.getText().toString().isEmpty()){
            customToast("can not null time Observation");
        }
    }
}