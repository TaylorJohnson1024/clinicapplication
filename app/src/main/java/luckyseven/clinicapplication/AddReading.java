package luckyseven.clinicapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;

import static android.widget.Toast.LENGTH_SHORT;
import static luckyseven.clinicapplication.R.array;
import static luckyseven.clinicapplication.R.id;
import static luckyseven.clinicapplication.R.layout;


public class AddReading extends AppCompatActivity {

    final int ACTIVITY_CHOOSE_FILE = 1;
    Input in = new Input();

    Button viewDataBtn;
    Button addReadingBtn;
    Button importBtn;
    Button startTrialBtn;
    Button endTrialBtn;


    EditText clinic;
    EditText patientID;
    EditText readingValue;

    Spinner readingType;
    ArrayAdapter<CharSequence> arrayAdapter;

    String readingTypeStr = "";


    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(layout.activity_clinic_application);

        viewDataBtn = findViewById(id.viewDataBtn);
        addReadingBtn = findViewById(id.addReadingBtn);
        importBtn = findViewById(id.importBtn);
        startTrialBtn = findViewById(id.startTrialBtn);
        endTrialBtn = findViewById(id.endTrialBtn);

        clinic = findViewById(id.clinic);
        patientID = findViewById(id.patientID);
        readingValue = findViewById(id.readingValue);

        readingType = findViewById(id.readingType);
        arrayAdapter = ArrayAdapter.createFromResource(this, array.reading_type_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readingType.setAdapter(arrayAdapter);

        readingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        readingTypeStr = "Temperature";
                        break;

                    case 1:
                        readingTypeStr = "Weight";
                        break;

                    case 2:
                        readingTypeStr = "Steps";
                        break;

                    case 3:
                        readingTypeStr = "Blood Pressure";
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        endTrialBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    endTrial();
                }catch (NumberFormatException e){

                }
            }

        });

        startTrialBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    startTrial();
                }catch (NumberFormatException e){
                    
                }
            }

        });


        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AddReading.this, ViewData.class));
            }
        });

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                intent = Intent.createChooser(chooseFile, "Choose a File");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }

        });

        addReadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                String clinicStr = "";
                String patientIDStr = "";
                String readingValueStr = "";

                Date date = new Date(System.currentTimeMillis());
                if (clinic.getText().toString().matches("")) {
                    isValid = false;
                } else if (patientID.getText().toString().matches("")) {
                    isValid = false;
                } else if (readingValue.getText().toString().matches("")) {
                    isValid = false;
                } else if(!checkTrial()) {
                    isValid = false;
                }else{
                    clinicStr = clinic.getText().toString();
                    patientIDStr = patientID.getText().toString();
                    readingValueStr = readingValue.getText().toString();
                }

                if (isValid) {
                    PatientList.getInstance().addReading(new Reading(patientIDStr, clinicStr, readingTypeStr, readingValueStr, date));
                    addConfirm();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    File file = new File(uri.getPath());
                    in.setInFile(file);
                }
            }
        }
    }

    public void startTrial() {
        if (!patientID.getText().toString().matches("")) {
            int id = Integer.parseInt(patientID.getText().toString());
            int pos = searchPatient(id);
            if (pos != -1) {
                if (!PatientList.getInstance().getPatientList().get(pos).isInTrial()) {
                    PatientList.getInstance().getPatientList().get(pos).setInTrial(true);
                    Toast.makeText(this, "Patient's trial has started", LENGTH_SHORT).show(); //statusLabel.setText("trial for Patient with ID: " + id + " has started.");
                }
            } else {
                Toast.makeText(this, "Patient not found", LENGTH_SHORT).show(); //statusLabel.setText("patient not found");
            }
        } else {
            Toast.makeText(this, "No ID entered", LENGTH_SHORT).show(); //statusLabel.setText("no patient ID entered");
        }
    }

    public void endTrial() {
        if (!patientID.getText().toString().matches("")) {
            int id = Integer.parseInt(patientID.getText().toString());
            int pos = searchPatient(id);
            if (pos != -1) {
                if (PatientList.getInstance().getPatientList().get(pos).isInTrial()) {
                    PatientList.getInstance().getPatientList().get(pos).setInTrial(false);
                    Toast.makeText(this, "Patient's trial has ended", LENGTH_SHORT).show();//statusLabel.setText("Trial for Patient with ID: " + id + " has ended.");
                }
            } else {
                Toast.makeText(this, "Patient not found", LENGTH_SHORT).show(); // statusLabel.setText("patient not found");
            }
        } else {
            Toast.makeText(this, "No ID entered", LENGTH_SHORT).show();
        }
    }


    public boolean checkTrial() {
        boolean inTrial = true;
        if (!patientID.getText().toString().matches("")) {
            try {
                int id = Integer.parseInt(patientID.getText().toString());
                int pos = searchPatient(id);
                if (pos != -1) {
                    if (!PatientList.getInstance().getPatientList().get(pos).isInTrial()) {
                        inTrial = false;
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Patient ID must be numerical", LENGTH_SHORT).show();//Switch to toast statusLabel.setText("Patient ID must be numerical");
                inTrial = false;
            }
        }
        return inTrial;
    }

    public int searchPatient(int i) {

        int pos = -1;
        for (int p = 0; p < PatientList.getInstance().getPatientList().size(); p++) {
            if (PatientList.getInstance().getPatientList().get(p).getId() == i) {
                pos = p;
            }
        }
        return pos;

    }

    public void addConfirm(){
        Toast.makeText(this, "Reading Added", LENGTH_SHORT).show();
    }
}