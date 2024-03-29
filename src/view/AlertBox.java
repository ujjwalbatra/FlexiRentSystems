/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 07/09/18
 *
 */

package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/*
 *
 * This class is responsible for generating different types of alert boxes ,
 * to alert user about any error or confirmation of user action.
 *
 * */
public class AlertBox {

    /*
     *
     * generates an alert box of type warning. Which will tell the users
     * about esceptions, like invalid input or invalid operation.
     *
     * */
    public void generateWarningAlertBox(String title, String headerText,String contentText) {

        Dialog alert = new Alert(Alert.AlertType.WARNING);

        //block user interaction with other windows, until this window has been taken care of
        alert.initModality(Modality.APPLICATION_MODAL);

        //styling the alert box
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("myWarningDialog-pane");

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /*
     *
     * generates an alert box when the user is about to quit.
     * Just to confirm before quitting, to avoid data loss of
     * entered input.
     *
     */
    public Boolean confirmQuitting() {

        String title = "Quit",
                headerText = "Are you sure you want to quit?",
                contentText = "Click on ok to quit and cancel to stay.";

        Dialog alert = new Alert(Alert.AlertType.CONFIRMATION);

        //block user interaction with other windows, until this window has been taken care of
        alert.initModality(Modality.APPLICATION_MODAL);

        //styling the alert box
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("myQuittingDialog-pane");

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();

        //get response value, and act on the response appropriately
        if (result.get() == ButtonType.OK) {
            return  true;
        } else if (result.get() == ButtonType.CANCEL) {
            return false;
        }
        return false;
    }
}
