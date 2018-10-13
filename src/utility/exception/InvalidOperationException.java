package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 20/09/18
 *
 */

public class InvalidOperationException extends FlexiRentException{

    public InvalidOperationException(String title, String header, String message) {
        super(title, header,message);
    }

}
