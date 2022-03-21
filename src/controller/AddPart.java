package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
/**This class is used as a controller for the AddPart page.*/
public class AddPart {
    Stage stage;
    Parent scene;

    public RadioButton partIHRadio;//In House Radio
    public RadioButton partORadio;//Out Sourced Radio
    public Label changeableArea;
    public TextField partInventoryAdded;
    public TextField partCostAdded;
    public TextField partMaxAdded;
    public TextField partMinAdded;
    public TextField companyOSAdded;
    public TextField partIDAdded;
    public TextField partNameAdd;

    static int id;
/**This method changes the changeable area text.
 * The changeable area text is changed to machine ID.
 * @param actionEvent actionEvent.*/
    @FXML
    void inHouseHandler(ActionEvent actionEvent) {
        changeableArea.setText("Machine ID");
    }
/**This method changes the changeable area text.
 * The changeable area text is changed to Company name.
 * @param actionEvent actionEvent.*/
    @FXML
    void outsourcedHandler(ActionEvent actionEvent) {
        changeableArea.setText("Company Name");
    }
/**This method handles the actions after the save button is pressed.
 * The saveHandler saves the information placed in the text fields and creates a new part object.
 * RUNTIME ERROR: NumberFormatException, a try catch block was added.
 * @param actionEvent actionEvent.*/
    @FXML
    void saveHandler(ActionEvent actionEvent) throws IOException {

        String partName = partNameAdd.getText();
        String partInventory = partInventoryAdded.getText();
        String partCost = partCostAdded.getText();
        String partMax = partMaxAdded.getText();
        String partMin = partMinAdded.getText();
        String partCompanyOS = companyOSAdded.getText();

        if (partName == null || partName.length() == 0 || partInventory == null ||
                partInventory.length() == 0 || partCost == null || partCost.length() == 0 ||
                partMax.length() == 0 || partMax == null || partMin.length() == 0 || partMin == null ||
                partCompanyOS == null || partCompanyOS.length() == 0) {

            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("You must fill in all fields");
            alert.showAndWait().ifPresent((btnType) -> {
                clearDialogOptionSelections();
            });

        } else {

            try {
                String name = partNameAdd.getText();
                double price = Double.parseDouble(partCostAdded.getText());
                int stock = Integer.parseInt(partInventoryAdded.getText());
                int max = Integer.parseInt(partMaxAdded.getText());
                int min = Integer.parseInt(partMinAdded.getText());
                String companyName = companyOSAdded.getText();

                if (stock < min || stock > max) {

                    var alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Warning");
                    alert2.setContentText("Stock value must be in between min and max");
                    alert2.showAndWait().ifPresent((btnType) -> {
                        clearDialogOptionSelections();
                    });

                } else {

                    if (partORadio.isSelected()) {
                        Inventory.addPart(new Outsourced(++id, name, price, stock, min, max, companyName));
                    } else {
                        int machineID = Integer.parseInt(companyOSAdded.getText());
                        Inventory.addPart(new InHouse(++id, name, price, stock, min, max, machineID));
                    }
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }

            } catch (NumberFormatException e) {

                var alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning");
                alert2.setHeaderText("Warning");
                alert2.setContentText("Price, stock, min, and max should be integers");
                alert2.showAndWait().ifPresent((btnType) -> {
                    clearDialogOptionSelections();
                });

            }
        }
    }
/**This method cancels out of the form.
 * After clicking the cancel button this method returns to the main form.
 * @param actionEvent actionEvent.*/
    @FXML
    void cancelHandler(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
/**This method works with the Alert boxes.
 * This method must be here for the alert boxes.*/
    private void clearDialogOptionSelections() {
    }

}
