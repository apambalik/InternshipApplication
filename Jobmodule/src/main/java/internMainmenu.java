/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HONOR
 */
import java.util.Scanner;
import control.JobManager;
import control.JobFunc;

public class internMainmenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        JobManager jobManager = new JobManager();
        JobFunc jobFunc = new JobFunc(jobManager);

        int choice;

        do {
            System.out.println("\n==========================================");
            System.out.println("|            INTERN MANAGEMENT              |");
            System.out.println("==========================================");
            System.out.println("|  1. Applicant Portal                    |");
            System.out.println("|  2. Employer Portal                     |");
            System.out.println("|  3.                      |");
            System.out.println("|  0. Exit                                |");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine()); 

                switch (choice) {
                    case 1:
                        ApplicantUI();
                        break;
                    case 2:
                        JobUI();
                        break;
                    case 3:
                        ;
                        break;
                    case 0:
                        System.out.println("Exiting ... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric choice.");
                choice = -1; // Keep loop running
            }
        } while (choice != 0);

        scanner.close();
    }

}

