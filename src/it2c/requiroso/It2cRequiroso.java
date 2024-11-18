package it2c.requiroso;

import java.util.Scanner;

public class It2cRequiroso {

   public static void main(String[] args) {
        String response = "yes";  
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        int action = 0;

        do {
            System.out.println("-------------------------------");
            System.out.println("WELCOME TO FLIGHTS");
            System.out.println("");
            System.out.println("1. PASSENGER");
            System.out.println("2. FLIGHT INFORMATION");
            System.out.println("3. BOOK A FLIGHT");
            System.out.println("4. REPORTS");
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
            Passenger demo = new Passenger();
            FlightInformation fi = new FlightInformation();
            BookFlight bk = new BookFlight();
            reports sr = new reports();
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
                     sr.showReports();
                    break;
                default:
                    
                    System.out.println("Invalid action. Please try again.");
            }

           
            System.out.println("Continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You!");
    }
}

