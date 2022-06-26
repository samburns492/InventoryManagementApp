package InventoryManagementApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Creates concrete class Product which is similar to a part but has a list of associated parts. 
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;   
    
    /** This method constructs the product object.
         @param id int product id.
         @param name string name.
         @param price double product price.
         @param stock int inventory level.
         @param min int minimum.
         @param max int maximum.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /** Returns the product id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /** Sets the product id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Gets the name of the product.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /** Sets the name of the product.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Gets the price of the product in double.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /** Sets the price of the product. 
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /** Gets the inventory level of the product.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /** Sets the inventory level of the product.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Gets the minimum inventory level of the product.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /** Sets the minimum inventory level of the product.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Gets the maximum inventory level of the product.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /** Sets the maximum inventory levle of the product. 
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Adds the an associated part to the associated parts observable list. 
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    /** This method deletes an associated part from the product ohject.
     @param selectedAssociatedPart the part to be remove from assoiated parts of the product.
     @return Returns true when called.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    
    /** Method returns a list off all associated parts of the product.
     @return Returns an observable list of parts. 
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    /** Method sets all of the associated parts of the product.
     @param list observable list of parts to set.
     */
    public void setAllAssociatedParts(ObservableList<Part> list) {
        associatedParts = list;
    }
}

