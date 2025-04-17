/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.Applicant;
import ADT.ArrayList;
import ADT.HashMap;
import ADT.HashSet;
import ADT.Iterator;
import ADT.Set;
import boundary.ApplicantUI;
import entity.Job;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantManager {

    private int nextApplicantId = 101; // Start from A101
    private ArrayList<Applicant> applicants;
    private HashMap<String, Applicant> applicantMap; // For efficient lookups by ID

    public ApplicantManager() {
        applicants = new ArrayList<>();
        applicantMap = new HashMap<>();

        initializeDefaultApplicants();
    }

    public String generateNextApplicantId() {
        return "A" + (nextApplicantId++);
    }

    private void initializeDefaultApplicants() {
        // Existing applicants
        Set<String> skills1 = new HashSet<>();
        skills1.add("Java");
        skills1.add("Python");
        addApplicant(new Applicant(generateNextApplicantId(), "John Doe", "Kuala Lumpur", "Data Analyst Intern", skills1, 3.80));

        Set<String> skills2 = new HashSet<>();
        skills2.add("JavaScript");
        addApplicant(new Applicant(generateNextApplicantId(), "Alice Smith", "Singapore", "Software Engineer Intern", skills2, 3.50));

        Set<String> skills3 = new HashSet<>();
        skills3.add("Java");
        skills3.add("C++");
        skills3.add("Problem-Solving");
        addApplicant(new Applicant(generateNextApplicantId(), "Bob Tan", "Kuala Lumpur", "Software Engineer Intern", skills3, 3.67));

        Set<String> skills4 = new HashSet<>();
        skills4.add("Python");
        skills4.add("SQL");
        skills4.add("Data Visualization");
        addApplicant(new Applicant(generateNextApplicantId(), "Sarah Lim", "Penang", "Data Analyst Intern", skills4, 3.45));

        Set<String> skills5 = new HashSet<>();
        skills5.add("Excel");
        skills5.add("Financial Modeling");
        addApplicant(new Applicant(generateNextApplicantId(), "Ahmad bin Ismail", "Selangor", "Financial Analyst Intern", skills5, 3.72));

        Set<String> skills6 = new HashSet<>();
        skills6.add("AutoCAD");
        skills6.add("SolidWorks");
        addApplicant(new Applicant(generateNextApplicantId(), "Priya Kaur", "Johor", "Mechanical Engineer Intern", skills6, 3.55));

        Set<String> skills7 = new HashSet<>();
        skills7.add("Biostatistics");
        skills7.add("Research Writing");
        addApplicant(new Applicant(generateNextApplicantId(), "David Wong", "Kuala Lumpur", "Medical Research Intern", skills7, 3.90));

        Set<String> skills8 = new HashSet<>();
        skills8.add("JavaScript");
        skills8.add("React");
        skills8.add("UI/UX");
        addApplicant(new Applicant(generateNextApplicantId(), "Nurul Hassan", "Selangor", "Frontend Developer Intern", skills8, 3.25));

        Set<String> skills9 = new HashSet<>();
        skills9.add("Machine Learning");
        skills9.add("TensorFlow");
        skills9.add("Python");
        addApplicant(new Applicant(generateNextApplicantId(), "Chen Wei", "Penang", "AI Research Intern", skills9, 3.88));

        Set<String> skills10 = new HashSet<>();
        skills10.add("Communication");
        skills10.add("Market Research");
        addApplicant(new Applicant(generateNextApplicantId(), "Fatimah Abdullah", "Kuala Lumpur", "Business Consultant Intern", skills10, 3.60));

        Set<String> skills11 = new HashSet<>();
        skills11.add("SEO");
        skills11.add("Content Creation");
        skills11.add("Social Media");
        addApplicant(new Applicant(generateNextApplicantId(), "Rajesh Kumar", "Selangor", "Digital Marketing Intern", skills11, 3.30));

        Set<String> skills12 = new HashSet<>();
        skills12.add("IoT");
        skills12.add("Precision Agriculture");
        addApplicant(new Applicant(generateNextApplicantId(), "Amirah Yusof", "Johor", "AgriTech Intern", skills12, 3.75));

        Set<String> skills13 = new HashSet<>();
        skills13.add("Semiconductor Physics");
        skills13.add("Problem-Solving");
        addApplicant(new Applicant(generateNextApplicantId(), "Kevin Ng", "Penang", "Process Engineer Intern", skills13, 3.65));
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

    // Filter/Search applicants by multiple criteria (case-insensitive)
    public ArrayList<Applicant> filterApplicants(String location, String jobType, Set<String> skills) {
        ArrayList<Applicant> filtered = new ArrayList<>();

        for (int i = 0; i < applicants.size(); i++) {
            Applicant app = applicants.get(i);
            boolean matches = true;

            // Check location criteria
            if (location != null && !location.isEmpty() && !app.getLocation().equalsIgnoreCase(location)) {
                matches = false;
            }

            // Check job position criteria
            if (jobType != null && !jobType.isEmpty() && !app.getDesiredJobType().equalsIgnoreCase(jobType)) {
                matches = false;
            }

            // Check skills criteria
            if (skills != null && !skills.isEmpty()) {
                boolean hasAllSkills = true;
                Iterator<String> skillsIterator = skills.iterator(); // Use an iterator
                while (skillsIterator.hasNext()) {
                    String skill = skillsIterator.next();
                    boolean skillFound = false;
                    Iterator<String> applicantSkillsIterator = app.getSkills().iterator();
                    while (applicantSkillsIterator.hasNext()) {
                        if (applicantSkillsIterator.next().equalsIgnoreCase(skill)) {
                            skillFound = true;
                            break;
                        }
                    }
                    if (!skillFound) {
                        hasAllSkills = false;
                        break;
                    }
                }
                if (!hasAllSkills) {
                    matches = false;
                }
            }
            if (matches) {
                filtered.add(app);
            }
        }
        return filtered;
    }

    // Binary Search by name
    public ArrayList<Applicant> binarySearchByName(String name) {
        // Sort the list by name first (case-insensitive)
        sortApplicants((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));

        ArrayList<Applicant> results = new ArrayList<>();
        int low = 0;
        int high = applicants.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Applicant midApplicant = applicants.get(mid);
            int comparison = midApplicant.getName().compareToIgnoreCase(name);

            if (comparison == 0) {
                // Found a match; collect all adjacent matches
                results.add(midApplicant);

                // Check left side for duplicates
                int left = mid - 1;
                while (left >= 0 && applicants.get(left).getName().equalsIgnoreCase(name)) {
                    results.add(applicants.get(left));
                    left--;
                }

                // Check right side for duplicates
                int right = mid + 1;
                while (right < applicants.size() && applicants.get(right).getName().equalsIgnoreCase(name)) {
                    results.add(applicants.get(right));
                    right++;
                }

                return results;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return results; // Empty if no matches
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

    // Reporting Module for Applicants
    public static class ApplicantReportGenerator {

        private ArrayList<Applicant> applicants;

        public ApplicantReportGenerator(ArrayList<Applicant> applicants) {
            this.applicants = applicants;
        }

        // Helper class for skill counts
        private static class SkillCount {

            String skill;
            int count;

            SkillCount(String skill, int count) {
                this.skill = skill;
                this.count = count;
            }
        }

        public String generateSummaryReport() {
            if (applicants.isEmpty()) {
                return "No applicants to generate a report.";
            }

            StringBuilder report = new StringBuilder();
            int totalApplicants = applicants.size();
            int totalSkills = 0;
            HashMap<String, Integer> locationCounts = new HashMap<>();
            HashMap<String, Integer> jobTypeCounts = new HashMap<>();
            HashMap<String, Integer> skillCounts = new HashMap<>();

            // Collect data using custom ADTs
            Iterator<Applicant> appIterator = (Iterator<Applicant>) applicants.iterator();
            while (appIterator.hasNext()) {
                Applicant app = appIterator.next();

                // Location counts
                String location = app.getLocation();
                Integer locCount = locationCounts.get(location);
                locationCounts.put(location, (locCount == null) ? 1 : locCount + 1);

                // Job position counts
                String jobType = app.getDesiredJobType();
                Integer jobCount = jobTypeCounts.get(jobType);
                jobTypeCounts.put(jobType, (jobCount == null) ? 1 : jobCount + 1);

                // Skill counts
                Set<String> skills = app.getSkills();
                Iterator<String> skillIter = skills.iterator();
                while (skillIter.hasNext()) {
                    String skill = skillIter.next();
                    Integer skillCount = skillCounts.get(skill);
                    skillCounts.put(skill, (skillCount == null) ? 1 : skillCount + 1);
                    totalSkills++;
                }
            }

            // Build report
            report.append("=== Applicant Summary Report ===\n");
            report.append("Total Applicants: ").append(totalApplicants).append("\n");
            report.append("Avg Skills/Applicant: ").append(String.format("%.1f", (double) totalSkills / totalApplicants)).append("\n");

            // Location distribution
            report.append("\n**Location Distribution:**\n");
            for (int i = 0; i < locationCounts.size(); i++) {
                String loc = locationCounts.getKeyAtIndex(i);
                report.append("- ").append(loc).append(": ").append(locationCounts.get(loc)).append("\n");
            }

            // Job type distribution
            report.append("\n**Job Position Distribution:**\n");
            for (int i = 0; i < jobTypeCounts.size(); i++) {
                String job = jobTypeCounts.getKeyAtIndex(i);
                report.append("- ").append(job).append(": ").append(jobTypeCounts.get(job)).append("\n");
            }

            // Top 3 skills (manual sorting)
            ArrayList<SkillCount> skillCountList = new ArrayList<>();
            for (int i = 0; i < skillCounts.size(); i++) {
                String skill = skillCounts.getKeyAtIndex(i);
                skillCountList.add(new SkillCount(skill, skillCounts.get(skill)));
            }

            // Sort descending by count (selection sort)
            for (int i = 0; i < skillCountList.size() - 1; i++) {
                int maxIndex = i;
                for (int j = i + 1; j < skillCountList.size(); j++) {
                    if (skillCountList.get(j).count > skillCountList.get(maxIndex).count) {
                        maxIndex = j;
                    }
                }
                SkillCount temp = skillCountList.get(i);
                skillCountList.set(i, skillCountList.get(maxIndex));
                skillCountList.set(maxIndex, temp);
            }

            report.append("\n**Top 3 Skills:**\n");
            int limit = Math.min(3, skillCountList.size());
            for (int i = 0; i < limit; i++) {
                SkillCount sc = skillCountList.get(i);
                report.append("- ").append(sc.skill).append(" (").append(sc.count).append(")\n");
            }

            return report.toString();
        }
    }

    public String generateSummaryReport(String location, String jobType, Set<String> skills) {
        ArrayList<Applicant> filtered = filterApplicants(location, jobType, skills);
        return new ApplicantReportGenerator(filtered).generateSummaryReport();
    }

}
