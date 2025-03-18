/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.Iterator;
import adt.Set;
import adt.ArrayList;

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
    private ArrayList<JobPosting> appliedJobs;

    public Applicant(String id, String name, String location, String desiredJobType, Set<String> skills) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.desiredJobType = desiredJobType;
        this.skills = skills;
        this.active = true;
        this.appliedJobs = new ArrayList<>();
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

    public boolean isActive() {
        return active;
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

    public ArrayList<JobPosting> getAppliedJobs() {
        return appliedJobs;
    }

    public void applyForJob(JobPosting job) {
        if (!hasAppliedForJob(job.getId())) {
            appliedJobs.add(job);
        }
    }

    public boolean hasAppliedForJob(String jobId) {
        for (int i = 0; i < appliedJobs.size(); i++) {
            if (appliedJobs.get(i).getId().equals(jobId)) {
                return true;
            }
        }
        return false;
    }

    public void withdrawApplication(String jobId) {
        for (int i = 0; i < appliedJobs.size(); i++) {
            if (appliedJobs.get(i).getId().equals(jobId)) {
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

        // Build string for applied jobs
        StringBuilder jobsBuilder = new StringBuilder();
        jobsBuilder.append("[");
        for (int i = 0; i < appliedJobs.size(); i++) {
            jobsBuilder.append(appliedJobs.get(i).getTitle());
            if (i < appliedJobs.size() - 1) {
                jobsBuilder.append(", ");
            }
        }
        jobsBuilder.append("]");

        return "Applicant ID: " + id + ", Name: " + name + ", Location: " + location
                + ", Desired Job Type: " + desiredJobType + ", Skills: " + skillsBuilder.toString()
                + ", Applied Jobs: " + jobsBuilder.toString();
    }
}
