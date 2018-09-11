/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import model.RentalProperty;

/*
 * this class is used to generate view property UI, when
 * a user clicks on a particular Property
 *
 */

public class ViewProperty {
    public void generateViewPropertyUI() {
        Dialog<RentalProperty> dialog = new Dialog();

        //block user interaction with other windows, until this window has been taken care of
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setTitle("Property Viewer");

        // Set the button types.
        ButtonType closeBtn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeBtn);

        GridPane rentalPropertyDetails = new GridPane();
        GridPane rentalRecordDetails = new GridPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rentalRecordDetails);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(115, 100);

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

        Label rentalrec1 = new Label("record : ");
        Label rentalrec2 = new Label("record : ");
        Label rentalrec3 = new Label("record: ");
        Label rentalrec4 = new Label("record : ");
        Label rentalrec5 = new Label("record: ");
        Label rentalrec6 = new Label("record : ");
        Label rentalrec7 = new Label("record : ");
        Label rentalrec8 = new Label("record : ");

        Button rentBtn = new Button("Rent");
        Button returnPropertyBtn = new Button("Return");
        Button performMaintenanceBtn = new Button("Perform Maintenance");
        Button completeMaintenanceBtn = new Button("Complete Maintenance");

        rentalPropertyDetails.setHgap(10);
        rentalPropertyDetails.setVgap(10);
        rentalPropertyDetails.setPadding(new Insets(20, 20, 20, 20));

        rentalRecordDetails.add(rentalrec1, 0, 0);
        rentalRecordDetails.add(rentalrec2, 0, 1);
        rentalRecordDetails.add(rentalrec3, 0, 2);
        rentalRecordDetails.add(rentalrec4, 0, 3);
        rentalRecordDetails.add(rentalrec5, 0, 4);
        rentalRecordDetails.add(rentalrec6, 0, 5);
        rentalRecordDetails.add(rentalrec7, 0, 6);
        rentalRecordDetails.add(rentalrec8, 0, 7);

        rentalPropertyDetails.add(imageView, 0, 0, 4, 2);
        rentalPropertyDetails.add(streetNumber, 0, 4, 2, 1);
        rentalPropertyDetails.add(streetName, 0, 5, 2, 1);
        rentalPropertyDetails.add(suburb, 0, 6, 2, 1);
        rentalPropertyDetails.add(numberOfBedrooms, 0, 7, 2, 1);
        rentalPropertyDetails.add(propertyType, 0, 8, 2, 1);
        rentalPropertyDetails.add(description, 0, 9, 2, 2);
        rentalPropertyDetails.add(propertyStatus, 0, 11, 2, 1);
        rentalPropertyDetails.add(lastMaintenanceDate, 0, 12, 2, 1);
        rentalPropertyDetails.add(scrollPane, 0, 13, 4, 3);

        rentalPropertyDetails.add(rentBtn, 0, 16);
        rentalPropertyDetails.add(returnPropertyBtn, 1, 16);
        rentalPropertyDetails.add(performMaintenanceBtn, 2, 16);
        rentalPropertyDetails.add(completeMaintenanceBtn, 3, 16);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setHalignment(rentBtn, HPos.CENTER);
        GridPane.setHalignment(returnPropertyBtn, HPos.CENTER);
        GridPane.setHalignment(performMaintenanceBtn, HPos.CENTER);
        GridPane.setHalignment(completeMaintenanceBtn, HPos.CENTER);

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("viewPropertyDialog-pane");

        dialog.getDialogPane().setContent(rentalPropertyDetails);
        dialog.showAndWait();

    }
}
