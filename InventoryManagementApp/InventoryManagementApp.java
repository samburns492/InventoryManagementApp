package InventoryManagementApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class creates an app that manages an inventory of parts and products.
 * FUTURE ENHANCEMENT One future enhancement I would implement would be the ability to see 
 * which products a selected part is associated with. This would calling each part associated to a product 
 * in the add or modify product form. After each call a observable list associated with each part would be updated with
 * the associated product. This would result in the ability to track which product each part is associated with during modification of part. 
 */
public class InventoryManagementApp extends Application {

    /**
     Method to launch the application.  
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** *  Loads the initial fxml form of the application. Opens the main form controller.
    @param stage takes the stage FXML object.
     * @throws java.lang.Exception
    */
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/MainForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
}
