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
import java.util.Scanner;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantUI {

    private ApplicantManager applicantManager;
    private Scanner scanner;

    public ApplicantUI() {
        applicantManager = new ApplicantManager();
        scanner = new Scanner(System.in);
    }

    public void displayApplicantMenu() {
        int choice;
        do {
            System.out.println("\n===========================");
            System.out.println("Applicant Management System");
            System.out.println("===========================");
            System.out.println("1. Create Applicant");
            System.out.println("2. Update Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Filter Applicants");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

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
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public void createApplicantForm() {
        System.out.println("\nCreate New Applicant");
        System.out.print("Enter applicant name: ");
        String name = scanner.nextLine();

        System.out.print("Enter applicant ID: ");
        String id = scanner.nextLine();

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

        Applicant newApplicant = new Applicant(id, name, location, jobType, skillsSet);
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
        System.out.println("\nFilter Applicants");
        System.out.print("Enter location to filter by (leave blank for any): ");
        String location = scanner.nextLine();

        System.out.print("Enter job type to filter by (leave blank for any): ");
        String jobType = scanner.nextLine();

        System.out.print("Enter skills to filter by (comma-separated, leave blank for any): ");
        String[] skills = scanner.nextLine().split(",");
        Set<String> skillsSet = new HashSet<>();
        for (String skill : skills) {
            if (!skill.trim().isEmpty()) {
                skillsSet.add(skill.trim());
            }
        }

        ArrayList<Applicant> results = applicantManager.filterApplicants(
                location.isEmpty() ? null : location,
                jobType.isEmpty() ? null : jobType,
                skillsSet.isEmpty() ? null : skillsSet
        );

        System.out.println("\nFilter Results (" + results.size() + " matches):");
        for (int i = 0; i < results.size(); i++) {
            Applicant app = results.get(i);
            System.out.println("\nName: " + app.getName());
            System.out.println("ID: " + app.getId());
            System.out.println("Location: " + app.getLocation());
            System.out.println("Desired Job Type: " + app.getDesiredJobType());

            Set<String> applicantSkills = app.getSkills();
            System.out.print("Skills: ");

            Iterator<String> skillIterator = applicantSkills.iterator();
            StringBuilder skillsBuilder = new StringBuilder();

            while (skillIterator.hasNext()) {
                skillsBuilder.append(skillIterator.next());
                if (skillIterator.hasNext()) {
                    skillsBuilder.append(", ");
                }
            }

            System.out.println(skillsBuilder.toString());
        }
    }
}
