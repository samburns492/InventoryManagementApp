package controller;

import InventoryManagementApp.Inventory;
import InventoryManagementApp.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Creates a controller class which handles the add outsourced child part FXML file.  
 */
public class AddPartOutController implements Initializable {

    public TextField idfield;
    public TextField namefield;
    public TextField invfield;
    public TextField pricefield;
    public TextField maxfield;
    public TextField minfield;
    public TextField companynamefield;
    
    /** The initialize method of the controller class.
     * @param url
     * @param rb */
    public void initialize(URL url, ResourceBundle rb) {
        idfield.setText("Auto gen -disabled");
    }    
    
    /** This method passes the outsourced part to the modify in house part form.
        @param actionEvent when user clicks the In-house radio button.
     * @throws java.io.IOException
     */
    public void toAddPart_In(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/AddPartinHouse.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part In House");
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
    
    /** This method checks all fields and then saves the new part as an outsourced child part.
        @param actionEvent when user clicks the save button.
     * @throws java.io.IOException
     */
    public void toSavePartOut(ActionEvent actionEvent) throws IOException {
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
        
        try { if (Integer.parseInt(companynamefield.getText()) >= 0) {
                alert.setHeaderText("Error");
                alert.setContentText("Company name must be a string!");
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
            alert.setContentText("Price needs to be greater than 0.00. Add Part failed");
            alert.showAndWait();
            return;
        } else {
            try {
                Outsourced tempPart = new Outsourced(0,namefield.getText(),Double.parseDouble(pricefield.getText()),Integer.parseInt(invfield.getText()),Integer.parseInt(minfield.getText()),Integer.parseInt(maxfield.getText()));
                ((Outsourced)tempPart).setCompanyName(companynamefield.getText());
                Inventory.addPart(tempPart);
                
            } catch (NumberFormatException e) {
                alert.setHeaderText("Error");
                alert.setContentText("One of the inputs is the wrong type. Add save failed");
                alert.showAndWait();
                return;    
            }
            
            alert.setHeaderText("Success!");
            alert.setContentText("Outsourced Part succesfully added!");
            alert.showAndWait();
            
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }
}
