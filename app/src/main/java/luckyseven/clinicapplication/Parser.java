package luckyseven.clinicapplication;

import java.io.File;

/**
 * An abstract class with a focus on parsing objects
 * into a usable format.
 */
abstract public class Parser {
    private File inFile;

    /**
     * All parsing class will have a method which parses
     * an object.
     */
    abstract void parseObject();

    /**
     * Sets inFile to the passed parameter File f
     *
     * @param f -- the file that inFile will be set to
     */
    public void setInFile(File f) {
        this.inFile = f;
    }

    /**
     * @return -- the File inFile
     */
    public File getInFile() {
        return inFile;
    }
}
