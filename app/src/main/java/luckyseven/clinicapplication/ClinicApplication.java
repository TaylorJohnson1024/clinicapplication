package luckyseven.clinicapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class ClinicApplication extends AppCompatActivity {




    private static final String TAG = "ClinicApplication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(ClinicApplication.this, AddReading.class));

    }
}

