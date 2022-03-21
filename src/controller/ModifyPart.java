package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainForm.modifyIndex;
/**This class is used as a controller for the ModifyProduct page.*/
public class ModifyPart implements Initializable {
    Stage stage;
    Parent scene;

    public RadioButton inHouseRadio;
    public RadioButton outSourcedRadio;
    public Button saveButton;
    public Button cancelButton;
    //Text Fields
    public TextField modifyID;
    public TextField modifyName;
    public TextField modifyInv;
    public TextField modifyPrice;
    public TextField modifyMax;
    public TextField modifyMin;
    public TextField inHouseOutsourced;
    //Labels
    public Label changeableArea;
    //For modifying across controllers
    int partIndex = modifyIndex();

    static int id;

/**This method changes the changeable area text.
 * The changeable area text is changed to machine ID.
 * @param actionEvent actionEvent.*/
    public void modifyInHouseHandler(ActionEvent actionEvent) {
        changeableArea.setText("Machine ID");
    }
/**This method changes the changeable area text.
 * The changeable area text is changed to Company name.
 * @param actionEvent actionEvent.*/
    public void modifyOutsourcedHandler(ActionEvent actionEvent) {
        changeableArea.setText("Company Name");
    }
/**This method handles the actions after the save button is pressed.
 * The saveHandler saves the information placed in the text fields and creates a new part object.
 * RUNTIME ERROR: NumberFormatException, a try catch block was added.
 * @param actionEvent actionEvent.
 * @throws IOException FXMLLoader.*/
    public void saveHandler(ActionEvent actionEvent) throws IOException {

        String partName = modifyName.getText();
        String partInventory = modifyInv.getText();
        String partCost = modifyPrice.getText();
        String partMax = modifyMax.getText();
        String partMin = modifyMin.getText();
        String partCompanyOS = inHouseOutsourced.getText();

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

                int id = Integer.parseInt(modifyID.getText());
                String name = modifyName.getText();
                double price = Double.parseDouble(modifyPrice.getText());
                int stock = Integer.parseInt(modifyInv.getText());
                int max = Integer.parseInt(modifyMax.getText());
                int min = Integer.parseInt(modifyMin.getText());
                String companyName = inHouseOutsourced.getText();

                if (stock < min || stock > max) {

                    var alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Warning");
                    alert2.setContentText("Stock value must be in between min and max");
                    alert2.showAndWait().ifPresent((btnType) -> {
                        clearDialogOptionSelections();
                    });

                } else {

                    if (outSourcedRadio.isSelected()) {
                        Outsourced outsourced = new Outsourced(id, name, price, stock, max, min, companyName);
                        outsourced.setId(id);
                        outsourced.setName(name);
                        outsourced.setPrice(price);
                        outsourced.setStock(stock);
                        outsourced.setMax(max);
                        outsourced.setMin(min);
                        outsourced.setCompanyName(companyName);

                        Inventory.updatePart(partIndex, outsourced);
                    } else {
                        int machineID = Integer.parseInt(inHouseOutsourced.getText());
                        InHouse inHouse = new InHouse(id, name, price, stock, max, min, machineID);
                        inHouse.setId(id);
                        inHouse.setName(name);
                        inHouse.setPrice(price);
                        inHouse.setStock(stock);
                        inHouse.setMax(max);
                        inHouse.setMin(min);
                        inHouse.setMachineId(machineID);

                        Inventory.updatePart(partIndex, inHouse);
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
/**This method works with the Alert boxes.
 * This method must be here for the alert boxes.*/
    private void clearDialogOptionSelections() {
    }
/**This method cancels out of the form.
 * After clicking the cancel button this method returns to the main form.
 * @param actionEvent actionEvent.
 * @throws IOException FXMLLoader.*/
    public void cancelHandler(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
/**This method sends the part information from the main form.
 * Information added from the AddPart form is held in the MainForm and sent here.
 * @param receivedPart the previous information from the part.*/
    public void sendPart(Part receivedPart) {

        modifyID.setText(String.valueOf(receivedPart.getId()));
        modifyName.setText(receivedPart.getName());
        modifyInv.setText(String.valueOf(receivedPart.getStock()));
        modifyPrice.setText(String.valueOf(receivedPart.getPrice()));
        modifyMin.setText(String.valueOf(receivedPart.getMin()));
        modifyMax.setText(String.valueOf(receivedPart.getMax()));

        if (receivedPart instanceof Outsourced) {
            outSourcedRadio.setSelected(true);
            changeableArea.setText("Company Name");
            inHouseOutsourced.setText(((Outsourced) receivedPart).getCompanyName());

        } else if (receivedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            changeableArea.setText("Machine ID");
            inHouseOutsourced.setText(String.valueOf(((InHouse) receivedPart).getMachineId()));
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
