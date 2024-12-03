package it2c.requiroso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class reports {
 public void showReports() {
    Scanner sc = new Scanner(System.in);

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

       
        String flightCheckSql = "SELECT COUNT(*) FROM flight WHERE flight_id = ?";
        boolean flightExists = false;
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(flightCheckSql)) {
            pstmt.setString(1, flightId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                flightExists = rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking flight ID: " + e.getMessage());
        }

        if (!flightExists) {
            System.out.println("Selected Flight ID does not exist! Please try again.");
        } else {
            break;
        }
    }

    System.out.println("You entered: " + flightId);

    
    String flightDetailsSql = "SELECT flight_number, destination, departure, arrival FROM flight WHERE flight_id = ?";
    try (Connection conn = config.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(flightDetailsSql)) {
        pstmt.setString(1, flightId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println("\n*** Flight Details ***");
            System.out.println("Flight Number : " + rs.getString("flight_number"));
            System.out.println("Destination   : " + rs.getString("destination"));
            System.out.println("Departure     : " + rs.getString("departure"));
            System.out.println("Arrival       : " + rs.getString("arrival"));
        } else {
            System.out.println("No flight details found for the given Flight ID.");
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving flight details: " + e.getMessage());
    }

   
    double passengerCount = 0.0;
    String countSql = "SELECT COUNT(*) FROM passenger " +
                      "JOIN book ON passenger.p_id = book.p_id " +
                      "JOIN flight ON book.flight_id = flight.flight_id " +
                      "WHERE flight.flight_id = ?";
    try (Connection conn = config.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(countSql)) {
        pstmt.setString(1, flightId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            passengerCount = rs.getDouble(1);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving passenger count: " + e.getMessage());
    }

    if (passengerCount == 0) {
        System.out.println("No Passengers for this flight.");
    } else {
        System.out.println("\n*** Passengers on this Flight ***");
       
        String passengerSql = "SELECT passenger.p_fname, passenger.p_lname, passenger.p_email, passenger.p_pnumber, passenger.p_passport " +
                              "FROM passenger " +
                              "JOIN book ON passenger.p_id = book.p_id " +
                              "JOIN flight ON book.flight_id = flight.flight_id " +
                              "WHERE flight.flight_id = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(passengerSql)) {
            pstmt.setString(1, flightId);
            ResultSet rs = pstmt.executeQuery();

          System.out.printf("%-30s| %-30s | %-30s | %-30s | %-30s%n",
                  "First Name", "Last Name", "Email", "Phone Number", "Passport");
System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

while (rs.next()) {
    System.out.printf("%-30s| %-30s| %-30s| %-30s| %-30s%n",
                      rs.getString("p_fname"),
                      rs.getString("p_lname"),
                      rs.getString("p_email"),
                      rs.getString("p_pnumber"),
                      rs.getString("p_passport"));
}

        } catch (SQLException e) {
            System.out.println("Error retrieving passenger details: " + e.getMessage());
        }
    }
}

  
}
