package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */


import controller.ExitBtnHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.RentalProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/*
 * this class will be used to generate UI for adding a property to database.
 */
public class AddPropertyUI {

    Dialog<RentalProperty> dialog;
    TextField streetNumberInput;
    TextField streetNameInput;
    TextField suburbInput;
    TextArea descriptionInput;
    DatePicker lastMaintenanceDateInput;

    Label streetNumber;
    Label streetName;
    Label suburb;
    Label numberOfBedrooms;
    Label propertyType;
    Label description;
    Label image;
    Label lastMaintenanceDate;

    ToggleGroup groupPropertyType;
    ToggleGroup groupNumberOfBedrooms;
    ToggleButton apartment;
    ToggleButton premiumSuit;
    ToggleButton one;
    ToggleButton two;
    ToggleButton three;

    public AddPropertyUI() {
        this.dialog = new Dialog();
        this.streetNumberInput = new TextField();
        this.streetNameInput = new TextField();
        this.suburbInput = new TextField();
        this.descriptionInput = new TextArea();
        this.lastMaintenanceDateInput = new DatePicker();

        this.streetNumber = new Label("Street Number : ");
        this.streetName = new Label("Street Name : ");
        this.suburb = new Label("Suburb : ");
        this.numberOfBedrooms = new Label("Number Of Bedrooms : ");
        this.propertyType = new Label("Property Type : ");
        this.description = new Label("Description : ");
        this.image = new Label("Upload image(png) : ");
        this.lastMaintenanceDate = new Label("Last Maintenance Date : ");

        this.groupPropertyType = new ToggleGroup();
        this.groupNumberOfBedrooms = new ToggleGroup();
        this.apartment = new ToggleButton("Apartment");
        this.premiumSuit = new ToggleButton("Premium Suit");
        this.one = new ToggleButton("1");
        this.two = new ToggleButton("2");
        this.three = new ToggleButton("3");

    }

    public void generateAddpropertyUI() {


        String dateFormat = "yyyy-MM-dd";

        this.dialog.setTitle("Add Property");
        this.dialog.setHeaderText("Add new property");

        //set the icon
        this.dialog.setGraphic(new ImageView(this.getClass().getResource("images/addProperty.png").toString()));

        // Set the button types.
        ButtonType addPropertyBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.dialog.getDialogPane().getButtonTypes().addAll(addPropertyBtn, ButtonType.CANCEL);

        //create form for user input
        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 20, 20, 20));

        this.lastMaintenanceDateInput.setConverter(new StringConverter<LocalDate>() {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });

        //getting image input
        Button imageInput = new Button("Upload Image");

        //implementing file chooser ot get file from user
        imageInput.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select image resource");
            fileChooser.showOpenDialog(new Stage());
        });

        this.streetNameInput.setPromptText("Street name");
        this.streetNumberInput.setPromptText("Street number");
        this.suburbInput.setPromptText("suburb");
        this.descriptionInput.setPromptText("Enter property description here.");

        //grouping toggle button
        apartment.setToggleGroup(groupPropertyType);
        premiumSuit.setToggleGroup(groupPropertyType);

        //grouping number of bedroom toggle
        one.setToggleGroup(groupNumberOfBedrooms);
        two.setToggleGroup(groupNumberOfBedrooms);
        three.setToggleGroup(groupNumberOfBedrooms);

        HBox propertyTypeInput = new HBox();
        propertyTypeInput.getChildren().addAll(apartment, premiumSuit);

        HBox numberOfBedroomInput = new HBox();
        numberOfBedroomInput.getChildren().addAll(one, two, three);

        form.add(streetNumber, 0, 0);
        form.add(streetNumberInput, 1, 0);
        form.add(streetName, 0, 1);
        form.add(streetNameInput, 1, 1);
        form.add(suburb, 0, 2);
        form.add(suburbInput, 1, 2);
        form.add(propertyType, 0, 3);
        form.add(propertyTypeInput, 1, 3);

        //setting dependent objects as invisible, will make them visible when condition is satisfied
        lastMaintenanceDate.opacityProperty().set(0);
        lastMaintenanceDateInput.opacityProperty().set(0);
        numberOfBedrooms.opacityProperty().set(0);
        numberOfBedroomInput.opacityProperty().set(0);

        //listener for toggle button
        groupPropertyType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                boolean setVisibility = (new_toggle == apartment);
                int visibility, visibilityInverse;

                if (setVisibility) {
                    visibility = 0;
                    visibilityInverse = 1;
                } else {
                    visibility = 1;
                    visibilityInverse = 0;
                }

                //hide unwanted options
                lastMaintenanceDate.opacityProperty().set(visibility);
                lastMaintenanceDateInput.opacityProperty().set(visibility);
                numberOfBedrooms.opacityProperty().set(visibilityInverse);
                numberOfBedroomInput.opacityProperty().set(visibilityInverse);

                //disable hidden options
                lastMaintenanceDate.setDisable(setVisibility);
                lastMaintenanceDateInput.setDisable(setVisibility);
                numberOfBedrooms.setDisable(!setVisibility);
                numberOfBedroomInput.setDisable(!setVisibility);
                lastMaintenanceDateInput.getEditor().setEditable(false);

            }
        });

        form.add(numberOfBedrooms, 0, 4);
        form.add(numberOfBedroomInput, 1, 4);
        form.add(lastMaintenanceDate, 0, 4);
        form.add(lastMaintenanceDateInput, 1, 4);

        form.add(description, 0, 5);
        form.add(descriptionInput, 0, 6, 2, 3);
        form.add(image, 0, 9);
        form.add(imageInput, 1, 9);

        dialog.getDialogPane().setContent(form);

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("addPropertyDialog-pane");

        dialog.showAndWait();
        dialog.getDialogPane().setContent(form);

    }

    public void close() {
        this.dialog.close();
    }

    public void closeProcedure() {
        this.streetNameInput.clear();
        this.descriptionInput.clear();
        this.streetNumberInput.clear();
        this.suburbInput.clear();
    }

}
