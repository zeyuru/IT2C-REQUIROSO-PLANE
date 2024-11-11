
package it2c.requiroso;

import java.util.Scanner;


public class FlightInformation {
    
   public void fTransaction(){
     Scanner sc = new Scanner(System.in);
   String response = "yes";
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
       System.out.println("Exiting Flight Panel...");
                    return;
         default:
                    System.out.println("Invalid action. Please try again.");
                    continue;
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
      
       while (flightNum.isEmpty()) {
        System.out.println("Flight Number cannot be empty. Please enter a valid Flight Number.: ");
        flightNum = sc.nextLine();
    }
    
       
       System.out.print("Destination: ");
String destination = sc.nextLine();

while (destination.isEmpty() || destination.matches(".*\\d.*")) {
    System.out.println("Invalid input. Destination cannot be empty or contain numbers. Please enter a valid destination: ");
    destination = sc.nextLine();
}

    
    
     System.out.print("Departure Time & Date: ");
    String departure = sc.nextLine();
    while (departure.isEmpty()) {
        System.out.println("Departure Time & Date cannot be empty. Please enter a valid departure date.");
        departure = sc.nextLine();
    }
    
   System.out.print("Arrival Time & Date: ");
    String arrival = sc.nextLine();
    while (arrival.isEmpty()) {
        System.out.println("Arrival Time & Date cannot be empty. Please enter a valid arrival date.");
        arrival = sc.nextLine();
    }
    
    System.out.print("Number of Seats: ");
    String seats = sc.nextLine();
    
    System.out.print("Price per Ticket: ");
    String price = sc.nextLine();
      System.out.print("Status of the flight: ");
    String fstatus = sc.nextLine();

        String qry = "INSERT INTO flight (flight_number, destination, departure, arrival, seats, price,f_status) VALUES (?, ?, ?, ?, ?, ?,?)";
        
    config conf = new config();
    
   conf.addRecord(qry, flightNum, destination, departure, arrival, seats, price,fstatus);
   }
 
   
   public void viewFlight() {
   
    String qry = "SELECT * FROM flight";
    String[] hdrs = {"ID", "Flight Number", "Destination", "Departure", "Arrival", "Seats", "Price", "Status"};
    String[] clmns = {"flight_id", "flight_number", "destination", "departure", "arrival", "seats", "price", "f_status"};
    
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
        
         while (flightnum.isEmpty()) {
        System.out.println("Flight Number cannot be empty. Please enter a valid Flight Number.: ");
        flightnum = sc.nextLine();
    }
        
        System.out.println("Enter the new Destination: ");
        String fdest = sc.nextLine();
        
        while (fdest.isEmpty() || fdest.matches(".*\\d.*")) {
    System.out.println("Invalid input. Destination cannot be empty or contain numbers. Please enter a valid destination: ");
  fdest = sc.nextLine();
}
        
        System.out.println("Enter the new Departure: ");
        String fdept = sc.nextLine();
        
        while (fdept.isEmpty()) {
        System.out.println("Departure Time & Date cannot be empty. Please enter a valid departure date.");
        fdept = sc.nextLine();
    }
        
        System.out.println("New Arrival: ");
        String farrival = sc.nextLine();
        
         while (farrival.isEmpty()) {
        System.out.println("Arrival Time & Date cannot be empty. Please enter a valid arrival date.");
       farrival = sc.nextLine();
    }
        
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
    
   public void viewAvailableFlights() {
    String qry = "SELECT * FROM flight WHERE f_status = 'AVAILABLE'";
    String[] hdrs = {"ID", "Flight Number", "Destination", "Departure", "Arrival", "Seats", "Price", "Status"};
    String[] clmns = {"flight_id", "flight_number", "destination", "departure", "arrival", "seats", "price", "f_status"};

    config conf = new config();
    conf.viewRecord(qry, hdrs, clmns);
}

    public void updateSeatsAfterBooking(String flightId) {
       config conf = new config();
       
        String updateSeatsQry = "UPDATE flight SET seats = seats - 1 WHERE flight_id = ?";
         conf.addRecord(updateSeatsQry, flightId); 
    }

   
   
}
