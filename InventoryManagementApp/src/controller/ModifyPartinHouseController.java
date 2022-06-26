package controller;

import InventoryManagementApp.InHouse;
import InventoryManagementApp.Inventory;
import InventoryManagementApp.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



/** Creates a controller class which handles modifying an in house part and associated FXML file.  
 */
public class ModifyPartinHouseController implements Initializable {
    
    public TextField idfield;
    public TextField namefield;
    public TextField invfield;
    public TextField pricefield;
    public TextField maxfield;
    public TextField minfield;
    public TextField machineIdfield;
    
    private ObservableList<Part> invParts = Inventory.getAllParts();
    private Part tempPartMod = null;
    private InHouse convertPartMod = new InHouse(0,"",0.0,0,0,0);
    
    /** The initialize method of the controller class.
     * @param url
     * @param rb */
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
  /** *  This method sets the part to be modified.Called by other controllers to pass a selected part.
     * @param temp
     */
   public void setterPartMod(Part temp) {
        this.tempPartMod = temp;
      
        idfield.setText(String.valueOf(tempPartMod.getId()));
        namefield.setText(tempPartMod.getName());
        invfield.setText(String.valueOf(tempPartMod.getStock()));
        pricefield.setText(String.valueOf(tempPartMod.getPrice()));
        maxfield.setText(String.valueOf(tempPartMod.getMax()));
        minfield.setText(String.valueOf(tempPartMod.getMin()));
        
        if (tempPartMod instanceof InHouse) {
        machineIdfield.setText(String.valueOf(((InHouse)tempPartMod).getMachineId()));
        }
   }
   
    /** This method checks all fields and then saves the modified part as an in house child part.
        @param actionEvent when user clicks the save button.
     * @throws java.io.IOException
     */
    public void toSavePart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        try { if (Integer.parseInt(namefield.getText()) >= 0) {
                alert.setHeaderText("Error");
                alert.setContentText("Name must be a string!");
                alert.showAndWait();
                return;
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        
        try {   int a = Integer.parseInt(invfield.getText());
                double b = Double.parseDouble(pricefield.getText());
                int c = Integer.parseInt(maxfield.getText());     
                int d = Integer.parseInt(minfield.getText());
                int e = Integer.parseInt(machineIdfield.getText());
            } catch (NumberFormatException e) {
                alert.setHeaderText("Error");
                alert.setContentText("Invalid input for one or more variables!");
                alert.showAndWait();
                return;
            }
      
     
        if (namefield.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Part name field is empty! Modify Part failed");
            alert.showAndWait();
        } else if (Integer.parseInt(maxfield.getText()) <= Integer.parseInt(minfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Max is less than or equal to min! Modify Part failed");
            alert.showAndWait();
        } else if (Double.parseDouble(pricefield.getText()) <= 0.00) {
            alert.setHeaderText("Error");
            alert.setContentText("Price needs to be greater than 0.00. Modify Part failed");
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
        } else if (machineIdfield.getText().isBlank()) {
            alert.setHeaderText("Error");
            alert.setContentText("Machine ID field is empty! Modify Part failed");
            alert.showAndWait();
        } else {
            tempPartMod = Inventory.lookupPart(Integer.parseInt(idfield.getText()));
            int indexPart = invParts.indexOf(tempPartMod);
           
            try {
                convertPartMod = new InHouse(Integer.parseInt(idfield.getText()),namefield.getText(),Double.parseDouble(pricefield.getText()),Integer.parseInt(invfield.getText()),Integer.parseInt(minfield.getText()),Integer.parseInt(maxfield.getText()));
                convertPartMod.setMachineId(Integer.parseInt(machineIdfield.getText()));
                Inventory.updatePart(indexPart, convertPartMod);
            } catch (NumberFormatException e) {
                alert.setHeaderText("Error");
                alert.setContentText("One of the number inputs is the wrong type. Add save failed");
                alert.showAndWait();
                return;
            }
            
            alert.setHeaderText("Success!");
            alert.setContentText("Part succesfully modified!");
            alert.showAndWait();
      
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /** This method passes the in house part to the modify outsourced part form.
        @param actionEvent when user clicks the outsourced radio button.
     * @throws java.io.IOException
     */
    public void toModifyPart_Out(ActionEvent actionEvent) throws IOException {
        
        tempPartMod.setName(namefield.getText());
        tempPartMod.setStock(Integer.parseInt(invfield.getText()));
        tempPartMod.setPrice(Double.parseDouble(pricefield.getText()));
        tempPartMod.setMax(Integer.parseInt(maxfield.getText()));
        tempPartMod.setMin(Integer.parseInt(minfield.getText()));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartOutsourced.fxml"));
        Parent root = loader.load();
        
        ModifyPartOutsourcedController tempPartModMain = loader.getController();
        tempPartModMain.setterPartMod(tempPartMod);
        
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Part In-House");
        stage.setScene(scene);
        stage.show();
        
    }
    
    /** This method launches the main form without any changes saved.
        @param actionEvent when user clicks cancel button on the modify in house part pane.
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
}
