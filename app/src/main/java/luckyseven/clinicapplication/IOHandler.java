package luckyseven.clinicapplication;

import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class IOHandler {

    //Used to store the path of the save file.
    private static String savePath;
    private static final String saveName = "savedData.json";

    private static IOHandler singleton = null;
    private static Object mutex = new Object();

    private static final int ACTIVITY_CHOOSE_FILE = 1;
    private static Input in = new Input();

    private IOHandler() {
        try {
            loadSavedData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns an instance of The class, and sets up the class as a singleton if not
     * already done.
     */
    public static IOHandler getInstance() {
        IOHandler instanceOfConfigurer = singleton;

        if (instanceOfConfigurer == null) {
            synchronized (mutex) {
                instanceOfConfigurer = singleton;
                if (instanceOfConfigurer == null) {
                    singleton = instanceOfConfigurer = new IOHandler();
                }
            }
        }

        return instanceOfConfigurer;
    }



    private void loadSavedData() {
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
            e.printStackTrace();
        }

    }

    /*
     * Call after a File chooser iss used to have the file processed.
     * calls inputJSONObject if the file is of type json,
     * or inputXML if the file is of type xml. If the file is neither
     * json or XML then nothing is done with it.
     */
    public void inputChooser(Input in) {
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
    public void inputJSONObject(File inFile) {
        ReadingsJSONAdaptor adpt = new ReadingsJSONAdaptor();
        ParserJSON p = new ParserJSON(inFile);
        ArrayList<Reading> patientReadings = adpt.switchJSONArrayToReadings(p.getJSONArray("patient_readings"));
        PatientList patientList = PatientList.getInstance();

        for (Reading reading : patientReadings) {
            patientList.addReading(reading);
        }
    }

    /**
     * Takes the input of an XML File
     * and converts it into Reading Ojbects.
     *
     * @param inFile -- The file that's being parsed into Reading Objects
     */
    public void inputXML(File inFile) {
        ReadingsXMLAdaptor adpt = new ReadingsXMLAdaptor();
        ParserXML p = new ParserXML(inFile);
        ArrayList<Reading> patientReadings = adpt.switchXMLToReadings(p.getXMLDocument());
        PatientList patientList = PatientList.getInstance();

        for (Reading reading : patientReadings) {
            patientList.addReading(reading);
        }
    }

    /**
     * Converts all readings to a JSONArray, and exports
     * them to the JSON save file.
     *
     * @throws FileNotFoundException
     */
    public void exportAllReadings() throws FileNotFoundException {
        ReadingsJSONAdaptor adpt = new ReadingsJSONAdaptor();
        ArrayList<Reading> outReadings = new ArrayList<Reading>();
        PatientList patientList = PatientList.getInstance();

        for (Patient patient : patientList.getPatientList()) {
            outReadings.addAll(patient.getReadings());
        }

        JSONArray out = adpt.readingArrayListToJSONArray(outReadings);
        Output output = new Output(savePath);
        output.parseJSONAndExportAllReadings(out);
        output.displayPatientReadings(out);
    }
}
