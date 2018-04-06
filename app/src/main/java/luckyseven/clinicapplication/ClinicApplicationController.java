package luckyseven.clinicapplication;

/*Author: Jacob Fulton

    Sets up and Manages the GUI
    contains ReadingList of all added readings

 */

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Jacob Fulton
 */

public class ClinicApplicationController {


    //===============Labels=========
    @FXML
    private Label statusLabel;

    //================Table Setup============
    @FXML
    private TableView<Reading> table;
    @FXML
    private TableColumn<Reading, String> patientIDColumn;
    @FXML
    private TableColumn<Reading, String> readingColumn;
    @FXML
    private TableColumn<Reading, String> valueColumn;
    @FXML
    private TableColumn<Reading, String> clinicColumn;
    @FXML
    private TableColumn<Reading, Date> dateColumn;
    @FXML
    private TableColumn<Reading, String> readingIDColumn;

    //===============TextFields===================
    @FXML
    private TextField clinicField;
    @FXML
    private TextField patientIDField;
    @FXML
    private TextField readingValueField;


    //==============Radio Buttons===========
    @FXML
    RadioButton bloodPressureRButton;
    @FXML
    RadioButton stepsRButton;
    @FXML
    RadioButton weightRButton;
    @FXML
    RadioButton temperatureRButton;

    private ToggleGroup readingValueGroup = new ToggleGroup();

    //Creates a ReadingList object to populate the Table
    ReadingList readingList = new ReadingList();

    public ReadingList getReadingList() {
        return readingList;
    }

    public void initialize() {

        bloodPressureRButton.setToggleGroup(readingValueGroup);
        stepsRButton.setToggleGroup(readingValueGroup);
        weightRButton.setToggleGroup(readingValueGroup);
        temperatureRButton.setToggleGroup(readingValueGroup);

        //statemens that set what kind of data go into each column in the GUI's Table.
        patientIDColumn.setCellValueFactory(new PropertyValueFactory<Reading, String>("patientID"));
        readingIDColumn.setCellValueFactory(new PropertyValueFactory<Reading, String>("rId"));
        readingColumn.setCellValueFactory(new PropertyValueFactory<Reading, String>("rType"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Reading, String>("rValue"));
        clinicColumn.setCellValueFactory(new PropertyValueFactory<Reading, String>("clinic"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Reading, Date>("rDate"));
        loadReadings();

    }

    public void handleAddReadingButton() {
        boolean isValid = true;
        String clinic = "";
        String patientID = "";
        String readingValue = "";
        String readingType = "";
        Date date = new Date(System.currentTimeMillis());

        //Makes sure that all data fields are populated.
        //If all data fields are populated, the values are set to the variables.
        if (clinicField.getText().isEmpty()) {
            isValid = false;
        } else if (patientIDField.getText().isEmpty()) {
            isValid = false;
        } else if (readingValueField.getText().isEmpty()) {
            isValid = false;
        } else {

            clinic = clinicField.getText();
            patientID = patientIDField.getText();
            readingValue = readingValueField.getText();

            if (bloodPressureRButton.isSelected() == true) {
                readingType = "Blood Pressure";
            } else if (stepsRButton.isSelected() == true) {
                readingType = "Steps";
            } else if (weightRButton.isSelected() == true) {
                readingType = "Weight";
            } else if (temperatureRButton.isSelected() == true) {
                readingType = "temperatue";
            } else {
                isValid = false;
            }
        }

        //only adds a new reading object & refreshes table if all data fields are populated.
        if (checkTrial() == true) {
            if (isValid == true) {

                readingList.addReading(new Reading(patientID, clinic, readingType, readingValue, date));
                refreshTable();
                ClinicApplication.addReading(new Reading(patientID, clinic, readingType, readingValue, date));
                statusLabel.setText("Reading Added");
            } else {
                statusLabel.setText("Missing Data");
            }
        } else {
            statusLabel.setText("patient not in trial");
        }

    }

    /**
     * Refreshes the table
     */
    public void refreshTable() {
        table.setItems(readingList.getReadings());
    }

    /**
     * Loads all readings from the patientList in main
     */
    public void loadReadings() {
        readingList.clear();
        for (int i = 0; i < ClinicApplication.patientList.size(); i++) {
            ArrayList<Reading> readings = ClinicApplication.patientList.get(i).getReadings();
            for (int q = 0; q < readings.size(); q++) {
                readingList.addReading(readings.get(q));
            }
        }
        refreshTable();
    }

    /**
     * Checks if patient is in a trial using an id retrieved from the textField: patientIDField
     *
     * @return -- boolean value true if patient is in a trial or if patient is not found; false if patient is not in a trial
     */
    public boolean checkTrial() {
        boolean inTrial = true;
        if (!patientIDField.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(patientIDField.getText());
                int pos = searchPatient(id);
                if (pos != -1) {
                    if (ClinicApplication.patientList.get(pos).isInTrial() == false) {
                        inTrial = false;
                    }
                }
            } catch (NumberFormatException e) {
                statusLabel.setText("Patient ID must be numerical");
                inTrial = false;
            }
        }
        return inTrial;
    }

    /**
     * Loads a file, and retrives all readings from the patientList in main
     */
    public void importFile() {
        ClinicApplication.inputChooser();
        loadReadings();
    }

    /**
     * Checks if the patient is in a trial
     * If the patient is not in a trial, sets inTrial to true
     */
    public void startTrial() {
        if (!patientIDField.getText().isEmpty()) {
            int id = Integer.parseInt(patientIDField.getText());
            int pos = searchPatient(id);
            if (pos != -1) {
                if (ClinicApplication.patientList.get(pos).isInTrial() == false) {
                    ClinicApplication.patientList.get(pos).setInTrial(true);
                    statusLabel.setText("trial for Patient with ID: " + id + " has started.");
                }
            } else {
                statusLabel.setText("patient not found");
            }
        } else {
            statusLabel.setText("no patient ID entered");
        }
    }

    /**
     * Checks if a patient is in a trial
     * If the patient is in a trial, sets inTrial to false
     */
    public void endTrial() {
        if (!patientIDField.getText().isEmpty()) {
            int id = Integer.parseInt(patientIDField.getText());
            int pos = searchPatient(id);
            if (pos != -1) {
                if (ClinicApplication.patientList.get(pos).isInTrial() == true) {
                    ClinicApplication.patientList.get(pos).setInTrial(false);
                    statusLabel.setText("Trial for Patient with ID: " + id + " has ended.");
                }
            } else {
                statusLabel.setText("patient not found");
            }
        } else {
            statusLabel.setText("no patient ID entered");
        }
    }

    /**
     * @param i -- int variable representing a patient's id
     * @return -- the position of the patient with the patient id that matches the passed parameter
     */
    public int searchPatient(int i) {

        int pos = -1;
        for (int p = 0; p < ClinicApplication.patientList.size(); p++) {
            if (ClinicApplication.patientList.get(p).getId() == i) {
                pos = p;
            }
        }
        return pos;
    }
}
