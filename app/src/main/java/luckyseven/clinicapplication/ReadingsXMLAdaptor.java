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
    private String readingType;
    private String reading_id;
    private String readingValue;
    private String date;


    private final String id = "id";
    private final String type = "type";
    private final String clinic = "Clinic";
    private final String reading = "Reading";
    private final String value = "Value";
    private final String patient = "Patient";


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
                    case clinic:
                        if(clinic_id == null)
                        {
                            clinic_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }else if(patient_id != null && readingType != null && readingValue != null)
                        {
                            AddReadingToList();
                            clinic_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }else{
                            setAllDataNull();
                            clinic_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }
                        break;
                    case reading:
                        if(readingType == null)
                        {
                            readingType = data.getAttributes().getNamedItem(type).getNodeValue();
                            reading_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }else if(patient_id != null && readingValue != null) //ensure there is a patient ID before adding a Reading.
                        {
                            AddReadingToList();
                            readingType = data.getAttributes().getNamedItem(type).getNodeValue();
                            reading_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }else
                        {
                            setAllDataNull();
                            readingType = data.getAttributes().getNamedItem(type).getNodeValue();
                            reading_id = data.getAttributes().getNamedItem(id).getNodeValue();
                        }
                        break;
                    case value:
                        if(readingValue == null)
                        {
                            readingValue = data.getTextContent();
                        }else if(patient_id != null && readingType != null) //ensure there is a patient ID before adding a Reading.
                        {
                            AddReadingToList();
                            readingValue = data.getTextContent();
                        }else
                        {
                            setAllDataNull();
                            readingValue = data.getTextContent();
                        }
                        break;
                    case patient:
                        if(patient_id == null)
                        {
                            patient_id = data.getTextContent();
                        }else if(readingType != null && readingValue != null)
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
        readingType = null;
        reading_id = null;
        readingValue = null;
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
        
        thisReading = new  Reading(patient_id, clinic_id, readingType, reading_id, readingValue, date);
        readingArrayList.add(thisReading);

        setAllDataNull();
    }

}
