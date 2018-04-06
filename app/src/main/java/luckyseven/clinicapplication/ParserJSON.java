package luckyseven.clinicapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser class with a focus on parsing a JSON file
 * into a JSONObject or a JSONArray.
 */
public class ParserJSON extends Parser {
    private FileReader reader;
    private JSONParser jsonParser;
    private JSONArray selectedJSONArray;
    private JSONObject selectedJSONObject;

    /**
     * Constructor. Sets the file to the parent property
     * and calls the method to parse the file.
     *
     * @param f File from input
     */
    public ParserJSON(File f) {
        super.setInFile(f);
        parseObject();
    }

    /**
     * Parses file into a JSONObject.
     */
    void parseObject() {
        // Reads and parses the file
        try {
            this.reader = new FileReader(super.getInFile());
            this.jsonParser = new JSONParser();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Turns contents of JSON file into a JSONObject
        try {
            this.selectedJSONObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the JSONObject from the file selected.
     *
     * @return JSONObject from file selected
     */
    public JSONObject getJSONObject() {
        return selectedJSONObject;
    }

    /**
     * Get the main JSONArray from the file selected.
     *
     * @return JSONArray from file selected
     */
    public JSONArray getJSONArray(String nameOfArray) {
        JSONArray selectedJSONArray;
        selectedJSONArray = (JSONArray) selectedJSONObject.get(nameOfArray);
        return selectedJSONArray;
    }
}
