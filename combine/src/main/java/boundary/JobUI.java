/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author leong kah tian
 */
import control.ApplicantManager;
import control.JobManager;
import java.util.InputMismatchException;
import java.util.Scanner;
import control.JobFunc;
import control.ReportManager;

public class JobUI {

    private JobManager jobManager;
    private JobFunc jobFunc;
    private ReportManager reportManager;
    private Scanner scanner;

    public JobUI(JobManager jobManager, JobFunc jobFunc, ReportManager reportManager) {
        this.jobManager = jobManager;
        this.jobFunc = jobFunc;
        this.reportManager = reportManager;
        this.scanner = new Scanner(System.in);
    }

    //main menu for job module
    public void jobMenu() {
        int choice = 0;
        do {
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
            System.out.println("+       8. Approve Applications                +");
            System.out.println("+       9. Exit                                +");
            System.out.println("+==============================================+");
            System.out.print("Enter your choice: ");
            String inputLine = scanner.nextLine().trim();
            if (inputLine.isEmpty()) {
                System.out.println("No input provided. Please enter a number.");
                continue;
            }
            try {
                choice = Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    jobFunc.addJob();
                    break;
                case 2:
                    jobFunc.searchJob();
                    break;
                case 3:
                    jobFunc.updateJob();
                    break;
                case 4:
                    jobFunc.deleteJob();
                    break;
                case 5:
                    jobFunc.displayJobs();
                    break;
                case 6:
                    jobFunc.sortJobs();
                    break;
                case 7:
                    reportManager.generateJobReports();
                    break;
                case 8:
                    jobFunc.approveApplications();           
                    break;
                case 9:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);

    }
}
