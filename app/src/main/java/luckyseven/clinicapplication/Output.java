package luckyseven.clinicapplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class to Export All Readings in to JSON file
 *
 * @author Zinet
 * @Date Sun Jan, 28 2018
 */
public class Output {
    //readings export filename
    private String exportFileName;

    /**
     * Constructor
     *
     * @param exportFileName filename to export all readings to
     */
    public Output(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    /**
     * Method to read patient record and generates all readings export data
     *
     * @param patientReadings
     * @throws java.io.FileNotFoundException
     */
    public void parseJSONAndExportAllReadings(JSONArray patientReadings) throws FileNotFoundException {

        Iterator iterator = patientReadings.iterator();
        JSONObject allPatientReadings = new JSONObject();
        JSONArray eachReadingArray = new JSONArray();

        String patient_id, reading_id, reading_type, reading_value, reading_date, clinic_id;
        while (iterator.hasNext()) {
            JSONObject dataParser = (JSONObject) iterator.next();
            JSONObject patientReading = new JSONObject();
            JSONObject reading = new JSONObject();

            patient_id = String.valueOf(dataParser.get("patient_id"));
            reading_id = String.valueOf(dataParser.get("reading_id"));
            reading_type = String.valueOf(dataParser.get("reading_type"));
            reading_value = String.valueOf(dataParser.get("reading_value"));
            reading_date = String.valueOf(dataParser.get("reading_date"));
            clinic_id = String.valueOf(dataParser.get("clinic_id"));

            patientReading.put("patient_id", patient_id);
            patientReading.put("reading_id", reading_id);
            patientReading.put("reading_type", reading_type);
            patientReading.put("reading_value", reading_value);
            patientReading.put("reading_date", reading_date);
            patientReading.put("clinic_id", clinic_id);

            eachReadingArray.add(patientReading);
            allPatientReadings.put("patient_readings", eachReadingArray);
        }
        //write output to file
        writeOutputToFile(allPatientReadings);
    }

    /**
     * Method to read patient record and generates patient readings
     *
     * @param patientReadings JSONArray object
     * @throws java.io.FileNotFoundException
     */
    public void displayPatientReadings(JSONArray patientReadings) throws FileNotFoundException {
        //use HashMap to group patient readings
        HashMap<Integer, List<JSONObject>> allPatientReadings = new HashMap<>();

        Iterator iterator = patientReadings.iterator();
        String reading_id, reading_type, reading_value, reading_date, clinic_id;
        int patient_id;
        while (iterator.hasNext()) {
            JSONObject dataParser = (JSONObject) iterator.next();

            //parse and get each values from JSON data
            patient_id = Integer.parseInt((String) dataParser.get("patient_id"));
            reading_id = String.valueOf(dataParser.get("reading_id"));
            reading_type = String.valueOf(dataParser.get("reading_type"));
            reading_value = String.valueOf(dataParser.get("reading_value"));
            reading_date = String.valueOf(dataParser.get("reading_date"));
            clinic_id = String.valueOf(dataParser.get("clinic_id"));

            JSONObject reading = new JSONObject();
            reading.put("reading_id", reading_id);
            reading.put("reading_type", reading_type);
            reading.put("reading_value", reading_value);
            reading.put("reading_date", reading_date);
            reading.put("clinic_id", clinic_id);

            //check if patient_id already exist and append the record
            if (!allPatientReadings.containsKey(patient_id)) {
                List<JSONObject> list = new ArrayList<>();
                list.add(reading);
                allPatientReadings.put(patient_id, list);
            } else {
                allPatientReadings.get(patient_id).add(reading);
            }
        }
        //show output to the console
        displayToConsole(allPatientReadings);
    }

    /**
     * Method to display output to the console
     *
     * @param allPatientReadings HashMap object
     */
    protected void writeOutputToFile(JSONObject allPatientReadings) {
        //Write JSON file
        try (FileWriter file = new FileWriter(this.exportFileName)) {
            file.write(allPatientReadings.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to display output to the console
     *
     * @param allPatientReadings HashMap object
     */
    protected void displayToConsole(HashMap<Integer, List<JSONObject>> allPatientReadings) {
        String reading_id, reading_type, reading_value, reading_date;

        Set set = allPatientReadings.entrySet();
        Iterator iterator2 = set.iterator();
        while (iterator2.hasNext()) {

            Map.Entry patientRecord = (Map.Entry) iterator2.next();
            ArrayList readings = (ArrayList) patientRecord.getValue();

            System.out.println("\nPatient ID: " + patientRecord.getKey());
            System.out.println("=====================================");

            for (int i = 0; i < readings.size(); i++) {
                JSONObject reading = (JSONObject) readings.get(i);

                reading_id = String.valueOf(reading.get("reading_id"));
                reading_type = String.valueOf(reading.get("reading_type"));
                reading_value = String.valueOf(reading.get("reading_value"));
                reading_date = String.valueOf(reading.get("reading_date"));

                System.out.println("\t Reading ID: " + reading_id);
                System.out.println("\t Reading Type: " + reading_type);
                System.out.println("\t Reading Value:  " + reading_value);
                System.out.println("\t reading Date: " + reading_date);
                System.out.println("--------------------------------------");

            }
        }
    }
}