package luckyseven.clinicapplication;

import java.util.ArrayList;

public class PatientList {

    /*
     * Used to keep track of the patients in the trial.
     */
    private static ArrayList<Patient> patientList = new ArrayList<Patient>();

    private static PatientList singleton = null;
    private static Object mutex = new Object();

    private PatientList() {
    }

    /*
     * Returns an instance of The class, and sets up the class as a singleton if not
     * already done.
     */
    public static PatientList getInstance() {
        PatientList instanceOfConfigurer = singleton;

        if (instanceOfConfigurer == null) {
            synchronized (mutex) {
                instanceOfConfigurer = singleton;
                if (instanceOfConfigurer == null) {
                    singleton = instanceOfConfigurer = new PatientList();
                }
            }
        }

        return instanceOfConfigurer;
    }

    /**
     * Adds the given reading to the correct patient.
     * A new patient is added if none with the id from
     * the given Reading match any existing id in
     * patientList.
     *
     * @param reading
     */
    public void addReading(Reading reading) {
        int patient_id;
        patient_id = Integer.parseInt((String) reading.getPatientID());

        /*
         * checks if the patientList is empty
         * if it is it adds a new patient to it
         * with the reading as its parameter.
         * Otherwise it cycles through patientList
         * looking for a patient with a matching ID.
         * If none is found with the same id it adds
         * a new patient. The patients are added with
         * there id's sorted from least to greatest.
         */
        if (patientList.isEmpty()) {
            addPatient(0, patient_id, reading);
        } else {
            for (int i = 0; i < patientList.size(); i++) {
                if (patientList.get(i).getId() == patient_id) {
                    patientList.get(i).addReading(reading);
                    i = patientList.size();
                } else if (patientList.get(i).getId() < patient_id) {
                    addPatient(i, patient_id, reading);
                    i = patientList.size();

                    /*
                     * adds a new patient to the end of
                     * patientList if all of patientList
                     * has been checked and readings id
                     * is greater than every other id in
                     * patientList.
                     */
                } else if (i == patientList.size() - 1) {
                    addPatient(-1, patient_id, reading);
                    i++;
                }
            }
        }
    }

    /**
     * Adds a new patient with the Reading at the given index.
     * The new patient is added to the end of patientList if the
     * index is less than 0 (usually -1).
     *
     * @param index
     * @param patient_id
     * @param reading
     */
    public void addPatient(int index, int patient_id, Reading reading) {
        Patient newP = new Patient(patient_id, true);
        newP.addReading(reading);

        if (index < 0) {
            patientList.add(newP);
        } else {
            patientList.add(index, newP);
        }

    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }
}
