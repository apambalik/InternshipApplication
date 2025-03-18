/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import entity.Report;

/**
 *
 * @author Goh Ee Lin
 */
public class ReportGenerator {
    private JobPostingManager jobManager;
    private ApplicantManager applicantManager;
    
    public Report generateJobPostingsReport(String companyId, String location, String jobType) {
        // Generate report on job postings with filters
        return new Report();
    }
    
    public Report generateApplicantsReport(String location, String desiredJobType, ArrayList<String> skills) {
        // Generate report on applicants with filters
        return new Report();
    }
    
    
}