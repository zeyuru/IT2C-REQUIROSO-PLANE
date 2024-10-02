package it2c.requiroso;

import java.util.Scanner;

public class It2cRequiroso {

    public void addPassenger() {
      
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        System.out.println("First Name: ");
        String fname = sc.next();
        System.out.println("Last Name: ");
        String lname = sc.next();
        System.out.println("Email: ");
        String email = sc.next();
        System.out.println("Passport Number: ");
        String passport = sc.next();
        System.out.println("Nationality: ");
        String nationality = sc.next();
        System.out.println("Date of Birth: ");
        String birth = sc.next();
        System.out.println("Phone Number: ");
        String pnumber = sc.next();
     
        String sql = "INSERT INTO passenger (p_fname, p_lname, p_email, p_passport, p_nationality, p_birth, p_pnumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
          conf.addRecord (sql,fname, lname, email, passport, nationality, birth, pnumber);  
      

 
        System.out.println("Passenger added: " + fname + " " + lname);
    }

    public static void main(String[] args) {
        String response;
        Scanner sc = new Scanner(System.in);
        It2cRequiroso demo = new It2cRequiroso();
        int action;

        do {
            System.out.println("1. ADD");
            System.out.print("Enter Action: ");
            action = sc.nextInt();

            switch (action) {
                case 1:
                    demo.addPassenger();
                    break;
            }
            
            System.out.println("Continue?(Yes/No): ");
            response = sc.next();
            
        } while (response.equals("yes"));

        System.out.println("Thank You!");
    }
}
