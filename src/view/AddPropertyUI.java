package view;/*
 *
 * @project - FlexiRentSystems
 * @author - ujjwalbatra on 09/09/18
 *
 */


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


/*
 * this class will be used to generate UI for adding a property to database.
 */
public class AddPropertyUI {

    public void generateAddpropertyUI() {

        Dialog<RentalProperty> dialog = new Dialog();

        String dateFormat = "yyyy-MM-dd";

        dialog.setTitle("Add Property");
        dialog.setHeaderText("Add new property");

        //set the icon
        dialog.setGraphic(new ImageView(this.getClass().getResource("images/addProperty.png").toString()));

        // Set the button types.
        ButtonType addPropertyBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addPropertyBtn, ButtonType.CANCEL);

        //create form for user input
        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 20, 20, 20));

        Label streetNumber = new Label("Street Number : ");
        Label streetName = new Label("Street Name : ");
        Label suburb = new Label("Suburb : ");
        Label numberOfBedrooms = new Label("Number Of Bedrooms : ");
        Label propertyType = new Label("Property Type : ");
        Label description = new Label("Description : ");
        Label image = new Label("Upload image(png) : ");
        Label lastMaintenanceDate = new Label("Last Maintenance Date : ");

        TextField streetNumberInput = new TextField();
        TextField streetNameInput = new TextField();
        TextField suburbInput = new TextField();
        TextArea descriptionInput = new TextArea();
        DatePicker lastMaintenanceDateInput = new DatePicker();
        lastMaintenanceDateInput.setConverter(new StringConverter<LocalDate>() {

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

        streetNameInput.setPromptText("Street name");
        streetNumberInput.setPromptText("Street number");
        suburbInput.setPromptText("suburb");
        descriptionInput.setPromptText("Enter property description here.");

        //making a toogle button for property type
        ToggleGroup groupPropertyType = new ToggleGroup();
        ToggleGroup groupNumberOfBedrooms = new ToggleGroup();

        //toggle for proper types
        ToggleButton apartment = new ToggleButton("Apartment");
        ToggleButton premiumSuit = new ToggleButton("Premium Suit");

        //toggle for number of bedrooms
        ToggleButton one = new ToggleButton("1");
        ToggleButton two = new ToggleButton("2");
        ToggleButton three = new ToggleButton("3");

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

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("addPropertyDialog-pane");

        dialog.getDialogPane().setContent(form);
        dialog.showAndWait();
    }

}
