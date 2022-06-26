package InventoryManagementApp;

/** This class creates an in house child part with the added machine id variable.
 */
public class InHouse extends Part {
    
       private int machineId;
        
        /** This method constructs the InHouse child object. Sets machineId to 0.
         @param id int part id.
         @param name string name.
         @param price double part price.
         @param stock int inventory level.
         @param min int minimum.
         @param max int maximum.
         */
        public InHouse(int id, String name, double price, int stock, int min, int max) {
            super(id,name,price,stock,min,max);
            this.machineId = 0;
        }
   
        /** Method to set the machineId int variable of the object.
         @param machineId integer for machine id variable.
         */
        public void setMachineId(int machineId){
            this.machineId = machineId;
        }
        
        /** Method to retrieve the integer machine Id of the object. 
         @return Returns the machine ID in integer.
         */
        public int getMachineId() {
           return machineId;
        }   
}
