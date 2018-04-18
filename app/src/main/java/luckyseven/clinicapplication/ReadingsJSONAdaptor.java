package luckyseven.clinicapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * Adaptor for changing JSONArrays into
 * ArrayLists, and vise versa.
 */

public class ReadingsJSONAdaptor {

    private final String clinicId = "clinic_id";
    private final String patientId = "patient_id";
    private final String readingType = "reading_type";
    private final String readingId = "reading_id";
    private final String readingValue = "reading_value";
    private final String readingDate = "reading_date";

    /**
     * Constructor method for ReadingsJSONAdaptor
     */
    public ReadingsJSONAdaptor() { }

    //==================================JSONToReading========================================
    /**
     * Converts a JSONArray into an ArrayList of Readings
     * and returns the ArrayList of Readings
     *
     * @param patientReadings
     * @return
     */
    public ArrayList<Reading> switchJSONArrayToReadings(JSONArray patientReadings)
    {
        ArrayList<Reading> readingArrayList = new ArrayList<Reading>();

        for(Object rawReading: patientReadings)
        {
            JSONObject reading = (JSONObject) rawReading;
            Reading thisReading = switchJSONObjectToReading((JSONObject) rawReading);
            readingArrayList.add(thisReading);
        }
        return readingArrayList;
    }
    /**
     * Converts a JSONObject into a Reading
     * and returns the Reading
     *
     * @param reading
     * @return
     */
    public Reading switchJSONObjectToReading(JSONObject reading)
    {
        String clinic_id = "";
        try{ clinic_id = reading.get(clinicId).toString();
        }catch (NullPointerException e){
       
        }
         //reading.get("clinic_id").toString();  <-- Must not have a value for clinic id
        String patient_id = reading.get(patientId).toString();
        String type = reading.get(readingType).toString();
        String reading_id = reading.get(readingId).toString();
        String value = reading.get(readingValue).toString();
        String date = reading.get(readingDate).toString();

        Reading thisReading = new  Reading(patient_id, clinic_id, type, reading_id, value, date);
        return thisReading;
    }


    //========================================ReadingToJSON==================================================
    /**
     * Converst an ArrayList of Readings into a JSONArray
     * and returns the JSON Array
     *
     * @param readingArrayList
     * @return
     */
    public JSONArray readingArrayListToJSONArray(ArrayList<Reading> readingArrayList)
    {
        JSONArray readings = new JSONArray();


        for(Reading readingObject: readingArrayList)
        {
            JSONObject thisReading = readingToJSONObject(readingObject);

            readings.add(thisReading);
        }

        return readings;
    }

    /**
     * Converts a Reading into a JSONObject
     * and returns the JSONObject
     *
     * @param readingObject
     * @return
     */
    public JSONObject readingToJSONObject(Reading readingObject)
    {
        JSONObject thisReading = new JSONObject();

        thisReading.put(clinicId, readingObject.getClinic());
        thisReading.put(patientId, readingObject.getPatientID());
        thisReading.put(readingType, readingObject.getRType());
        thisReading.put(readingId, readingObject.getRId());
        thisReading.put(readingValue, readingObject.getRValue());
        thisReading.put(readingDate, readingObject.getRDate().getTime());

        return thisReading;
    }


}