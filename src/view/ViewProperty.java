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

    public ViewProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
        this.stage = new Stage();
        this.completeUI = new VBox();
        this.functionBtns = new VBox();
        this.topHalfPage = new HBox();
        this.closeBtn = new Button("Close");
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

        Image image = new Image(this.getClass().getResource("images/sampleHouse1.png").toString(), 350, 350, true, true);
        ImageView imageView = new ImageView(image);

        Label streetNumber = new Label("Street Number : " + this.rentalProperty.getStreetNumber());
        Label streetName = new Label("Street Name : " + this.rentalProperty.getStreetName());
        Label suburb = new Label("Suburb : " + this.rentalProperty.getSuburb());
        Label numberOfBedrooms = new Label("Number Of Bedrooms : " + this.rentalProperty.getNumberOfBedrooms());
        Label propertyType = new Label("Property - " + this.rentalProperty.getPropertyType());
        Label description = new Label("Description : " + this.rentalProperty.getDescription());
        Label lastMaintenanceDate;


        Label propertyStatus = new Label("Property Status : " + this.rentalProperty.getPropertyStatus());

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

        if (this.rentalProperty instanceof PremiumSuit) {
            lastMaintenanceDate = new Label("Last Maintenance Date : " + ((PremiumSuit) this.rentalProperty).getLastMaintenanceDate());
            rentalPropertyDetails.add(lastMaintenanceDate, 0, 12, 2, 1);
        }

        this.functionBtns.getChildren().addAll(rentBtn, returnPropertyBtn, performMaintenanceBtn, completeMaintenanceBtn);
        this.functionBtns.setSpacing(10);
        this.functionBtns.setAlignment(Pos.CENTER_RIGHT);
        rentBtn.setMaxWidth(Double.MAX_VALUE);
        returnPropertyBtn.setMaxWidth(Double.MAX_VALUE);
        performMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);
        completeMaintenanceBtn.setMaxWidth(Double.MAX_VALUE);

        this.closeBtn.setOnAction(event -> {
            this.stage.close();
        });

        this.topHalfPage.getChildren().addAll(imageView,this.functionBtns);
        this.topHalfPage.setSpacing(50);
        this.topHalfPage.setAlignment(Pos.CENTER);

        this.completeUI.setPadding(new Insets(20,20,20,20));
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
