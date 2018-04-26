package luckyseven.clinicapplication;

import java.io.File;

/**
 * Object intended for retrieving a file.
 *
 * @author Taylor Johnson
 * @see //www.examples.javacodegeeks.com/core-java/json/java-json-parser-example/
 * @see //www.geeksforgeeks.org/parse-json-java/
 * @see //www.stackoverflow.com/questions/10739128/how-to-create-multiple-directories-given-the-folder-names
 */
public class Input {
    private File inFile;
    private String fileType;

    /**
     * Constructor method for Input.
     */
    public Input() {
    }

    /**
     * Parses inFile to retrieve the file type.
     * Ex. file named foobar.txt would return "txt"
     *
     * @return String of the fileType
     * @see "https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java/21974043"
     */
    public String parseFileType() {
        String name = inFile.getName();
        try {
            this.fileType = name.substring(name.lastIndexOf(".") + 1);
            return fileType;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gets the save file from an already completed session of this application.
     * If the filepath provided does not yield a file, then the directories are made instead
     * and an error is thrown.
     *
     * @param filePath path of the saved file
     * @return the saved file
     */
    public File getSaveFile(String filePath) {
        try {
            File f = new File(filePath);
            return f;
        } catch (Exception e) {
            File f = new File(filePath);
            File directory = new File(f.getParent());
            directory.mkdirs();

            throw e;
        }
    }

    /**
     * @return -- the file that the user has selected for import
     */
    public File getFile() {
        return inFile;
    }

    public void setInFile(File inFile) {
        this.inFile = inFile;
        this.fileType = parseFileType();
    }

    /**
     * @return -- the file type of the file that the user has selected for import
     */
    public String getFileType() {
        return fileType;
    }
}
