package com.example.course_work_java;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.course_work_java.database.AppDatabase;
import com.example.course_work_java.models.HikeEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddFragment extends Fragment {

    MainActivity addMainActivity;
    RadioButton radioButtonYes, radioButtonNo;
    Button addHikeButton;
    View addView;
    EditText setName, setLocation, setDateOfTheHike, setHigh, setDescription;
    Spinner setDifficulty;

    String name, location, description, dateOfTheHike, difficultyLevel;
    int highOfLength;
    String[] items = {"Easy", "Medium", "Hard"};
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Reference to file fragment_add
        addView = inflater.inflate(R.layout.fragment_add, container, false);
        addMainActivity = (MainActivity) getActivity();
        radioButtonYes = addView.findViewById(R.id.radioButtonYes);
        radioButtonNo = addView.findViewById(R.id.radioButtonNo);
        addHikeButton = addView.findViewById(R.id.addHike);
        setName = addView.findViewById(R.id.editTextName);
        setLocation = addView.findViewById(R.id.editTextLocation);
        setDateOfTheHike = addView.findViewById(R.id.editTextDateOfTheHike);
        setHigh = addView.findViewById(R.id.editTextLengthOfTheHike);
        setDifficulty = addView.findViewById(R.id.editTextDifficulyLevel);
        setDescription = addView.findViewById(R.id.editTextDescription);
        appDatabase = Room.databaseBuilder(addView.getContext(), AppDatabase.class, "course_java").allowMainThreadQueries().build();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(addView.getContext(),
                android.R.layout.simple_spinner_item, items);
        setDifficulty.setAdapter(dataAdapter);
        pressFunction();
        return addView;
    }

    private void pressFunction() {
        radioButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonYes.setChecked(true);
                radioButtonNo.setChecked(false);
            }
        });
        radioButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonYes.setChecked(false);
                radioButtonNo.setChecked(true);
            }
        });
        setDateOfTheHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDatePickerDialog(setDateOfTheHike);
            }
        });
        addHikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void confirmButton() {
        name = setName.getText().toString();
        location = setLocation.getText().toString();
        dateOfTheHike = setDateOfTheHike.getText().toString();
        difficultyLevel = setDifficulty.getSelectedItem().toString();
        highOfLength = Integer.parseInt(setHigh.getText().toString());
        description = setDescription.getText().toString();
        displayNextAlert();
    }

    private void saveData() {
        HikeEntity newHike = new HikeEntity();
        newHike.name = name;
        newHike.location = location;
        newHike.dateOfTheHike = dateOfTheHike;
        newHike.difficultyLevel = difficultyLevel;
        newHike.statusParking = radioButtonYes.isChecked() ? "Yes" : "No";
        newHike.lengthOfTheHike = highOfLength;
        newHike.description = description;
        long hikeId = appDatabase.hikeDao().saveHike(newHike);
    }
    private void customToast(String message) {
        Toast.makeText(addView.getContext(),
                        message,
                        Toast.LENGTH_SHORT)
                .show();
    }
    public void displayNextAlert() {
        String parkingStatus = radioButtonYes.isChecked() ? "yes" : " No";
        new AlertDialog.Builder(addView.getContext())
                .setTitle("Confirmation")
                .setMessage("New hike will be added: \n" +
                        "Name: " + name + "\n" +
                        "Location: " + location + "\n" +
                        "Date Of Hike: " + dateOfTheHike + "\n" +
                        "Parking available: " + parkingStatus + "\n" +
                        "Length Of the hike: " + highOfLength + "\n" +
                        "Difficultly Level: " + difficultyLevel + "\n"
                )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the "Cancel" button click
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveData();
                        resetForm();
                        customToast("success save new hike");
                    }
                })
                .show();
    }

    /////////
    private void selectDatePickerDialog(EditText datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(addView.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Handle the date selection
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        datePicker.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public void validate() {
        if (setName.getText().toString().trim().isEmpty()) {
            String messageName = "Name Can Not Null";
            customToast(messageName);
            return;
        }
        if (setDateOfTheHike.getText().toString().trim().isEmpty()) {
            String messageDateOfTheHigh = "Date Of The High Can Not Null";
            customToast(messageDateOfTheHigh);
            return;
        }
        if (setLocation.getText().toString().trim().isEmpty()) {
            String messageDateOfTheHigh = "Date Of The High Can Not Null";
            customToast(messageDateOfTheHigh);
            return;
        }
        if (!radioButtonYes.isChecked() && !radioButtonNo.isChecked()) {
            String messageParking = "Choose Parking";
            customToast(messageParking);
            return;
        }
        if (setHigh.getText().toString().trim().isEmpty()) {
            String messageLengthOfTheHigh = "Length Of The Hike";
            customToast(messageLengthOfTheHigh);
            return;
        }
        confirmButton();
    }

    private void resetForm() {
        setName.setText("");
        setLocation.setText("");
        setDateOfTheHike.setText("");
        setHigh.setText("");
        setDifficulty.setSelection(1);
        setDescription.setText("");
        radioButtonNo.setChecked(false);
        radioButtonYes.setChecked(false);
    }
}