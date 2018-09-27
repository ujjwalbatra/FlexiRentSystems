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

import java.sql.ResultSet;

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
    private ScrollPane scrollPane;
    private Label propertyTypeAndStatus;
    private GridPane rentalPropertyDetails;


    public ViewProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
        this.stage = new Stage();
        this.completeUI = new VBox();
        this.functionBtns = new VBox();
        this.topHalfPage = new HBox();
        this.closeBtn = new Button("Close");
        this.rentDate = new TableColumn("Rent Date");
        this.estimatedReturnDate = new TableColumn("Estimated Return Date");
        this.actualReturnDate = new TableColumn("Actual Return Date");
        this.rentalFee = new TableColumn("Rental Fee");
        this.lateFee = new TableColumn("Late Fee");
        this.custID = new TableColumn("Customer ID");
        this.scrollPane = new ScrollPane();
        this.rentalPropertyDetails = new GridPane();
        this.propertyTypeAndStatus = new Label();
    }

    public void generateViewPropertyUI() {

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Property Viewer");

        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setPrefSize(700, 200);

        updateView(null);

        String imagePath = rentalProperty.getImagePath();

        ImageView imageView;
        Image image;
        try {
            image = new Image(this.getClass().getResource(imagePath).toString(), 500, 500, true, true);
            imageView = new ImageView(image);
        } catch (NullPointerException e) {
            image = new Image(this.getClass().getResource("images/sample.jpg").toString(), 200, 200, true, true);
            imageView = new ImageView(image);
        }

        Label propertyID = new Label(String.format("%s : %s", "Property ID", this.rentalProperty.getPropertyID()));
        Label streetNumber = new Label(String.format("%s : %s", "Street Number", this.rentalProperty.getStreetNumber()));
        Label streetName = new Label(String.format("%s : %s", "Street name", this.rentalProperty.getStreetName()));
        Label suburb = new Label(String.format("%s : %s", "Suburb", this.rentalProperty.getSuburb()));
        Label numberOfBedrooms = new Label(String.format("%s : %s", "Number of Bedrooms", this.rentalProperty.getNumberOfBedrooms()));
        Label description = new Label(String.format("%s : %s", "Description", this.rentalProperty.getDescription()));
        Label lastMaintenanceDate;


        Button rentBtn = new Button("Rent Property");
        Button returnPropertyBtn = new Button("Return Property");
        Button performMaintenanceBtn = new Button("Perform Maintenance");
        Button completeMaintenanceBtn = new Button("Complete Maintenance");

        rentalPropertyDetails.add(propertyID, 0, 5, 4, 1);
        rentalPropertyDetails.add(streetNumber, 0, 6, 4, 1);
        rentalPropertyDetails.add(streetName, 0, 7, 4, 1);
        rentalPropertyDetails.add(suburb, 0, 8, 4, 1);
        rentalPropertyDetails.add(numberOfBedrooms, 0, 9, 4, 1);
        rentalPropertyDetails.add(description, 0, 11, 4, 3);

        if (this.rentalProperty instanceof PremiumSuit) {
            lastMaintenanceDate = new Label(String.format("%s : %s", "Last Maintenance Date:", ((PremiumSuit) this.rentalProperty).getLastMaintenanceDate().toString()));
            rentalPropertyDetails.add(lastMaintenanceDate, 0, 14, 4, 1);
        }

        this.functionBtns.getChildren().addAll(rentBtn, returnPropertyBtn, performMaintenanceBtn, completeMaintenanceBtn);
        this.functionBtns.setSpacing(10);
        this.functionBtns.setAlignment(Pos.CENTER_RIGHT);
        rentBtn.setMaxWidth(Double.MAX_VALUE);
        returnPropertyBtn.setMaxWidth(Double.MAX_VALUE);
        performMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);
        completeMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);


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
            PropertyOperationsUI propertyOperationsUI = new PropertyOperationsUI(this);
            propertyOperationsUI.rentPropertyUI(this.rentalProperty);
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

        this.stage.setScene(new Scene(completeUI, 1050, 900));
        this.stage.showAndWait();

    }

    public void updateView(ResultSet resultSet){

        propertyTypeAndStatus.setText("");
        propertyTypeAndStatus = new Label(this.rentalProperty.getPropertyType().toUpperCase() + " - " + this.rentalProperty.getPropertyStatus().toUpperCase());
        rentalPropertyDetails.add(propertyTypeAndStatus, 0, 4, 4, 1);


        this.rentalRecords = new TableView();

        //making rental record table
        this.rentalRecords.getColumns().addAll(rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee, custID);
        this.rentalRecords.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rentalRecords.prefWidthProperty().bind(Bindings.add(-15, scrollPane.widthProperty()));
        scrollPane.setContent(rentalRecords);

        if (resultSet != null){

        }

    }
}
