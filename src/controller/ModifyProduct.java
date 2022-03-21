package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainForm.modifyIndex;
import static model.Inventory.lookUpPart;

public class ModifyProduct implements Initializable {
    Stage stage;
    Parent scene;

    //TableView
    public TableView<Part> topModifyTable;
    public TableView<Part> bottomModifyTable;
    //Text Fields
    public TextField modifyProductID;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductPrice;
    public TextField modifyProductMax;
    public TextField modifyProductMin;
    public TextField productSearchBar;
    //Top Table Columns
    public TableColumn<Part, Integer> topIDCol;
    public TableColumn<Part, String> topNameCol;
    public TableColumn<Part, Integer> topInvCol;
    public TableColumn<Part, Double> topPriceCol;
    //Bottom Table Columns
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
    //For modifying across controllers
    int productIndex = modifyIndex();


    private Product product;

    private ObservableList<Part> selectedProductParts = FXCollections.observableArrayList();
    private ObservableList<Part> deletedProductParts = FXCollections.observableArrayList();


/**The addHandler method adds a part to be associated with the product.
 * The selected part is added to the selectedProductParts list.
 * @param actionEvent actionEvent*/
    public void addHandler(ActionEvent actionEvent) {

        Part selectedPart = topModifyTable.getSelectionModel().getSelectedItem();

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
/**This method works with the Alert boxes.
 * This method must be here for the alert boxes.*/
    private void clearDialogOptionSelections() {
    }

/**This saveHandler modifies the product.
 * When save is clicked the product is modified and the parts in selectedProductParts are added to associatedParts list.
 * RUNTIME ERROR: NumberFormatException, a try catch block was added.
 * @param actionEvent actionEvent.*/
    public void saveHandler(ActionEvent actionEvent) {


        String productNameString = modifyProductName.getText();
        String productInventoryString = modifyProductInv.getText();
        String productCostString = modifyProductPrice.getText();
        String productMaxString = modifyProductMax.getText();
        String productMinString = modifyProductMin.getText();

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

                int id = Integer.parseInt(modifyProductInv.getText());
                String name = modifyProductName.getText();
                double price = Double.parseDouble(modifyProductPrice.getText());
                int stock = Integer.parseInt(modifyProductInv.getText());
                int max = Integer.parseInt(modifyProductMax.getText());
                int min = Integer.parseInt(modifyProductMin.getText());

                if (stock < min || stock > max) {

                    var alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Warning");
                    alert2.setContentText("Stock value must be in between min and max");
                    alert2.showAndWait().ifPresent((btnType) -> {
                        clearDialogOptionSelections();
                    });

                } else {

                        Product newProduct = new Product(id, name, price, stock, min, max);

                        for (Part selectedProductPart : selectedProductParts) {
                            newProduct.addAssociatedPart(selectedProductPart);
                        }

                        for (Part deletedProductPart : deletedProductParts) {
                            newProduct.deleteAssociatedPart(deletedProductPart);
                        }

                        Inventory.updateProduct(productIndex, product);

                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                }
            } catch (NumberFormatException | IOException e) {

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
/**The removeHandler removes a part from being associated with the product.
 * The selected part is removed from the bottom table as well as the selectedProductParts list.
 * @param actionEvent actionEvent.*/
    public void removeHandler(ActionEvent actionEvent) {

        Part removePart = bottomModifyTable.getSelectionModel().getSelectedItem();

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
                    deletedProductParts.add(removePart);


                } else if (btnType == ButtonType.CANCEL) {
                    //Closes Application
                }
                clearDialogOptionSelections();
            });
        }
    }
/**This method sends back to main form.
 * This method sends back to main form.
 * @param actionEvent actionEvent.
 * @throws IOException FXMLLoader.*/
    public void cancelHandler(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
/**This method searches for the entered part.
 * This method looks for an integer or partial string to find the part.
 * @param actionEvent actionEvent.*/
    public void searchHandler(ActionEvent actionEvent) {

        String query = productSearchBar.getText();

        ObservableList<Part> partId = lookUpPart(query);

        if (partId.size() == 0) {
            try {
                int idNumber = Integer.parseInt(query);
                Part part = lookUpPart(idNumber);
                if (partId != null) {
                    partId.add(part);
                }
            } catch (NumberFormatException e){
                //Ignore
            }
        }

        topModifyTable.setItems(partId);
        productSearchBar.setText("");
    }
/**This method sends the product information from the main form.
 * Information added from the AddProduct form is held in the MainForm and sent here.
 * @param receivedProduct the previous information from the product.*/
    public void sendProduct(Product receivedProduct) {

        modifyProductID.setText(String.valueOf(receivedProduct.getId()));
        modifyProductName.setText(receivedProduct.getName());
        modifyProductInv.setText(String.valueOf(receivedProduct.getStock()));
        modifyProductPrice.setText(String.valueOf(receivedProduct.getPrice()));
        modifyProductMin.setText(String.valueOf(receivedProduct.getMin()));
        modifyProductMax.setText(String.valueOf(receivedProduct.getMax()));

        product = receivedProduct;

        selectedProductParts = product.getAllAssociatedParts();

        bottomModifyTable.setItems(selectedProductParts);

        }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        topModifyTable.setItems(Inventory.getAllParts());

        topIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        botIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        botNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        botInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        botPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
