/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.JobPosting;
import entity.Applicant;
import adt.ArrayList;
import adt.HashMap;
import adt.HashSet;
import adt.Set;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantManager {

    private ArrayList<Applicant> applicants;
    private ArrayList<JobPosting> jobPostings;
    private HashMap<String, Applicant> applicantMap; // For efficient lookups by ID

    public ApplicantManager() {
        applicants = new ArrayList<>();
        jobPostings = new ArrayList<>();
        applicantMap = new HashMap<>();

        initializeDefaultApplicants();
    }

    private void initializeDefaultApplicants() {
        Set<String> skills1 = new HashSet<>();
        skills1.add("Java");
        skills1.add("Python");
        addApplicant(new Applicant("A101", "John Doe", "Kuala Lumpur", "Full-Time", skills1));

        Set<String> skills2 = new HashSet<>();
        skills2.add("JavaScript");
        addApplicant(new Applicant("A102", "Alice Smith", "Singapore", "Part-Time", skills2));

        Set<String> skills3 = new HashSet<>();
        skills3.add("Java");
        skills3.add("C++");
        skills3.add("SQL");
        addApplicant(new Applicant("A103", "Bob Tan", "Kuala Lumpur", "Full-Time", skills3));
    }

    public Applicant getApplicantById(String applicantId) {
        return applicantMap.get(applicantId);
    }

    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
        applicantMap.put(applicant.getId(), applicant);
    }

    public boolean updateApplicant(String applicantId, Applicant updatedInfo) {
        if (applicantMap.containsKey(applicantId)) {
            Applicant oldApplicant = applicantMap.get(applicantId);
            applicants.remove(oldApplicant);
            applicants.add(updatedInfo);
            applicantMap.put(applicantId, updatedInfo);
            return true;
        } else {
            return false;
        }

    }

    public boolean removeApplicant(String applicantId) {
        if (applicantMap.containsKey(applicantId)) {
            Applicant applicant = applicantMap.get(applicantId);
            applicants.remove(applicant);
            applicantMap.remove(applicantId);
            return true;
        }
        return false;
    }

    public ArrayList<Applicant> filterApplicants(String location, String jobType, Set<String> skills) {
        // Filter applicants based on criteria
        ArrayList<Applicant> filtered = new ArrayList<>();

        for (int i = 0; i < applicants.size(); i++) {
            Applicant app = applicants.get(i);
            boolean matches = true;

            // Check location criteria
            if (location != null && !location.isEmpty() && !app.getLocation().equals(location)) {
                matches = false;
            }

            // Check job type criteria
            if (jobType != null && !jobType.isEmpty() && !app.getDesiredJobType().equals(jobType)) {
                matches = false;
            }

            // Check skills criteria
            if (skills != null && !skills.isEmpty()) {
                if (!app.getSkills().containsAll(skills)) { // Works with Set
                    matches = false;
                }
            }

            if (matches) {
                filtered.add(app);
            }
        }

        return filtered;
    }

    // Sorting methods using different algorithms
    public void sortApplicantsByName() {
        // Insertion sort implementation
        for (int i = 1; i < applicants.size(); i++) {
            Applicant key = applicants.get(i);
            int j = i - 1;

            while (j >= 0 && applicants.get(j).getName().compareTo(key.getName()) > 0) {
                applicants.set(j + 1, applicants.get(j));
                j = j - 1;
            }
            applicants.set(j + 1, key);
        }
    }

    public void sortApplicantsBySkillCount() {
        // Selection sort implementation
        for (int i = 0; i < applicants.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < applicants.size(); j++) {
                if (applicants.get(j).getSkills().size() > applicants.get(minIndex).getSkills().size()) {
                    minIndex = j;
                }
            }

            // Swap
            Applicant temp = applicants.get(minIndex);
            applicants.set(minIndex, applicants.get(i));
            applicants.set(i, temp);
        }
    }
}
