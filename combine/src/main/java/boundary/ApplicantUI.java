package boundary;

import control.ApplicantManager;
import control.JobManager;
import control.MatchingEngineControl;
import entity.Applicant;
import entity.Job;
import ADT.ArrayList;
import ADT.HashSet;
import ADT.Iterator;
import ADT.ListInterface;
import ADT.Set;
import java.util.Scanner;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantUI {

    // Controller objects to handle business logic
    private ApplicantManager applicantManager;    // Manages applicant data and operations
    private JobManager jobManager;                // Manages job data and operations
    private MatchingEngineControl matchingEngineControl;  // Handles job matching operations
    private Scanner scanner;                      // For user input

    // Constructor that initializes the UI with necessary control objects.
    public ApplicantUI(ApplicantManager applicantManager, JobManager jobManager, MatchingEngineControl matchingEngineControl) {
        this.applicantManager = applicantManager;
        this.jobManager = jobManager;
        this.matchingEngineControl = matchingEngineControl;
        scanner = new Scanner(System.in);
    }

    // Displays the main applicant management menu and processes user selections.
    public void displayApplicantMenu() {
        int choice = 0;
        do {
            // Display menu options
            System.out.println("\n+==========================================+");
            System.out.println("+        Applicant Management Menu         +");
            System.out.println("+==========================================+");
            System.out.println("+       1. Create Applicant                +");
            System.out.println("+       2. Update Applicant                +");
            System.out.println("+       3. Remove Applicant                +");
            System.out.println("+       4. Search/Filter Applicants        +");
            System.out.println("+       5. Sort Applicants                 +");
            System.out.println("+       6. Generate Report                 +");
            System.out.println("+       7. Apply for Job                   +");
            System.out.println("+       8. Exit                            +");
            System.out.println("+==========================================+");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim(); // Read input and trim whitespace

            // Input validation check
            if (input.isEmpty()) {
                System.out.println("Error: No input provided. Please enter a number.");
                continue;
            }

            // Parse input to integer with error handling
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                continue;
            }

            // Process user's choice
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
                    searchFilterApplicantsForm();
                    break;
                case 5:
                    sortApplicantsForm();
                    break;
                case 6:
                    generateReportForm();
                    break;
                case 7:
                    applyForJobForm();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    /**
     * Collects user input to create and add a new applicant to the system.
     * Gathers details such as name, location, job position, skills, and CGPA.
     */
    public void createApplicantForm() {
        System.out.println("\nCreate New Applicant (Enter 'cancel' at any time to cancel)");

        try {
            String name = "";
            String location = "";
            String jobType = "";
            Set<String> skillsSet = new HashSet<>();
            double applicantCGPA = 0.0;

            // Collect applicant name with validation loop
            while (true) {
                System.out.print("Enter applicant name: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) { // Use equalsIgnoreCase for robustness
                    System.out.println("Applicant creation canceled.");
                    return;
                }
                if (input.isEmpty()) {
                    System.out.println("Error: Name cannot be empty!");
                    
                } else {
                    name = input;
                    break; 
                }
            }

            // Collect location with validation
            while (true) {
                System.out.print("Enter location: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) {
                    System.out.println("Applicant creation canceled.");
                    return;
                }
                
                if (input.isEmpty()) {
                     System.out.println("Error: Location cannot be empty!"); 
                   
                } else {
                    location = input; 
                    break; 
                }
            }

            // Collect job position with validation
            while (true) {
                System.out.print("Enter desired job position: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) {
                    System.out.println("Applicant creation canceled.");
                    return;
                }
                
                if (input.isEmpty()) {
                     System.out.println("Error: Job position cannot be empty!");
                     
                } else {
                    jobType = input; 
                    break;
                }
            }

            // Process skills with validation
            while (true) {
                System.out.print("Enter skills (comma-separated, at least one required): ");
                String skillsInput = scanner.nextLine().trim();
                if (skillsInput.equalsIgnoreCase("cancel")) {
                    System.out.println("Applicant creation canceled.");
                    return;
                }

                if (!skillsInput.isEmpty()) {
                    String[] skills = skillsInput.split(",");
                    boolean addedSkill = false; // Flag to check if at least one valid skill was added
                    for (String skill : skills) {
                        String trimmedSkill = skill.trim();
                        if (!trimmedSkill.isEmpty()) {
                            skillsSet.add(trimmedSkill);
                            addedSkill = true;
                        }
                    }
                    if (addedSkill) { // Only break if at least one non-empty skill was found
                         break;
                    } else {
                         System.out.println("Error: Input contained only empty skill entries. At least one skill is required!");
                    }
                } else { // Handle the case where the user just presses Enter
                     System.out.println("Error: At least one skill is required!");
                }
            }

            // Collect and validate CGPA
            while (true) {
                System.out.print("Enter Current CGPA (0.0-4.0): ");
                String cgpaInput = scanner.nextLine().trim();

                if (cgpaInput.equalsIgnoreCase("cancel")) {
                    System.out.println("Applicant creation canceled.");
                    return;
                }

                try {
                    applicantCGPA = Double.parseDouble(cgpaInput);
                    if (applicantCGPA < 0 || applicantCGPA > 4.0) {
                        System.out.println("Error: CGPA must be between 0.0 and 4.0!");
                        // Loop continues
                    } else {
                        break; // Exit loop on valid CGPA
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number (e.g. 3.5)!");
                    // Loop continues
                }
            }

            // Create and add the new applicant
            Applicant newApplicant = new Applicant(
                    applicantManager.generateNextApplicantId(),
                    name,
                    location,
                    jobType,
                    skillsSet,
                    applicantCGPA
            );
            applicantManager.addApplicant(newApplicant);
            System.out.println("\nApplicant created successfully!");

        } catch (Exception e) { // Catch potential Scanner issues or others
            System.out.println("An unexpected error occurred during input: " + e.getMessage());
        }
    }

    /**
     * Collects user input to update an existing applicant's information. Only
     * fields that the user provides input for will be updated.
     */
    public void updateApplicantForm() {
        System.out.println("\nUpdate Applicant");
        System.out.print("Enter applicant ID to update: ");
        String id = scanner.nextLine();

        // Verify the applicant exists before proceeding
        Applicant existingApplicant = applicantManager.getApplicantById(id);
        if (existingApplicant == null) {
            System.out.println("Applicant with ID " + id + " not found.");
            return;
        }

        // Collect updated information (empty input means keep existing value)
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("Enter new location (leave blank to keep current): ");
        String location = scanner.nextLine();

        System.out.print("Enter new job position (leave blank to keep current): ");
        String jobType = scanner.nextLine();

        // Process skills update
        System.out.print("Enter new skills (comma-separated, leave blank to keep current): ");
        String skillsInput = scanner.nextLine();
        Set<String> skillsList = existingApplicant.getSkills();

        if (!skillsInput.trim().isEmpty()) {
            skillsList = new HashSet<>();
            String[] skills = skillsInput.split(",");
            for (String skill : skills) {
                skillsList.add(skill.trim());
            }
        }

        System.out.print("Enter new CGPA (0 to keep current): ");
        double applicantCGPA = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Create updated applicant object with new values or existing ones if empty
        Applicant updatedApplicant = new Applicant(
                id,
                name.isEmpty() ? existingApplicant.getName() : name,
                location.isEmpty() ? existingApplicant.getLocation() : location,
                jobType.isEmpty() ? existingApplicant.getDesiredJobType() : jobType,
                skillsList,
                applicantCGPA
        );

        // Attempt to update and provide feedback
        if (applicantManager.updateApplicant(id, updatedApplicant)) {
            System.out.println("\nApplicant updated successfully!");
        } else {
            System.out.println("\nFailed to update applicant.");
        }
    }

    // Removes an applicant from the system based on the provided ID.
    public void removeApplicantForm() {
        System.out.println("\nRemove Applicant");
        System.out.print("Enter applicant ID to remove: ");
        String id = scanner.nextLine();

        // Attempt to remove and provide feedback
        if (applicantManager.removeApplicant(id)) {
            System.out.println("Applicant removed successfully!");
        } else {
            System.out.println("Failed to remove applicant.");
        }
    }

    /**
     * Allows searching and filtering applicants based on various criteria.
     * Users can filter by name, location, job type, and skills.
     */
    public void searchFilterApplicantsForm() {
        System.out.println("\n---------------------------");
        System.out.println("Search/Filter Applicants");
        System.out.println("---------------------------");

        // Collect search criteria
        System.out.print("Enter name to search (leave blank for any): ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter location to filter by (leave blank for any): ");
        String location = scanner.nextLine().trim();

        System.out.print("Enter job type to filter by (leave blank for any): ");
        String jobType = scanner.nextLine().trim();

        // Process skills for filtering
        System.out.print("Enter skills to filter by (comma-separated, leave blank for any): ");
        String[] skills = scanner.nextLine().split(",");
        Set<String> skillsSet = new HashSet<>();
        for (String skill : skills) {
            if (!skill.trim().isEmpty()) {
                skillsSet.add(skill.trim().toLowerCase());
            }
        }

        // Search for applicants using the provided criteria
        ArrayList<Applicant> results = applicantManager.searchApplicants(
                name.isEmpty() ? null : name,
                location.isEmpty() ? null : location,
                jobType.isEmpty() ? null : jobType,
                skillsSet.isEmpty() ? null : skillsSet
        );

        // Display results in a formatted table
        System.out.println("\nSearch Results (" + results.size() + " matches):");
        printApplicantsAsTable(results);
    }

    /**
     * Provides options for sorting applicants by different criteria. Includes
     * retry logic for invalid inputs.
     */
    public void sortApplicantsForm() {
        int retryLimit = 3;
        int retryCount = 0;

        while (retryCount < retryLimit) {
            System.out.println("\n---------------------------");
            System.out.println("Sort Applicants");
            System.out.println("---------------------------");
            System.out.println("1. Sort by Name");
            System.out.println("2. Sort by Location");
            System.out.println("3. Sort by Skill Count");
            System.out.println("4. Sort by ID");
            System.out.print("Enter your choice: ");

            // Validate input with retry mechanism
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: No input provided. Please try again.");
                retryCount++;
                continue;
            }

            int sortChoice;
            try {
                sortChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                retryCount++;
                continue;
            }

            // Process sorting choice using lambda comparators
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
                case 4:
                    applicantManager.sortApplicants((a1, a2) -> a1.getId().compareTo(a2.getId()));
                    System.out.println("Applicants sorted by ID.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    retryCount++;
                    continue;
            }

            // Display sorted results
            ArrayList<Applicant> sortedApplicants = applicantManager.getApplicants();
            printApplicantsAsTable(sortedApplicants);
            return;
        }

        System.out.println("Too many invalid attempts. Returning to applicant management menu.");
    }

    /**
     * Displays a formatted table of applicants with their details. Includes
     * columns for ID, name, location, job position, skills, CGPA, applied jobs,
     * and approved job status.
     */
    private void printApplicantsAsTable(ArrayList<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants to display.");
            return;
        }

        // Define table format
        String headerFormat = "+ %-8s | %-20s | %-15s | %-40s | %-40s | %-10s | %-35s | %-45s |";
        String rowFormat = "+ %-8s | %-20s | %-15s | %-40s | %-40s | %-10.2f | %-35s | %-45s |";
        String separator = "+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

        // Print table header
        System.out.println("\n" + separator);
        System.out.println(String.format(headerFormat, "ID", "Name", "Location", "Job Position", "Skills", "CGPA", "Applied Jobs (ID + Category)", "Job Approved"));
        System.out.println(separator);

        // Print each applicant's data as a row
        for (int i = 0; i < applicants.size(); i++) {
            Applicant app = applicants.get(i);

            // Format skills as comma-separated string
            StringBuilder skillsStr = new StringBuilder();
            Iterator<String> skillIter = app.getSkills().iterator();
            while (skillIter.hasNext()) {
                skillsStr.append(skillIter.next());
                if (skillIter.hasNext()) {
                    skillsStr.append(", ");
                }
            }

            // Format applied jobs as comma-separated string
            StringBuilder jobsStr = new StringBuilder();
            ArrayList<Job> appliedJobs = app.getAppliedJobs();
            for (int j = 0; j < appliedJobs.size(); j++) {
                Job job = appliedJobs.get(j);
                jobsStr.append(job.getJobID()).append(": ").append(job.getCategory());
                if (j < appliedJobs.size() - 1) {
                    jobsStr.append(", ");
                }
            }

            // Check for approved job
            String approvedStr = "None";
            if (app.hasApprovedApplication()) {
                Job approvedJob = matchingEngineControl.findApprovedJob(app);
                approvedStr = approvedJob.getJobID() + ": " + approvedJob.getJobType() + " at " + approvedJob.getCompany();
            }

            // Print formatted row
            System.out.println(String.format(rowFormat,
                    app.getId(),
                    app.getName(),
                    app.getLocation(),
                    app.getDesiredJobType(),
                    skillsStr,
                    app.getApplicantCGPA(),
                    jobsStr,
                    approvedStr
            ));
        }
        System.out.println(separator + "\n");
    }

    /**
     * Generates applicant reports based on specified filters. Supports both
     * summary and detailed reports.
     */
    public void generateReportForm() {
        System.out.println("\nGenerate Report");
        int reportChoice = 0;
        
        // Collect filter criteria for the report
        System.out.print("Enter location (leave blank for any): ");
        String location = scanner.nextLine();

        System.out.print("Enter job position (leave blank for any): ");
        String jobType = scanner.nextLine();

        // Process skills filter
        System.out.print("Enter skills to filter (comma-separated, leave blank for any): ");
        Set<String> skills = new HashSet<>();
        String[] skillsInput = scanner.nextLine().split(",");
        for (String skill : skillsInput) {
            if (!skill.trim().isEmpty()) {
                skills.add(skill.trim());
            }
        }

        while (true) { // Loop until valid input is received
                System.out.println("\nChoose Report Type:");
                System.out.println("1. Summary Report");
                System.out.println("2. Detailed Report");
                System.out.print("Enter choice: ");
                String choiceInput = scanner.nextLine().trim(); // Read as String and trim

                if (choiceInput.isEmpty()) {
                    System.out.println("Error: Input cannot be empty. Please enter 1 or 2.");
                    continue; // Ask again
                }

                try {
                    reportChoice = Integer.parseInt(choiceInput);
                    if (reportChoice == 1 || reportChoice == 2) {
                        break; // Valid input, exit the loop
                    } else {
                        System.out.println("Error: Invalid choice. Please enter 1 or 2.");
                        // Loop continues, asking again
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input. Please enter a number (1 or 2).");
                    // Loop continues, asking again
                }
            }

        // Filter applicants according to criteria
        ArrayList<Applicant> filtered = applicantManager.filterApplicants(
                location.isEmpty() ? null : location,
                jobType.isEmpty() ? null : jobType,
                skills.isEmpty() ? null : skills
        );

        // Generate and display the selected report type
        if (reportChoice == 1) {
            String report = applicantManager.generateSummaryReport(
                    location.isEmpty() ? null : location,
                    jobType.isEmpty() ? null : jobType,
                    skills.isEmpty() ? null : skills
            );
            System.out.println("\n" + report);
        } else {
            System.out.println("\n=== Detailed Applicant Report ===");
            printApplicantsAsTable(filtered);
        }
    }

    /**
     * Handles the process of an applicant applying for a job. Validates
     * applicant ID, checks for already approved applications, and presents
     * available jobs to apply for.
     */
    private void applyForJobForm() {
        System.out.println("\nApply for Job");
        System.out.print("Enter applicant ID: ");
        String applicantId = scanner.nextLine();

        // Verify applicant exists
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        // Check if applicant already has an approved job - cannot apply for more
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

        // Display available jobs
        ListInterface<Job> jobs = jobManager.getAllJobs();
        if (jobs.size() == 0) {
            System.out.println("No job postings available.");
            return;
        }

        System.out.println("\nAvailable Jobs:");
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            System.out.printf("%d. %s at %s (ID: %s)\n", i + 1, job.getJobType(), job.getCompany(), job.getJobID());
        }

        // Process job selection and application
        System.out.print("Enter job number to apply: ");
        try {
            int jobChoice = Integer.parseInt(scanner.nextLine());
            if (jobChoice < 1 || jobChoice > jobs.size()) {
                System.out.println("Invalid job number.");
                return;
            }

            Job selectedJob = jobs.get(jobChoice - 1);
            // Check if already applied for this job
            if (applicant.hasAppliedForJob(selectedJob.getJobID())) {
                System.out.println("You have already applied for this job.");
            } else {
                // Apply for the job
                applicant.applyForJob(selectedJob);
                System.out.println("Successfully applied for: " + selectedJob.getJobID());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}
