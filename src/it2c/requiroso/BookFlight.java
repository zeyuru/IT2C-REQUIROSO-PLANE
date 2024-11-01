
package it2c.requiroso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class BookFlight {
    
     public void bTransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("BOOK A FLIGHT PANEL");
            System.out.println("1. BOOK A FLIGHT");
             System.out.println("2. VIEW BOOKED FLIGHT");
             System.out.println("3. UPDATE BOOKED FLIGHT");
             System.out.println("4. DELETE BOOKED FLIGHT");
             System.out.println("5. EXIT");
             System.out.println("Enter Action");
           int  action = sc.nextInt(); 
            
             BookFlight bk = new BookFlight();
     switch(action){
    case 1:
        bk.viewBF();
       bk.bookFlight();
     bk.viewBF();
    break; 
    case 2:
      bk.viewBF();
        break;
    case 3:
         bk.viewBF();
       bk.updateBF();
       bk.viewBF();
        break;
    case 4:
            bk.viewBF();
            bk.deleteBF();
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
     
     private void bookFlight() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Passenger demo = new Passenger();
    FlightInformation fi = new FlightInformation();
    demo.viewPassenger();
    fi.viewFlight();
    System.out.println("------BOOK A FLIGHT!-----");
       System.out.println("Enter the ID of the Passenger: ");
    String p_id = sc.nextLine();
    
      while (conf.getSingleValue("SELECT p_id FROM passenger WHERE p_id = ?", p_id) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.println("Select Passenger ID again: ");
        p_id = sc.nextLine();
    }
      
    System.out.println("Enter the ID of the Selected Flight: ");
    String flight_id = sc.nextLine();
    
    while (conf.getSingleValue("SELECT flight_id FROM flight WHERE flight_id = ?", flight_id) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.println("Select Flight ID again: ");
        flight_id = sc.nextLine();
    }
    
    String priceqry = "SELECT price FROM flight WHERE flight_id = ?";
    double price = conf.getSingleValue(priceqry, flight_id);
    
    System.out.println("---------------------");
    System.out.print("Enter received cash: ");
    double rcash = sc.nextDouble();
    
   
    while (rcash < price) {
        System.out.print("Invalid Amount, Try Again: ");
        rcash = sc.nextDouble();
    }
         System.out.println("Cash Received: " +rcash);
         double change = rcash - price;
         System.out.println("Change: "+ change);
         
    LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);
    
    String status = "Pending";
    
    
    String qry = "INSERT INTO book (p_id,flight_id, b_cash, b_date, status) VALUES (?, ?, ?, ?,?)";
    conf.addRecord(qry,p_id, flight_id, String.valueOf(rcash), date, status);
    
    System.out.println("Added Successfully!");
}
     
public void viewBF(){
       
        String qry = "SELECT book.*, passenger.p_fname, passenger.p_lname, book.b_date, book.status FROM book "
            + "LEFT JOIN passenger ON passenger.p_id = book.p_id "
            + "LEFT JOIN flight ON flight.flight_id = book.flight_id";

        String[] hdrs = {"BID", "Passenger First Name", "Passenger Last Name", "Date", "Status"};
String[] clmns = {"b_id", "p_fname", "p_lname", "b_date", "status"};

        config conf = new config();
        conf.viewRecord(qry, hdrs, clmns);
       
       
       
   }

private void updateBF(){
    Scanner sc = new Scanner(System.in);
         config conf = new config();
  Passenger demo = new Passenger();
  FlightInformation fi = new FlightInformation();
  
    
        System.out.println("Enter Booking ID:");
        int id = sc.nextInt();
        
         while (conf.getSingleValue ("SELECT b_id FROM book WHERE b_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Book ID again: ");
            id = sc.nextInt();
        }
          sc.nextLine(); 
          
          demo.viewPassenger();
        System.out.println("Enter the ID of the Passenger: ");
    String p_id = sc.nextLine();
    
     
      while (conf.getSingleValue("SELECT p_id FROM passenger WHERE p_id = ?", p_id) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.println("Select Passenger ID again: ");
        p_id = sc.nextLine();
    }
    
         fi.viewFlight();
        System.out.println("Enter the new Flight ID:");
        String flight_id = sc.nextLine();
        
         while (conf.getSingleValue("SELECT flight_id FROM flight WHERE flight_id = ?", flight_id) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.println("Select Flight ID again: ");
        flight_id = sc.nextLine();
    }
        
         String priceqry = "SELECT price FROM flight WHERE flight_id = ?";
    double price = conf.getSingleValue(priceqry, flight_id);
    
    System.out.println("---------------------");
    System.out.print("Enter received cash: ");
    double rcash = sc.nextDouble();
    
   
    while (rcash < price) {
        System.out.print("Invalid Amount, Try Again: ");
        rcash = sc.nextDouble();
    }
         System.out.println("Cash Received: " +rcash);
         double change = rcash - price;
         System.out.println("Change: "+ change);
       
        
       LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);
    
    String status = "Pending";
    
    
    String qry = "INSERT INTO book (p_id,flight_id, b_cash, b_date, status) VALUES (?, ?, ?, ?,?)";
    conf.addRecord(qry,p_id, flight_id, String.valueOf(rcash), date, status);
    
    System.out.println("Updated Successfully!");
    }
    
    private void deleteBF(){
        Scanner sc = new Scanner(System.in);
         config conf = new config();
        System.out.println("Enter Book ID to delete: ");
        int id = sc.nextInt();
        
          while (conf.getSingleValue ("SELECT b_id FROM book WHERE b_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Booking ID again: ");
            id = sc.nextInt();
        }
        
        
        String sqlDelete = "Delete from book WHERE b_id = ?";
       
        conf.deleteRecord(sqlDelete, id);
        
        
    }
    
    


}
     
     

