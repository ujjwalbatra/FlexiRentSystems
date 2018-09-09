/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import model.RentalProperty;

import java.io.File;

public class ViewProperty {
    public void generateViewPropertyUI() {
        Dialog<RentalProperty> dialog = new Dialog();

        //block user interaction with other windows, until this window has been taken care of
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setTitle("Property Viewer");

        // Set the button types.
        ButtonType closeBtn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeBtn);

        GridPane gridPane = new GridPane();


        Image image = new Image(this.getClass().getResource("images/sampleHouse1.png").toString(), 350, 350, true, true);
        ImageView imageView = new ImageView(image);

        Label streetNumber = new Label("Street Number : ");
        Label streetName = new Label("Street Name : ");
        Label suburb = new Label("Suburb : ");
        Label numberOfBedrooms = new Label("Number Of Bedrooms : ");
        Label propertyType = new Label("Property Type : ");
        Label description = new Label("Description : ");
        Label lastMaintenanceDate = new Label("Last Maintenance Date : ");
        Label propertyStatus = new Label("Property Status : ");

        Button rentBtn = new Button("Rent");
        Button returnPropertyBtn = new Button("Return");
        Button performMaintenanceBtn = new Button("Perform Maintenance");
        Button completeMaintenanceBtn = new Button("Complete Maintenance");

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20,20));

        gridPane.add(imageView, 0 , 0, 4, 2);
        gridPane.add(streetNumber, 0,4,2,1);
        gridPane.add(streetName, 0,5,2,1);
        gridPane.add(suburb, 0,6,2,1);
        gridPane.add(numberOfBedrooms, 0,7,2,1);
        gridPane.add(propertyType, 0,8,2,1);
        gridPane.add(description, 0,9,2,2);
        gridPane.add(propertyStatus, 0,11,2,1);
        gridPane.add(lastMaintenanceDate, 0,12,2,1);

        gridPane.add(rentBtn, 0, 13);
        gridPane.add(returnPropertyBtn, 1, 13);
        gridPane.add(performMaintenanceBtn, 2, 13);
        gridPane.add(completeMaintenanceBtn, 3, 13);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setHalignment(rentBtn, HPos.CENTER);
        GridPane.setHalignment(returnPropertyBtn, HPos.CENTER);
        GridPane.setHalignment(performMaintenanceBtn, HPos.CENTER);
        GridPane.setHalignment(completeMaintenanceBtn, HPos.CENTER);

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("viewPropertyDialog-pane");

        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait();

    }
}
