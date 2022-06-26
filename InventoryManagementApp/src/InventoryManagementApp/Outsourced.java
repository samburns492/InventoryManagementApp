/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementApp;

/** This class creates an outsourced child part with the added company name variable.
 */
public class Outsourced extends Part {
    
        private String companyName;
        
        /** This method constructs the Outsourced child object. Sets company set to default Acme.
         @param id int part id.
         @param name string name.
         @param price double part price.
         @param stock int inventory level.
         @param min int minimum.
         @param max int maximum.
         */
        public Outsourced(int id, String name, double price, int stock, int min, int max) {
            super(id,name,price,stock,min,max);
            this.companyName = "Acme";
        }
    
        /** Method to set the company name String variable of the object.
         @param companyName string variable for the object.
         */
        public void setCompanyName(String companyName){
            this.companyName = companyName;
        } 
        
        /** This method retrieves the company name string variable of the object.
         @return Returns string company name.
         */
        public String getCompanyName() {
           return companyName;
        }              
    
}


