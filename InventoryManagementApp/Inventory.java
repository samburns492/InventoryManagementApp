package InventoryManagementApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates an inventory holds the static list of both products and parts
 * with associated methods. Includes assigning id numbers.
 */
public class Inventory {
    
    private static int partid = 2;
    private static int productid = 1;
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /** Adds a part object to the parts list and also sets the unique even ID of the part
     @param newPart is the part object to be added to inventory.
     */
    public static void addPart(Part newPart){
        newPart.setId(partid);
        partid = partid + 2;
        allParts.add(newPart);
    }
    
    /** Adds a product object to the parts list and also sets the unique odd ID of the product 
     @param newProduct is the product object to be added to inventory.
     */
    public static void addProduct(Product newProduct) {
        newProduct.setId(productid);
        productid = productid + 2;
        allProducts.add(newProduct);
    }
    
    /** Method to look up a part based on its id integer variable.
     @param partId integer id of the part.
     @return Returns a part if found in the all parts list.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        
        Part retrievePart = null;
        
        for(int i = 0; i < allParts.size(); i++) {
            Part x = allParts.get(i);
            
            if (x.getId() == partId) {
                retrievePart = x;
                break;
            }
        }
        return retrievePart;
    }
    
    /** Method to look up a product based on its id integer variable.
     @param productId integer id of the product.
     @return Returns a product if found in the all products list.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        
        Product retrieveProduct = null;
        
        for(int i = 0; i < allProducts.size(); i++) {
            Product x = allProducts.get(i);
            
            if (x.getId() == productId) {
                retrieveProduct = x;
                break;
            }
        }
        return retrieveProduct;
    }
    
    /** Method to look up a part based on its string name variable.
     @param partName String name of the part.
     @return Returns a part if found in the all parts list.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> idParts = FXCollections.observableArrayList();
        
        ObservableList<Part> allParts = Inventory.getAllParts();
        
        for(Part pt : allParts){
          
            if (pt.getName().contains(partName)) {
                idParts.add(pt);
            }
        }
        
        return idParts;
    }
    
    /** Method to look up a product based on its string product name variable.
     @param productName String name of the product.
     @return Returns a product if found in the all products list.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> idProducts = FXCollections.observableArrayList();
        
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        
        for(Product pd : allProducts){
          
            if (pd.getName().contains(productName)) {
                idProducts.add(pd);
            }
        }
        
        return idProducts;
    }
    
    /** Updates a part in the all parts list.
     @param index integer with the index location of the part.
     @param selectedPart part to be put into inventory.
     */
    public static void updatePart(int index,Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    /** Updates a product in the all products list.
     @param index integer with the index location of the product.
     @param selectedProduct Product to be put into inventory.
     */
    public static void updateProduct(int index,Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    
    /** Removes a part in the all parts list.
     @param selectedPart part to be removed from inventory.
     @return Returns true once called.
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }
    
    /** Removes a product in the all products list.
     @param selectedProduct product to be removed from inventory.
     @return Returns true once called.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }
    
    /** Method that returns a list of all parts in inventory
     @return Returns all parts in inventory.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /** Method that returns a list of all products in inventory
     @return Returns all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
}
