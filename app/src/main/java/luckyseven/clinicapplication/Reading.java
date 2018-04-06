package luckyseven.clinicapplication;

import java.util.Date;
import java.util.Random;

public class Reading {

    private String patientID;
    private String clinic;
    private String rType;
    private String rId;
    private String rValue;
    private Date rDate;

    /**
     * Constructor method for Reading
     *
     * @param patientID -- a String variable representing the patient's id
     * @param clinic    -- a String variable representing the clinic
     * @param type      -- a String variable representing the reading type
     * @param id        -- a String variable representing the reading id
     * @param value     -- a String variable representing the reading value
     * @param date      -- a Date variable representing the reading date
     */
    public Reading(String patientID, String clinic, String type, String id, String value, Date date) {


        setClinic(clinic);
        setPatientID(patientID);
        setRType(type);
        setRId(id);
        setRValue(value);
        setRDate(date);
    }

    /**
     * Constructor for Reading method that creates a random id instead of using an already created id
     *
     * @param patientID -- a String variable representing the patient's id
     * @param clinic    -- a String variable representing the clinic
     * @param type      -- a String variable representing the reading type
     * @param value     -- a String variable representing the reading value
     * @param date      -- a Date variable representing the reading date
     */
    public Reading(String patientID, String clinic, String type, String value, Date date) {


        setClinic(clinic);
        setPatientID(patientID);
        setRType(type);
        this.rId = generateID();
        setRValue(value);
        setRDate(date);
    }

    /**
     * Construct method for Reading that accepts date as a String of the millisecond time value instead of an already
     * instantiated Date Object
     *
     * @param patientID
     * @param clinic
     * @param type
     * @param id
     * @param value
     * @param date
     */
    public Reading(String patientID, String clinic, String type, String id, String value, String date) {

        setClinic(clinic);
        setPatientID(patientID);
        setRType(type);
        setRId(id);
        setRValue(value);
        setRDate(date);
    }

    // =================================setters===================

    /**
     * Set the clinic to the passed parameter
     *
     * @param clinic -- a String variable representing a clinic
     */
    public void setClinic(String clinic) {
        if (clinic == null) {
            this.clinic = "";
        } else {
            this.clinic = clinic;
        }
    }

    /**
     * Set the patient id to the passed parameter
     *
     * @param id -- String variable representing the patient id
     */
    public void setPatientID(String id) {
        if (id == null) {
            this.patientID = "";
        } else {
            patientID = id;
        }
    }

    /**
     * Set the reading type to the passed parameter
     *
     * @param type -- String variable representing the reading type
     */
    public void setRType(String type) {
        if (type == null) {
            rType = "";
        } else {
            rType = type;
        }
    }

    /**
     * Set the reading type to the passed parameter
     *
     * @param id -- String variable representing the reading id
     */
    public void setRId(String id) {
        if (id == null) {
            rId = "";
        } else {
            rId = id;
        }
    }

    /**
     * Set the reading value to the passed parameter
     *
     * @param value -- String variable representing the reading value
     */
    public void setRValue(String value) {
        if (value == null) {
            rValue = "";
        } else {
            rValue = value;
        }
    }

    /**
     * Set the reading date to the passed parameter
     *
     * @param date -- Date variable representing the reading date
     */
    public void setRDate(Date date) {
        if (date == null) {
            rDate = new Date();
        } else {
            rDate = date;
        }

    }

    /**
     * Overloads the setRDate method to accept a String of the millisecond time
     * value
     *
     * @param date -- String variable representing the reading date
     */
    public void setRDate(String date) {
        if (date == null) {
            rDate = new Date();
        } else {
            Long L = new Long(date);
            rDate = new Date(L);
        }

    }

    // =================================getters===================

    /**
     * @return -- the clinic
     */
    public String getClinic() {
        return clinic;
    }

    /**
     * @return -- the patient id
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * @return -- the reading type
     */
    public String getRType() {
        return rType;
    }

    /**
     * @return -- the reading id
     */
    public String getRId() {
        return rId;
    }

    /**
     * @return -- the reading value
     */
    public String getRValue() {
        return rValue;
    }

    /**
     * @return -- the reading date
     */
    public Date getRDate() {
        return rDate;
    }

    // =============================================================

    /**
     * @return -- returns a random reading id
     */
    public String generateID() {
        Random q = new Random();
        String id = "";
        final String charBank = "abcdefghijklmnopqrstuvwxyz1234567890";
        for (int i = 5; i > 0; i--) {
            id = id + (charBank.charAt(q.nextInt(charBank.length())));
        }
        return id;
    }

    /**
     * @return -- an empty String value
     */
    public String toString() {
        return "";
    }
}
