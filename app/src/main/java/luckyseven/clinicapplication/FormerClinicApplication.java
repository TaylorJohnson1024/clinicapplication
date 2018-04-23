package groupproject;

import luckyseven.clinicapplication.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import static javafx.application.Application.launch;


/**
 * Contains main. Gets the input from Input as a JSONArray,
 * and adds the given readings to the appropriate patient in
 * the patientList ArrayList. New patients are added to patientList
 * if there is not already a patient with a matching id.
 *
 * @GroupName: The Lucky Seven
 * @MainClass Author:           Christopher Neuman
 * @Input Author: 				Taylor Johnson
 * @Patient Author: 			Jacob Fulton
 * @Output Author:              Zinet Kemal
 */
public class ClinicApplication extends Application {

    private static final String saveName = "savedData.json";
    /*
     * Used to keep track of the patients in the trial.
     */
    static ArrayList<Patient> patientList = new ArrayList<Patient>();
    //Used to store the path of the save file.
    private static String savePath;

    /**
     * main Class, calls getInput and setOutput.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            loadSavedData();
        } catch (Exception e) {

        }

        launch(args);

        exportAllReadings();
    }

    private static void loadSavedData() {
        Input in = new Input();
        //sets savePath as the directory name the program is stored in.
        savePath = System.getProperty("user.dir");
        savePath += "/src/" + saveName;

        File f = null;
        try {

            f = new File(savePath);
            if (f.exists()) {
                f = in.getSaveFile(savePath);
                inputJSONObject(f);
            }

        } catch (Exception e) {

        }

    }

    /*
     * opens the File chooser and calls inputJSONObject
     * if the file is of type json, or inputXML if the
     * file is of type xml. If the file is neither json
     * or XML then nothing is done with it.
     */
    public static void inputChooser() {
        Input in = new Input();
        in.fileChooser();
        String fileType = in.getFileType();

        try {
            if (fileType.equalsIgnoreCase("json")) {
                inputJSONObject(in.getFile());
            } else if (fileType.equalsIgnoreCase("xml")) {
                inputXML(in.getFile());
            }
        } catch (NullPointerException e) { /* This is here in case the user decides to close the filechooser dialog */ }
    }

    /**
     * Take the input of a JSON File
     * and voncerts it into Reading Objects
     *
     * @param inFile -- The file that's being parsed into Reading Objects
     */
    public static void inputJSONObject(File inFile) {
        ReadingsJSONAdaptor adpt = new ReadingsJSONAdaptor();
        ParserJSON p = new ParserJSON(inFile);
        ArrayList<Reading> patientReadings = adpt.switchJSONArrayToReadings(p.getJSONArray("patient_readings"));

        for (Reading reading : patientReadings) {
            addReading(reading);
        }
    }

    /**
     * Takes the input of an XML File
     * and converts it into Reading Ojbects.
     *
     * @param inFile -- The file that's being parsed into Reading Objects
     */
    public static void inputXML(File inFile) {
        ReadingsXMLAdaptor adpt = new ReadingsXMLAdaptor();
        ParserXML p = new ParserXML(inFile);
        ArrayList<Reading> patientReadings = adpt.switchXMLToReadings(p.getXMLDocument());

        for (Reading reading : patientReadings) {
            addReading(reading);
        }
    }

    /**
     * Converts all readings to a JSONArray, and exports
     * them to the JSON save file.
     *
     * @throws FileNotFoundException
     */
    public static void exportAllReadings() throws FileNotFoundException {
        ReadingsJSONAdaptor adpt = new ReadingsJSONAdaptor();
        ArrayList<Reading> outReadings = new ArrayList<Reading>();
        for (Patient patient : patientList) {
            outReadings.addAll(patient.getReadings());
        }

        JSONArray out = adpt.readingArrayListToJSONArray(outReadings);
        Output output = new Output(savePath);
        output.parseJSONAndExportAllReadings(out);
        output.displayPatientReadings(out);
    }

    /**
     * Adds the given reading to the correct patient.
     * A new patient is added if none with the id from
     * the given Reading match any existing id in
     * patientList.
     *
     * @param reading
     */
    public static void addReading(Reading reading) {
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
    public static void addPatient(int index, int patient_id, Reading reading) {
        Patient newP = new Patient(patient_id, true);
        newP.addReading(reading);

        if (index < 0) {
            patientList.add(newP);
        } else {
            patientList.add(index, newP);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("ClinicApplication.fxml"));
        primaryStage.setScene(new Scene(root, 683, 473));
        primaryStage.show();
    }
}