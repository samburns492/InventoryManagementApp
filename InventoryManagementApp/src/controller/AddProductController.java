package controller;

import InventoryManagementApp.Inventory;
import InventoryManagementApp.Part;
import InventoryManagementApp.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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

/** Creates a controller class which handles adding an product and associated FXML file.  
 */
public class AddProductController implements Initializable {
    
    public TableView<Part> partsTable;
    public TableView<Part> assocpartsTable;
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
    
    private Product tempProduct = new Product(0,"empty",0.0,0,0,0);
    private ObservableList<Part> invParts = Inventory.getAllParts();
    private ObservableList<Part> invassocParts = null;
    
    /** Method initializes the add product form and sets the two tables
     * @param url
     * @param rb.
     */
    public void initialize(URL url, ResourceBundle rb) {
        
        idfield.setText("Auto gen -disabled");
        
        partsTable.setItems(invParts);
        partsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        assocpartsTable.setItems(invassocParts);
        assocpartsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocpartsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocpartsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocpartsTableUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
    /** This method launches the main form without any changes saved.
        @param actionEvent when user clicks cancel button on the add product pane.
     * @throws java.io.IOException
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
       
        
    }
    
    /** This method takes the selected part and adds it to product objects associated parts list. 
    @param actionEvent when the user clicks add button in the add product form. 
     * @throws java.io.IOException 
    */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Part tempPart = partsTable.getSelectionModel().getSelectedItem();
        
        if(tempPart == null){
            alert.setHeaderText("Error");
            alert.setContentText("No part selected! Add failed");
            alert.showAndWait();
        } else {
            tempProduct.addAssociatedPart(tempPart);
            invassocParts = tempProduct.getAllAssociatedParts();
            assocpartsTable.setItems(invassocParts);
        }
        
    }
    
    /** *  The search associated part method. Will take a string (case sensitive) or integer and return associated part.
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
    
    /** This method checks all modify part fields then adds a product to inventory with associated data.
        @param actionEvent when user click save in the modify product form.
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
                return;
            }
        
        if (namefield.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Part name field is empty! Add Product failed");
            alert.showAndWait();
            return;
        } else if (Integer.parseInt(maxfield.getText()) <= Integer.parseInt(minfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Max is less than or equal to min! Add Product failed");
            alert.showAndWait();
            return;
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
            alert.setContentText("Price needs to be greater than 0.00. Add Product failed");
            alert.showAndWait();
            return;
        } else { 
            Product x = new Product(0,namefield.getText(),Double.parseDouble(pricefield.getText()),Integer.parseInt(invfield.getText()),Integer.parseInt(maxfield.getText()),Integer.parseInt(minfield.getText()));
            
            if (invassocParts != null) {
                for (int i = 0; i < invassocParts.size(); i++) {
                x.addAssociatedPart(invassocParts.get(i));
                }
            }
            
            Inventory.addProduct(x);
            
            alert.setHeaderText("Success!");
            alert.setContentText("Product succesfully added!");
            alert.showAndWait();
        }
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
        
    }
    
    /** This method asks user to confirm and then removes selected part from the tempProduct object.
        @param actionEvent user remove in the add product pane.
     * @throws java.io.IOException
     */
    public void toRemoveAssoc (ActionEvent actionEvent) throws IOException {
      
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        Part tempPartRemove = assocpartsTable.getSelectionModel().getSelectedItem();
        
        if(tempPartRemove == null) {
            alert2.setHeaderText("Error");
            alert2.setContentText("No part selected!");
            alert2.showAndWait();
        } else {
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete the " + tempPartRemove.getName() + " part?" );
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() ==  ButtonType.OK){
                tempProduct.deleteAssociatedPart(tempPartRemove);
            } 
            invassocParts = tempProduct.getAllAssociatedParts();
            assocpartsTable.setItems(invassocParts);
        }
    }
}
