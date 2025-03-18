/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author leong kah tian
 */


import control.JobManager;
import java.util.InputMismatchException;
import java.util.Scanner;
import control.JobFunc;


public class JobUI {
    public static void main(String[] args) {
        //initiate JobManager and JobFunc to handle operations
        JobManager jobManager = new JobManager();
        JobFunc jobFunc = new JobFunc(jobManager);
        Scanner input = new Scanner(System.in);
        
        //main menu for job module
        while (true) {
            System.out.println("\n+==============================================+");
            System.out.println("+           Job Management System              +");
            System.out.println("+==============================================+");
            System.out.println("+       1. Add Job Posting                     +");
            System.out.println("+       2. Search Job Posting                  +");
            System.out.println("+       3. Update Job Posting                  +");
            System.out.println("+       4. Delete Job Posting                  +");
            System.out.println("+       5. Display All Job Postings            +");
            System.out.println("+       6. Sort Job Postings                   +");
            System.out.println("+       7. Generate Job Reports                +");
            System.out.println("+       8. Exit                                +");
            System.out.println("+==============================================+");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = input.nextInt();
                input.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                input.nextLine(); // Clear invalid input
                System.out.println("Invalid input! Please enter a number between 1 and 8.");
                continue;
            }

            switch (choice) {
                case 1: jobFunc.addJob();
                        break;
                case 2: jobFunc.searchJob();
                        break;
                case 3: jobFunc.updateJob();
                        break;
                case 4: jobFunc.deleteJob();
                        break;
                case 5: jobFunc.displayJobs();
                        break;
                case 6: jobFunc.sortJobs();
                        break;
                case 7: jobFunc.generateJobReports();
                        break;
                case 8:
                    System.out.println("Exiting... Goodbye!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid option!! Please enter a number between 1 and 8.");
            }
        }
    }
}
