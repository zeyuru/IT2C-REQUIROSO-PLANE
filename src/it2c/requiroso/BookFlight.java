
package it2c.requiroso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class BookFlight {
    
    public void bTransaction() {
    Scanner sc = new Scanner(System.in);
    boolean validInput;
    String response = "yes";
    int action = 0;

    do {
        validInput = false; 

        System.out.println("-------------------------------");
        System.out.println("BOOK A FLIGHT PANEL");
        System.out.println("1. BOOK A FLIGHT");
        System.out.println("2. VIEW BOOKED FLIGHT");
        System.out.println("3. UPDATE BOOKED FLIGHT");
        System.out.println("4. DELETE BOOKED FLIGHT");
        System.out.println("5. EXIT");
        System.out.println("-------------------------------");

        
        while (!validInput) {
            System.out.print("Enter Action: ");
            String input = sc.nextLine();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between 1-5: ");
            } else {
                try {
                    action = Integer.parseInt(input);
                    if (action >= 1 && action <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid action. Pick only from 1-5: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please pick only from 1-5: ");
                }
            }
        }

        
        if (action == 5) {
            System.out.println("Exiting...");
            break;
        }

        
        BookFlight bk = new BookFlight();
        switch (action) {
            case 1:
                bk.bookFlight();
                break;
            case 2:
                bk.viewBF();
                break;
            case 3:
                bk.updateBF();
                break;
            case 4:
                
                bk.deleteBF();
                break;
            default:
                System.out.println("Invalid action. Please try again.");
                continue; 
        }

        
        boolean validResponse = false;
        while (!validResponse) {
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.nextLine().trim();

            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                validResponse = true; 
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }

    } while (response.equalsIgnoreCase("yes"));

    System.out.println("Thank You!");
}

     
     private void bookFlight() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Passenger demo = new Passenger();
    FlightInformation fi = new FlightInformation();
    demo.viewPassenger();
    fi.viewAvailableFlights();
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

    
    double availableSeats = conf.getSingleValue("SELECT seats FROM flight WHERE flight_id = ?", flight_id);
    if (availableSeats <= 0) {
        System.out.println("No seats available for this flight.");
        return;
    }

   
    String priceqry = "SELECT price FROM flight WHERE flight_id = ?";
    double price = conf.getSingleValue(priceqry, flight_id);

    
    System.out.println("---------------------");
    double rcash = -1;
    while (rcash < price) {
        System.out.print("Enter received cash: ");
        if (sc.hasNextDouble()) {
            rcash = sc.nextDouble();
            if (rcash < price) {
                System.out.println("Amount is less than the required price. Try again.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid numeric value for the received cash.");
            sc.next(); 
        }
    }

  
    System.out.println("Cash Received: " + rcash);
    double change = rcash - price;
    System.out.println("Change: " + change);

   
    LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);

    String status = "Pending";
    String qry = "INSERT INTO book (p_id, flight_id, b_cash, b_date, status) VALUES (?, ?, ?, ?, ?)";
    conf.addRecord(qry, p_id, flight_id, String.valueOf(rcash), date, status);

  
    String updateSeatsQry = "UPDATE flight SET seats = seats - 1 WHERE flight_id = ?";
    conf.addRecord(updateSeatsQry, flight_id);
    System.out.println("Added Successfully!");
}

     
public void viewBF() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    System.out.print("Enter Passenger ID to view booked flights: ");
    String passengerId = sc.nextLine();
    
    while (conf.getSingleValue("SELECT p_id FROM passenger WHERE p_id = ?", passengerId) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.println("Select Passenger ID again: ");
        passengerId = sc.nextLine();
    }

   
    String qry = "SELECT book.*, passenger.p_fname, passenger.p_lname, flight.flight_number, book.b_date, book.status " +
                 "FROM book " +
                 "LEFT JOIN passenger ON passenger.p_id = book.p_id " +
                 "LEFT JOIN flight ON flight.flight_id = book.flight_id " +
                 "WHERE book.p_id = '" + passengerId + "'";

    String[] hdrs = {"BID", "Passenger First Name", "Passenger Last Name", "Flight Number", "Date", "Status"};
    String[] clmns = {"b_id", "p_fname", "p_lname", "flight_number", "b_date", "status"};


    String countSql = "SELECT COUNT(*) FROM book WHERE p_id = '" + passengerId + "'";
   double bookingCount = conf.getSingleValue(countSql);

    if (bookingCount == 0) {
        System.out.println("No booked flights found for this Passenger ID.");
    } else {
        
        conf.viewRecord(qry, hdrs, clmns);
    }
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
    
          fi.viewAvailableFlights();
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
        System.out.print("Enter received cash: ");
        
       
        if (sc.hasNextDouble()) {
            rcash = sc.nextDouble();
            if (rcash < price) {
                System.out.println("Amount is less than the required price. Try again.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid numeric value for the received cash.");
            sc.next(); 
        }
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
    
   private void deleteBF() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    int id = -1;
    boolean validInput = false;
    
    while (!validInput) {
        System.out.print("Enter Book ID to delete: ");
        
      
        String input = sc.nextLine().trim();
        
       
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
            continue;
        }
        
        
        try {
            id = Integer.parseInt(input);

        
            if (conf.getSingleValue("SELECT b_id FROM book WHERE b_id = ?", id) != 0) {
                validInput = true; 
            } else {
                System.out.println("Selected ID doesn't exist! Please try again.");
            }
        } catch (NumberFormatException e) {
           
            System.out.println("Invalid input. Please enter a valid integer ID.");
        }
    }

   
    String sqlDelete = "DELETE FROM book WHERE b_id = ?";
    conf.deleteRecord(sqlDelete, id);

    System.out.println("Booking deleted successfully.");
}

    
    


}
     
     

