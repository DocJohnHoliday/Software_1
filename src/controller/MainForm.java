package controller;

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
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.*;
/**This Class is the main form.*/
public class MainForm implements Initializable {
        Stage stage;
        Parent scene;

        //Search Bars
        public TextField searchFieldPart;
        public TextField searchFieldProduct;
        //Tables
        public TableView<Part> partTable;
        public TableView<Product> productTable;
        //Part table
        public TableColumn<Part, Integer> partIDCol;
        public TableColumn<Part, String> partNameCol;
        public TableColumn<Part, Integer> partInStockCol;
        public TableColumn<Part, Double> partPriceCol;
        //Product Table
        public TableColumn<Product,Integer> productIDCol;
        public TableColumn<Product, String> productNameCol;
        public TableColumn<Product, Integer> productInStockCol;
        public TableColumn<Product, Double> productPriceCol;
        //Buttons
        public Button addPartButton;
        public Button addProductButton;
        public Button deletePartButton;
        public Button deleteProductButton;
        public Button modifyPartButton;
        public Button modifyProductButton;
        public Button exitMain;
        //For modifying
        private static int index;

/**This method takes the user to the AddPart form.
 * This method takes the user to the AddPart form.
 * @param actionEvent actionEvent.*/
        @FXML
        void addPartHandler(ActionEvent actionEvent) throws IOException {

                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }
        /**This method takes the user to the AddProduct form.
         * This method takes the user to the AddProduct form.
         * @param actionEvent actionEvent.*/
        @FXML
        void addProductHandler(ActionEvent actionEvent) throws IOException {

                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }
/**This method deletes the selected part.
 * If no part is selected a Alert box shows and if a part is selected a confirmation box is shown.
 * @param actionEvent actionEvent.*/
        @FXML
        void deletePartHandler(ActionEvent actionEvent) throws IOException {

                Part selectedPart = partTable.getSelectionModel().getSelectedItem();

                if(selectedPart == null) {
                        var alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("You must select a Part to delete");
                        alert.showAndWait().ifPresent((btnType) -> {
                                clearDialogOptionSelections();
                        });
                } else {

                        var alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Delete?");
                        alert.setContentText("Are you sure you want to delete this product?");
                        alert.showAndWait().ifPresent((btnType) -> {
                                if (btnType == ButtonType.OK) {

                                        deletePart(selectedPart);

                                } else if (btnType == ButtonType.CANCEL) {
                                        //Closes
                                }
                                clearDialogOptionSelections();
                        });
                }
        }

/**This method deletes the selected product.
 * If no product is selected a Alert box shows and if a product is selected a confirmation box is shown.
 * @param actionEvent actionEvent.*/
        @FXML
        void deleteProductHandler(ActionEvent actionEvent) throws IOException {

                Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

                if (selectedProduct == null) {
                        var alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("You must select a Product to delete");
                        alert.showAndWait().ifPresent((btnType) -> {
                                clearDialogOptionSelections();
                        });
                } else {

                       if (selectedProduct.getAllAssociatedParts().size() == 0) {

                               var alert = new Alert(Alert.AlertType.CONFIRMATION);
                               alert.setTitle("Confirmation");
                               alert.setHeaderText("Delete?");
                               alert.setContentText("Are you sure you want to delete this product?");
                               alert.showAndWait().ifPresent((btnType) -> {
                                       if (btnType == ButtonType.OK) {

                                               deleteProduct(selectedProduct);

                                       } else if (btnType == ButtonType.CANCEL) {
                                               //
                                       }
                                       clearDialogOptionSelections();
                               });

                       } else {

                               var alert = new Alert(Alert.AlertType.WARNING);
                               alert.setTitle("Warning");
                               alert.setHeaderText("Warning");
                               alert.setContentText("You must delete parts from associated parts list before deleting product");
                               alert.showAndWait().ifPresent((btnType) -> {
                                       clearDialogOptionSelections();
                               });

                       }
                }

        }
/**This method takes the user to the ModifyPart form.
 * This method takes the user to the ModifyPart form.
 * @param actionEvent actionEvent.*/
        @FXML
        void modifyPartHandler(ActionEvent actionEvent) throws IOException {

                Part selectedPart = partTable.getSelectionModel().getSelectedItem();
                index = getAllParts().indexOf(selectedPart);

                if (selectedPart == null) {
                        var alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("You must select a Part to modify");
                        alert.showAndWait().ifPresent((btnType) -> {
                                clearDialogOptionSelections();
                        });
                } else {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
                        loader.load();

                        ModifyPart modifyPartController = loader.getController();
                        modifyPartController.sendPart(partTable.getSelectionModel().getSelectedItem());

                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();
                }
        }
/**This method takes the user to the ModifyProduct form.
 * This method takes the user to the ModifyProduct form.
 * @param actionEvent actionEvent.*/
        @FXML
        void modifyProductHandler(ActionEvent actionEvent) throws IOException {

                Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
                index = getAllProducts().indexOf(selectedProduct);

                if (selectedProduct == null) {
                        var alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Warning");
                        alert.setContentText("You must select a Product to modify");
                        alert.showAndWait().ifPresent((btnType) -> {
                                clearDialogOptionSelections();
                        });
                } else {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
                        loader.load();

                        ModifyProduct modifyProductController = loader.getController();
                        modifyProductController.sendProduct(productTable.getSelectionModel().getSelectedItem());

                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();
                }
        }
/**This method exits the application.
 * A Alert box asks for confirmation.
 * @param actionEvent actionEvent.*/
        @FXML
        void exitHandler(ActionEvent actionEvent) {
                var alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Exit?");
                alert.setContentText("Are you sure you want to exit?");
                alert.showAndWait().ifPresent((btnType) -> {
                        if (btnType == ButtonType.OK) {
                                System.exit(0);
                        } else if (btnType == ButtonType.CANCEL) {
                               //Closes Application
                        }
                        clearDialogOptionSelections();
                });
        }
/**This method searches for a entered part.
 * Either an id or partial string is used.
 * @param actionEvent actionEvent.*/
        @FXML
        void enterPartPressed(ActionEvent actionEvent) {
                String query = searchFieldPart.getText();

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

                partTable.setItems(partId);
                searchFieldPart.setText("");
        }
/**This method searches for a entered product.
 * Either an id or partial string is used.
 * @param actionEvent actionEvent.*/
        @FXML
        void enterProductPressed(ActionEvent actionEvent) {
                String query = searchFieldProduct.getText();

                ObservableList<Product> products = lookUpProduct(query);

                if (products.size() == 0) {
                        try {
                                int idNumber = Integer.parseInt(query);
                                Product product = lookUpProduct(idNumber);
                                if (products != null) {
                                        products.add(product);
                                }
                        } catch (NumberFormatException e){
                                //Ignore
                        }
                }

                productTable.setItems(products);
                searchFieldProduct.setText("");
        }
/**This method uses the index.
 * This method uses the product index for sending the product information to the modify page.
 * @return product index.*/
        public static int modifyIndex(){
                return index;
        }
/**This method works with the Alert boxes.
 * This method must be here for the alert boxes.*/
        private void clearDialogOptionSelections() {
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                partTable.setItems(getAllParts());
                productTable.setItems(getAllProducts());

                partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                partInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

                productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                productInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }
}

