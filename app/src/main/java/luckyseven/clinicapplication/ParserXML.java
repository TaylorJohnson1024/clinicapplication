package luckyseven.clinicapplication;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser class with a focus on parsing an XML file
 * into a Document object.
 */
public class ParserXML extends Parser {
    private Document selectedXMLDocument;

    /**
     * Constructor. Sets the file to the parent property
     * and calls the method to parse the file.
     *
     * @param f File from input
     */
    public ParserXML(File f) {
        super.setInFile(f);
        parseObject();
    }

    /**
     * Parse file into XML Document.
     */
    void parseObject() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(super.getInFile());

            // See link for normalization process
            // https://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            this.selectedXMLDocument = doc;
        } catch (Exception ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the XML Document from the file selected.
     *
     * @return Document from the file selected
     */
    public Document getXMLDocument() {
        return selectedXMLDocument;
    }
}
