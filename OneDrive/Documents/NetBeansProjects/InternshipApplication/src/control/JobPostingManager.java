/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.HashMap;
import entity.JobPosting;

/**
 *
 * @author Goh Ee Lin
 */
public class JobPostingManager {
    
    private ArrayList<JobPosting> jobPostings;
    private HashMap<String, JobPosting> jobPostingMap; // For efficient lookups by ID
    
    public JobPostingManager() {
        jobPostings = new ArrayList<>();
        jobPostingMap = new HashMap<>();
        
        initializeJobPosting();
    }
    
    public void initializeJobPosting(){
        ArrayList<String> skills1 = new ArrayList<>();
        skills1.add("Java");
        JobPosting job1 = new JobPosting("J1", "Java Developer", "Code in Java", "Kuala Lumpur", skills1);
        addJobPosting(job1);   
        
        ArrayList<String> skills2 = new ArrayList<>();
        skills2.add("HTML");
        skills2.add("CSS");
        skills2.add("Javascript");
        JobPosting job2 = new JobPosting("J2", "Web Developer", "Code in HTML, CSS and Javascript", "Singapore", skills2);
        addJobPosting(job2);
        
        ArrayList<String> skills3 = new ArrayList<>();
        skills3.add("Python");
        JobPosting job3 = new JobPosting("J3", "Python Developer", "Code in Python", "Kuala Lumpur", skills3);
        addJobPosting(job3);
    }
    
    /**
     * Adds a new job posting to the system
     * @param jobPosting The job posting to add
     * @return true if the job was added successfully, false if job with same ID already exists
     */
    public boolean addJobPosting(JobPosting jobPosting) {
        if (jobPosting == null || jobPostingMap.containsKey(jobPosting.getId())) {
            return false;
        }
        
        jobPostings.add(jobPosting);
        jobPostingMap.put(jobPosting.getId(), jobPosting);
        return true;
    }
    
    /**
     * Updates an existing job posting
     * @param id The ID of the job posting to update
     * @param updatedJobPosting The updated job posting information
     * @return true if updated successfully, false if job was not found
     */
    public boolean updateJobPosting(String id, JobPosting updatedJobPosting) {
        if (id == null || updatedJobPosting == null || !jobPostingMap.containsKey(id)) {
            return false;
        }
        
        // Remove old job posting from list
        JobPosting oldJobPosting = jobPostingMap.get(id);
        jobPostings.remove(oldJobPosting);
        
        // Add updated job posting
        jobPostings.add(updatedJobPosting);
        jobPostingMap.put(id, updatedJobPosting);
        
        return true;
    }
    
    /**
     * Removes a job posting from the system
     * @param id The ID of the job posting to remove
     * @return true if removed successfully, false if job was not found
     */
    public boolean removeJobPosting(String id) {
        if (id == null || !jobPostingMap.containsKey(id)) {
            return false;
        }
        
        JobPosting jobToRemove = jobPostingMap.get(id);
        jobPostings.remove(jobToRemove);
        jobPostingMap.remove(id);
        
        return true;
    }
    
    /**
     * Gets a job posting by its ID
     * @param id The ID of the job posting to retrieve
     * @return The JobPosting object, or null if not found
     */
    public JobPosting getJobPosting(String id) {
        return jobPostingMap.get(id);
    }
    
    /**
     * Gets all job postings in the system
     * @return ArrayList of all job postings
     */
    public ArrayList<JobPosting> getAllJobPostings() {
        return jobPostings;
    }
    
    /**
     * Filters job postings based on various criteria
     * @param location Location filter (null to ignore)
     * @param keyword Keyword to search in title and description (null to ignore)
     * @param requiredSkills Skills that jobs should require (null to ignore)
     * @return ArrayList of filtered job postings
     */
    public ArrayList<JobPosting> filterJobPostings(String location, String keyword, ArrayList<String> requiredSkills) {
        ArrayList<JobPosting> filtered = new ArrayList<>();
        
        for (int i = 0; i < jobPostings.size(); i++) {
            JobPosting job = jobPostings.get(i);
            boolean matches = true;
            
            // Check location criteria
            if (location != null && !location.isEmpty() && !job.getLocation().equalsIgnoreCase(location)) {
                matches = false;
            }
            
            // Check keyword in title or description
            if (keyword != null && !keyword.isEmpty()) {
                boolean keywordMatch = job.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                                      job.getDescription().toLowerCase().contains(keyword.toLowerCase());
                if (!keywordMatch) {
                    matches = false;
                }
            }
            
            // Check required skills
            if (requiredSkills != null && requiredSkills.size() > 0) {
                ArrayList<String> jobSkills = job.getRequiredSkills();
                
                for (int j = 0; j < requiredSkills.size(); j++) {
                    String requiredSkill = requiredSkills.get(j);
                    boolean hasSkill = false;
                    
                    for (int k = 0; k < jobSkills.size(); k++) {
                        if (jobSkills.get(k).equalsIgnoreCase(requiredSkill)) {
                            hasSkill = true;
                            break;
                        }
                    }
                    
                    if (!hasSkill) {
                        matches = false;
                        break;
                    }
                }
            }
            
            if (matches) {
                filtered.add(job);
            }
        }
        
        return filtered;
    }
    
    /**
     * Sorts job postings by title using Insertion Sort
     */
    public void sortJobPostingsByTitle() {
        for (int i = 1; i < jobPostings.size(); i++) {
            JobPosting key = jobPostings.get(i);
            int j = i - 1;
            
            while (j >= 0 && jobPostings.get(j).getTitle().compareTo(key.getTitle()) > 0) {
                jobPostings.set(j + 1, jobPostings.get(j));
                j = j - 1;
            }
            jobPostings.set(j + 1, key);
        }
    }
    
    /**
     * Sorts job postings by location using Selection Sort
     */
    public void sortJobPostingsByLocation() {
        for (int i = 0; i < jobPostings.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < jobPostings.size(); j++) {
                if (jobPostings.get(j).getLocation().compareTo(jobPostings.get(minIndex).getLocation()) < 0) {
                    minIndex = j;
                }
            }
            
            // Swap
            JobPosting temp = jobPostings.get(minIndex);
            jobPostings.set(minIndex, jobPostings.get(i));
            jobPostings.set(i, temp);
        }
    }
    
    /**
     * Sorts job postings by number of required skills using Quick Sort
     */
    public void sortJobPostingsBySkillCount() {
        quickSortBySkillCount(0, jobPostings.size() - 1);
    }
    
    private void quickSortBySkillCount(int low, int high) {
        if (low < high) {
            int partitionIndex = partitionBySkillCount(low, high);
            
            quickSortBySkillCount(low, partitionIndex - 1);
            quickSortBySkillCount(partitionIndex + 1, high);
        }
    }
    
    private int partitionBySkillCount(int low, int high) {
        // Using the last element as pivot
        int pivotSkillCount = jobPostings.get(high).getRequiredSkills().size();
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (jobPostings.get(j).getRequiredSkills().size() <= pivotSkillCount) {
                i++;
                
                // Swap elements
                JobPosting temp = jobPostings.get(i);
                jobPostings.set(i, jobPostings.get(j));
                jobPostings.set(j, temp);
            }
        }
        
        // Swap pivot to its correct position
        JobPosting temp = jobPostings.get(i + 1);
        jobPostings.set(i + 1, jobPostings.get(high));
        jobPostings.set(high, temp);
        
        return i + 1;
    }
    
    /**
     * Searches for a job posting by title using Binary Search
     * Note: Must call sortJobPostingsByTitle() before using this method
     * @param title The title to search for
     * @return The job posting if found, null otherwise
     */
    public JobPosting binarySearchByTitle(String title) {
        int left = 0;
        int right = jobPostings.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            String midTitle = jobPostings.get(mid).getTitle();
            int comparison = midTitle.compareTo(title);
            
            // Check if title is present at mid
            if (comparison == 0) {
                return jobPostings.get(mid);
            }
            
            // If title is greater, ignore left half
            if (comparison < 0) {
                left = mid + 1;
            } else {
                // If title is smaller, ignore right half
                right = mid - 1;
            }
        }
        
        return null; // Not found
    }
    
    /**
     * Checks if a job posting exists with the given ID
     * @param id The ID to check
     * @return true if exists, false otherwise
     */
    public boolean jobPostingExists(String id) {
        return jobPostingMap.containsKey(id);
    }
    
    /**
     * Gets the number of job postings in the system
     * @return Number of job postings
     */
    public int getJobPostingCount() {
        return jobPostings.size();
    }
}