package control;

/**
 *
 * @author leong kah tian
 */

import ADT.*;
import entity.Job;
import java.util.*;

public class JobManager {

    private HashMap<String, Job> jobMap;   // HashMap for fast search/delete
    private ListInterface<Job> jobList;   // List for sorting and iteration

    public JobManager() {
        this.jobMap = new HashMap<>();
        this.jobList = new ArrayListImplementer<>();
        addPredefinedJobs(); // Load predefined jobs on startup
    }

    //job exists validation
    public boolean jobExists(String jobID) {
        return jobMap.containsKey(jobID.toLowerCase());
    }

    public ListInterface<Job> getAllJobs() {
        return jobList;
    }

    //add predefined jobs
    private void addPredefinedJobs() {
        addJob(new Job("A1", "Google", "Kuala Lumpur", "IT", "Software Engineer Intern", "Java, C++, Problem-Solving", 1500, 6));
        addJob(new Job("A2", "Maybank", "Selangor", "Finance", "Financial Analyst Intern", "Excel, Financial Modeling, Data Analysis", 1200, 6));
        addJob(new Job("A3", "Shopee", "Penang", "IT", "Data Analyst Intern", "Python, SQL, Data Visualization", 1300, 3));
        addJob(new Job("A4", "Sunway Medical", "Johor", "Healthcare", "Medical Research Intern", "Biostatistics, Research Writing, Lab Techniques", 1000, 3));
        addJob(new Job("A5", "Tesla", "Kuala Lumpur", "Engineering", "Mechanical Engineer Intern", "AutoCAD, SolidWorks, Thermodynamics", 1600, 6));
    }

    //job adding
    public void addJob(Job job) {
        if (jobExists(job.getJobID())) {
            System.out.println("Job ID already exists. Try again.");
            return;
        }
        jobMap.put(job.getJobID().toLowerCase(), job);
        jobList.add(job);
    }

    //job searching 
    public ListInterface<Job> searchJob(String keyword) {
        ListInterface<Job> results = new ArrayListImplementer<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return results; // Return empty result if keyword is empty
        }

        keyword = keyword.toLowerCase();

        // Sort jobList by company name before performing binary search
        ListInterface<Job> sortedJobs = getSortedJobs(3); // 3 represents sorting by company name

        int index = binarySearchCompany(sortedJobs, keyword);

        if (index != -1) {
            // Collect all matching jobs (as there might be multiple with the same company)
            int left = index;
            int right = index;

            while (left >= 0 && sortedJobs.get(left).getCompany().toLowerCase().contains(keyword)) {
                results.add(sortedJobs.get(left));
                left--;
            }

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

            if (midCompany.contains(keyword)) {
                return mid; // Found a match
            } else if (midCompany.compareTo(keyword) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // Not found
    }

    //job updating
    public void updateJob(String jobID, String company, String location, String category, String jobType, String skill, int salary, int duration) {
        jobID = jobID.toLowerCase();

        if (!jobExists(jobID)) {
            System.out.println("Job not found! Please enter a correct job ID.");
            return;
        }

        Job job = jobMap.get(jobID);

        if (job != null) {
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

    //job deleting
    public boolean deleteJob(String jobID) {
        jobID = jobID.toLowerCase();
        if (!jobExists(jobID)) {
            System.out.println("Job not found! Please enter the correct job ID.");
            return false;
        }

        Job removedJob = jobMap.remove(jobID);
        for (int i = 0; i < jobList.size(); i++) {
            if (jobList.get(i).getJobID().equalsIgnoreCase(jobID)) {
                jobList.remove(i);
                break;
            }
        }
        return removedJob != null;
    }

    //get sorted jobs with QuickSort
    public ListInterface<Job> getSortedJobs(int criteria) {
        ListInterface<Job> sortedJobs = new ArrayListImplementer<>();

        //copy original job list to preserve order
        for (int i = 0; i < jobList.size(); i++) {
            sortedJobs.add(jobList.get(i));
        }
        Comparator<Job> comparator;

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

        quickSort(sortedJobs, 0, sortedJobs.size() - 1, comparator);
        return sortedJobs;
    }

//QuickSort algorithm
    private void quickSort(ListInterface<Job> jobs, int low, int high, Comparator<Job> comparator) {
        if (low < high) {
            int pi = partition(jobs, low, high, comparator);
            quickSort(jobs, low, pi - 1, comparator);
            quickSort(jobs, pi + 1, high, comparator);
        }
    }

//partition function for QuickSort
    private int partition(ListInterface<Job> jobs, int low, int high, Comparator<Job> comparator) {
        Job pivot = jobs.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(jobs.get(j), pivot) < 0) {
                i++;
                jobs.swap(i, j);
            }
        }
        jobs.swap(i + 1, high);
        return i + 1;
    }

    //job filtering
    public ListInterface<Job> filterJobs(String category, String location, int minSalary, int maxSalary) {
        ListInterface<Job> filteredJobs = new ArrayListImplementer<>();

        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            boolean matches = true;

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

            if (matches) {
                filteredJobs.add(job);
            }
        }
        return filteredJobs;
    }
}
