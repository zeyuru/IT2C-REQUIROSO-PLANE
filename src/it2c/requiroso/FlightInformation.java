
package it2c.requiroso;

import java.util.Scanner;


public class FlightInformation {
    
   public void fTransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("FLIGHT INFORMATION PANEL");
            System.out.println("1. ADD FLIGHT");
             System.out.println("2. VIEW FLIGHT");
             System.out.println("3. UPDATE FLIGHT");
             System.out.println("4. DELETE FLIGHT");
             System.out.println("5. EXIT");
             System.out.println("Enter Action");
           int  action = sc.nextInt(); 
             
              FlightInformation fi = new FlightInformation();
     switch(action){
    case 1:
        fi.addFlight();
    break;
    case 2:
      fi.viewFlight();
        break;
    case 3:
        fi.viewFlight();
        fi.updateFlight();
        fi.viewFlight();
        break;
    case 4:
            fi.viewFlight();
            fi.deleteFlight();
              fi.viewFlight();
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
   
   
   public void addFlight(){
         Scanner sc = new Scanner(System.in);
         
         System.out.println("Flight Number: ");
       String flightNum = sc.nextLine();
       
        System.out.print("Destination: ");
    String destination = sc.nextLine();
    
    System.out.print("Departure Time: ");
  String departure = sc.nextLine();
    
    System.out.print("Arrival Time: ");
    String arrival = sc.nextLine();
    
    System.out.print("Number of Seats: ");
    String seats = sc.nextLine();
    
    System.out.print("Price per Ticket: ");
    String price = sc.nextLine();

        String qry = "INSERT INTO flight (flight_number, destination, departure, arrival, seats, price) VALUES (?, ?, ?, ?, ?, ?)";
        
    config conf = new config();
    
   conf.addRecord(qry, flightNum, destination, departure, arrival, seats, price);
   }
   
   public void viewFlight(){
       
        String qry = "SELECT * FROM flight";
        String[] hdrs = {"ID", "Flight Number","Destination", "Departure", "Arrival", "Seats", "Price"};
        String[] clmns = {"flight_id", "flight_number","destination", "departure", "arrival", "seats", "price"};
        config conf = new config();
        conf.viewRecord(qry, hdrs, clmns);
       
       
       
   }
   private void updateFlight(){
        Scanner sc = new Scanner(System.in);
         config conf = new config();
        System.out.println("Enter Flight ID:");
        int id = sc.nextInt();
        
        
        while (conf.getSingleValue ("SELECT flight_id FROM flight WHERE flight_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Flight ID again: ");
            id = sc.nextInt();
        }
        sc.nextLine(); 
        
        System.out.println("Enter the new Flight Number:");
        String flightnum = sc.nextLine();
        
        System.out.println("Enter the new Destination: ");
        String fdest = sc.nextLine();
        
        System.out.println("Enter the new Departure: ");
        String fdept = sc.nextLine();
        
        System.out.println("New Arrival: ");
        String farrival = sc.nextLine();
        
        System.out.println("Enter new Seats: ");
        String fseats = sc.nextLine();
        
        System.out.println("Enter new price: ");
        String fprice = sc.nextLine();
        
       
        
        String qry = "UPDATE flight SET flight_number = ?, destination = ?, departure = ?, arrival = ?, seats = ?, price = ? WHERE flight_id = ?";
       
        conf.updateRecord(qry, flightnum, fdest, fdept,farrival, fseats, fprice, id);
    }
   
   private void deleteFlight(){
        Scanner sc = new Scanner(System.in);
         config conf = new config();
        System.out.println("Enter Flight ID to delete: ");
        int id = sc.nextInt();
        
          while (conf.getSingleValue ("SELECT flight_id FROM flight WHERE flight_id = ?",id)== 0){
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Flight ID again: ");
            id = sc.nextInt();
        }
        
        
        String sqlDelete = "Delete from flight WHERE flight_id = ?";
       
        conf.deleteRecord(sqlDelete, id);
        
        
    }
    
   
   
}
