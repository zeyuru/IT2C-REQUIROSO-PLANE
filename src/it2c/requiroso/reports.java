
package it2c.requiroso;
import java.util.Scanner;

public class reports {
    public void showReports() {
    reports sr = new reports();
    FlightInformation fi = new FlightInformation();
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    fi.viewFlight();

    System.out.print("Enter Flight ID to view passengers: ");
    String flightId = sc.nextLine();  

    while (conf.getSingleValue("SELECT flight_id FROM flight WHERE flight_id = ?", flightId) == 0) {
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Flight ID again: ");
        flightId = sc.nextLine(); 
    }

   
    String countSql = "SELECT COUNT(*) FROM passenger " +
                      "JOIN book ON passenger.p_id = book.p_id " +
                      "JOIN flight ON book.flight_id = flight.flight_id " +
                      "WHERE flight.flight_id = '" + flightId + "'";
    double passengerCount = conf.getSingleValue(countSql);

    if (passengerCount == 0) {
        System.out.println("No Passengers for this flight.");
    } else {
        
        String sql = "SELECT flight.flight_number, passenger.p_fname, passenger.p_lname, passenger.p_email, passenger.p_pnumber " +
                     "FROM passenger " +
                     "JOIN book ON passenger.p_id = book.p_id " +
                     "JOIN flight ON book.flight_id = flight.flight_id " +
                     "WHERE flight.flight_id = '" + flightId + "'";

        String[] headers = {"Flight Number", "First Name", "Last Name", "Email", "Phone Number"};
        String[] columns = {"flight_number", "p_fname", "p_lname", "p_email", "p_pnumber"};

       
        conf.viewRecord(sql, headers, columns);
    }
}

}
