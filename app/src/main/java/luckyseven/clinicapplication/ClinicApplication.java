package luckyseven.clinicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ClinicApplication extends AppCompatActivity {


    private static final String TAG = "ClinicApplication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        IOHandler.getInstance();

        super.onCreate(savedInstanceState);
        startActivity(new Intent(ClinicApplication.this, AddReading.class));

        try{
            IOHandler.getInstance().exportAllReadings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

