package it2c.requiroso;

import java.util.Scanner;

public class reports {
  public void showReports() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    String flightId = "";

    
    while (true) {
        System.out.print("Enter Flight ID: ");
        flightId = sc.nextLine(); 

        if (flightId.isEmpty()) {
            System.out.println("Flight ID cannot be empty!");
            continue; 
        }

      
        if (!flightId.matches("\\d+")) {  
            System.out.println("Invalid Flight ID. Please enter an existing Flight ID.");
            continue; 
        }

       
        String flightCheckSql = "SELECT COUNT(*) FROM flight WHERE flight_id = '" + flightId + "'";
        double flightExists = conf.getSingleValue(flightCheckSql); 

        if (flightExists == 0) {
            System.out.println("Selected Flight ID does not exist! Please try again.");
        } else {
            break; 
        }
    }

    
    System.out.println("You entered: " + flightId);

    
    String flightDetailsSql = "SELECT flight_number, destination, departure, arrival FROM flight WHERE flight_id = '" + flightId + "'";
    String[] flightHeaders = {"Flight Number", "Destination", "Departure", "Arrival"};
    String[] flightColumns = {"flight_number", "destination", "departure", "arrival"};

   
    System.out.println("\n*** Flight Details ***");
    conf.viewRecord(flightDetailsSql, flightHeaders, flightColumns);

   
    String countSql = "SELECT COUNT(*) FROM passenger " +
                      "JOIN book ON passenger.p_id = book.p_id " +
                      "JOIN flight ON book.flight_id = flight.flight_id " +
                      "WHERE flight.flight_id = '" + flightId + "'";
    double passengerCount = conf.getSingleValue(countSql);  

    
    if (passengerCount == 0) {
        System.out.println("No Passengers for this flight.");
    } else {
       
        String passengerSql = "SELECT passenger.p_fname, passenger.p_lname, passenger.p_email, passenger.p_pnumber, passenger.p_passport " +
                               "FROM passenger " +
                               "JOIN book ON passenger.p_id = book.p_id " +
                               "JOIN flight ON book.flight_id = flight.flight_id " +
                               "WHERE flight.flight_id = '" + flightId + "'";
        String[] passengerHeaders = {"First Name", "Last Name", "Email", "Phone Number","Passport"};
        String[] passengerColumns = {"p_fname", "p_lname", "p_email", "p_pnumber","p_passport"};

      
        System.out.println("\n*** Passengers on this Flight ***");
        conf.viewRecord(passengerSql, passengerHeaders, passengerColumns);
    }
}

}
