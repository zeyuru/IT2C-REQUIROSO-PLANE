
package it2c.requiroso;

import java.util.Scanner;


public class Passenger {
    
    public void pTransaction() {
    Scanner sc = new Scanner(System.in);
    String response = "yes"; 
    int action = 0;

    do {
        boolean validInput = false; 
        System.out.println("-------------------------------");
        System.out.println("PASSENGER PANEL");
        System.out.println("1. ADD PASSENGER");
        System.out.println("2. VIEW PASSENGER");
        System.out.println("3. UPDATE PASSENGER");
        System.out.println("4. DELETE PASSENGER");
        System.out.println("5. EXIT");
        System.out.println("-------------------------------");

        
        while (!validInput) {
            System.out.print("Enter Action: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between 1-5:");
            } else {
                try {
                    action = Integer.parseInt(input);
                    if (action >= 1 && action <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid action. Pick only from 1-5:");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number between 1-5:");
                }
            }
        }

        
        if (action == 5) {
            System.out.println("Exiting...");
            break;
        }

        
        Passenger demo = new Passenger();
        switch (action) {
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

    } while (response.equalsIgnoreCase("yes"));

    System.out.println("Thank you!");
}


    public void addPassenger() {
        config conf = new config();
        Scanner sc = new Scanner(System.in);

        System.out.print("First Name: ");
    String fname = sc.nextLine();
        while (fname.isEmpty() || !fname.matches("[a-zA-Z]+")) {
           System.out.println("Please enter a valid first name.: ");
                 fname = sc.nextLine();
}
        
        System.out.print("Last Name: ");
            String lname = sc.nextLine();
                while (lname.isEmpty() || !lname.matches("[a-zA-Z]+")) {
                    System.out.println("Please enter a valid last name.: ");
                        lname = sc.nextLine();
}
                
        System.out.print("Email: ");
            String email = sc.nextLine();
                    while (email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("Invalid email format. Please enter a valid email address.: ");
                            email = sc.nextLine();
}

      System.out.print("Passport Number: ");
    String passport = sc.nextLine();
            while (passport.isEmpty() || !passport.matches("[a-zA-Z0-9]+")) {
    System.out.println("Please enter a valid passport number: ");
    passport = sc.nextLine();
}

       System.out.print("Nationality: ");
    String nationality = sc.nextLine();
                while (nationality.isEmpty()) {
                     System.out.println("Please enter a valid nationality: ");
                             nationality = sc.nextLine();
    }
       System.out.print("Date of Birth (MM-DD-YYYY): ");
    String birth = sc.next();
    while (!birth.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
        System.out.println("Invalid Date of Birth format. Please use MM-DD-YYYY (e.g., 01-02-2004): ");
        birth = sc.next();
    }
        System.out.print("Phone Number: ");
    String pnumber = sc.next();
    while (!pnumber.matches("^\\d{11}$")) {
        System.out.println("Invalid Phone Number. Please enter exactly 11 digits without characters: ");
        pnumber = sc.next();
    }

        String sql = "INSERT INTO passenger (p_fname, p_lname, p_email, p_passport, p_nationality, p_birth, p_pnumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, passport, nationality, birth, pnumber);

        System.out.println("Passenger added: " + fname + " " + lname);
    }

    public void viewPassenger() {
        String qry = "SELECT * FROM passenger";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Nationality", "Date of Birth", "Phone Number"};
        String[] clmns = {"p_id", "p_fname", "p_lname", "p_email", "p_nationality", "p_birth", "p_pnumber"};
        
        config conf = new config();
        conf.viewRecord(qry, hdrs, clmns);
    }

    private void updatePassenger() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Passenger ID: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT p_id FROM passenger WHERE p_id = ?", id) == 0) {
            System.out.print("Selected ID doesn't exist! Select Passenger ID again: ");
            id = sc.nextInt();
        }

        sc.nextLine(); 

        System.out.print("Enter the new First Name: ");
        String ufname = sc.nextLine();
          while (ufname.isEmpty() || !ufname.matches("[a-zA-Z]+")) {
           System.out.println(" Please enter a valid first name.: ");
                 ufname = sc.nextLine();
}
        System.out.print("Enter the new Last Name: ");
        String ulname = sc.nextLine();
         while (ulname.isEmpty() || !ulname.matches("[a-zA-Z]+")) {
                    System.out.println(" Please enter a valid last name.: ");
                        ulname = sc.nextLine();
}
        System.out.print("Enter the new Email: ");
        String uemail = sc.nextLine();
          while (uemail.isEmpty() || !uemail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("Invalid email format. Please enter a valid email address.: ");
                            uemail = sc.nextLine();
}
        System.out.print("New Passport: ");
        String upassport = sc.nextLine();
        while (upassport.isEmpty() || !upassport.matches("[a-zA-Z0-9]+")) {
    System.out.println("Please enter a valid passport number: ");
    upassport = sc.nextLine();
}
        System.out.print("Enter new Nationality: ");
        String unationality = sc.nextLine();
        while (unationality.isEmpty()) {
                     System.out.println("Please enter a valid nationality: ");
                             unationality = sc.nextLine();
    }
        System.out.print("Enter new Date of Birth: ");
        String ubirth = sc.nextLine();
        while (!ubirth.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
        System.out.println("Invalid Date of Birth format. Please use MM-DD-YYYY (e.g., 01-02-2004): ");
        ubirth = sc.next();
    }
        
        System.out.print("Enter new Phone Number: ");
        String upnumber = sc.nextLine();
 while (!upnumber.matches("^\\d{11}$")) {
        System.out.println("Invalid Phone Number: ");
        upnumber = sc.next();
    }
        String qry = "UPDATE passenger SET p_fname = ?, p_lname = ?, p_email = ?, p_passport = ?, p_nationality = ?, p_birth = ?, p_pnumber = ? WHERE p_id = ?";
        conf.updateRecord(qry, ufname, ulname, uemail, upassport, unationality, ubirth, upnumber, id);

        System.out.println("Passenger updated successfully.");
    
    }
  private void deletePassenger() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    int id = -1;
    boolean validInput = false;
    
    while (!validInput) {
        System.out.print("Enter Passenger ID to delete: ");
        
      
        String input = sc.nextLine().trim(); 

       
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
            continue;
        }
        
        
        try {
            id = Integer.parseInt(input);

         
            if (conf.getSingleValue("SELECT p_id FROM passenger WHERE p_id = ?", id) != 0) {
                validInput = true;  
            } else {
                System.out.println("Selected ID doesn't exist! Please try again.");
            }
        } catch (NumberFormatException e) {
          
            System.out.println("Invalid input. Please enter a valid integer ID.");
        }
    }

   
    String sqlDelete = "DELETE FROM passenger WHERE p_id = ?";
    conf.deleteRecord(sqlDelete, id);

    System.out.println("Passenger deleted successfully.");
}


    
  
    
}
