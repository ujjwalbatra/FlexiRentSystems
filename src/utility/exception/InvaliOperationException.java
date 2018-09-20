package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

public class InvaliOperationException extends Exception{
    private String title;
    private String header;

    public InvaliOperationException(String title, String header, String message) {
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
