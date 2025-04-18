package control;

/**
 *
 * @author leong kah tian
 */
import ADT.HashMap;
import ADT.ArrayList;
import ADT.*;
import entity.Job;
import java.util.*;

public class JobManager {

    private HashMap<String, Job> jobMap;   // HashMap for fast search/delete
    private ListInterface<Job> jobList;      // List for sorting and iteration

    public JobManager() {
        this.jobMap = new HashMap<>();
        this.jobList = new ArrayList<>();
        addPredefinedJobs(); // Load predefined jobs on startup
    }

    // Check if a job exists (case-insensitive)
    public boolean jobExists(String jobID) {
        return jobMap.containsKey(jobID.toLowerCase());
    }

    // Return all jobs
    public ListInterface<Job> getAllJobs() {
        return jobList;
    }

    // Retrieve a job by its ID (case-insensitive)
    public Job getJobById(String jobId) {
        if (jobId == null) {
            return null;
        }
        return jobMap.get(jobId.toLowerCase());
    }

    //Finds a job ID by company name
    public String getJobIdByName(String company) {
        if (company == null || company.trim().isEmpty()) {
            return null;
        }

        String searchKey = company.toLowerCase();

        // Iterate through all jobs to find a matching company name
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            if (job.getCompany().toLowerCase().equals(searchKey)) {
                return job.getJobID(); // Return the Job ID if company matches
            }
        }

        return null; // Return null if no matching company is found
    }

    private void addPredefinedJobs() {
        //Add sample job postings 
        addJob(new Job("J1", "Google", "Kuala Lumpur", "IT", "Software Engineer Intern", "Java, C++, Problem-Solving", 1500, 6, 3.00));
        addJob(new Job("J2", "Maybank", "Selangor", "Finance", "Financial Analyst Intern", "Excel, Financial Modeling, Data Analysis", 1200, 6, 3.25));
        addJob(new Job("J3", "Shopee", "Penang", "IT", "Data Analyst Intern", "Python, SQL, Data Visualization", 1300, 3, 2.5));
        addJob(new Job("J4", "Sunway Medical", "Johor", "Healthcare", "Medical Research Intern", "Biostatistics, Research Writing, Lab Techniques", 1000, 3, 3.0));
        addJob(new Job("J5", "Tesla", "Kuala Lumpur", "Engineering", "Mechanical Engineer Intern", "AutoCAD, SolidWorks, Thermodynamics", 1600, 6, 3.10));
        addJob(new Job("J6", "Intel", "Penang", "Semiconductor", "Process Engineer Intern", "Semiconductor Physics, Statistical Process Control, Problem-Solving", 1800, 6, 3.20));
        addJob(new Job("J7", "AirAsia", "Selangor", "Aviation", "Aviation Operations Intern", "Aviation Management, Logistics, Communication Skills", 1100, 3, 2.75));
        addJob(new Job("J8", "Petronas", "Terengganu", "Oil & Gas", "Petroleum Engineering Intern", "Reservoir Engineering, Geology, Data Analysis", 2000, 6, 3.30));
        addJob(new Job("J9", "Grab", "Kuala Lumpur", "Technology", "UX Design Intern", "Figma, User Research, UI/UX Principles, Prototyping", 1400, 3, 3.00));
        addJob(new Job("J10", "Sime Darby", "Johor", "Agriculture", "AgriTech Research Intern", "Precision Agriculture, IoT, Data Collection, Sustainability", 1250, 6, 2.80));
        addJob(new Job("J11", "KPMG", "Kuala Lumpur", "Consulting", "Business Consultant Intern", "Business Analysis, Presentation Skills, Market Research", 1350, 3, 3.25));
        addJob(new Job("J12", "Lazada", "Selangor", "E-commerce", "Digital Marketing Intern", "Social Media Marketing, SEO, Content Creation, Analytics", 1200, 3, 2.90));
    }

    // Add new job
    public void addJob(Job job) {
        if (jobExists(job.getJobID())) {
            System.out.println("Job ID already exists. Try again.");
            return;
        }
        jobMap.put(job.getJobID().toLowerCase(), job);
        jobList.add(job);
    }

    // Search jobs by a keyword based on company name using binary search
    public ListInterface<Job> searchJob(String keyword) {
        ListInterface<Job> results = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        keyword = keyword.toLowerCase();

        // Sort jobList by company name before performing binary search
        ListInterface<Job> sortedJobs = getSortedJobs(3); // 3 represents sorting by company name

        int index = binarySearchCompany(sortedJobs, keyword);

        if (index != -1) {
            int left = index;
            int right = index;

            // Collect all matches to the left of the found index
            while (left >= 0 && sortedJobs.get(left).getCompany().toLowerCase().contains(keyword)) {
                results.add(sortedJobs.get(left));
                left--;
            }
            // Collect all matches to the right of the found index
            while (right < sortedJobs.size() && sortedJobs.get(right).getCompany().toLowerCase().contains(keyword)) {
                if (right != index) { // Avoid adding the same job twice
                    results.add(sortedJobs.get(right));
                }
                right++;
            }
        }

        return results;
    }

    private int binarySearchCompany(ListInterface<Job> jobList, String keyword) {
        int low = 0, high = jobList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midCompany = jobList.get(mid).getCompany().toLowerCase();

            // Check if the company name contains the keyword
            if (midCompany.contains(keyword)) {
                return mid;
            } else if (midCompany.compareTo(keyword) < 0) {
                low = mid + 1; // Search in the right half
            } else {
                high = mid - 1; // Search in the left half
            }
        }
        return -1;
    }

    // Update job information
    public void updateJob(String jobID, String company, String location, String category, String jobType, String skill, int salary, int duration, double minCGPA) {
        jobID = jobID.toLowerCase();

        if (!jobExists(jobID)) {
            System.out.println("Job not found! Please enter a correct job ID.");
            return;
        }

        // Get the job from HashMap
        Job job = jobMap.get(jobID);

        if (job != null) {  // Update only the fields that have new values
            if (company != null && !company.isEmpty()) {
                job.setCompany(company);
            }
            if (location != null && !location.isEmpty()) {
                job.setLocation(location);
            }
            if (category != null && !category.isEmpty()) {
                job.setCategory(category);
            }
            if (jobType != null && !jobType.isEmpty()) {
                job.setJobType(jobType);
            }
            if (skill != null && !skill.isEmpty()) {
                job.setSkills(skill);
            }
            if (salary > 0) {
                job.setSalary(salary);
            }
            if (duration > 0) {
                job.setDuration(duration);
            }
            if (minCGPA > 0) {
                job.setMinCGPA(minCGPA);
            }
            // Update job in jobList
            for (int i = 0; i < jobList.size(); i++) {
                if (jobList.get(i).getJobID().equalsIgnoreCase(jobID)) {
                    jobList.set(i, job);
                    break;
                }
            }
        } else {
            System.out.println("Error: Could not update job.");
        }
    }

    // Delete a job
    public boolean deleteJob(String jobID) {
        jobID = jobID.toLowerCase();
        if (!jobExists(jobID)) {
            System.out.println("Job not found! Please enter the correct job ID.");
            return false;
        }
        
        // Remove from HashMap
        Job removedJob = jobMap.remove(jobID);
        for (int i = 0; i < jobList.size(); i++) {// Remove from ArrayList
            if (jobList.get(i).getJobID().equalsIgnoreCase(jobID)) {
                jobList.remove(i);
                break;
            }
        }
        return removedJob != null;
    }

    // Return a sorted list of jobs based on criteria 
    public ListInterface<Job> getSortedJobs(int criteria) {
        ListInterface<Job> sortedJobs = new ArrayList<>();

        // Copy original job list to preserve order
        for (int i = 0; i < jobList.size(); i++) {
            sortedJobs.add(jobList.get(i));
        }
        Comparator<Job> comparator; // Select comparator based on sorting criteria

        switch (criteria) {
            case 1:
                comparator = Comparator.comparing(Job::getCategory);
                break;
            case 2:
                comparator = Comparator.comparingInt(Job::getSalary);
                break;
            case 3:
                comparator = Comparator.comparing(Job::getCompany);
                break;
            default:
                System.out.println("Invalid choice! Sorting by Job ID as default.");
                comparator = Comparator.comparing(Job::getJobID);
                break;
        }

        // Sort the jobs using QuickSort algorithm
        quickSort(sortedJobs, 0, sortedJobs.size() - 1, comparator);
        return sortedJobs;
    }

    // QuickSort algorithm for sorting jobs
    private void quickSort(ListInterface<Job> jobs, int low, int high, Comparator<Job> comparator) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partition(jobs, low, high, comparator);
           // Sort elements before and after partition
            quickSort(jobs, low, pi - 1, comparator); // Sort left part
            quickSort(jobs, pi + 1, high, comparator); // Sort right part
        }
    }

    // Partition function used by QuickSort
    private int partition(ListInterface<Job> jobs, int low, int high, Comparator<Job> comparator) {
        Job pivot = jobs.get(high); // Select the rightmost element as pivot
        int i = low - 1;  // Index of smaller element

        for (int j = low; j < high; j++) {// Place all elements smaller than pivot to the left
            if (comparator.compare(jobs.get(j), pivot) < 0) {
                i++;
                jobs.swap(i, j);
            }
        }
        jobs.swap(i + 1, high);// Place pivot in its final sorted position
        return i + 1;
    }

    // Filter jobs based on category, location, and salary range
    public ListInterface<Job> filterJobs(String category, String location, int minSalary, int maxSalary) {
        ListInterface<Job> filteredJobs = new ArrayList<>();

         // Check each job against all criteria
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            boolean matches = true;

            // Apply each filter if specified
            if (category != null && !job.getCategory().equalsIgnoreCase(category)) {
                matches = false;
            }
            if (location != null && !job.getLocation().equalsIgnoreCase(location)) {
                matches = false;
            }
            if (minSalary > 0 && job.getSalary() < minSalary) {
                matches = false;
            }
            if (maxSalary < Integer.MAX_VALUE && job.getSalary() > maxSalary) {
                matches = false;
            }

            if (matches) { // Add job to results if it passes all filters
                filteredJobs.add(job);
            }
        }
        return filteredJobs;
    }
}
