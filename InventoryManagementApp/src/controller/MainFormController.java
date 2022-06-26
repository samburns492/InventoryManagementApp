package controller;

import InventoryManagementApp.Inventory;
import InventoryManagementApp.Outsourced;
import InventoryManagementApp.Part;
import InventoryManagementApp.Product;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;




/** Creates a controller class which handles main form functions and associated FXML file.  
 */
public class MainFormController implements Initializable {
    
    
    public TableView<Part> partsTable;
    public TableColumn partsTableId;
    public TableColumn partsTableName;
    public TableColumn partsTableInv;
    public TableColumn partsTableUnit;
    public TableView<Product> productsTable;
    public TableColumn productsTableId;
    public TableColumn productsTableName;
    public TableColumn productsTableInv;
    public TableColumn productsTableUnit;
    
    private ObservableList<Part> invParts = Inventory.getAllParts();
    private ObservableList<Product> invProducts = Inventory.getAllProducts();
    private Product selectedProduct = null;
    private Part selectedPart = null;   

    public Outsourced selectedPartOut = null;
    public TextField searchBar;
    public TextField searchBarProd;

    /** Method launches the add in house part form.No data object is handed off.
     @param actionEvent when the user clicks the add button in the part section of the screen.
     * @throws java.io.IOException
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/AddPartinHouse.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part In-House");
        stage.setScene(scene);
        stage.show();
    }
    
    /** Method launches either the modify in house or outsourced modify part form.The method passes the selected part using the setter method.
     @param actionEvent when the user clicks the modify button in the part section of the screen.
     * @throws java.io.IOException
     */
    public void toModifyPart (ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
       
        
        if (selectedPart == null) {
            alert.setHeaderText("Error");
            alert.setContentText("No Part Selected");
            alert.showAndWait();
        } else {
            if (selectedPart instanceof Outsourced) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartOutsourced.fxml"));
                Parent root = loader.load();
                
                ModifyPartOutsourcedController tempPartModMain = loader.getController();
                tempPartModMain.setterPartMod(selectedPart);
                
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Modify Part In-House");
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartInHouse.fxml"));
                Parent root = loader.load();
                
                ModifyPartinHouseController tempPartModMain = loader.getController();
                tempPartModMain.setterPartMod(selectedPart);
                
                
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Modify Part In-House");
                stage.setScene(scene);
                stage.show();
             
            }
        }
    }
    
    /** Method launches the add product  form.No data object is handed off.
     @param actionEvent when the user clicks the add button in the product section of the screen.
     * @throws java.io.IOException
     */  
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
        
    }
    
    /** Method launches the modify product form.The method passes the selected product using the setter method.
     @param actionEvent when the user clicks the modify button in the product section of the screen.
     * @throws java.io.IOException
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            alert.setHeaderText("Error");
            alert.setContentText("No Product Selected");
            alert.showAndWait();
        } else {
          
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent root = loader.load();
            
            ModifyProductController tempPartModMain = loader.getController();
            tempPartModMain.setterProdMod(selectedProduct);
            
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /** Deletes the selected part from the parts table view and the inventory.
     * @param actionEvent
     * @throws java.io.IOException 
     */
    public void deletePart(ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Alert alert2 = new Alert(AlertType.INFORMATION);
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            alert2.setHeaderText("Error");
            alert2.setContentText("No Part Selected");
            alert2.showAndWait();
        } else {
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete the " + selectedPart.getName() + " part?" );
       
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() ==  ButtonType.OK){
                Inventory.deletePart(selectedPart);
            }
        
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /** Method removes a selected product from the application inventory.The method will error if a product with at least one associated part is selected.
     @param actionEvent when the user clicks the delete button in the product section of the screen.
     * @throws java.io.IOException
     */
    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Alert alert2 = new Alert(AlertType.INFORMATION);
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> tempPartslist = FXCollections.observableArrayList();
        
        if(selectedProduct == null) {
            alert2.setHeaderText("Error");
            alert2.setContentText("No Product Selected");
            alert2.showAndWait();
            return;
        } else {
            tempPartslist = selectedProduct.getAllAssociatedParts();
        }
        
        if(tempPartslist.size() != 0) {
            
            alert2.setHeaderText("Error");
            alert2.setContentText("Delete Product with one or more associated part is not allowed. Please modify first.");
            alert2.showAndWait();
            
        } else {
           
            alert.setHeaderText("Delete Product");
            alert.setContentText("Are you sure you want to delete the " + selectedProduct.getName() + " product?" );
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() ==  ButtonType.OK){
                Inventory.deleteProduct(selectedProduct);
            }
        }
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    
    /** Method exits the application.
     @param actionEvent when the user clicks the exit button.
     * @throws java.io.IOException
     */
    public void exitApp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    
    /** Method takes input in the part search bar and returns one or more part in tableview if found in inventory. 
     @param actionEvent when the user clicks inside the part search bar and then presses enter.
     * @throws java.io.IOException
     */
    public void searchPart(ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(AlertType.INFORMATION);
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
        partsTable.getSelectionModel().select(0);
        partsTable.getFocusModel().focus(0);
        searchBar.setText("");
    }
    
    /** Method takes input in the product search bar and returns one or more product in tableview if found in inventory. 
     @param actionEvent when the user clicks inside the product search bar and then presses enter.
     * @throws java.io.IOException
     */
    public void searchProduct (ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        ObservableList<Product> tempProductslist = null;
        String x = searchBarProd.getText();
        int tempproductId = 0;
       
        if (searchBarProd.getText().isEmpty())  {
                alert.setHeaderText("Search is empty!");
                alert.setContentText("No user input found");
                alert.showAndWait();
                tempProductslist = Inventory.getAllProducts();
        } else {
             
            tempProductslist = Inventory.lookupProduct(x);
        
            if(tempProductslist.size()== 0){
           
                try {
                    tempproductId = Integer.parseInt(x);
                    Product tempProduct = Inventory.lookupProduct(tempproductId);
                
                    if (tempProduct == null) {
                        alert.setHeaderText("No results found");
                        alert.setContentText("Search returned no results!");
                        alert.showAndWait();
                        tempProductslist = Inventory.getAllProducts();
                    } else {
                        tempProductslist.add(Inventory.lookupProduct(tempproductId));
                    }
                } catch (NumberFormatException e) {
                    alert.setHeaderText("No result");
                    alert.setContentText("No result found!");
                    alert.showAndWait();
                    tempProductslist = Inventory.getAllProducts();
                }
            }
        }
       
        productsTable.setItems(tempProductslist);
        productsTable.requestFocus();
        productsTable.getSelectionModel().select(0);
        productsTable.getFocusModel().focus(0);
        searchBarProd.setText("");
    }
    
    /** Method initializes the main form and sets the two tables
     * @param url
     * @param resourceBundle.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        
        
        partsTable.setItems(invParts);
        partsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTable.setItems(invProducts);
        productsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
            
    }
    
}
