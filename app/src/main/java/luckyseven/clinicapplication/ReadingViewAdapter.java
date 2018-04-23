package luckyseven.clinicapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class ReadingViewAdapter extends RecyclerView.Adapter<ReadingViewAdapter.ViewHolder> {
    ArrayList<Reading> readingList;
    DateFormat date = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");

    public ReadingViewAdapter(ArrayList<Reading> readings) {
        this.readingList = readings;
    }
    @NonNull
    @Override
    public ReadingViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_row, parent, false);
       return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReadingViewAdapter.ViewHolder holder, int position) {
        holder.patientID.setText(readingList.get(position).getPatientID());
        holder.clinic.setText(readingList.get(position).getClinic());
        holder.date.setText(date.format( readingList.get(position).getRDate()));
        holder.readingID.setText(readingList.get(position).getRId());
        holder.readingType.setText(readingList.get(position).getRType());
        holder.readingValue.setText(readingList.get(position).getRValue());
        holder.trialStatus.setText("");


    }

    @Override
    public int getItemCount() {
        return readingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView patientID;
        public TextView readingID;
        public TextView clinic;
        public TextView readingType;
        public TextView readingValue;
        public TextView date;
        public TextView trialStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            patientID = itemView.findViewById(R.id.patientID);
            readingID = itemView.findViewById(R.id.readingID);
            clinic = itemView.findViewById(R.id.clinic);
            readingType = itemView.findViewById(R.id.readingType);
            readingValue = itemView.findViewById(R.id.readingValue);
            date = itemView.findViewById(R.id.date);
            trialStatus = itemView.findViewById(R.id.trialStatus);
        }
    }
}
