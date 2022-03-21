package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static model.Inventory.lookUpPart;
/**This class creates a product object as well as setters and getters.*/
public class Product{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
/**This method creates a product object.
 * All the parameters for the product object are here.
 * @param id product ID.
 * @param name product name.
 * @param price product price.
 * @param stock product stock.
 * @param max product stock max.
 * @param min product stock min.*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
/**Getter for product ID.
 * Gets the product ID.
 * @return the product ID.*/
    public int getId() {
        return id;
    }
/**Setter for the product ID.
 * Sets the product ID.
 * @param id the products ID.*/
    public void setId(int id) {
        this.id = id;
    }
/**Getter for the product name.
 * Gets the product name.
 * @return  name the products Name.*/
    public String getName() {
        return name;
    }
/**Setter for the product name.
 * Sets the product name.
 * @param name the products name.*/
    public void setName(String name) {
        this.name = name;
    }
/**Getter for product price.
 * Gets the product price.
 * @return the product price.*/
    public double getPrice() {
        return price;
    }
/**Setter for the product price.
 * Sets the product price.
 * @param price the products price.*/
    public void setPrice(double price) {
        this.price = price;
    }
/**Getter for product Stock.
 * Gets the product Stock.
 * @return the product Stock.*/
    public int getStock() {
        return stock;
    }
/**Setter for the product stock.
 * Sets the product stock.
 * @param stock the products stock.*/
    public void setStock(int stock) {
        this.stock = stock;
    }
/**Getter for product stock min.
 * Gets the product stock min.
 * @return the product stock min.*/
    public int getMin() {
        return min;
    }
/**Setter for the product stock min.
 * Sets the product stock min.
 * @param min the products stock min.*/
    public void setMin(int min) {
        this.min = min;
    }
/**Getter for product stock max.
 * Gets the product stock max.
 * @return the product max.*/
    public int getMax() {
        return max;
    }
/**Setter for the product stock max.
 * Sets the product stock max.
 * @param max the products stock max.*/
    public void setMax(int max) {
        this.max = max;
    }
/**Adds part to the products associated parts list.
 * A part is added to the associatedParts list.
 * @param part the part to be added.*/
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
/**Deletes a part from the associatedParts list.
 * A part is selected and found with the parts ID then deleted.
 * @param selectedAssociatedPart the part to be deleted.
 * @return false if part does not exist and true if part is found.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        Part partDelete = lookUpPart(selectedAssociatedPart.getId());

        if (partDelete == null) {
            System.out.println("Part does not exist");
            return false;
        } else {
            associatedParts.remove(partDelete);
            System.out.println("Part deleted");
            return true;
        }
    }
/**Gets all of the parts in the associatedParts list.
 * All parts in the associatedParts list are returned.
 * @return All parts in associatedParts.*/
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
