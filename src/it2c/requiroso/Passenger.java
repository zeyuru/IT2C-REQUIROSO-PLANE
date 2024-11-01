
package it2c.requiroso;

import java.util.Scanner;


public class Passenger {
    
    public void pTransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("PASSENGER PANEL");
            System.out.println("1. ADD PASSENGER");
             System.out.println("2. VIEW PASSENGER");
             System.out.println("3. UPDATE PASSENGER");
             System.out.println("4. DELETE PASSENGER");
             System.out.println("5. EXIT");
             System.out.println("Enter Action");
           int  action = sc.nextInt(); 
             
              Passenger demo = new Passenger();
     switch(action){
    case 1:
         demo.addPassenger();
           demo.viewPassenger();
    break;
    case 2:
        demo.viewPassenger();
        break;
    case 3:
          demo.viewPassenger();
        demo.updatePassenger();
         demo.viewPassenger();
        break;
    case 4:
          demo.viewPassenger();
        demo.deletePassenger();
          demo.viewPassenger();
        break;
    case 5:
       System.out.println("Exiting...");
        break;
     }
        System.out.println("Do you want to continue?(yes/no): ");
          response = sc.next();
        
     }while(response.equalsIgnoreCase("yes"));
            System.out.println("Thank You!");
}
    
      
    

    
     public void addPassenger(){
        
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        System.out.println("First Name: ");
        String fname = sc.next();
        System.out.println("Last Name: ");
        String lname = sc.next();
        System.out.println("Email: ");
        String email = sc.next();
        System.out.println("Passport Number: ");
        String passport = sc.next();
        System.out.println("Nationality: ");
        String nationality = sc.next();
        System.out.println("Date of Birth: ");
        String birth = sc.next();
        System.out.println("Phone Number: ");
        String pnumber = sc.next();
     
        String sql = "INSERT INTO passenger (p_fname, p_lname, p_email, p_passport, p_nationality, p_birth, p_pnumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
          conf.addRecord (sql,fname, lname, email, passport, nationality, birth, pnumber);  
      

 
        System.out.println("Passenger added: " + fname + " " + lname);
    }
     
     public void viewPassenger() {
        String qry = "SELECT * FROM passenger";
        String[] hdrs = {"ID", "First Name","Last Name", "Email", "Nationality", "Date of Birth", "Phone Number"};
        String[] clmns = {"p_id", "p_fname","p_lname", "p_email", "p_nationality", "p_birth", "p_pnumber"};
        config conf = new config();
        conf.viewRecord(qry, hdrs, clmns);
    }
     
     private void updatePassenger(){
        Scanner sc = new Scanner(System.in);
         config conf = new config();
        System.out.println("Enter Passenger ID:");
        int id = sc.nextInt();
        
        
        while (conf.getSingleValue ("SELECT p_id FROM passenger WHERE p_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Passenger ID again: ");
            id = sc.nextInt();
        }
        sc.nextLine(); 
        
        System.out.println("Enter the new First Name:");
        String ufname = sc.nextLine();
        
        System.out.println("Enter the new Last Name: ");
        String ulname = sc.nextLine();
        
        System.out.println("Enter the new Email: ");
        String uemail = sc.nextLine();
        
        System.out.println("New Passport: ");
        String upassport = sc.nextLine();
        
        System.out.println("Enter new Nationality: ");
        String unationality = sc.nextLine();
        
        System.out.println("Enter new Date of Birth: ");
        String ubirth = sc.nextLine();
        
        System.out.println("Enter new Phone Number: ");
        String upnumber = sc.nextLine();
        
        String qry = "UPDATE passenger SET p_fname = ?, p_lname = ?, p_email = ?, p_passport = ?, p_nationality = ?, p_birth = ?, p_pnumber = ? WHERE p_id = ?";
       
        conf.updateRecord(qry, ufname, ulname, uemail,upassport, unationality, ubirth, upnumber, id);
    }
    
    private void deletePassenger(){
        Scanner sc = new Scanner(System.in);
         config conf = new config();
        System.out.println("Enter Passenger ID to delete: ");
        int id = sc.nextInt();
        
          while (conf.getSingleValue ("SELECT p_id FROM passenger WHERE p_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Passenger ID again: ");
            id = sc.nextInt();
        }
        
        
        String sqlDelete = "Delete from passenger WHERE p_id = ?";
       
        conf.deleteRecord(sqlDelete, id);
        
        
    }
    
  
    
}
