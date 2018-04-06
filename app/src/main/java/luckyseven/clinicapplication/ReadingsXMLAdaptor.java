package luckyseven.clinicapplication;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Date;
import java.util.ArrayList;

public class ReadingsXMLAdaptor {

    private ArrayList<Reading> readingArrayList;
    private Reading thisReading;
    private String clinic_id;
    private String patient_id;
    private String type;
    private String reading_id;
    private String value;
    private String date;


    /**
     * Constructor method for ReadingsXMLAdaptor
     */
    public ReadingsXMLAdaptor() { }

    /**
     * Converts an XML into an ArrayList of Readings
     * and returns the ArrayList of Readings
     *
     * @param patientReadings
     * @return -- an ArrayList of Readings objects
     */
    public ArrayList<Reading> switchXMLToReadings(Document patientReadings)
    {
        setAllDataNull();
        readingArrayList = new ArrayList<Reading>();

        NodeList nodeList =  patientReadings.getDocumentElement().getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node data = nodeList.item(i);
            if (data.getNodeType() == Node.ELEMENT_NODE) {
                // do something with the current element

                switch(data.getNodeName())
                {
                    case "Clinic":
                        if(clinic_id == null)
                        {
                            clinic_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }else if(patient_id != null && type != null && value != null)
                        {
                            AddReadingToList();
                            clinic_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }else{
                            setAllDataNull();
                            clinic_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }
                        break;
                    case "Reading":
                        if(type == null)
                        {
                            type = data.getAttributes().getNamedItem("type").getNodeValue();
                            reading_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }else if(patient_id != null && value != null) //ensure there is a patient ID before adding a Reading.
                        {
                            AddReadingToList();
                            type = data.getAttributes().getNamedItem("type").getNodeValue();
                            reading_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }else
                        {
                            setAllDataNull();
                            type = data.getAttributes().getNamedItem("type").getNodeValue();
                            reading_id = data.getAttributes().getNamedItem("id").getNodeValue();
                        }
                        break;
                    case "Value":
                        if(value == null)
                        {
                            value = data.getTextContent();
                        }else if(patient_id != null && type != null) //ensure there is a patient ID before adding a Reading.
                        {
                            AddReadingToList();
                            value = data.getTextContent();
                        }else
                        {
                            setAllDataNull();
                            value = data.getTextContent();
                        }
                        break;
                    case "Patient":
                        if(patient_id == null)
                        {
                            patient_id = data.getTextContent();
                        }else if(type != null && value != null)
                        {
                            AddReadingToList();
                            patient_id = data.getTextContent();
                        }else {
                            setAllDataNull();
                            patient_id = data.getTextContent();
                        }
                        AddReadingToList();
                        break;
                }

            }
        }
        return readingArrayList;
    }

    /**
     * Sets all Strings back to null
     * except for clinic_id
     */
    private void setAllDataNull()
    {
        patient_id = null;
        type = null;
        reading_id = null;
        value = null;
        date = null;
    }

    /**
     * Inserts the data from all of the
     * Strings  into a new Reading Object,
     * adds that Reading to readingArrayList,
     * and resets all the strings except
     * clinic_id to null.
     */
    private void AddReadingToList()
    {
        Date date = new Date();
        
        thisReading = new  Reading(patient_id, clinic_id, type, reading_id, value, date);
        readingArrayList.add(thisReading);

        setAllDataNull();
    }

}
