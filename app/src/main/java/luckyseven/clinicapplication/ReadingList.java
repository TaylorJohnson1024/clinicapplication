package luckyseven.clinicapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

public class ReadingList {

    private ObservableList<Reading> readings;

    /**
     * Constructor method for ReadingList
     */
    public ReadingList() {
        readings = FXCollections.observableArrayList();
    }

    /**
     * @return -- the ObservableList of Readings
     */
    public ObservableList<Reading> getReadings() {

        return readings;
    }

    /**
     * Add a reading to the observable list with the passed parameter
     *
     * @param reading -- Reading variable representing a the reading to be added to the observable list
     */
    public void addReading(Reading reading) {
        readings.add(reading);
    }

    /**
     * Clear the observable list
     */
    public void clear() {
        this.readings.clear();
    }

}
