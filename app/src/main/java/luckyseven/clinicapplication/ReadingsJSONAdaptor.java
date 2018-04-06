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

    /**
     * Constructor method for ReadingsJSONAdaptor
     */
    ReadingsJSONAdaptor() { }

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
        try{ clinic_id = reading.get("clinic_id").toString();
        }catch (NullPointerException e){
       
        }
         //reading.get("clinic_id").toString();  <-- Must not have a value for clinic id
        String patient_id = reading.get("patient_id").toString();
        String type = reading.get("reading_type").toString();
        String reading_id = reading.get("reading_id").toString();
        String value = reading.get("reading_value").toString();
        String date = reading.get("reading_date").toString();

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

        thisReading.put("clinic_id", readingObject.getClinic());
        thisReading.put("patient_id", readingObject.getPatientID());
        thisReading.put("reading_type", readingObject.getRType());
        thisReading.put("reading_id", readingObject.getRId());
        thisReading.put("reading_value", readingObject.getRValue());
        thisReading.put("reading_date", readingObject.getRDate().getTime());

        return thisReading;
    }


}