/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import ADT.Iterator;
import ADT.Set;
import ADT.ArrayList;
import ADT.HashMap;
import entity.Job;
import java.util.Map;

/**
 *
 * @author Goh Ee Lin
 */
public class Applicant {

    private String id;
    private String name;
    private String location;
    private String desiredJobType;
    private Set<String> skills;
    private boolean active;
    private ArrayList<Job> appliedJobs;
    private double applicantCGPA;
    private HashMap<String, String> applicationStatus; // Store Job ID and Application Status


    public Applicant(String id, String name, String location, String desiredJobType, Set<String> skills, double applicantCGPA) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.desiredJobType = desiredJobType;
        this.skills = skills;
        this.active = true;
        this.appliedJobs = new ArrayList<>();
        this.applicantCGPA = applicantCGPA;
        this.applicationStatus = new HashMap<>();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDesiredJobType() {
        return desiredJobType;
    }

    public Set<String> getSkills() {
        return skills;
    }
    public String getSkillsAsString() {
        StringBuilder skillsBuilder = new StringBuilder();
        Iterator<String> skillIterator = skills.iterator();
        while (skillIterator.hasNext()) {
            skillsBuilder.append(skillIterator.next());
            if (skillIterator.hasNext()) {
                skillsBuilder.append(", ");
            }
        }
        return skillsBuilder.toString();
    }

    public boolean isActive() {
        return active;
    }
    
    public ArrayList<Job> getAppliedJobs() {
        return appliedJobs;
    }
    
    public double getApplicantCGPA(){
        return this.applicantCGPA;
    }
    
    public String getApplicationStatus(String jobId) {
        return applicationStatus.getOrDefault(jobId, "Not Applied");
    }
    
    public void setApplicationStatus(String jobId, String status) {
        applicationStatus.put(jobId, status);
    }
    
    
    
    public void updateInfo(String name, String location, String desiredJobType, Set<String> skills) {
        this.name = name;
        this.location = location;
        this.desiredJobType = desiredJobType;
        this.skills = skills;
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    // Use the centralized Job instance: check if already applied using job.getJobID()
    public void applyForJob(Job job) {
        if (!hasAppliedForJob(job.getJobID())) {
            appliedJobs.add(job);
            applicationStatus.put(job.getJobID(), "Pending");
        }
    }

    public boolean hasAppliedForJob(String jobId) {
        for (int i = 0; i < appliedJobs.size(); i++) {
            if (appliedJobs.get(i).getJobID().equals(jobId)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasApprovedApplication() {
        for (int i = 0; i < applicationStatus.size(); i++) {
            String jobId = applicationStatus.getKeyAtIndex(i); // Assuming this exists
            String status = applicationStatus.get(jobId);
            if ("Approved".equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    public void withdrawApplication(String jobId) {
        for (int i = 0; i < appliedJobs.size(); i++) {
            if (appliedJobs.get(i).getJobID().equals(jobId)) {
                appliedJobs.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder skillsBuilder = new StringBuilder();
        skillsBuilder.append("[");
        Iterator<String> skillIterator = skills.iterator();
        while (skillIterator.hasNext()) {
            skillsBuilder.append(skillIterator.next());
            if (skillIterator.hasNext()) {
                skillsBuilder.append(", ");
            }
        }
        skillsBuilder.append("]");

        // Build string for applied jobs. Here we display the Job ID and Job Type.
        StringBuilder jobsBuilder = new StringBuilder();
        jobsBuilder.append("[");
        for (int i = 0; i < appliedJobs.size(); i++) {
            jobsBuilder.append(appliedJobs.get(i).getJobID())
                       .append(" - ")
                       .append(appliedJobs.get(i).getJobType());
            if (i < appliedJobs.size() - 1) {
                jobsBuilder.append(", ");
            }
        }
        jobsBuilder.append("]");

        return "Applicant ID: " + id 
                + ", Name: " + name 
                + ", Location: " + location
                + ", Desired Job Type: " + desiredJobType 
                + ", Skills: " + skillsBuilder.toString()
                + ", Applied Jobs: " + jobsBuilder.toString()
                + ", Current CGPA: " + applicantCGPA;
    }
}
