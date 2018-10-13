package utility.exception;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/10/18
 *
 */

public class PropertyAlreadyExistException extends InvalidOperationException{

    public PropertyAlreadyExistException(String title, String header, String message) {
        super(title, header, message);

    }

}
