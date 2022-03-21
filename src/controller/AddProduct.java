package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.lookUpPart;
/**This class is used as a controller for the AddProduct page.*/
public class AddProduct implements Initializable {
    Stage stage;
    Parent scene;

    public TextField productID;
    public TextField productName;
    public TextField productInv;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public TextField productSearchBar;
    //Top Table
    public TableColumn<Part, Integer> topIDCol;
    public TableColumn<Part, String> topNameCol;
    public TableColumn<Part, Integer> topInvCol;
    public TableColumn<Part, Double> topPriceCol;
    //Bottom Table
    public TableColumn<Part, Integer> botIDCol;
    public TableColumn<Part, String> botNameCol;
    public TableColumn<Part, Integer> botInvCol;
    public TableColumn<Part, Double> botPriceCol;
    //Buttons
    public Button addButton;
    public Button removePartButton;
    public Button savButton;
    public Button cancelButton;
    public Button productSearchButton;
    //Tables
    public TableView<Part> topTable;
    public TableView<Part> bottomTable;

    static int id;

    private ObservableList<Part> selectedProductParts = FXCollections.observableArrayList();
/**The addHandler method adds a part to be associated with the product.
 * The selected part is added to the selectedProductParts list.
 * @param actionEvent actionEvent*/
    @FXML
    void addHandler(ActionEvent actionEvent) {

        Part selectedPart = topTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("You must select a Part to add");
            alert.showAndWait().ifPresent((btnType) -> {
                clearDialogOptionSelections();
            });
        } else {

            selectedProductParts.add(selectedPart);

        }
    }
/**The removeHandler removes a part from being associated with the product.
 * The selected part is removed from the bottom table as well as the selectedProductParts list.
 * @param actionEvent actionEvent.*/
    @FXML
    void removeHandler(ActionEvent actionEvent) {

        Part removePart = bottomTable.getSelectionModel().getSelectedItem();

        if (removePart == null) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("WARNING PART MUST BE SELECTED");
            alert.setContentText("You must select a Part to remove");
            alert.showAndWait().ifPresent((btnType) -> {
                clearDialogOptionSelections();
            });
        } else {
            var alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Remove?");
            alert.setContentText("Are you sure you want to remove this part?");
            alert.showAndWait().ifPresent((btnType) -> {
                if (btnType == ButtonType.OK) {

                    selectedProductParts.remove(removePart);

                } else if (btnType == ButtonType.CANCEL) {
                    //Closes Application
                }
                clearDialogOptionSelections();
            });
        }
    }
/**This saveHandler creates a new product object.
 * When save is clicked a new product object is created and the parts in selectedProductParts are added to associatedParts list.
 * RUNTIME ERROR: NumberFormatException, a try catch block was added.
 * @param actionEvent actionEvent.*/
    @FXML
    void saveHandler(ActionEvent actionEvent) throws IOException {

        String productNameString = productName.getText();
        String productInventoryString = productInv.getText();
        String productCostString = productPrice.getText();
        String productMaxString = productMax.getText();
        String productMinString = productMax.getText();

        if (productNameString == null || productNameString.length() == 0 || productInventoryString == null ||
                productInventoryString.length() == 0 || productCostString == null || productCostString.length() == 0 ||
                productMaxString.length() == 0 || productMaxString == null || productMinString.length() == 0 || productMinString == null) {

            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("You must fill in all fields");
            alert.showAndWait().ifPresent((btnType) -> {
                clearDialogOptionSelections();
            });

        } else {

            try {
                String name = productName.getText();
                double price = Double.parseDouble(productPrice.getText());
                int stock = Integer.parseInt(productInv.getText());
                int max = Integer.parseInt(productMax.getText());
                int min = Integer.parseInt(productMin.getText());

                if (stock < min || stock > max) {

                    var alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Warning");
                    alert2.setContentText("Stock value must be in between min and max");
                    alert2.showAndWait().ifPresent((btnType) -> {
                        clearDialogOptionSelections();
                    });

                } else {

                    if(selectedProductParts.size() == 0) {

                        var alert2 = new Alert(Alert.AlertType.WARNING);
                        alert2.setTitle("Warning");
                        alert2.setHeaderText("Warning");
                        alert2.setContentText("At least one part should be added");
                        alert2.showAndWait().ifPresent((btnType) -> {
                            clearDialogOptionSelections();
                        });

                    } else {

                        Product newProduct = new Product(++id, name, price, stock, min, max);

                        for (Part selectedProductPart : selectedProductParts) {
                            newProduct.addAssociatedPart(selectedProductPart);
                        }

                        Inventory.addProduct(newProduct);

                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }

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
/**This method sends back to main form.
 * This method sends back to main form.
 * @param actionEvent actionEvent.*/
    @FXML
    void cancelHandler(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
/**This method searches for the entered part.
 * This method looks for an integer or partial string to find the part.
 * @param actionEvent actionEvent.*/
    @FXML
    void searchHandler(ActionEvent actionEvent) {
        String query = productSearchBar.getText();

        ObservableList<Part> parts = lookUpPart(query);

        if (parts.size() == 0) {
            try {
                int idNumber = Integer.parseInt(query);
                Part part = lookUpPart(idNumber);
                if (parts != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException e){
                //Ignore
            }
        }

        topTable.setItems(parts);
        productSearchBar.setText("");
    }
/**This method works with the Alert boxes.
 * This method must be here for the alert boxes.*/
    private void clearDialogOptionSelections() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        topTable.setItems(getAllParts());

        topIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bottomTable.setItems(selectedProductParts);

        botIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        botNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        botInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        botPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
