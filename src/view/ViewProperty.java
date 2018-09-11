/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * this class is used to generate view property UI, when
 * a user clicks on a particular Property
 *
 */

public class ViewProperty {

    private Stage stage;
    private VBox completeUI;
    private HBox functionBtns;

    public ViewProperty() {
        this.stage = new Stage();
        this.completeUI = new VBox();
        this.functionBtns = new HBox();
    }

    public void generateViewPropertyUI() {

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Property Viewer");

        GridPane rentalPropertyDetails = new GridPane();
        GridPane rentalRecordDetails = new GridPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rentalRecordDetails);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(700, 200);

        Image image = new Image(this.getClass().getResource("images/sampleHouse1.png").toString(), 300, 300, true, true);
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

        Button rentBtn = new Button("Rent Property");
        Button returnPropertyBtn = new Button("Return Property");
        Button performMaintenanceBtn = new Button("Perform Maintenance");
        Button completeMaintenanceBtn = new Button("Complete Maintenance");

        rentalRecordDetails.add(rentalrec1, 0, 0);
        rentalRecordDetails.add(rentalrec2, 0, 1);
        rentalRecordDetails.add(rentalrec3, 0, 2);
        rentalRecordDetails.add(rentalrec4, 0, 3);
        rentalRecordDetails.add(rentalrec5, 0, 4);
        rentalRecordDetails.add(rentalrec6, 0, 5);
        rentalRecordDetails.add(rentalrec7, 0, 6);
        rentalRecordDetails.add(rentalrec8, 0, 7);


        rentalPropertyDetails.add(streetNumber, 0, 4, 2, 1);
        rentalPropertyDetails.add(streetName, 0, 5, 2, 1);
        rentalPropertyDetails.add(suburb, 0, 6, 2, 1);
        rentalPropertyDetails.add(numberOfBedrooms, 0, 7, 2, 1);
        rentalPropertyDetails.add(propertyType, 0, 8, 2, 1);
        rentalPropertyDetails.add(description, 0, 9, 2, 2);
        rentalPropertyDetails.add(propertyStatus, 0, 11, 2, 1);
        rentalPropertyDetails.add(lastMaintenanceDate, 0, 12, 2, 1);

        this.functionBtns.getChildren().addAll(rentBtn, returnPropertyBtn, performMaintenanceBtn, completeMaintenanceBtn);
        this.functionBtns.setSpacing(25);

        this.completeUI.setPadding(new Insets(20,20,20,20));
        this.completeUI.setSpacing(15);

        rentalPropertyDetails.setVgap(5);

        this.completeUI.getChildren().addAll(imageView, rentalPropertyDetails, scrollPane, this.functionBtns);
        this.completeUI.setAlignment(Pos.CENTER);
        rentalPropertyDetails.setAlignment(Pos.CENTER_LEFT);
        this.functionBtns.setAlignment(Pos.CENTER);

        //styling the page
        completeUI.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        completeUI.getStyleClass().add("viewPropertyDialog-pane");
        rentalPropertyDetails.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        rentalPropertyDetails.getStyleClass().add("viewPropertyDialog-pane");
        rentalRecordDetails.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        rentalRecordDetails.getStyleClass().add("viewPropertyDialog-pane");

        this.stage.setScene(new Scene(completeUI, 800, 750));
        this.stage.showAndWait();

    }
}
