package control;
/**
 *
 * @author leong kah tian
 */
import ADT.ArrayList;
import ADT.HashMap;
import ADT.HashMapInterface;
import ADT.ListInterface;
import entity.Job;
import entity.Applicant;
import java.util.Scanner;

public class ReportManager {

    // References to other manager classes to access job and applicant data
    private JobManager jobManager;
    private ApplicantManager applicantManager;
    private Scanner input;

    public ReportManager(JobManager jobManager, ApplicantManager applicantManager) {
        this.jobManager = jobManager;
        this.applicantManager = applicantManager;
        this.input = new Scanner(System.in);
    }

    //report menu
    public void generateJobReports() {
        while (true) {
            System.out.println("\n+======================================+");
            System.out.println("|        Job Reporting                 |");
            System.out.println("|======================================|");
            System.out.println("|  1. Full Detailed Job Postings Report|");       
            System.out.println("|  2. Filtered Job Report              |");
            System.out.println("|  3. Sorted Job Report                |");
            System.out.println("|  4. Summary Report                   |");
            System.out.println("|  5. Back                             |");
            System.out.println("|======================================|");
           
            //user input
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    detailedReport();
                    break;
                case 2:
                    filterJobs();
                    break;
                case 3:
                    sortJobs();
                    break;
                case 4:
                    summaryReport();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
    }

    //detailed report based on either Job ID or Company Name
    private void detailedReport() {
        System.out.println("\n----------- Detailed Job Posting Report -----------");
        System.out.println("1. Search by Job ID");
        System.out.println("2. Search by Company Name");
        
        System.out.print("Enter your choice: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu...");
            return;
        }

        if (choice == 1) {
            // Search by Job ID
            System.out.print("Enter Job ID: ");
            String jobId = input.nextLine().trim();
            Job job = jobManager.getJobById(jobId);

            if (job == null) {
                System.out.println("No job found with ID: " + jobId);
                return;
            }

            System.out.println("\n------------- Job Details -------------\n ");
            displayJobDetails(job);

            //show which applicants applied for this job
            ArrayList<Applicant> applicantsForJob = getApplicantsByJobId(jobId);
            if (applicantsForJob.size() == 0) {
                System.out.println("\nNo applicants have applied for this job.");
            } else {
                System.out.println("\nApplicants who applied for this job:");
                displayApplicants(applicantsForJob, jobId);
            }
        } else if (choice == 2) {
            // Search by Company Name
            System.out.print("Enter Company Name: ");
            String companyName = input.nextLine().trim();

            //find all jobs based on user input
            ListInterface<Job> allJobs = jobManager.getAllJobs();
            ArrayList<String> jobIds = new ArrayList<>();
            for (int i = 0; i < allJobs.size(); i++) {
                Job j = allJobs.get(i);
                if (j.getCompany().equalsIgnoreCase(companyName)) {
                    jobIds.add(j.getJobID());
                }
            }

            if (jobIds.size() == 0) {
                System.out.println("No jobs found for company: " + companyName);
                return;
            }

            System.out.println("\nJobs at " + companyName + ":");
            for (int i = 0; i < jobIds.size(); i++) {
                Job job = jobManager.getJobById(jobIds.get(i));
                displayJobDetails(job);
            }

            //find all applicants who applied to any of those job IDs
            ArrayList<Applicant> applicantsForCompany = getApplicantsByCompany(companyName);
            if (applicantsForCompany.size() == 0) {
                System.out.println("\nNo applicants have applied for jobs at " + companyName);
            } else {
                System.out.println("\nApplicants who applied for jobs at " + companyName + ":");
                displayApplicants(applicantsForCompany, jobManager.getJobIdByName(companyName));
            }
        }  else {
            System.out.println("Invalid choice. Returning to menu...");
        }
    }

    //filter job postings based on category, location, and salary range
    private void filterJobs() {
        System.out.println("\n=================================");
        System.out.println("          Job Filtering          ");
        System.out.println("=================================");
        System.out.print("Enter Job Category (press Enter to skip): ");
        String category = input.nextLine().trim();

        System.out.print("Enter Job Location (press Enter to skip): ");
        String location = input.nextLine().trim();
        System.out.print("Enter Minimum Salary (or 0 to skip): ");
        int minSalary = input.nextInt();
        System.out.print("Enter Maximum Salary (or 0 to skip): ");
        int maxSalary = input.nextInt();
        input.nextLine(); // Consume newline

        ListInterface<Job> filteredJobs = jobManager.filterJobs(
            // Convert empty inputs to null to indicate no filter
            category.isEmpty() ? null : category,
            location.isEmpty() ? null : location,
            minSalary == 0 ? -1 : minSalary,
            maxSalary == 0 ? Integer.MAX_VALUE : maxSalary
        );
        // Display filtered results
        System.out.println("\nFiltered Job Results:");
        if (filteredJobs.size() == 0) {
            System.out.println("No matching jobs found!");
        } else {
            for (int i = 0; i < filteredJobs.size(); i++) {
                displayJobDetails(filteredJobs.get(i));
            }
            System.out.println("---------------------------------------------------------------");
        }
    }

    //sort job postings based on selected criteria
    private void sortJobs() {
        System.out.println("\n=================================");
        System.out.println("        Sort Job Listings        ");
        System.out.println("=================================");
        System.out.println("|1. Sort by Job Category        |");
        System.out.println("|2. Sort by Salary              |");
        System.out.println("|3. Sort by Company Name        |");
        System.out.println("=================================");
        System.out.println("Enter sorting choice: ");

        int choice = input.nextInt();
        input.nextLine(); // Consume newline
        
        // Retrieve sorted job list based on user's choice
        ListInterface<Job> sortedJobs = jobManager.getSortedJobs(choice);
        System.out.println("\nSorted Job Listings:");
        if (sortedJobs.size() == 0) {
            System.out.println("No jobs available to sort!");
            return;
        }

        for (int i = 0; i < sortedJobs.size(); i++) {// Display the sorted job listings
            displayJobDetails(sortedJobs.get(i));
        }
        System.out.println("-------------------------------------------------------------------");
    }

    //generate a summary report
    private void summaryReport() {
        ListInterface<Job> jobs = jobManager.getAllJobs();
        if (jobs.size() == 0) {
            System.out.println("No job postings available!");
            return;
        }

        //count job postings per company and determine highest/lowest salary
        HashMapInterface<String, Integer> companyJobCount = new HashMap<>();
        int totalJobs = jobs.size();
        int highestSalary = Integer.MIN_VALUE;
        int lowestSalary = Integer.MAX_VALUE;

        for (int i = 0; i < jobs.size(); i++) {// Process each job to collect statistics
            Job job = jobs.get(i);
            String company = job.getCompany();

            // Update job count for this company
            int count = companyJobCount.get(company) == null ? 0 : companyJobCount.get(company);
            companyJobCount.put(company, count + 1);

            highestSalary = Math.max(highestSalary, job.getSalary());
            lowestSalary = Math.min(lowestSalary, job.getSalary());
        }
        System.out.println("\n+======================================+");
        System.out.println("|          Job Summary Report          |");
        System.out.println("+======================================+");
        System.out.printf("| Total Job Listings: %-16d |\n", totalJobs);
        System.out.printf("| Highest Salary    : RM %-10d    |\n", highestSalary);
        System.out.printf("| Lowest Salary     : RM %-10d    |\n", lowestSalary);
        System.out.println("+======================================+");

        //display the count of jobs per company
        for (int i = 0; i < jobs.size(); i++) {
            String company = jobs.get(i).getCompany();
            if (companyJobCount.containsKey(company)) {
                System.out.printf("| %-20s : %2d job(s)     |\n", company, companyJobCount.get(company));
                companyJobCount.remove(company);
            }
        }
        System.out.println("+======================================+");
    }

    //find applicants for a specific job ID
    public ArrayList<Applicant> getApplicantsByJobId(String jobId) {
        ArrayList<Applicant> matchingApplicants = new ArrayList<>();
        ArrayList<Applicant> allApplicants = applicantManager.getApplicants();

        for (int i = 0; i < allApplicants.size(); i++) {
            Applicant app = allApplicants.get(i);
            if (app.hasAppliedForJob(jobId)) {
                matchingApplicants.add(app);
            }
        }
        return matchingApplicants;
    }

    //find applicants who applied for jobs from a specific company
    private ArrayList<Applicant> getApplicantsByCompany(String companyName) {
        ArrayList<Applicant> matchingApplicants = new ArrayList<>();
        ArrayList<Applicant> allApplicants = applicantManager.getApplicants();

        for (int i = 0; i < allApplicants.size(); i++) {
            Applicant app = allApplicants.get(i);
            // Check each job the applicant applied
            ArrayList<Job> appliedJobs = app.getAppliedJobs();
            for (int j = 0; j < appliedJobs.size(); j++) {
                if (appliedJobs.get(j).getCompany().equalsIgnoreCase(companyName)) {
                    matchingApplicants.add(app);
                    break; // Avoid duplicates if multiple jobs from same company
                }
            }
        }
        return matchingApplicants;
    }

    //display a single job's details
    private void displayJobDetails(Job job) {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf(" Job ID      : %s\n", job.getJobID());
        System.out.printf(" Company     : %s\n", job.getCompany());
        System.out.printf(" Location    : %s\n", job.getLocation());
        System.out.printf(" Category    : %s\n", job.getCategory());
        System.out.printf(" Position    : %s\n", job.getJobType());
        System.out.printf(" Skills      : %s\n", job.getSkills());
        System.out.printf(" Salary      : RM %d\n", job.getSalary());
        System.out.printf(" Duration    : %d months\n", job.getDuration());
    }
    
    //display a table of applicants
    public void displayApplicants(ArrayList<Applicant> applicants, String jobId) {
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-15s | %-30s | %-20s |\n", 
                          "ID", "Name", "Location", "Desired Job Position", "Application Status");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < applicants.size(); i++) {
            Applicant a = applicants.get(i);
            System.out.printf("| %-10s | %-15s | %-15s | %-30s | %-20s |\n",
                              a.getId(),
                              a.getName(),
                              a.getLocation(),
                              a.getDesiredJobType(),
                              a.getApplicationStatus(jobId)
            );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }
}
