/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.JobPosting;
import adt.ArrayList;

public class JobPostingTest {
    public static void main(String[] args) {
        JobPostingManager manager = new JobPostingManager();
        
        // Add a job posting
        ArrayList<String> skills = new ArrayList<>();
        skills.add("Java");
        JobPosting job = new JobPosting("J1", "Developer", "Code in Java", "NYC", skills);
        boolean added = manager.addJobPosting(job);
        System.out.println("Job added: " + added);

        // Retrieve and print all jobs
        System.out.println("\nAll Jobs:");
        ArrayList<JobPosting> jobs = manager.getAllJobPostings();
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println(jobs.get(i));
        }
    }
}