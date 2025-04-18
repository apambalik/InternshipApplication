package control;

import entity.Applicant;
import ADT.ArrayList;
import ADT.HashMap;
import ADT.HashSet;
import ADT.Iterator;
import ADT.Set;

/**
 *
 * @author Goh Ee Lin
 */
public class ApplicantManager {

    private int nextApplicantId = 101; // Start from A101
    private ArrayList<Applicant> applicants;      // List to store all applicants
    private HashMap<String, Applicant> applicantMap; // For efficient lookups by ID

    // Constructor initializes the data structures and populates them with default applicants.
    public ApplicantManager() {
        applicants = new ArrayList<>();
        applicantMap = new HashMap<>();

        initializeDefaultApplicants();
    }

    // Generates a unique applicant ID
    public String generateNextApplicantId() {
        return "A" + (nextApplicantId++);
    }

    /**
     * Initializes the system with default applicants for testing and
     * demonstration purposes. Each applicant is created with a unique ID, name,
     * location, job type, skills, and CGPA.
     */
    private void initializeDefaultApplicants() {
        // Create first applicant with Java and Python skills
        Set<String> skills1 = new HashSet<>();
        skills1.add("Java");
        skills1.add("Python");
        addApplicant(new Applicant(generateNextApplicantId(), "John Doe", "Kuala Lumpur", "Data Analyst Intern", skills1, 3.80));

        // Create second applicant with JavaScript skills
        Set<String> skills2 = new HashSet<>();
        skills2.add("JavaScript");
        addApplicant(new Applicant(generateNextApplicantId(), "Alice Smith", "Singapore", "Software Engineer Intern", skills2, 3.50));

        // Create third applicant with Java, C++, and Problem-Solving skills
        Set<String> skills3 = new HashSet<>();
        skills3.add("Java");
        skills3.add("C++");
        skills3.add("Problem-Solving");
        addApplicant(new Applicant(generateNextApplicantId(), "Bob Tan", "Kuala Lumpur", "Software Engineer Intern", skills3, 3.67));

        // Create fourth applicant with Python, SQL, and Data Visualization skills
        Set<String> skills4 = new HashSet<>();
        skills4.add("Python");
        skills4.add("SQL");
        skills4.add("Data Visualization");
        addApplicant(new Applicant(generateNextApplicantId(), "Sarah Lim", "Penang", "Data Analyst Intern", skills4, 3.45));

        // Create fifth applicant with Excel and Financial Modeling skills
        Set<String> skills5 = new HashSet<>();
        skills5.add("Excel");
        skills5.add("Financial Modeling");
        addApplicant(new Applicant(generateNextApplicantId(), "Ahmad bin Ismail", "Selangor", "Financial Analyst Intern", skills5, 3.72));

        // Create sixth applicant with AutoCAD and SolidWorks skills
        Set<String> skills6 = new HashSet<>();
        skills6.add("AutoCAD");
        skills6.add("SolidWorks");
        addApplicant(new Applicant(generateNextApplicantId(), "Priya Kaur", "Johor", "Mechanical Engineer Intern", skills6, 3.55));

        // Create seventh applicant with Biostatistics and Research Writing skills
        Set<String> skills7 = new HashSet<>();
        skills7.add("Biostatistics");
        skills7.add("Research Writing");
        addApplicant(new Applicant(generateNextApplicantId(), "David Wong", "Kuala Lumpur", "Medical Research Intern", skills7, 3.90));

        // Create eighth applicant with JavaScript, React, and UI/UX skills
        Set<String> skills8 = new HashSet<>();
        skills8.add("JavaScript");
        skills8.add("React");
        skills8.add("UI/UX");
        addApplicant(new Applicant(generateNextApplicantId(), "Nurul Hassan", "Selangor", "Frontend Developer Intern", skills8, 3.25));

        // Create ninth applicant with Machine Learning, TensorFlow, and Python skills
        Set<String> skills9 = new HashSet<>();
        skills9.add("Machine Learning");
        skills9.add("TensorFlow");
        skills9.add("Python");
        addApplicant(new Applicant(generateNextApplicantId(), "Chen Wei", "Penang", "AI Research Intern", skills9, 3.88));

        // Create tenth applicant with Communication and Market Research skills
        Set<String> skills10 = new HashSet<>();
        skills10.add("Communication");
        skills10.add("Market Research");
        addApplicant(new Applicant(generateNextApplicantId(), "Fatimah Abdullah", "Kuala Lumpur", "Business Consultant Intern", skills10, 3.60));

        // Create eleventh applicant with SEO, Content Creation, and Social Media skills
        Set<String> skills11 = new HashSet<>();
        skills11.add("SEO");
        skills11.add("Content Creation");
        skills11.add("Social Media");
        addApplicant(new Applicant(generateNextApplicantId(), "Rajesh Kumar", "Selangor", "Digital Marketing Intern", skills11, 3.30));

        // Create twelfth applicant with IoT and Precision Agriculture skills
        Set<String> skills12 = new HashSet<>();
        skills12.add("IoT");
        skills12.add("Precision Agriculture");
        addApplicant(new Applicant(generateNextApplicantId(), "Amirah Yusof", "Johor", "AgriTech Intern", skills12, 3.75));

        // Create thirteenth applicant with Semiconductor Physics and Problem-Solving skills
        Set<String> skills13 = new HashSet<>();
        skills13.add("Semiconductor Physics");
        skills13.add("Problem-Solving");
        addApplicant(new Applicant(generateNextApplicantId(), "Kevin Ng", "Penang", "Process Engineer Intern", skills13, 3.65));
    }

    // Returns the list of all applicants in the system.
    public ArrayList<Applicant> getApplicants() {
        return applicants;
    }

    // Retrieves an applicant by their ID using the HashMap for O(1) lookup time.
    public Applicant getApplicantById(String applicantId) {
        return applicantMap.get(applicantId);
    }

    /**
     * Adds a new applicant to both the ArrayList and HashMap. The HashMap
     * allows for efficient lookups by ID, while the ArrayList maintains
     * insertion order and supports indexed access.
     */
    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
        applicantMap.put(applicant.getId(), applicant);
    }

    /**
     * Updates an existing applicant's information. This method removes the old
     * applicant entry and adds the updated one to both the ArrayList and
     * HashMap.
     */
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

    // Removes an applicant from both the ArrayList and HashMap by ID.
    public boolean removeApplicant(String applicantId) {
        if (applicantMap.containsKey(applicantId)) {
            Applicant applicant = applicantMap.get(applicantId);
            applicants.remove(applicant);
            applicantMap.remove(applicantId);
            return true;
        }
        return false;
    }

    /**
     * Filters applicants based on specified criteria (location, job type,
     * skills). This method is a wrapper that calls filterApplicantsList with
     * the full list of applicants.
     */
    public ArrayList<Applicant> filterApplicants(String location, String jobType, Set<String> skills) {
        return filterApplicantsList(applicants, location, jobType, skills);
    }

    /**
     * Filters a list of applicants based on specified criteria. This is a
     * helper method used by both filterApplicants and searchApplicants.
     */
    private ArrayList<Applicant> filterApplicantsList(ArrayList<Applicant> inputList, String location, String jobType, Set<String> skills) {
        ArrayList<Applicant> filtered = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            Applicant app = inputList.get(i);
            boolean matches = true;

            // Check location criteria (case-insensitive)
            if (location != null && !location.isEmpty() && !app.getLocation().equalsIgnoreCase(location)) {
                matches = false;
            }

            // Check job position criteria (case-insensitive)
            if (jobType != null && !jobType.isEmpty() && !app.getDesiredJobType().equalsIgnoreCase(jobType)) {
                matches = false;
            }

            // Check skills criteria - applicant must have ALL specified skills (case-insensitive)
            if (skills != null && !skills.isEmpty()) {
                boolean hasAllSkills = true;
                Iterator<String> skillsIterator = skills.iterator();
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

            // If all criteria match, add to filtered list
            if (matches) {
                filtered.add(app);
            }
        }
        return filtered;
    }

    /**
     * Searches for applicants by name (using binary search) and then filters
     * the results by location, job type, and skills.
     */
    public ArrayList<Applicant> searchApplicants(String name, String location, String jobType, Set<String> skills) {
        ArrayList<Applicant> nameMatches;

        // If name is provided, use binary search to find matches by name
        if (name != null && !name.isEmpty()) {
            nameMatches = binarySearchByName(name);
        } else {
            // Otherwise, use all applicants as the base list
            nameMatches = new ArrayList<>();
            for (int i = 0; i < applicants.size(); i++) {
                nameMatches.add(applicants.get(i));
            }
        }

        // Apply additional filters to the name matches
        return filterApplicantsList(nameMatches, location, jobType, skills);
    }

    /**
     * Performs a binary search to find applicants by name (case-insensitive).
     * This method first sorts the applicants by name to ensure binary search
     * works correctly. It then finds all instances of the specified name,
     * including duplicates.
     */
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

    /**
     * Sorts applicants by location using Quick Sort algorithm. Delegates to the
     * more general quickSort method with a location comparator.
     */
    public void sortApplicantsByLocation() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> a1.getLocation().compareTo(a2.getLocation()));
    }

    /**
     * Sorts applicants by name using Quick Sort algorithm. Delegates to the
     * more general quickSort method with a name comparator.
     */
    public void sortApplicantsByName() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> a1.getName().compareTo(a2.getName()));
    }

    /**
     * Sorts applicants by the number of skills they have (skill count) using
     * Quick Sort algorithm. Delegates to the more general quickSort method with
     * a skill count comparator.
     */
    public void sortApplicantsBySkillCount() {
        quickSort(applicants, 0, applicants.size() - 1, (a1, a2) -> Integer.compare(a1.getSkills().size(), a2.getSkills().size()));
    }

    /**
     * Implements the Quick Sort algorithm to sort applicants based on a given
     * comparator.
     */
    private void quickSort(ArrayList<Applicant> list, int low, int high, java.util.Comparator<Applicant> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator); // Partition the array
            quickSort(list, low, pi - 1, comparator);  // Sort the left sub-array
            quickSort(list, pi + 1, high, comparator); // Sort the right sub-array
        }
    }

    /**
     * Helper method for quickSort that partitions the array around a pivot
     * element. This implementation uses the last element as the pivot.
     */
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

    /**
     * Generic method for sorting applicants using any comparator. This provides
     * flexibility for sorting by different criteria.
     */
    public void sortApplicants(java.util.Comparator<Applicant> comparator) {
        quickSort(applicants, 0, applicants.size() - 1, comparator);
    }

    /**
     * Nested class that provides report generation functionality for
     * applicants. This class aggregates and analyzes applicant data to produce
     * summary reports.
     */
    public static class ApplicantReportGenerator {

        private ArrayList<Applicant> applicants;

        // Constructor initializes the report generator with a list of applicants.
        public ApplicantReportGenerator(ArrayList<Applicant> applicants) {
            this.applicants = applicants;
        }

        /**
         * Helper class to store skill name and count for sorting. Used in
         * report generation to identify the most common skills.
         */
        private static class SkillCount {

            String skill;
            int count;

            SkillCount(String skill, int count) {
                this.skill = skill;
                this.count = count;
            }
        }

        /**
         * Generates a summary report of applicant data including: - Total
         * applicants - Average skills per applicant - Location distribution -
         * Job type distribution - Top 3 most common skills
         */
        public String generateSummaryReport() {
            if (applicants.isEmpty()) {
                return "No applicants to generate a report.";
            }

            StringBuilder report = new StringBuilder();
            int totalApplicants = applicants.size();
            int totalSkills = 0;

            // HashMaps to store counts of locations, job types, and skills
            HashMap<String, Integer> locationCounts = new HashMap<>();
            HashMap<String, Integer> jobTypeCounts = new HashMap<>();
            HashMap<String, Integer> skillCounts = new HashMap<>();

            // Collect data using custom ADTs
            Iterator<Applicant> appIterator = (Iterator<Applicant>) applicants.iterator();
            while (appIterator.hasNext()) {
                Applicant app = appIterator.next();

                // Count locations
                String location = app.getLocation();
                Integer locCount = locationCounts.get(location);
                locationCounts.put(location, (locCount == null) ? 1 : locCount + 1);

                // Count job positions
                String jobType = app.getDesiredJobType();
                Integer jobCount = jobTypeCounts.get(jobType);
                jobTypeCounts.put(jobType, (jobCount == null) ? 1 : jobCount + 1);

                // Count skills
                Set<String> skills = app.getSkills();
                Iterator<String> skillIter = skills.iterator();
                while (skillIter.hasNext()) {
                    String skill = skillIter.next();
                    Integer skillCount = skillCounts.get(skill);
                    skillCounts.put(skill, (skillCount == null) ? 1 : skillCount + 1);
                    totalSkills++;
                }
            }

            // Build report sections
            report.append("=== Applicant Summary Report ===\n");
            report.append("Total Applicants: ").append(totalApplicants).append("\n");
            report.append("Avg Skills/Applicant: ").append(String.format("%.1f", (double) totalSkills / totalApplicants)).append("\n");

            // Location distribution section
            report.append("\n**Location Distribution:**\n");
            for (int i = 0; i < locationCounts.size(); i++) {
                String loc = locationCounts.getKeyAtIndex(i);
                report.append("- ").append(loc).append(": ").append(locationCounts.get(loc)).append("\n");
            }

            // Job type distribution section
            report.append("\n**Job Position Distribution:**\n");
            for (int i = 0; i < jobTypeCounts.size(); i++) {
                String job = jobTypeCounts.getKeyAtIndex(i);
                report.append("- ").append(job).append(": ").append(jobTypeCounts.get(job)).append("\n");
            }

            // Convert skill counts to a list for sorting
            ArrayList<SkillCount> skillCountList = new ArrayList<>();
            for (int i = 0; i < skillCounts.size(); i++) {
                String skill = skillCounts.getKeyAtIndex(i);
                skillCountList.add(new SkillCount(skill, skillCounts.get(skill)));
            }

            // Sort skills by count in descending order using selection sort
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

            // Top 3 skills section (or fewer if there are less than 3 skills)
            report.append("\n**Top 3 Skills:**\n");
            int limit = Math.min(3, skillCountList.size());
            for (int i = 0; i < limit; i++) {
                SkillCount sc = skillCountList.get(i);
                report.append("- ").append(sc.skill).append(" (").append(sc.count).append(")\n");
            }

            return report.toString();
        }
    }

    // Generates a summary report for applicants that match the given filter criteria.
    public String generateSummaryReport(String location, String jobType, Set<String> skills) {
        ArrayList<Applicant> filtered = filterApplicants(location, jobType, skills);
        return new ApplicantReportGenerator(filtered).generateSummaryReport();
    }
}
