package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 18/09/18
 *
 */

public class IncompleteInputException extends FlexiRentException{


    public IncompleteInputException(String title, String header, String message) {
        super(title, header, message);
    }

}
