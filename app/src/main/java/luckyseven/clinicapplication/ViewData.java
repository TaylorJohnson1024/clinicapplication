package luckyseven.clinicapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class ViewData extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter rVAdapter;

    ArrayList<Reading> readingList;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_clinic_application_data);

        readingList = new ArrayList<Reading>();

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(!PatientList.getInstance().getPatientList().isEmpty()) {
            rVAdapter = new ReadingViewAdapter(readingList);
        }
        recyclerView.setAdapter(rVAdapter);
        loadReadings();

    }

    public void loadReadings() {
        if(!PatientList.getInstance().getPatientList().isEmpty()) {
            for (int i = 0; i < PatientList.getInstance().getPatientList().size(); i++) {
                ArrayList<Reading> readings = PatientList.getInstance().getPatientList().get(i).getReadings();
                for (int q = 0; q < readings.size(); q++) {

                        readingList.add(readings.get(q));

                }
            }
        }
    }



}
