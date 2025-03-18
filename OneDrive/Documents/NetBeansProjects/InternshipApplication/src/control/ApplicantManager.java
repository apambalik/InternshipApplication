/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

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
    private HashMap<String, Applicant> applicantMap; // For efficient lookups by ID

    public ApplicantManager() {
        applicants = new ArrayList<>();
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

    public ArrayList<Applicant> getApplicants() {
        return applicants;
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

    // Filter/Search applicants by multiple criteria
    public ArrayList<Applicant> filterApplicants(String location, String jobType, Set<String> skills) {
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
    
    
    // Quick Sort
    public void sortApplicantsByLocation() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> a1.getLocation().compareTo(a2.getLocation()));
    }

    public void sortApplicantsByName() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> a1.getName().compareTo(a2.getName()));
    }

    public void sortApplicantsBySkillCount() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> Integer.compare(a1.getSkills().size(), a2.getSkills().size()));
    }
    
    private void quickSort(ArrayList<Applicant> list, int low, int high, java.util.Comparator<Applicant> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator); // Partition the array
            quickSort(list, low, pi - 1, comparator);  // Sort the left sub-array
            quickSort(list, pi + 1, high, comparator); // Sort the right sub-array
        }
    }

    private int partition(ArrayList<Applicant> list, int low, int high, java.util.Comparator<Applicant> comparator) {
        Applicant pivot = list.get(high); // Choose the last element as the pivot
        int i = low - 1; // Index of the smaller element

        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (comparator.compare(list.get(j), pivot) < 0) {
                i++;
                // Swap list[i] and list[j]
                Applicant temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Swap the pivot element with list[i+1]
        Applicant temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1; // Return the partition index
    }
    
    // Generic Sorting Method - sort by any criteria
    public void sortApplicants(java.util.Comparator<Applicant> comparator) {
        quickSort(applicants, 0, applicants.size() - 1, comparator);
    }

}
