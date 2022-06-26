package controller;

import InventoryManagementApp.Inventory;
import InventoryManagementApp.Part;
import InventoryManagementApp.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**  Creates a controller class which handles modifying a product and associated FXML file.
 * RUNTIME ERROR Description: One logical error I corrected in the application was the how the associated parts were handled in the modify product controller.
 The issue was that I was trying to use the tempProdMod object which is the object passed from the main form screen. I would use it to add associated parts
 and remove them to update the tables in the modified product form. The problem was if you click cancel and return to the main form the added and removed parts persisted. 
 To remedy this I used an observable array list invAssocParts that is private to the modify product form to update both tables and then is only saved to the selected product once save is clicked.
 If cancel is clicked the list is cleared and no changes are saved to the product that was selected to be modified. The objects in question are the private tempProcduct Mod declared below and the 
 private observable array list invAssocParts also declared below.   
 */
public class ModifyProductController implements Initializable {

    public TableView<Part> partsTable;
    public TableView<Part> assocpartsTableMod;
    public TableColumn partsTableId;
    public TableColumn partsTableName;
    public TableColumn partsTableInv;
    public TableColumn partsTableUnit;
    public TableColumn assocpartsTableId;
    public TableColumn assocpartsTableName;
    public TableColumn assocpartsTableInv;
    public TableColumn assocpartsTableUnit;
    public TextField idfield;
    public TextField namefield;
    public TextField invfield;
    public TextField pricefield;
    public TextField maxfield;
    public TextField minfield;
    public TextField searchBar;
    
    private ObservableList<Part> invParts = Inventory.getAllParts();
    private ObservableList<Product> invProducts = Inventory.getAllProducts();
    public ObservableList<Part> invAssocParts = FXCollections.observableArrayList();
    private ObservableList<Part> partRemoveList = FXCollections.observableArrayList();
    private ObservableList<Part> partAddList = FXCollections.observableArrayList();
    private Product tempProductMod = null;
    private int indexProduct;
    
    /** *  The search associated part method.Will take a string (case sensitive) or integer and return associated part.
       @param actionEvent when the user presses enter after clicking the search text field (search bar).
     * @throws java.io.IOException
     */
    public void searchPart(ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ObservableList<Part> tempPartslist = null; 
        String x = searchBar.getText();
        
        if (searchBar.getText().isEmpty())  {
                alert.setHeaderText("Search is empty!");
                alert.setContentText("No user input found");
                alert.showAndWait();
                tempPartslist = Inventory.getAllParts();
        } else {
        
            tempPartslist = Inventory.lookupPart(x);
        
            if(tempPartslist.size()== 0){
           
                try {
                    int temppartId = Integer.parseInt(x);
                    Part tempPart = Inventory.lookupPart(temppartId);
                
                    if (tempPart == null) {
                        alert.setHeaderText("No results found");
                        alert.setContentText("Search returned no results!");
                        alert.showAndWait();
                        tempPartslist = Inventory.getAllParts();
                    } else {
                        tempPartslist.add(Inventory.lookupPart(temppartId));
                    }
                } catch (NumberFormatException e) {
                    alert.setHeaderText("No result");
                    alert.setContentText("No result found!");
                    alert.showAndWait();
                    tempPartslist = Inventory.getAllParts();
                }
            }
        }
        
        partsTable.setItems(tempPartslist);
        partsTable.requestFocus();
        partsTable.getSelectionModel();
        partsTable.getFocusModel().focus(0);
        searchBar.setText("");
    }
    
    /** The initialize method of the controller class. 
     * @param url
     * @param rb 
     */
    public void initialize(URL url, ResourceBundle rb) {
       
    }  
    
    /** This method allows the main form controller to instance the selected product.
     * @param temp
     */
    public void setterProdMod(Product temp) {
        
        this.tempProductMod = temp;
        invAssocParts = tempProductMod.getAllAssociatedParts();
        
        idfield.setText(String.valueOf(tempProductMod.getId()));
        namefield.setText(tempProductMod.getName());
        invfield.setText(String.valueOf(tempProductMod.getStock()));
        pricefield.setText(String.valueOf(tempProductMod.getPrice()));
        maxfield.setText(String.valueOf(tempProductMod.getMin()));
        minfield.setText(String.valueOf(tempProductMod.getMax()));
        
        partsTable.setItems(invParts);
        partsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        assocpartsTableMod.setItems(invAssocParts);
        assocpartsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocpartsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocpartsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocpartsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    /** This method removes any parts add and puts back any parts removed from observable list and goes to the main form.
        @param actionEvent when user clicks cancel button on the modify product pane.
     * @throws java.io.IOException
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
       
        
        for (Part p: partAddList) {
                invAssocParts.remove(p);
            }
        
        for (Part f: partRemoveList) {
                invAssocParts.add(f);
            }
        
        partAddList.clear();
        partRemoveList.clear();
        
        invAssocParts = tempProductMod.getAllAssociatedParts();
        assocpartsTableMod.setItems(invAssocParts);
        
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    
    /** This method takes a selected part and adds it to two observable lists.
        @param actionEvent user clicks Add in the modify product screen.
     * @throws java.io.IOException
     */
    public void toAddProductMod (ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); 
        Part tempPartMod = partsTable.getSelectionModel().getSelectedItem();
        invAssocParts = assocpartsTableMod.getItems();
        
        if(tempPartMod == null){
            alert.setHeaderText("Error");
            alert.setContentText("No part selected! Add failed");
            alert.showAndWait();
        } else {
            partAddList.add(tempPartMod);
            invAssocParts.add(tempPartMod);
            assocpartsTableMod.setItems(invAssocParts);
        }
    }
    
    /** This method asks user to confirm and then removes selected part from one observable list and adds to another.
        @param actionEvent user remove in the modify product pane.
     * @throws java.io.IOException
     */
    public void toRemoveAssoc (ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        Part tempPartRemove = assocpartsTableMod.getSelectionModel().getSelectedItem();
        invAssocParts = assocpartsTableMod.getItems();
        
        if (tempPartRemove == null) {
            alert2.setHeaderText("Error");
            alert2.setContentText("No part selected");
            alert2.showAndWait();
        } else {
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete the " + tempPartRemove.getName() + " part?" );
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() ==  ButtonType.OK){
                partRemoveList.add(tempPartRemove);
                invAssocParts.remove(tempPartRemove);
            }
        } 
        
    }
    
    /** This method checks all modify part fields then updates the product with associated data.
     * RUNTIME ERROR Description: One logical error I corrected in the application was the how the associated parts were handled in the modify product controller.
     * The issue was that I was trying to use the tempProdMod object which is the object passed from the main form screen. I would use it to add associated parts
     * and remove them to update the tables in the modified product form. The problem was if you click cancel and return to the main form the added and removed parts persisted. 
     * To remedy this I used an observable array list invAssocParts that is private to the modify product form to update both tables and then is only saved to the selected product once save is clicked.
     * If cancel is clicked the list is cleared and no changes are saved to the product that was selected to be modified. The objects in question are the private tempProcduct Mod declared below and the 
     * private observable array list invAssocParts also declared below.
     * @param actionEvent when user click save in the modify product form.
     * @throws java.io.IOException
     */
    public void toSaveProduct(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        try { if (Integer.parseInt(namefield.getText()) >= 0) {
                alert.setHeaderText("Error");
                alert.setContentText("Name must be a string!");
                alert.showAndWait();
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
                return;
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        
        try {   int a = Integer.parseInt(invfield.getText());
                double b = Double.parseDouble(pricefield.getText());
                int c = Integer.parseInt(maxfield.getText());     
                int d = Integer.parseInt(minfield.getText());
            } catch (NumberFormatException e) {
                alert.setHeaderText("Error");
                alert.setContentText("Invalid input for one or more variables!");
                alert.showAndWait();
                
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
                return;
            }
        
        if (namefield.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Part name field is empty! Modify Product failed");
            alert.showAndWait();
            
        } else if (Integer.parseInt(maxfield.getText()) <= Integer.parseInt(minfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Max is less than or equal to min! Modify Product failed");
            alert.showAndWait();
           
        } else if (Integer.parseInt(invfield.getText()) > Integer.parseInt(maxfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Inventory is greater then maximum! Add Part failed");
            alert.showAndWait();
            return;
        } else if (Integer.parseInt(invfield.getText()) < Integer.parseInt(minfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Inventory is less then minimum! Add Part failed");
            alert.showAndWait();
            return;
        } else if (Double.parseDouble(pricefield.getText()) <= 0.00) {
            alert.setHeaderText("Error");
            alert.setContentText("Price needs to be greater than 0.00. Modify Product failed");
            alert.showAndWait();
      
        } else { 
            indexProduct = invProducts.indexOf(tempProductMod);
            tempProductMod = new Product(Integer.parseInt(idfield.getText()),namefield.getText(),Double.parseDouble(pricefield.getText()),Integer.parseInt(invfield.getText()),Integer.parseInt(maxfield.getText()),Integer.parseInt(minfield.getText()));
            invAssocParts = assocpartsTableMod.getItems();
            tempProductMod.setAllAssociatedParts(invAssocParts);
            Inventory.updateProduct(indexProduct, tempProductMod);
            
            alert.setHeaderText("Success!");
            alert.setContentText("Product succesfully modified!");
            alert.showAndWait();
        }
        
        partAddList.clear();
        partRemoveList.clear();
        
        assocpartsTableMod.setItems(invAssocParts);
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    } 
}
