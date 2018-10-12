/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */

package view;

import controller.DataRequestHandler;
import controller.RentalRecordsOperationsHandler;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PremiumSuit;
import model.RentalProperty;
import model.RentalRecord;
import utility.exception.InvaliOperationException;

import java.util.Collections;
import java.util.List;

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
    private TableView<RentalRecordTable> rentalRecords;
    private TableColumn<RentalRecordTable, String> rentDate;
    private TableColumn<RentalRecordTable, String> estimatedReturnDate;
    private TableColumn<RentalRecordTable, String> actualReturnDate;
    private TableColumn<RentalRecordTable, String> rentalFee;
    private TableColumn<RentalRecordTable, String> lateFee;
    private TableColumn<RentalRecordTable, String> custID;
    private StackPane tableStackPane;
    private Label propertyTypeAndStatus;
    private GridPane rentalPropertyDetails;
    private Button rentBtn;
    private Button returnPropertyBtn;
    private Button performMaintenanceBtn;
    private Button completeMaintenanceBtn;
    private Label lastMaintenanceDate;


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
        this.rentalFee = new TableColumn("Rental Fee (in $)");
        this.lateFee = new TableColumn("Late Fee (in $)");
        this.custID = new TableColumn("Customer ID");
        this.tableStackPane = new StackPane();
        this.rentalPropertyDetails = new GridPane();
        this.rentBtn = new Button("Rent Property");
        this.returnPropertyBtn = new Button("Return Property");
        this.performMaintenanceBtn = new Button("Perform Maintenance");
        this.completeMaintenanceBtn = new Button("Complete Maintenance");
        this.propertyTypeAndStatus = new Label();
        this.lastMaintenanceDate = new Label();

    }

    public void generateViewPropertyUI() {

        //block user interaction with other windows, until this window has been taken care of
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Property Viewer");

        this.tableStackPane.setPrefSize(700, 200);

        //load view property pane with rental records in another thread
        DataRequestHandler dataRequestHandler = new DataRequestHandler();
        dataRequestHandler.requestAllRentalRecords(this, rentalProperty.getPropertyID());

        String imagePath = rentalProperty.getImagePath();

        ImageView imageView;
        Image image;
        try {
            image = new Image(this.getClass().getResource("images/"+imagePath).toString(), 500, 500, true, true);
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
        Label rentalRate = new Label(String.format("%s : %s", "Rate", this.rentalProperty.getRentalRate()));
        Label description = new Label(String.format("%s : %s", "Description", this.rentalProperty.getDescription()));

        this.showPropertyStatus();

        rentalPropertyDetails.add(propertyID, 0, 5, 4, 1);
        rentalPropertyDetails.add(streetNumber, 0, 6, 4, 1);
        rentalPropertyDetails.add(streetName, 0, 7, 4, 1);
        rentalPropertyDetails.add(suburb, 0, 8, 4, 1);
        rentalPropertyDetails.add(numberOfBedrooms, 0, 9, 4, 1);
        rentalPropertyDetails.add(rentalRate, 0, 10, 4, 1);
        rentalPropertyDetails.add(description, 0, 13, 4, 3);

        if (this.rentalProperty instanceof PremiumSuit) {
            this.showLastMaintenanceDate();
        }

        this.functionBtns.getChildren().addAll(rentBtn, returnPropertyBtn, performMaintenanceBtn, completeMaintenanceBtn);
        this.functionBtns.setSpacing(10);
        this.functionBtns.setAlignment(Pos.CENTER_RIGHT);
        rentBtn.setMaxWidth(Double.MAX_VALUE);
        returnPropertyBtn.setMaxWidth(Double.MAX_VALUE);
        performMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);
        completeMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);


        //configuring buttons
        this.closeBtn.setOnAction(event -> this.stage.close());

        rentBtn.setOnAction(event -> {
            PropertyOperationsUI propertyOperationsUI = new PropertyOperationsUI(this);
            propertyOperationsUI.rentPropertyUI();
        });

        returnPropertyBtn.setOnAction(event -> {
            PropertyOperationsUI propertyOperationsUI = new PropertyOperationsUI(this);
            propertyOperationsUI.returnPropertyUI();
        });

        performMaintenanceBtn.setOnAction(event -> {

            RentalRecordsOperationsHandler rentalRecordsOperationsHandler = new RentalRecordsOperationsHandler(this, null);

            try {
                rentalRecordsOperationsHandler.verifyPerformMaintenanceConditions();
            } catch (InvaliOperationException e) {
                AlertBox alertBox = new AlertBox();
                alertBox.generateWarningAlertBox(e.getTitle(), e.getHeader(), e.getMessage());
            }
        });

        completeMaintenanceBtn.setOnAction(event -> {
            PropertyOperationsUI propertyOperationsUI = new PropertyOperationsUI(this);
            propertyOperationsUI.completeMaintenanceUI();
        });

        this.topHalfPage.getChildren().addAll(imageView, this.functionBtns);
        this.topHalfPage.setSpacing(50);
        this.topHalfPage.setAlignment(Pos.CENTER);

        this.completeUI.setPadding(new Insets(20, 20, 20, 20));
        this.completeUI.setSpacing(20);

        rentalPropertyDetails.setVgap(5);

        this.completeUI.getChildren().addAll(this.topHalfPage, rentalPropertyDetails, tableStackPane, this.closeBtn);
        this.completeUI.setAlignment(Pos.CENTER_RIGHT);
        rentalPropertyDetails.setAlignment(Pos.CENTER_LEFT);

        //styling the page
        completeUI.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        completeUI.getStyleClass().add("viewPropertyDialog-pane");

        this.stage.setScene(new Scene(completeUI, 1050, 900));
        this.stage.showAndWait();

    }

    public void updateView(List<RentalRecord> recordsFound) {


        //disable property function buttons on the basis of property status
        this.switchButtons();

        this.showPropertyStatus();

        if (this.rentalProperty instanceof PremiumSuit) {
            this.showLastMaintenanceDate();
        }

        this.rentalRecords = new TableView();

        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        rentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        estimatedReturnDate.setCellValueFactory(new PropertyValueFactory<>("estimatedReturnDate"));
        actualReturnDate.setCellValueFactory(new PropertyValueFactory<>("actualReturnDate"));
        rentalFee.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        lateFee.setCellValueFactory(new PropertyValueFactory<>("lateFee"));

        RentalRecordTable rentalRecordTable;
        String none = "none";

        if (recordsFound != null) {

            //keep the most recent record on top
            Collections.reverse(recordsFound);

            for (RentalRecord rentalRecord : recordsFound) {

                if (rentalRecord.getActualReturnDate() != null) {
                    rentalRecordTable = new RentalRecordTable(rentalRecord.getCustID(), rentalRecord.getRentDate().toString(), rentalRecord.getEstimatedReturnDate().toString(),
                            rentalRecord.getActualReturnDate().toString(), Double.toString(rentalRecord.getRentalFee()), Double.toString(rentalRecord.getLateFee()));
                    rentalRecords.getItems().add(rentalRecordTable);

                } else {
                    rentalRecordTable = new RentalRecordTable(rentalRecord.getCustID(), rentalRecord.getRentDate().toString(), rentalRecord.getEstimatedReturnDate().toString(),
                            none, none, none);
                    rentalRecords.getItems().add(rentalRecordTable);

                }
            }
        }

        this.rentalRecords.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        this.rentalRecords.getStyleClass().add("table");

        //making rental record table
        this.rentalRecords.getColumns().addAll(custID, rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee);

        this.rentalRecords.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rentalRecords.prefWidthProperty().bind(Bindings.add(-15, tableStackPane.widthProperty()));
        tableStackPane.getChildren().add(rentalRecords);


    }

    //disabling property function buttons on the basis of property status
    private void switchButtons() {
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
    }

    private void showPropertyStatus() {
        //        rentalPropertyDetails.getChildren().remove(propertyTypeAndStatus);
        propertyTypeAndStatus.setText("");
        propertyTypeAndStatus = new Label(this.rentalProperty.getPropertyType().toUpperCase() + " " + this.rentalProperty.getPropertyStatus().toUpperCase());
        propertyTypeAndStatus.setTextFill(Color.PALEVIOLETRED);
        rentalPropertyDetails.add(propertyTypeAndStatus, 0, 4, 4, 1);

    }

    private void showLastMaintenanceDate() {
        lastMaintenanceDate.setText("");
        lastMaintenanceDate = new Label(String.format("%s : %s", "Last Maintenance Date", ((PremiumSuit) this.rentalProperty).getLastMaintenanceDate().toString()));
        rentalPropertyDetails.add(lastMaintenanceDate, 0, 12, 4, 1);
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }
}
