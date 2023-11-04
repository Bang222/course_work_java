package com.example.course_work_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.models.HikeEntity;

import java.util.Objects;

public class UpdateHikeActivity extends AppCompatActivity {
    EditText updateEditTextName,updateEditTextLocation,dateOfTheHike,updateLengthOfTheHike,updateDescription;
    Spinner updateDifficultlyLevel;
    RadioButton updateRadioButtonYes,updateRadioButtonNo;
    Button backButtonUpdate,updateButton;
    String[] items = {"Easy", "Medium", "Hard"};
    String name, location, description, dateOfTheHikeData, difficultyLevel;
    int highOfLength;
    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        updateEditTextName = findViewById(R.id.UpdateTextName);
        backButtonUpdate = findViewById(R.id.backObservation);
        updateEditTextLocation= findViewById(R.id.UpdateTextLocation);
        dateOfTheHike = findViewById(R.id.UpdateTextDateOfTheHike);
        updateLengthOfTheHike = findViewById(R.id.UpdateTextLengthOfTheHike);
        updateDifficultlyLevel = findViewById(R.id.UpdateTextDifficulyLevel);
        updateDescription = findViewById(R.id.UpdateTextDescription);
        updateRadioButtonYes = findViewById(R.id.UpdateRadioButtonYes);
        updateRadioButtonNo = findViewById(R.id.UpdateRadioButtonNo);
        updateButton = findViewById(R.id.UpdateHike);
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "course_java").allowMainThreadQueries().build();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        updateDifficultlyLevel.setAdapter(dataAdapter);
        HikeEntity hikeData = (HikeEntity) bundle.get("hikeDataToUpdate");
        assert hikeData != null;
        initData(hikeData);
        pressFunction(hikeData);
    }
    private void initData (HikeEntity hikeData) {
        updateEditTextName.setText(hikeData.name);
        updateEditTextLocation.setText(hikeData.location);
        dateOfTheHike.setText(hikeData.dateOfTheHike);
        updateLengthOfTheHike.setText(String.valueOf(hikeData.lengthOfTheHike));
        if ("Yes".equals(String.valueOf(hikeData.statusParking))) {
            updateRadioButtonYes.setChecked(true);
        } if ("No".equals(String.valueOf(hikeData.statusParking))) {
            updateRadioButtonNo.setChecked(true);
        }
        updateDifficultlyLevel.setSelection(positionItems(hikeData.difficultyLevel));
        updateDescription.setText(hikeData.description);
    }
     int positionItems(String difficultyLevel) {

        if("Easy".equals(difficultyLevel)){
            return 0;
        }
        else if("Medium".equals(difficultyLevel)){
            return 1;
        }
        else if("Hard".equals(difficultyLevel)){
            return 2;
        }
         return 0;
     }
    private void customToast(String message) {
        Toast.makeText(this,
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }
    public void validate() {
        if (updateEditTextName.getText().toString().trim().isEmpty()) {
            String messageName = "Name Can Not Null";
            customToast(messageName);
            return;
        }
        if (updateEditTextLocation.getText().toString().trim().isEmpty()) {
            String messageLocation = "Location Can Not Null";
            customToast(messageLocation);
            return;
        }
        if (dateOfTheHike.getText().toString().trim().isEmpty()) {
            String messageDateOfTheHigh = "Date Of The High Can Not Null";
            customToast(messageDateOfTheHigh);
            return;
        }
        if (!updateRadioButtonNo.isChecked() && !updateRadioButtonYes.isChecked()) {
            String messageParking = "Choose Parking";
            customToast(messageParking);
            return;
        }
        if (updateLengthOfTheHike.getText().toString().trim().isEmpty()) {
            String messageLengthOfTheHigh = "Length Of The Hike";
            customToast(messageLengthOfTheHigh);
            return;
        }
    }
    private void newData() {
        name = updateEditTextName.getText().toString();
        location = updateEditTextLocation.getText().toString();
        dateOfTheHikeData = dateOfTheHike.getText().toString();
        difficultyLevel = updateDifficultlyLevel.getSelectedItem().toString();
        highOfLength = Integer.parseInt(updateLengthOfTheHike.getText().toString());
        description = updateDescription.getText().toString();
    }
    private void updateData(HikeEntity hike) {
        newData();
        HikeEntity newHike = new HikeEntity();
        newHike.hikeId = hike.hikeId;
        newHike.name = name;
        newHike.location = location;
        newHike.dateOfTheHike = dateOfTheHikeData;
        newHike.difficultyLevel = difficultyLevel;
        newHike.statusParking = updateRadioButtonYes.isChecked() ? "Yes" : "No";
        newHike.lengthOfTheHike = highOfLength;
        newHike.description = description;
        appDatabase.hikeDao().updateHikeById(newHike);
    }
    private void pressFunction(HikeEntity hike) {
        backButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateHikeActivity.this, MainActivity.class);

                // Add an extra to indicate that you want to show the HomeFragment
                intent.putExtra("showHomeFragment", true);

                // Start the MainActivity and clear any previous activities in the back stack
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                updateData(hike);
                customToast("Update success");
            }
        });
        updateRadioButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRadioButtonYes.setChecked(true);
                updateRadioButtonNo.setChecked(false);
            }
        });
        updateRadioButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRadioButtonYes.setChecked(false);
                updateRadioButtonNo.setChecked(true);
            }
        });
    }
}