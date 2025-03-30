/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leong kah tian
 */

import boundary.ApplicantUI;
import boundary.JobUI;
import control.JobManager;
import control.JobFunc;
import control.ReportManager;
import control.ApplicantManager;
import java.util.Scanner;

public class internMainmenu {

    public static void main(String[] args) {
        JobManager jobManager = new JobManager();
        ApplicantManager applicantManager = new ApplicantManager();
        JobFunc jobFunc = new JobFunc(jobManager);
        ReportManager reportManager = new ReportManager(jobManager, applicantManager);
        JobUI jobUI = new JobUI(jobManager, jobFunc, reportManager);
        ApplicantUI applicantUI = new ApplicantUI(applicantManager, jobManager);

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        //main menu
        while (true) {
            System.out.println("\n============================");
            System.out.println("+   Internship Main Menu   +");
            System.out.println("============================");
            System.out.println("+ 1. Job Management        +");
            System.out.println("+ 2. Applicant Management  +");
            System.out.println("+ 3.                       +");
            System.out.println("+ 4. Exit                  +");
            System.out.println("============================");            
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    jobUI.jobMenu();
                    break;
                case 2:
                    applicantUI.displayApplicantMenu();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
