package luckyseven.clinicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;


public class AddReading extends AppCompatActivity {

    Button viewDataBtn;
    Button addReadingBtn;

    EditText clinic;
    EditText patientID;
    EditText readingValue;



    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_clinic_application);

        viewDataBtn = findViewById(R.id.viewDataBtn);
        addReadingBtn = findViewById(R.id.addReadingBtn);

        clinic = findViewById(R.id.clinic);
        patientID = findViewById(R.id.patientID);
        readingValue = findViewById(R.id.readingValue);


        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AddReading.this, ViewData.class));
            }
        });

        addReadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                boolean isValid = true;
                String clinicStr = "";
                String patientIDStr = "";
                String readingValueStr = "";
                Date date = new Date(System.currentTimeMillis());
                clinicStr = clinic.getText().toString();
                patientIDStr = patientID.getText().toString();
                readingValueStr = readingValue.getText().toString();
            }
        });
    }
}