package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/10/18
 *
 */

public class PropertyAlreadyExistException extends Exception{

    private String title;
    private String header;

    public PropertyAlreadyExistException(String title, String header, String message) {
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
