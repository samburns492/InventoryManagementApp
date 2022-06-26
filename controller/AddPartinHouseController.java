package controller;

import InventoryManagementApp.InHouse;
import InventoryManagementApp.Inventory;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Creates a controller class which handles adding an in house part and associated FXML file.  
 */
public class AddPartinHouseController implements Initializable {
    
    public TextField idfield;
    public TextField namefield;
    public TextField invfield;
    public TextField pricefield;
    public TextField maxfield;
    public TextField minfield;
    public TextField machineIdfield;
    
    /** The initialize method of the controller class.
     * @param url
     * @param rb */
    public void initialize(URL url, ResourceBundle rb) {
        idfield.setText("Auto gen -disabled");
    }    
    
    /** This method checks all fields and then saves the new part as an in house child part.
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
            alert.setContentText("Part name field is empty! Add Part failed");
            alert.showAndWait();
            return;
        } else if (Integer.parseInt(maxfield.getText()) <= Integer.parseInt(minfield.getText())) {
            alert.setHeaderText("Error");
            alert.setContentText("Max is less than or equal to min! Add Part failed");
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
            alert.setContentText("Price needs to be greater than 0.00. Add Part failed ");
            alert.showAndWait();
            return;
        } else {
            try {
                InHouse tempPart = new InHouse(0,namefield.getText(),Double.parseDouble(pricefield.getText()),Integer.parseInt(invfield.getText()),Integer.parseInt(minfield.getText()),Integer.parseInt(maxfield.getText()));
                tempPart.setMachineId(Integer.parseInt(machineIdfield.getText()));
                Inventory.addPart(tempPart);
                
            } catch (NumberFormatException e) {
                alert.setHeaderText("Error");
                alert.setContentText("One of the inputs is the wrong type. Add save failed");
                alert.showAndWait();
                return;
            }
            
            alert.setHeaderText("Success!");
            alert.setContentText("In-House Part succesfully added!");
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
    public void toAddPart_Out(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/AddPartOutsourced.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part Outsourced");
        stage.setScene(scene);
        stage.show();
    }
    
    /** This method launches the main form without any part saved.
        @param actionEvent when user clicks cancel button on the add in house part pane.
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