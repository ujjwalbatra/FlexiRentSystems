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
import model.RentalProperty;


/*
 * this class will be used to generate UI for adding a property to database.
 */
public class AddPropertyUI {

    public void generateAddpropertyUI() {

        Dialog<RentalProperty> dialog = new Dialog();

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

        boolean apartmentSelected = apartment.isSelected();

        //listener for toggle button
        groupPropertyType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == apartment) {
                    lastMaintenanceDate.opacityProperty().set(0);
                    lastMaintenanceDateInput.opacityProperty().set(0);
                    numberOfBedrooms.opacityProperty().set(1);
                    numberOfBedroomInput.opacityProperty().set(1);

                    lastMaintenanceDate.setDisable(true);
                    lastMaintenanceDateInput.setDisable(true);
                    numberOfBedrooms.setDisable(false);
                    numberOfBedroomInput.setDisable(false);
                } else if (new_toggle == premiumSuit) {
                    numberOfBedrooms.opacityProperty().set(0);
                    numberOfBedroomInput.opacityProperty().set(0);
                    lastMaintenanceDate.opacityProperty().set(1);
                    lastMaintenanceDateInput.opacityProperty().set(1);

                    numberOfBedrooms.setDisable(true);
                    numberOfBedroomInput.setDisable(true);
                    lastMaintenanceDate.setDisable(false);
                    lastMaintenanceDateInput.setDisable(false);


                }
            }
        });

        form.add(numberOfBedrooms, 0, 4);
        form.add(numberOfBedroomInput, 1, 4);
        form.add(lastMaintenanceDate, 0, 5);
        form.add(lastMaintenanceDateInput, 1, 5);

        form.add(description, 0, 6);
        form.add(descriptionInput, 0, 7, 2, 3);
        form.add(image, 0, 10);
        form.add(imageInput, 1, 10);

        //styling the dialog box
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/StyleUI.css").toExternalForm());
        dialogPane.getStyleClass().add("addPropertyDialog-pane");

        dialog.getDialogPane().setContent(form);
        dialog.showAndWait();
    }

}
