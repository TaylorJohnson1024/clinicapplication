package luckyseven.clinicapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * This class is used for the patient object.
 * This class allows for the setting and retrieving of a Patient's ID, and whether they're in a trial or not.
 *
 * @author Jacob Fulton
 */

class Patient {
    private int id;
    private boolean inTrial;
    private ArrayList<Reading> readings;
    private ReadingList readingsArray;

    public Patient(int id, boolean inTrial) {
        readings = new ArrayList<Reading>();
        this.setId(id);
        this.setInTrial(inTrial);

    }

    //===============Setters=================

    /**
     * Sets whether a patient is in a trial or not
     *
     * @param inTrial -- a boolean variable
     */
    public void setInTrial(boolean inTrial) {
        this.inTrial = inTrial;
    }

    /**
     * Sets the patient's id
     *
     * @param id -- an int variable
     */
    public void setId(int id) {
        this.id = id;
    }

    //============Getters===============

    /**
     * Gets the patient's id
     *
     * @return -- an int variable representing the patient's id
     */
    public int getId() {
        return id;
    }

    /**
     * Checks to see if a patient is in a trial
     *
     * @return -- a boolean variable; true if patient is in trial; false if patient isn't in trial
     */
    public boolean isInTrial() {
        return inTrial;
    }

    /**
     * Start a patient's trial
     */
    public void startTrial() {
        this.inTrial = true;
    }

    /**
     * End a patient's trial
     */
    public void endTrial() {
        this.inTrial = false;
    }

    /**
     * @return -- an ArrayList of Reading objects
     */
    public ArrayList<Reading> getReadings() {
        return readings;
    }

    /**
     * Set instance variable readings to the passed paramter ArrayList readings
     *
     * @param readings -- ArrayList of Reading objects
     */
    public void setReadings(ArrayList<Reading> readings) {
        this.readings = readings;
    }


    //adds a Reading object to the ReadingList array if patient ID matches
    //returns "successful" if the object is added, and "patient id doesn't match" if unsuccessful

    /**
     * Adds a Reading object to the ReadingList arry if patient ID matches
     *
     * @param obj -- a Reading object
     * @return -- a String message displaying if the object was successfully added or not
     */
    public String addReading(Reading obj) {
        int patient_id;
        patient_id = Integer.parseInt((String) obj.getPatientID());
        if ((patient_id == id) && (inTrial == true)) {
            readings.add(obj);
            return ("successful");
        }

        return ("Patient ID doesn't match");
    }
}

	
