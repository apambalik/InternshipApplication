/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ArrayList;

/**
 *
 * @author Goh Ee Lin
 */
public class JobPosting {

    private String id;
    private String title;
    private String description;
    private String location;
    private ArrayList<String> requiredSkills;

    public JobPosting(String id, String title, String description, String location, ArrayList<String> requiredSkills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.requiredSkills = requiredSkills;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    @Override
    public String toString() {
        // Convert requiredSkills ArrayList to a comma-separated string
        StringBuilder skillsBuilder = new StringBuilder();
        for (int i = 0; i < requiredSkills.size(); i++) {
            skillsBuilder.append(requiredSkills.get(i));
            if (i < requiredSkills.size() - 1) {
                skillsBuilder.append(", ");
            }
        }

        return "Job ID: " + id + ", Title: " + title + ", Location: " + location
                + ", Required Skills: [" + skillsBuilder.toString() + "]";
    }
}
