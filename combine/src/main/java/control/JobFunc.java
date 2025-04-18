package control;

/**
 *
 * @author leong kah tian
 */
import ADT.*;
import entity.Applicant;
import entity.Job;
import java.util.ArrayList;
import java.util.Scanner;

public class JobFunc {

    private JobManager jobManager; // Handles job data persistence and retrieval
    private ReportManager reportManager; // Manages report generation
    private ApplicantManager applicantManager;  // Manages applicant data
    private MatchingEngineControl matchingEngineControl;  // Controls the matching between applicants and jobs
    private Scanner input;

    public JobFunc(JobManager jobManager, ReportManager reportManager, ApplicantManager applicantManager, MatchingEngineControl matchingEngineControl) {
        this.jobManager = jobManager;
        this.reportManager = reportManager;
        this.applicantManager = applicantManager;
        this.matchingEngineControl = matchingEngineControl;
        this.input = new Scanner(System.in);
    }

    // Add new job
    //Performs validation on job ID to prevent duplicates
    public void addJob() {
        System.out.println("\n=================================");
        System.out.println("           Add New Job           ");
        System.out.println("=================================");

        //user input and validation
        System.out.print("Enter Job ID: ");
        String jobID = input.nextLine().trim().toUpperCase();

        if (jobID.isEmpty()) {
            System.out.println("Job ID cannot be empty.");
            return;
        }
        if (jobManager.jobExists(jobID)) {//check duplicate job id
            System.out.println("Job ID already exists. You cannot add a duplicate job.");
            return;
        }

        //get job details
        System.out.print("Enter Company Name: ");
        String company = formatTitleCase(input.nextLine().trim());

        System.out.print("Enter Location: ");
        String location = formatTitleCase(input.nextLine().trim());

        System.out.print("Enter Job Category: ");
        String category = formatTitleCase(input.nextLine().trim());

        System.out.print("Enter Job Position: ");
        String jobType = formatTitleCase(input.nextLine().trim());

        System.out.print("Enter Required Skills (comma-separated): ");
        String skills = formatTitleCase(input.nextLine().trim());

        System.out.print("Enter Salary (RM): ");
        int salary = input.nextInt();

        System.out.print("Enter Duration (in months): ");
        int duration = input.nextInt();
        
        System.out.print("Enter Min. CGPA: ");
        double minCGPA = input.nextDouble();
        input.nextLine(); // Consume newline

        // Create and add the new job object 
        jobManager.addJob(new Job(jobID, company, location, category, jobType, skills, salary, duration, minCGPA));
        System.out.println("\nJob added successfully!");
    }

    // Search Job  based on company name
    public void searchJob() {
        System.out.println("\n=================================");
        System.out.println("           Job Searching         ");
        System.out.println("=================================");
        System.out.print("Enter Company Name: ");
        String keyword = input.nextLine().trim();

        //Get jobs matching the search keyword
        ListInterface<Job> results = jobManager.searchJob(keyword);

        //display
        System.out.println("Search Results:        ");
        if (results.size() == 0) {
            System.out.println("No matching jobs found!");
        } else {
            for (int i = 0; i < results.size(); i++) {
                displayJobDetails(results.get(i));
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }

    // Filter jobs based on category, location, salary range
    // Allows users to skip criteria by providing empty input or zeros
    public void filterJobs() {
        System.out.println("\n=================================");
        System.out.println("          Job Filtering          ");
        System.out.println("=================================");
        
        //user input
        System.out.print("Enter Job Category (press Enter to skip): ");
        String category = input.nextLine().trim();
        System.out.print("Enter Job Location (press Enter to skip): ");
        String location = input.nextLine().trim();
        System.out.print("Enter Minimum Salary (or 0 to skip): ");
        int minSalary = input.nextInt();
        System.out.print("Enter Maximum Salary (or 0 to skip): ");
        int maxSalary = input.nextInt();
        input.nextLine(); // Consume newline

        //process filter criteria then convert empty/zero values to appropriate defaults
        ListInterface<Job> filteredJobs = jobManager.filterJobs(
                category.isEmpty() ? null : category,
                location.isEmpty() ? null : location,
                minSalary == 0 ? -1 : minSalary,
                maxSalary == 0 ? Integer.MAX_VALUE : maxSalary
        );

        //display
        System.out.println("Filtered Job Results:      ");
        if (filteredJobs.size() == 0) {
            System.out.println("No matching jobs found!");
        } else {
            for (int i = 0; i < filteredJobs.size(); i++) {
                displayJobDetails(filteredJobs.get(i));
            }
            System.out.println("---------------------------------------------------------------");
        }
    }

    // Update existing job posting
    //Allows selective updates by leaving fields blank 
    public void updateJob() {
        System.out.println("\n=================================");
        System.out.println("          Job Update            ");
        System.out.println("=================================");
        
        //user input and validation
        System.out.print("Enter Job ID to update: ");
        String jobID = input.nextLine().trim().toUpperCase();
        if (jobID.isEmpty()) {
            System.out.println("Job ID cannot be empty.");
            return;
        }
        if (!jobManager.jobExists(jobID)) {
            System.out.println("Job not found! Please enter a correct job ID.");
            return;
        }

        // Collect update information
        System.out.print("Enter Company Name (press Enter to keep unchanged): ");
        String company = input.nextLine().trim();
        System.out.print("Enter Location (press Enter to keep unchanged): ");
        String location = input.nextLine().trim();
        System.out.print("Enter Job Category (press Enter to keep unchanged): ");
        String category = input.nextLine().trim();
        System.out.print("Enter Job Position (press Enter to keep unchanged): ");
        String jobType = input.nextLine().trim();
        System.out.print("Enter Required Skill (press Enter to keep unchanged): ");
        String skill = input.nextLine().trim();
        System.out.print("Enter Salary (or 0 to keep unchanged): ");
        int salary = input.nextInt();
        System.out.print("Enter Duration (in months, or 0 to keep unchanged): ");
        int duration = input.nextInt();
        System.out.print("Enter Min. CGPA (or 0 to keep unchanged): ");
        double minCGPA = input.nextDouble();
        input.nextLine(); // Consume newline

        //Update job with new values and passing default values for unchanged fields
        jobManager.updateJob(jobID,
                company.isEmpty() ? null : company,
                location.isEmpty() ? null : location,
                category.isEmpty() ? null : category,
                jobType.isEmpty() ? null : jobType,
                skill.isEmpty() ? null : skill,
                salary == 0 ? -1 : salary,
                duration == 0 ? -1 : duration,
                minCGPA == 0 ? -1 : minCGPA
        );

        System.out.println("\nJob updated successfully!");
    }

    // Delete Job
    public void deleteJob() {
        //user input and validation
        System.out.print("\nEnter Job ID to delete: ");
        String jobID = input.nextLine().trim().toUpperCase();
        if (jobID.isEmpty()) {
            System.out.println("Job ID cannot be empty.");
            return;
        }
        if (!jobManager.jobExists(jobID)) {
            System.out.println("Job not found! Please enter a correct job ID.");
            return;
        }
        System.out.print("Are you sure you want to delete this job? (yes/y to confirm): ");
        String confirm = input.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            if (jobManager.deleteJob(jobID)) {
                System.out.println("\nJob deleted successfully!");
            } else {
                System.out.println("Error deleting job.");
            }
        } else {
            System.out.println("Job deletion cancelled.");
        }
    }

    // Display Jobs all available job postings
    public void displayJobs() {
         // Retrieve all jobs from the manager
        ListInterface<Job> jobs = jobManager.getAllJobs();

        System.out.println("\n=================================");
        System.out.println("          Job Listings           ");
        System.out.println("=================================");

         // Check if jobs are available
        if (jobs.size() == 0) {
            System.out.println("\nNo job postings available!");
            return;
        }
        // Display details for each job
        for (int i = 0; i < jobs.size(); i++) {
            displayJobDetails(jobs.get(i));
        }
    }

    // Sort jobs by category, salary or company name
    public void sortJobs() {
        System.out.println("\n=================================");
        System.out.println("|        Sort Job Listings      | ");
        System.out.println("=================================");
        System.out.println("|1. Sort by Job Category        |");
        System.out.println("|2. Sort by Salary              |");
        System.out.println("|3. Sort by Company Name        |");
        System.out.println("=================================");
        
        //user input
        System.out.print("Enter sorting choice: ");
        int choice = input.nextInt();
        input.nextLine(); // Consume newline

        // Get sorted job list based on selected criteria
        ListInterface<Job> sortedJobs = jobManager.getSortedJobs(choice);

        //display
        System.out.println("Sorted Job Listings:       ");
        if (sortedJobs.size() == 0) {
            System.out.println("No jobs available to sort!");
            return;
        }

        for (int i = 0; i < sortedJobs.size(); i++) {
            displayJobDetails(sortedJobs.get(i));
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    //approval process for job applications
    public void approveApplications(){
        //user input
        System.out.print("\nEnter Job ID: ");
        String jobId = input.nextLine().trim().toUpperCase();

        // Get the Job object from JobManager
        Job jobToApply = jobManager.getJobById(jobId);
        if (jobToApply == null) {
            System.out.println("Job not found.");
            return;
        }
        
        // Get and display all applicants for the specified job
        ADT.ArrayList<Applicant> applicantsForJob = reportManager.getApplicantsByJobId(jobId);
        if (applicantsForJob.size() == 0) {
            System.out.println("\nNo applicants have applied for this job.");
            return;
        } else {
            System.out.println("\nApplicants who applied for this job:");
            reportManager.displayApplicants(applicantsForJob, jobId);
        } 

        // Get applicant ID to approve
        System.out.print("\nEnter Applicant ID to approve: ");
        String applicantId = input.nextLine().trim().toUpperCase();
        
        // Check if Applicant Valid
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        if (applicant == null) {
            System.out.println("Applicant with ID " + applicantId + " not found.");
            return;
        }

        // Process approval through matching engine
        boolean success = matchingEngineControl.approveApplicant(applicantId, jobId);
        if (success) {
            System.out.println("\nSuccessfully approved application for: " + applicantId + " - " + applicant.getName());
        } else {
            System.out.println("\nApproval failed. Possible reasons:");
            System.out.println("- Applicant not found for this job");
            System.out.println("- Application already approved");
        }
        
        return;
    }
    
    //formatting title case
    public String formatTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        } else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
        }
    }

    //display detailed information
    public static void displayJobDetails(Job job) {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf(" Job ID      : %s\n", job.getJobID());
        System.out.printf(" Company     : %s\n", job.getCompany());
        System.out.printf(" Location    : %s\n", job.getLocation());
        System.out.printf(" Category    : %s\n", job.getCategory());
        System.out.printf(" Position    : %s\n", job.getJobType());
        System.out.printf(" Skills      : %s\n", job.getSkills());
        System.out.printf(" Salary      : RM %d\n", job.getSalary());
        System.out.printf(" Duration    : %d months\n", job.getDuration());
        System.out.printf(" Req. CGPA   : %.2f\n", job.getMinCGPA());
    }
}
