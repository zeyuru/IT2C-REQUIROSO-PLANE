
package it2c.requiroso;

import java.util.Scanner;


public class FlightInformation {
    
   public void fTransaction() {
    Scanner sc = new Scanner(System.in);
    String response = "yes";
    int action = 0;

    do {
        boolean validInput = false; 
        System.out.println("-------------------------------");
        System.out.println("FLIGHT INFORMATION PANEL");
        System.out.println("1. ADD FLIGHT");
        System.out.println("2. VIEW FLIGHT");
        System.out.println("3. UPDATE FLIGHT");
        System.out.println("4. DELETE FLIGHT");
        System.out.println("5. EXIT");
        System.out.println("-------------------------------");

        
        while (!validInput) {
            System.out.print("Enter Action: ");
            String input = sc.nextLine().trim(); 

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between 1-5.");
            } else {
                try {
                    action = Integer.parseInt(input);
                    if (action >= 1 && action <= 5) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid action. Pick only from 1-5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number between 1-5.");
                }
            }
        }

        if (action == 5) {
            System.out.println("Exiting...");
            break; 
        }

        FlightInformation fi = new FlightInformation();

        
        switch (action) {
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
            default:
                System.out.println("Invalid action. Please try again.");
        }

        
        boolean validResponse = false;
        while (!validResponse) {
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.nextLine().trim();

            if (response.isEmpty()) {
                System.out.println("Input cannot be empty. Please type 'yes' or 'no'.");
            } else if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                validResponse = true; 
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }

        validInput = false;

    } while (response.equalsIgnoreCase("yes"));

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

    
    
     System.out.print("Departure Time & Date (MM-DD-YYYY HH:MM): ");
    String departure = sc.nextLine();
    while (departure.isEmpty() || !departure.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}")) {
        System.out.println("Invalid format. Please enter the date and time in MM-DD-YYYY HH:MM format.");
        departure = sc.nextLine();
    }

    
    System.out.print("Arrival Time & Date (MM-DD-YYYY HH:MM): ");
    String arrival = sc.nextLine();
    while (arrival.isEmpty() || !arrival.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}")) {
        System.out.println("Invalid format. Please enter the date and time in MM-DD-YYYY HH:MM format.");
        arrival = sc.nextLine();
    }

      System.out.print("Number of Seats: ");
    String seats = sc.nextLine();
    while (seats.isEmpty() || !seats.matches("\\d+") || Integer.parseInt(seats) <= 0) {
        System.out.println("Invalid input. Number of seats must be a positive integer.");
        seats = sc.nextLine();
    }
   System.out.print("Price per Ticket: ");
    String price = sc.nextLine();
    while (price.isEmpty() || !price.matches("\\d+(\\.\\d{1,2})?") || Double.parseDouble(price) <= 0) {
        System.out.println("Invalid input. Please enter a valid price greater than zero.");
        price = sc.nextLine();
    }
    
     System.out.print("Status of the flight (AVAILABLE/NOT AVAILABLE): ");
    String fstatus = sc.nextLine();
    while (fstatus.isEmpty() || (!fstatus.equalsIgnoreCase("AVAILABLE") && !fstatus.equalsIgnoreCase("NOT AVAILABLE"))) {
        System.out.println("Invalid input. Status must be either 'AVAILABLE' or 'NOT AVAILABLE'. Please try again.");
        fstatus = sc.nextLine();
    }

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
        
       System.out.print("Departure Time & Date (MM-DD-YYYY HH:MM): ");
    String fdept = sc.nextLine();
    while (fdept.isEmpty() || !fdept.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}")) {
        System.out.println("Invalid format. Please enter the date and time in MM-DD-YYYY HH:MM format.");
        fdept = sc.nextLine();
    }

        
        System.out.print("Arrival Time & Date (MM-DD-YYYY HH:MM): ");
    String farrival = sc.nextLine();
    while (farrival.isEmpty() || !farrival.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}")) {
        System.out.println("Invalid format. Please enter the date and time in MM-DD-YYYY HH:MM format.");
        farrival = sc.nextLine();
    }
        
        System.out.print("New Number of Seats: ");
    String fseats = sc.nextLine();
    
    while (fseats.isEmpty() || !fseats.matches("\\d+") || Integer.parseInt(fseats) <= 0) {
        System.out.println("Invalid input. Number of seats must be a positive integer.");
        fseats = sc.nextLine();
    }
        
        System.out.print("New Price per Ticket: ");
    String fprice = sc.nextLine();
    while (fprice.isEmpty() || !fprice.matches("\\d+(\\.\\d{1,2})?") || Double.parseDouble(fprice) <= 0) {
        System.out.println("Invalid input. Please enter a valid price greater than zero.");
        fprice = sc.nextLine();
    }
       
        
        String qry = "UPDATE flight SET flight_number = ?, destination = ?, departure = ?, arrival = ?, seats = ?, price = ? WHERE flight_id = ?";
       
        conf.updateRecord(qry, flightnum, fdest, fdept,farrival, fseats, fprice, id);
    }
   
  private void deleteFlight() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

   
    System.out.println("Enter Flight ID to delete: ");
    String input = sc.nextLine();  

   
    while (input.isEmpty() || !input.matches("\\d+")) {
        System.out.println("Invalid input. Flight ID should be a number. Please try again: ");
        input = sc.nextLine();  
    }
    int id = Integer.parseInt(input);  

  
    while (conf.getSingleValue("SELECT flight_id FROM flight WHERE flight_id = ?", id) == 0) {
        System.out.println("Selected ID doesn't exist! Please try again: ");
        input = sc.nextLine();  

        
        while (input.isEmpty() || !input.matches("\\d+")) {
            System.out.println("Invalid input. Flight ID should be a number. Please try again: ");
            input = sc.nextLine(); 
        }

        id = Integer.parseInt(input); 
    }

   
    String sqlDelete = "DELETE FROM flight WHERE flight_id = ?";
    conf.deleteRecord(sqlDelete, id);

    System.out.println("Flight with ID " + id + " deleted successfully.");
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
