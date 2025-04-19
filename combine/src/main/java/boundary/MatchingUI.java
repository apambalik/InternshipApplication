package boundary;

import entity.Applicant;
import entity.Job;
import entity.Matching;
import ADT.ArrayList;
import ADT.ListInterface;
import control.MatchingEngineControl;
import control.ApplicantManager;
import control .JobManager;
import java.util.Scanner;

///**
// *
// * @author Lai Zheng Xuan
// */

public class MatchingUI {
    private MatchingEngineControl matchingEngineControl;
    private ApplicantManager applicantManager;
    private JobManager jobManager;
    
    private Scanner scanner;

    public MatchingUI(MatchingEngineControl matchingEngineControl, ApplicantManager applicantManager, JobManager jobManager) {
        this.matchingEngineControl = matchingEngineControl;
        this.applicantManager = applicantManager;
        this.jobManager = jobManager;
        this.scanner = new Scanner(System.in);
    }

    // Job Matching System Main Menu
    public void displayMatchingMenu() {
        int choice = 0;
        do {
            System.out.println("\n+==========================================+");
            System.out.println("+        Job Matching System               +");
            System.out.println("+==========================================+");
            System.out.println("+       1. Match Applicant to Jobs         +");
            System.out.println("+       2. Match Job to Applicants         +");
            System.out.println("+       3. Show Best Matches               +");
            System.out.println("+       4. Generate Matching Statistics    +");
            System.out.println("+       5. Exit                            +");
            System.out.println("+==========================================+");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1-5). ");
                continue;
            }

            switch (choice) {
                case 1:
                    matchApplicantToJobs();
                    break;
                case 2:
                    matchJobToApplicants();
                    break;
                case 3:
                    showBestMatches();
                    break;
                case 4:
                    generateMatchingReport();
                    break;
                case 5:
                    System.out.println("Exiting Matching Engine...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again(1-5). ");
            }
        } while (choice != 5);
    }

    
    
    // USER PROMPT FOR EACH FUNCTION
    // 1. Match Applicant to Jobs
    private void matchApplicantToJobs() {
        System.out.print("\nEnter Applicant ID: ");
        String applicantId = scanner.nextLine();

        // Check if Applicant Valid
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        if (applicant == null) {
            System.out.println("Applicant with ID " + applicantId + " not found.");
            return;
        }

        ArrayList<Matching> results = matchingEngineControl.findJobsForApplicant(applicantId);
        
        // Use jobManager instance to retrieve job list (not JobPostingManager)
        ListInterface<Job> jobs = jobManager.getAllJobs();
        if (jobs.size() == 0) {
            System.out.println("No job postings available.");
            return;
        }
        
        if (results.isEmpty()) {
            System.out.println("No suitable jobs found for applicant: " + applicant.getName());
        } else {
            System.out.println("\n=== Matching Jobs for Applicant " + applicantId + ": " + applicant.getName() + " ===");
            displayMatchingResultsAFJ(results);
            applyForBestMatchJob(applicantId);
        }
    }

    // 2. Match Job to Applicants
    private void matchJobToApplicants() {
        System.out.print("\nEnter Job ID: ");
        String jobId = scanner.nextLine();

        // Check if Job Valid
        Job job = jobManager.getJobById(jobId);
        if (job == null) {
            System.out.println("Job with ID " + jobId + " not found.");
            return;
        }

        ArrayList<Matching> results = matchingEngineControl.findApplicantsForJob(jobId);

        if (results.isEmpty()) {
            System.out.println("No suitable applicants found for job: " + job.getJobType() + " at " + job.getCompany());
        } else {
            System.out.println("\n=== Matching Applicants for Job " + jobId + ": " + job.getJobType() + " at " + job.getCompany() + " ===");
            displayMatchingResultsJFA(results);
            handleApplicantApproval(job, results);  // New approval handling
        }
    }
    
    // 3. Show Best Matches
    private void showBestMatches() {
        System.out.println("\n=== Top Best Matches ===");
        ArrayList<Matching> results = matchingEngineControl.findTopMatches(10);
        displayBestMatchingResults(results);
    }

    // 4. Generate Matching Report
    private void generateMatchingReport() {
        System.out.println("\n=== Matching Report ===");
        String report = matchingEngineControl.generateMatchingReport();
        System.out.println(report);
    }

    
    
    // APPLY/APPROVE JOB
    // Apply Job 
    private void applyForBestMatchJob(String applicantId) {
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        
        // Check if applicant already has an approved job
        if (applicant.hasApprovedApplication()) {
            Job approvedJob = matchingEngineControl.findApprovedJob(applicant);
            System.out.println("\nYou already have a job approved.");
            System.out.println("\n=== Approved Application ===");
            System.out.println("Company: " + approvedJob.getCompany());
            System.out.println("Position: " + approvedJob.getJobType());
            System.out.println("Application Status: " + applicant.getApplicationStatus(approvedJob.getJobID()));
            System.out.println("\nCannot apply for new positions.");
            return;
        }
        
        System.out.print("\nDo you want to apply for job? (Y for Yes/[Any Key] for No): ");
        String applyJob = scanner.nextLine().trim().toUpperCase();
        if (!applyJob.equals("Y")) {
            return;
        }
        
        System.out.print("\nEnter Job ID to apply: ");
        String jobID = scanner.nextLine().trim().toUpperCase();

        // Get the Job object from JobManager
        Job jobToApply = jobManager.getJobById(jobID);
        if (jobToApply == null) {
            System.out.println("Job not found.");
            return;
        }

        try {
            if (applicant.hasAppliedForJob(jobID)) {
                System.out.println("\nYou have already applied for this job.");
                System.out.println("\n=== Application History ===");
                System.out.println("Company: " + jobToApply.getCompany());
                System.out.println("Position: " + jobToApply.getJobType());
                System.out.println("Application Status: " + applicant.getApplicationStatus(jobID));
            } else {
                // Use the control layer to process the application
                applicant.applyForJob(jobManager.getJobById(jobID));
                System.out.println("\n=== Application Successful ===");
                System.out.println("Company: " + jobToApply.getCompany());
                System.out.println("Position: " + jobToApply.getJobType());
                System.out.println("Application Status: " + applicant.getApplicationStatus(jobID));
            }
        } catch (Exception e) {
            System.out.println("Error processing application: " + e.getMessage());
        }
    } 
    
    // Approve Job
    private void handleApplicantApproval(Job job, ArrayList<Matching> matchingApplicants) {
        System.out.print("\nDo you want to approve any applicant? (Y for Yes/[Any Key] for No): ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (!choice.equals("Y")) {
            return;
        }

        System.out.print("Enter Applicant ID to approve: ");
        String applicantId = scanner.nextLine().trim().toUpperCase();

        // Verify applicant exists in matching list using indexed loop
        Applicant selectedApplicant = null;
        for (int i = 0; i < matchingApplicants.size(); i++) {
            Matching match = matchingApplicants.get(i);
            if (match.getApplicant().getId().equalsIgnoreCase(applicantId)) {
                selectedApplicant = match.getApplicant();
                break;
            }
        }

        if (selectedApplicant == null) {
            System.out.println("Error: This applicant hasn't applied for this job or doesn't exist.");
            return;
        }

        // Check current status before approval
        if ("Approved".equals(selectedApplicant.getApplicationStatus(job.getJobID()))) {
            System.out.println("\nThis application has already been approved.");
            System.out.println("\n=== Approval History ===");
            System.out.println("Applicant: " + selectedApplicant.getName());
            System.out.println("Position: " + job.getJobType());
            System.out.println("Company: " + job.getCompany());
            System.out.println("Application Status: " + selectedApplicant.getApplicationStatus(job.getJobID()));
            return;
        }

        boolean success = matchingEngineControl.approveApplicant(applicantId, job.getJobID());

        if (success) {
            System.out.println("\n=== Approval Successful ===");
            System.out.println("Applicant: " + selectedApplicant.getName());
            System.out.println("Position: " + job.getJobType());
            System.out.println("Company: " + job.getCompany());
            System.out.println("Application Status: " + selectedApplicant.getApplicationStatus(job.getJobID()));
        } else {
            System.out.println("\nApproval failed. Possible reasons:");
            System.out.println("- Applicant have been approved by other company");
        }
    }
    
    
    
    // RESULT DISPLAY
    // Result for 1. Match Applicant to Jobs
    private void displayMatchingResultsAFJ(ArrayList<Matching> results) {
    System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    System.out.printf("| %-6s | %-5s | %-15s | %-15s | %-15s | %-30s | %-10s | %-70s |\n", "Score","ID", "Category", "Company", "Location", "Job Position", "Salary(RM)", "Match Details");
    System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for (int i = 0; i < results.size(); i++) {
            Matching result = results.get(i);
            System.out.printf("| %-6.2f | %-5s | %-15s | %-15s | %-15s | %-30s | %-10s | %-70s |\n",
                    result.getMatchScore() * 100,
                    result.getJob().getJobID(),
                    result.getJob().getCategory(),
                    result.getJob().getCompany(),
                    result.getJob().getLocation(),
                    result.getJob().getJobType(),
                    result.getJob().getSalary(),
                    result.getMatchDetails());
        }
        System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    }
    
    // Result for 2. Match Job to Applicants
    private void displayMatchingResultsJFA(ArrayList<Matching> results){        
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-6s | %-5s | %-15s | %-15s | %-40s | %-10s | %-70s |\n", "Score", "ID", "Applicant", "Location", "Skills", "CGPA", "Match Details");
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for (int i = 0; i < results.size(); i++) {
            Matching result = results.get(i);
            
            System.out.printf("| %-6.2f | %-5s | %-15s | %-15s | %-40s | %-10.2f | %-70s |\n",
                    result.getMatchScore() * 100,
                    result.getApplicant().getId(),
                    result.getApplicant().getName(),
                    result.getApplicant().getLocation(),
                    result.getApplicant().getSkillsAsString(),
                    result.getApplicant().getApplicantCGPA(),
                    result.getMatchDetails());
        }
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    }
    
    // Results for 3. Show Best Matches
    private void displayBestMatchingResults(ArrayList<Matching> results) {        
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-6s | %-20s | %-30s | %-15s | %-15s | %-15s | %-70s |\n", "Score", "Applicant", "Job Position", "Company", "Location", "Category", "Match Details");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

        for (int i = 0; i < results.size(); i++) {
            Matching result = results.get(i);

            System.out.printf("| %-6.2f | %-20s | %-30s | %-15s | %-15s | %-15s | %-70s |\n",
                    result.getMatchScore() * 100,
                    result.getApplicant().getName(),
                    result.getJob().getJobType(),
                    result.getJob().getCompany(),
                    result.getApplicant().getLocation(),
                    result.getJob().getCategory(),
                    result.getMatchDetails());
        }
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

        viewMatchDetailsPrompt(results);
    }

    // Ask if want to check details for 3. Show Best Matches
    private void viewMatchDetailsPrompt(ArrayList<Matching> results) {
        System.out.print("\nEnter match number to view details (0 to return): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= results.size()) {
                displayFullMatchDetails(results.get(choice - 1));
                viewMatchDetailsPrompt(results); // Show prompt again after viewing details
            } else if (choice != 0) {
                System.out.println("Invalid selection. Please try again.");
                viewMatchDetailsPrompt(results);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            viewMatchDetailsPrompt(results);
        }
    }

    // Details for 3. Show Best Matches
    private void displayFullMatchDetails(Matching match) {
        System.out.println("\n=== Matching Details ===");
        System.out.println("+----------------------------------------------------+---------------------------------------------------------------------------------------+");
        System.out.printf("| %-50s | %-85s |\n", "Applicant Details", "Job Details");
        System.out.println("+----------------------------------------------------+---------------------------------------------------------------------------------------+");
        
        // Match Table
        System.out.printf("| %-50s | %-85s |\n", "Applicant ID: " + match.getApplicant().getId(),                        "Job ID: " + match.getJob().getJobID());
        System.out.printf("| %-50s | %-85s |\n", "Name: " + match.getApplicant().getName(),                              "Company: " + match.getJob().getCompany());
        System.out.printf("| %-50s | %-85s |\n", "Location: " + match.getApplicant().getLocation(),                      "Location: " + match.getJob().getLocation());
        System.out.printf("| %-50s | %-85s |\n", "Desired Position: " + match.getApplicant().getDesiredJobType(),        "Position: " + match.getJob().getJobType());
        System.out.printf("| %-50s | %-85s |\n", "CGPA: " + match.getApplicant().getApplicantCGPA(),                     "Req. CGPA: " + match.getJob().getMinCGPA());
        System.out.printf("| %-50s | %-85s |\n", "Skills: " + match.getApplicant().getSkillsAsString(),                  "Req. Skill: " + match.getJob().getSkills());
        System.out.printf("| %-50s | %-85s |\n", "",                                                                     "Category: " + match.getJob().getCategory());
        System.out.printf("| %-50s | %-85s |\n", "",                                                                     "Salary: RM" + match.getJob().getSalary());
        System.out.printf("| %-50s | %-85s |\n", "",                                                                     "Duration: " + match.getJob().getDuration() + " months");
        
        // Match Details
        System.out.println("+====================================================+=======================================================================================+");
        System.out.println("  Match Score: " + String.format("%.2f", match.getMatchScore() * 100) + "%");
        System.out.println("  Match Breakdown:");
        System.out.println("  -  " + match.getMatchDetails().replace(", ", "\n  -  "));
    }
}

