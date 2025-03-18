/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import control.ApplicantManager;
import entity.Applicant;
import adt.ArrayList;
import adt.HashSet;
import adt.Iterator;
import adt.Set;
import control.JobPostingManager;
import entity.JobPosting;
import java.util.Scanner;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantUI {

    private ApplicantManager applicantManager;
    private JobPostingManager jobPostingManager;
    private Scanner scanner;

    public ApplicantUI(ApplicantManager applicantManager, JobPostingManager jobPostingManager) {
        this.applicantManager = applicantManager;
        this.jobPostingManager = jobPostingManager;
        scanner = new Scanner(System.in);
    }

    public void displayApplicantMenu() {
        int choice = 0;
        do {
            System.out.println("\n===========================");
            System.out.println("Applicant Management Menu");
            System.out.println("===========================");
            System.out.println("1. Create Applicant");
            System.out.println("2. Update Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Filter Applicants");
            System.out.println("5. Sort Applicants");
            System.out.println("6. Search by Name (Binary)");
            System.out.println("7. Generate Report");
            System.out.println("8. Apply for Job");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim(); // Read input and trim whitespace

            // Validate input
            if (input.isEmpty()) {
                System.out.println("Error: No input provided. Please enter a number.");
                continue; // Skip the rest of the loop and prompt again
            }

            try {
                choice = Integer.parseInt(input); // Parse the input to an integer
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                continue; // Skip the rest of the loop and prompt again
            }

            switch (choice) {
                case 1:
                    createApplicantForm();
                    break;
                case 2:
                    updateApplicantForm();
                    break;
                case 3:
                    removeApplicantForm();
                    break;
                case 4:
                    filterApplicantsForm();
                    break;
                case 5:
                    sortApplicantsForm();
                    break;
                case 6:
                    binarySearchByNameForm();
                    break;
                case 7:
                    generateReportForm();
                    break;
                case 8:
                    applyForJobForm();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    public void createApplicantForm() {
        System.out.println("\nCreate New Applicant");
        System.out.print("Enter applicant name: ");
        String name = scanner.nextLine();

//        System.out.print("Enter applicant ID: ");
//        String id = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter desired job type: ");
        String jobType = scanner.nextLine();

        System.out.print("Enter skills (comma-separated): ");
        String[] skills = scanner.nextLine().split(",");
        Set<String> skillsSet = new HashSet<>();
        for (String skill : skills) {
            skillsSet.add(skill.trim());
        }

        Applicant newApplicant = new Applicant(applicantManager.generateNextApplicantId(), name, location, jobType, skillsSet);
        applicantManager.addApplicant(newApplicant);
        System.out.println("Applicant created successfully!");
    }

    public void updateApplicantForm() {
        System.out.println("\nUpdate Applicant");
        System.out.print("Enter applicant ID to update: ");
        String id = scanner.nextLine();

        // First, retrieve the existing applicant
        Applicant existingApplicant = applicantManager.getApplicantById(id);

        if (existingApplicant == null) {
            System.out.println("Applicant with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("Enter new location (leave blank to keep current): ");
        String location = scanner.nextLine();

        System.out.print("Enter new job type (leave blank to keep current): ");
        String jobType = scanner.nextLine();

        System.out.print("Enter new skills (comma-separated, leave blank to keep current): ");
        String skillsInput = scanner.nextLine();
        Set<String> skillsList = existingApplicant.getSkills(); // Start with existing skills

        if (!skillsInput.trim().isEmpty()) {
            // Replace with new skills if provided
            skillsList = new HashSet<>();
            String[] skills = skillsInput.split(",");
            for (String skill : skills) {
                skillsList.add(skill.trim());
            }
        }

        // Create updated applicant with correct parameter order and preserving existing values where needed
        Applicant updatedApplicant = new Applicant(
                id,
                name.isEmpty() ? existingApplicant.getName() : name,
                location.isEmpty() ? existingApplicant.getLocation() : location,
                jobType.isEmpty() ? existingApplicant.getDesiredJobType() : jobType,
                skillsList
        );

        if (applicantManager.updateApplicant(id, updatedApplicant)) {
            System.out.println("Applicant updated successfully!");
        } else {
            System.out.println("Failed to update applicant.");
        }
    }

    public void removeApplicantForm() {
        System.out.println("\nRemove Applicant");
        System.out.print("Enter applicant ID to remove: ");
        String id = scanner.nextLine();

        if (applicantManager.removeApplicant(id)) {
            System.out.println("Applicant removed successfully!");
        } else {
            System.out.println("Failed to remove applicant.");
        }
    }

    public void filterApplicantsForm() {
        System.out.println("\n---------------------------");
        System.out.println("Filter Applicants");
        System.out.println("---------------------------");
        System.out.print("Enter location to filter by (leave blank for any): ");
        String location = scanner.nextLine();

        System.out.print("Enter job type to filter by (leave blank for any): ");
        String jobType = scanner.nextLine();

        System.out.print("Enter skills to filter by (comma-separated, leave blank for any): ");
        String[] skills = scanner.nextLine().split(",");
        Set<String> skillsSet = new HashSet<>();
        for (String skill : skills) {
            if (!skill.trim().isEmpty()) {
                skillsSet.add(skill.trim().toLowerCase()); // Convert to lowercase for case-insensitive comparison
            }
        }

        ArrayList<Applicant> results = applicantManager.filterApplicants(
                location.isEmpty() ? null : location,
                jobType.isEmpty() ? null : jobType,
                skillsSet.isEmpty() ? null : skillsSet
        );

        System.out.println("\nFilter Results (" + results.size() + " matches):");
        printApplicantsAsTable(results);
    }

    public void sortApplicantsForm() {
        int retryLimit = 3; // Maximum number of invalid input attempts
        int retryCount = 0; // Counter for invalid input attempts

        while (retryCount < retryLimit) {
            System.out.println("\n---------------------------");
            System.out.println("Sort Applicants");
            System.out.println("---------------------------");
            System.out.println("1. Sort by Name");
            System.out.println("2. Sort by Location");
            System.out.println("3. Sort by Skill Count");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim(); // Read input and trim whitespace

            // Validate input
            if (input.isEmpty()) {
                System.out.println("Error: No input provided. Please try again.");
                retryCount++;
                continue; // Skip the rest of the loop and prompt again
            }

            int sortChoice;
            try {
                sortChoice = Integer.parseInt(input); // Parse the input to an integer
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                retryCount++;
                continue; // Skip the rest of the loop and prompt again
            }

            switch (sortChoice) {
                case 1:
                    applicantManager.sortApplicants((a1, a2) -> a1.getName().compareTo(a2.getName()));
                    System.out.println("Applicants sorted by name.");
                    break;
                case 2:
                    applicantManager.sortApplicants((a1, a2) -> a1.getLocation().compareTo(a2.getLocation()));
                    System.out.println("Applicants sorted by location.");
                    break;
                case 3:
                    applicantManager.sortApplicants((a1, a2) -> Integer.compare(a1.getSkills().size(), a2.getSkills().size()));
                    System.out.println("Applicants sorted by skill count.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    retryCount++;
                    continue; // Skip the rest of the loop and prompt again
            }

            // Display sorted applicants
            ArrayList<Applicant> sortedApplicants = applicantManager.getApplicants();
            // Replace the for-loop with:
            printApplicantsAsTable(sortedApplicants);

            return; // Exit the method after successful sorting
        }

        System.out.println("Too many invalid attempts. Returning to applicant management menu.");
    }

    public void binarySearchByNameForm() {
        System.out.println("\n---------------------------");
        System.out.println("Search by Name (Binary Search)");
        System.out.println("---------------------------");
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        ArrayList<Applicant> results = applicantManager.binarySearchByName(name);

        if (results.isEmpty()) {
            System.out.println("No applicants found with name: " + name);
        } else {
            System.out.println("\nSearch Results (" + results.size() + " matches):");
            printApplicantsAsTable(results);
        }
    }

    private void printApplicantsAsTable(ArrayList<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants to display.");
            return;
        }

        // Table headers and formatting
        String headerFormat = "%-8s | %-20s | %-15s | %-12s | %-30s | %s";
        String rowFormat = "%-8s | %-20s | %-15s | %-12s | %-30s | %s";
        String separator = "-----------------------------------------------------------------------------------------------------------------------------------------";

        // Print table header
        System.out.println("\n" + separator);
        System.out.println(String.format(headerFormat, "ID", "Name", "Location", "Job Type", "Skills", "Applied Jobs (ID + Title)"));
        System.out.println(separator);

        // Print each applicant
        for (int i = 0; i < applicants.size(); i++) {
            Applicant app = applicants.get(i);
            // Format skills as a comma-separated string
            StringBuilder skillsStr = new StringBuilder();
            Iterator<String> skillIter = app.getSkills().iterator();
            while (skillIter.hasNext()) {
                skillsStr.append(skillIter.next());
                if (skillIter.hasNext()) {
                    skillsStr.append(", ");
                }
            }

            // Format applied jobs (Job ID + Title)
            StringBuilder jobsStr = new StringBuilder();
            ArrayList<JobPosting> appliedJobs = app.getAppliedJobs();
            for (int j = 0; j < appliedJobs.size(); j++) {
                JobPosting job = appliedJobs.get(j);
                jobsStr.append(job.getId()).append(": ").append(job.getTitle()); // Job ID + Title
                if (j < appliedJobs.size() - 1) {
                    jobsStr.append(", ");
                }
            }

            // Print formatted row
            System.out.println(String.format(rowFormat,
                    app.getId(),
                    app.getName(),
                    app.getLocation(),
                    app.getDesiredJobType(),
                    skillsStr,
                    jobsStr));
        }
        System.out.println(separator + "\n");
    }

    public void generateReportForm() {
        System.out.println("\nGenerate Report");
        System.out.print("Enter location (leave blank for any): ");
        String location = scanner.nextLine();

        System.out.print("Enter job type (leave blank for any): ");
        String jobType = scanner.nextLine();

        System.out.print("Enter skills to filter (comma-separated, leave blank for any): ");
        Set<String> skills = new HashSet<>();
        String[] skillsInput = scanner.nextLine().split(",");
        for (String skill : skillsInput) {
            if (!skill.trim().isEmpty()) {
                skills.add(skill.trim());
            }
        }

        System.out.println("\nChoose Report Type:");
        System.out.println("1. Summary Report");
        System.out.println("2. Detailed Report");
        System.out.print("Enter choice: ");
        int reportChoice = Integer.parseInt(scanner.nextLine());

        ArrayList<Applicant> filtered = applicantManager.filterApplicants(
                location.isEmpty() ? null : location,
                jobType.isEmpty() ? null : jobType,
                skills.isEmpty() ? null : skills
        );

        if (reportChoice == 1) {
            // Generate summary report
            String report = applicantManager.generateSummaryReport(
                    location.isEmpty() ? null : location,
                    jobType.isEmpty() ? null : jobType,
                    skills.isEmpty() ? null : skills
            );
            System.out.println("\n" + report);
        } else {
            // For detailed report, print the filtered list directly
            System.out.println("\n=== Detailed Applicant Report ===");
            printApplicantsAsTable(filtered);
        }
    }

    private void applyForJobForm() {
        System.out.println("\nApply for Job");
        System.out.print("Enter applicant ID: ");
        String applicantId = scanner.nextLine();
        Applicant applicant = applicantManager.getApplicantById(applicantId);

        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        ArrayList<JobPosting> jobs = jobPostingManager.getAllJobPostings();
        if (jobs.isEmpty()) {
            System.out.println("No job postings available.");
            return;
        }

        System.out.println("\nAvailable Jobs:");
        for (int i = 0; i < jobs.size(); i++) {
            JobPosting job = jobs.get(i);
            System.out.printf("%d. %s (ID: %s)\n", i + 1, job.getTitle(), job.getId());
        }

        System.out.print("Enter job number to apply: ");
        try {
            int jobChoice = Integer.parseInt(scanner.nextLine());
            if (jobChoice < 1 || jobChoice > jobs.size()) {
                System.out.println("Invalid job number.");
                return;
            }

            JobPosting selectedJob = jobs.get(jobChoice - 1);
            if (applicant.hasAppliedForJob(selectedJob.getId())) {
                System.out.println("You have already applied for this job.");
            } else {
                applicant.applyForJob(selectedJob);
                System.out.println("Successfully applied for: " + selectedJob.getTitle());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}
