package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 18/09/18
 *
 */

public class InvalidInpuException extends Exception {
    private String title;
    private String header;

    public InvalidInpuException(String title, String header, String message) {
        super(message);
        this.title = title;
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }
}
