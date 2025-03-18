package control;

import ADT.*;
import entity.Job;
import java.util.Scanner;

/**
 *
 * @author leong kah tian
 */

public class JobFunc {

    private JobManager jobManager;
    private Scanner input;

    public JobFunc(JobManager jobManager) {
        this.jobManager = jobManager;
        this.input = new Scanner(System.in);
    }

    // Add Job
    public void addJob() {
        System.out.println("\n=================================");
        System.out.println("           Add New Job           ");
        System.out.println("=================================");

        // Job ID
        System.out.print("Enter Job ID: ");
        String jobID = input.nextLine().trim().toUpperCase();

        if (jobID.isEmpty()) {
            System.out.println("Job ID cannot be empty.");
            return;
        }
        if (jobManager.jobExists(jobID)) {
            System.out.println("Job ID already exists. You cannot add a duplicate job.");
            return;
        }

        // Other job details
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
        input.nextLine(); // Consume newline

        // Add job
        jobManager.addJob(new Job(jobID, company, location, category, jobType, skills, salary, duration));
        System.out.println("\nJob added successfully!");
    }

    // Search Job
    public void searchJob() {
        System.out.println("\n=================================");
        System.out.println("           Job Searching         ");
        System.out.println("=================================");
        System.out.print("Enter Company Name: ");
        String keyword = input.nextLine().trim();

        ListInterface<Job> results = jobManager.searchJob(keyword);

        System.out.println("Search Results:        ");

        if (results.size() == 0) {
            System.out.println("No matching jobs found！");
        } else {
            for (int i = 0; i < results.size(); i++) {
                displayJobDetails(results.get(i));
            }
            System.out.println("---------------------------------------------------------------");
        }
    }

    // Filter Jobs
    public void filterJobs() {
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
                category.isEmpty() ? null : category,
                location.isEmpty() ? null : location,
                minSalary == 0 ? -1 : minSalary,
                maxSalary == 0 ? Integer.MAX_VALUE : maxSalary
        );

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

    // Update Job
    public void updateJob() {
        System.out.println("\n=================================");
        System.out.println("          Job Update            ");
        System.out.println("=================================");
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

        // User input for updates
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
        input.nextLine(); // Consume newline

        // Update job
        jobManager.updateJob(jobID,
                company.isEmpty() ? null : company,
                location.isEmpty() ? null : location,
                category.isEmpty() ? null : category,
                jobType.isEmpty() ? null : jobType,
                skill.isEmpty() ? null : skill,
                salary == 0 ? -1 : salary,
                duration == 0 ? -1 : duration);

        System.out.println("\nJob updated successfully!");
    }

    // Delete Job
    public void deleteJob() {
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

    // Display Jobs
    public void displayJobs() {
        ListInterface<Job> jobs = jobManager.getAllJobs();

        System.out.println("\n=================================");
        System.out.println("          Job Listings           ");
        System.out.println("=================================");

        if (jobs.size() == 0) {
            System.out.println("\nNo job postings available!");
            return;
        }

        for (int i = 0; i < jobs.size(); i++) {
            displayJobDetails(jobs.get(i));
        }
    }

    // Sort Jobs
    public void sortJobs() {
        System.out.println("\n=================================");
        System.out.println("        Sort Job Listings        ");
        System.out.println("=================================");
        System.out.println("1. Sort by Job Category");
        System.out.println("2. Sort by Salary");
        System.out.println("3. Sort by Company Name");
        System.out.print("Enter sorting choice: ");

        int choice = input.nextInt();
        input.nextLine(); // Consume newline

        ListInterface<Job> sortedJobs = jobManager.getSortedJobs(choice);

        System.out.println("Sorted Job Listings:       ");
        if (sortedJobs.size() == 0) {
            System.out.println("No jobs available to sort!");
            return;
        }

        for (int i = 0; i < sortedJobs.size(); i++) {
            displayJobDetails(sortedJobs.get(i));
        }
        System.out.println("---------------------------------------------------------------");

    }

    // Generate Job Reports
    public void generateJobReports() {
        while (true) {
            System.out.println("\n+======================================+");
            System.out.println("|        Job Reporting           |");
            System.out.println("+======================================+");
            System.out.println("+  1. Full Job Postings Report         +");
            System.out.println("+  2. Filtered Job Report              +");
            System.out.println("+  3. Sorted Job Report                +");
            System.out.println("+  4. Summary Report                   +");
            System.out.println("+  5. Back                             +");
            System.out.println("+======================================+");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    displayJobs();
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

    private void summaryReport() {
        ListInterface<Job> jobs = jobManager.getAllJobs();

        if (jobs.size() == 0) {
            System.out.println("No job postings available！");
            return;
        }

        System.out.println("\n+======================================+");
        System.out.println("|          Job Summary Report          |");
        System.out.println("+======================================+");

        // Store job count per company
        HashMapInterface<String, Integer> companyJobCount = new HashMapImplementer<>();
        int totalJobs = jobs.size();
        int highestSalary = Integer.MIN_VALUE;
        int lowestSalary = Integer.MAX_VALUE;

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            String company = job.getCompany();

            int count = companyJobCount.get(company) == null ? 0 : companyJobCount.get(company);
            companyJobCount.put(company, count + 1);

            highestSalary = Math.max(highestSalary, job.getSalary());
            lowestSalary = Math.min(lowestSalary, job.getSalary());
        }

        System.out.printf("| Total Job Listings: %-16d |\n", totalJobs);
        System.out.printf("| Highest Salary    : RM %-10d    |\n", highestSalary);
        System.out.printf("| Lowest Salary     : RM %-10d    |\n", lowestSalary);
        System.out.println("+======================================+");

        // Display company job count
        for (int i = 0; i < jobs.size(); i++) {
            String company = jobs.get(i).getCompany();
            if (companyJobCount.containsKey(company)) {
                System.out.printf("| %-20s : %2d job(s)     |\n", company, companyJobCount.get(company));
                companyJobCount.remove(company);
            }
        }
        System.out.println("+======================================+");
    }

    // Formatting Title Case
    private String formatTitleCase(String input) {
        return input.isEmpty() ? input : Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }

    private void displayJobDetails(Job job) {
        System.out.println("---------------------------------------------------------------");
        System.out.printf(" Job ID      : %s\n", job.getJobID());
        System.out.printf(" Company     : %s\n", job.getCompany());
        System.out.printf(" Location    : %s\n", job.getLocation());
        System.out.printf(" Category    : %s\n", job.getCategory());
        System.out.printf(" Position    : %s\n", job.getJobType());
        System.out.printf(" Skills      : %s\n", job.getSkills());
        System.out.printf(" Salary      : RM %d\n", job.getSalary());
        System.out.printf(" Duration    : %d months\n", job.getDuration());
    }

}
