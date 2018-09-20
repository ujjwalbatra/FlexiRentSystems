/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

package view;

import javafx.beans.binding.Bindings;
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
import model.PremiumSuit;
import model.RentalProperty;

/*
 * this class is used to generate view property UI, when
 * a user clicks on a particular Property
 *
 */

public class ViewProperty {

    private Stage stage;
    private VBox completeUI;
    private VBox functionBtns;
    private HBox topHalfPage;
    private Button closeBtn;
    private RentalProperty rentalProperty;
    private TableView rentalRecords;
    private TableColumn rentDate;
    private TableColumn estimatedReturnDate;
    private TableColumn actualReturnDate;
    private TableColumn rentalFee;
    private TableColumn lateFee;
    private TableColumn custID;

    public ViewProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
        this.stage = new Stage();
        this.completeUI = new VBox();
        this.functionBtns = new VBox();
        this.topHalfPage = new HBox();
        this.closeBtn = new Button("Close");
        this.rentalRecords = new TableView();
        this.rentDate = new TableColumn("Rent Date");
        this.estimatedReturnDate = new TableColumn("Estimated Return Date");
        this.actualReturnDate = new TableColumn("Actual Return Date");
        this.rentalFee = new TableColumn("Rental Fee");
        this.lateFee = new TableColumn("Late Fee");
        this.custID = new TableColumn("Customer ID");
    }

    public void generateViewPropertyUI() {

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Property Viewer");

        GridPane rentalPropertyDetails = new GridPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rentalRecords);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(700, 200);

        String imagePath = rentalProperty.getImagePath();

        Image image = new Image(this.getClass().getResource(imagePath).toString(), 350, 350, true, true);
        ImageView imageView = new ImageView(image);

        Label streetNumber = new Label(String.format("%s : %s", "Street Number", this.rentalProperty.getStreetNumber()));
        Label streetName = new Label(String.format("%s : %s", "Street name", this.rentalProperty.getStreetName()));
        Label suburb = new Label(String.format("%s : %s", "Suburb", this.rentalProperty.getSuburb()));
        Label numberOfBedrooms = new Label(String.format("%s : %s", "Number of Bedrooms", this.rentalProperty.getNumberOfBedrooms()));
        Label description = new Label(String.format("%s : %s", "Description", this.rentalProperty.getPropertyID()));
        Label lastMaintenanceDate;

        Label propertyTypeAndStatus = new Label(this.rentalProperty.getPropertyType().toUpperCase() + " - " + this.rentalProperty.getPropertyStatus().toUpperCase());

        Button rentBtn = new Button("Rent Property");
        Button returnPropertyBtn = new Button("Return Property");
        Button performMaintenanceBtn = new Button("Perform Maintenance");
        Button completeMaintenanceBtn = new Button("Complete Maintenance");

        rentalPropertyDetails.add(propertyTypeAndStatus, 0, 4, 4, 1);
        rentalPropertyDetails.add(streetNumber, 0, 5, 4, 1);
        rentalPropertyDetails.add(streetName, 0, 6, 4, 1);
        rentalPropertyDetails.add(suburb, 0, 7, 4, 1);
        rentalPropertyDetails.add(numberOfBedrooms, 0, 8, 4, 1);
        rentalPropertyDetails.add(description, 0, 10, 4, 3);

        if (this.rentalProperty instanceof PremiumSuit) {
            lastMaintenanceDate = new Label(String.format("%s : %s", "Last Maintenance Date:", ((PremiumSuit) this.rentalProperty).getLastMaintenanceDate().toString()));
            rentalPropertyDetails.add(lastMaintenanceDate, 0, 13, 4, 1);
        }

        this.functionBtns.getChildren().addAll(rentBtn, returnPropertyBtn, performMaintenanceBtn, completeMaintenanceBtn);
        this.functionBtns.setSpacing(10);
        this.functionBtns.setAlignment(Pos.CENTER_RIGHT);
        rentBtn.setMaxWidth(Double.MAX_VALUE);
        returnPropertyBtn.setMaxWidth(Double.MAX_VALUE);
        performMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);
        completeMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);

        //making rental record table
        this.rentalRecords.getColumns().addAll(rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee, custID);
        this.rentalRecords.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rentalRecords.prefWidthProperty().bind(Bindings.add(-5, scrollPane.widthProperty()));


        //configuring close button
        this.closeBtn.setOnAction(event -> this.stage.close());

        //disabling property function buttons on the basis of status
        switch (this.rentalProperty.getPropertyStatus()) {
            case "available":

                returnPropertyBtn.setDisable(true);
                completeMaintenanceBtn.setDisable(true);
                performMaintenanceBtn.setDisable(false);
                rentBtn.setDisable(false);

                break;
            case "rented":

                returnPropertyBtn.setDisable(false);
                completeMaintenanceBtn.setDisable(true);
                performMaintenanceBtn.setDisable(true);
                rentBtn.setDisable(true);

                break;
            case "under maintenance":

                returnPropertyBtn.setDisable(true);
                completeMaintenanceBtn.setDisable(false);
                performMaintenanceBtn.setDisable(true);
                rentBtn.setDisable(true);

                break;
        }

        rentBtn.setOnAction(event -> {
            PropertyOperationsUI propertyOperationsUI = new PropertyOperationsUI(this.rentalProperty);
            propertyOperationsUI.rentPropertyUI();
        });

        this.topHalfPage.getChildren().addAll(imageView, this.functionBtns);
        this.topHalfPage.setSpacing(50);
        this.topHalfPage.setAlignment(Pos.CENTER);

        this.completeUI.setPadding(new Insets(20, 20, 20, 20));
        this.completeUI.setSpacing(20);

        rentalPropertyDetails.setVgap(5);

        this.completeUI.getChildren().addAll(this.topHalfPage, rentalPropertyDetails, scrollPane, this.closeBtn);
        this.completeUI.setAlignment(Pos.CENTER_RIGHT);
        rentalPropertyDetails.setAlignment(Pos.CENTER_LEFT);

        //styling the page
        completeUI.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        completeUI.getStyleClass().add("viewPropertyDialog-pane");

        this.stage.setScene(new Scene(completeUI, 700, 800));
        this.stage.showAndWait();

    }
}
