package entity;

/**
 *
 * @author leong kah tian
 */

import java.io.Serializable;
import java.util.Map;

public class Job implements Serializable {

    private String jobID;
    private String company;
    private String location;
    private String category;
    private String jobType;
    private String skills;
    private int duration;
    private int salary;
    private double minCGPA;


    //constructor
    public Job(String jobID, String company, String location, String category, String jobType, String skills, int salary, int duration, double minCGPA) {
        this.jobID = jobID;
        this.company = company;
        this.location = location;
        this.category = category;
        this.jobType = jobType;
        this.skills = skills;
        this.salary = salary;
        this.duration = duration;
        this.minCGPA = minCGPA;
    }

    //Getters
    public String getJobID() {
        return jobID;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public String getJobType() {
        return jobType;
    }

    public String getSkills() {
        return skills;
    }
    
    public int getSalary() {
        return salary;
    }

    public int getDuration() {
        return duration;
    }
    
    public double getMinCGPA(){
        return this.minCGPA;
    }

    //Setters
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    } 

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMinCGPA(double minCGPA){
        this.minCGPA = minCGPA;
    }    
    
    //toString method 
    @Override
    public String toString() {
        return "\nJob ID: " + jobID
                + "\nCompany: " + company
                + "\nLocation: " + location
                + "\nCategory: " + category
                + "\nPosition: " + jobType
                + "\nRequired Skills: " + skills
                + "\nSalary: RM " + salary
                + "\nDuration: " + duration + " months"
                + "\nRequired CGPA: " + minCGPA;
    }
}
