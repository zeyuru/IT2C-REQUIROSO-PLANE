package it2c.requiroso;

import java.util.Scanner;

public class It2cRequiroso {

   public static void main(String[] args) {
        String response;
        
        Scanner sc = new Scanner(System.in);
        
        int action;

        do {
             System.out.println("WELCOME TO FLIGHTS");
              System.out.println("");
            System.out.println("1. PASSENGER");
            System.out.println("2. FLIGHT INFORMATION");
            System.out.println("3. BOOK A FLIGHT");
            System.out.println("4. REPORTS");
             System.out.println("5. EXIT");
            System.out.print("Enter Action: ");
            action = sc.nextInt(); 
                Passenger demo = new Passenger();
              FlightInformation fi = new FlightInformation();
               BookFlight bk = new BookFlight();
            switch (action) {
                case 1:
                       
                 demo.pTransaction();
                    break;
                case 2:
               
                      fi.fTransaction();
                    break;
                
                case 3:
                    
                     bk.bTransaction();
                    break;
                case 4:
                 
                    break;
                    case 5:
                     System.out.println("Exiting...");
                    break;
                default: 
                    System.out.println("Invalid action. Please Try Again");
            }
            
            System.out.println("Continue?(Yes/No): ");
            response = sc.next();
            
        } while (response.equals("yes"));

        System.out.println("Thank You!");
    }
}

