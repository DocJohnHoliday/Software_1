package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class uses the main lists allParts and allProducts.*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

/**This method adds parts to the allParts list.
 * newPart is added to allParts.
 @param newPart is a new part*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
/**This method adds products to the allProducts list.
 * newProduct is added to allProducts.
 * @param newProduct ia a new product*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
/**This method looks up parts.
 * An integer is used to look up the partId
 * @param partId is the parts unique ID number.
 * @return part if found or null otherwise.*/
    public static Part lookUpPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
/**This method looks up products.
 * An integer is used to look up the productId.
 * @param productId is the parts unique ID number.
 * @return product if found null otherwise.*/
    public static Product lookUpProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
/**This method looks up parts.
 * A partial string is compared to the name for looking up the parts name.
 * @param partName is the parts name.
 * @return the list of named parts.*/
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for(Part foundPart : allParts) {
            if (foundPart.getName().contains(partName)) {
                namedPart.add(foundPart);
            }
        }
        return namedPart;
     }
/**This method looks up products.
 * A partial string is compared to the name for looking up the product.
 * @param productName is the products name.
 * @return the list of named products.*/
     public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for(Product foundProduct : allProducts) {
            if (foundProduct.getName().contains(productName)) {
                namedProduct.add(foundProduct);
            }
        }
        return namedProduct;
     }
/**This method updates the chosen part.
 * The index from the list is given from the selected part.
 * @param index is the selected parts index from the list.
 * @param selectedPart is the chosen part.*/
     public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
     }

     /**This method updates the chosen product.
     * The index from the list is given from the selected product.
     * @param index is the selected products index from the list.
     * @param newProduct is the new product.*/
     public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
     }
/**This method deletes the chosen part.
 * The chosen parts ID is found using the lookUpPart method.
 * @return False is returned if the chosen part does not exist and true if part is found.
 * @param selectedPart is the chosen part.*/
     public static boolean deletePart(Part selectedPart) {
        Part partDelete = lookUpPart(selectedPart.getId());

        if (partDelete == null) {
            System.out.println("Part does not exist");
            return false;
        } else {
            allParts.remove(partDelete);
            System.out.println("Part deleted");
            return true;
        }
     }
/**This method deletes the chosen product.
 * The chosen products ID is found using the lookUpProduct method.
 * @return False is returned if the chosen product does not exist and true if product is found.
 * @param selectedProduct is the chosen product.*/
     public static boolean deleteProduct(Product selectedProduct) {
        Product productDelete = lookUpProduct(selectedProduct.getId());

        if (productDelete == null) {
            System.out.println("Product does not exist");
            return false;
        } else {
            allProducts.remove(productDelete);
            System.out.println("Product deleted");
            return true;
        }
     }
/**This method returns all of the parts in the allParts list.
 * All parts are returned from the list.
 * @return allParts.*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
/**This method returns all of the products in the allProducts list.
 * All products are returned from the list.
 * @return allProducts.*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
