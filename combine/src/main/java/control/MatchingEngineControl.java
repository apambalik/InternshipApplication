package control;

import entity.Applicant;
import entity.Job;
import entity.Matching;
import ADT.ArrayList;
import ADT.HashMap;
import ADT.HashSet;
import ADT.Iterator;
import ADT.ListInterface;
import ADT.Set;
import java.util.Comparator;

///**
// *
// * @author Lai Zheng Xuan
// */
public class MatchingEngineControl {

    private ApplicantManager applicantManager;
    private JobManager jobManager;
    private HashMap<String, Double> skillWeights;

    public MatchingEngineControl(ApplicantManager applicantManager, JobManager jobManager) {
        this.applicantManager = applicantManager;
        this.jobManager = jobManager;
        importantSkillWeights();
    }

    // Some bonus marks for Important(Hard) Skill
    private void importantSkillWeights() {
        skillWeights = new HashMap<>();
         // ===== High-Value Technical Skills =====
        skillWeights.put("machine learning", 1.5);
        skillWeights.put("tensorflow", 1.6);
        skillWeights.put("pytorch", 1.6);
        skillWeights.put("autocad", 1.4);
        skillWeights.put("solidworks", 1.5);

        // ===== Industry-Specific Rare Skills =====
        skillWeights.put("biostatistics", 1.6);
        skillWeights.put("reservoir engineering", 1.7);
        skillWeights.put("semiconductor physics", 1.7);
        skillWeights.put("aviation management", 1.4);
        skillWeights.put("precision agriculture", 1.5);

        // ===== Strategic Business Skills =====
        skillWeights.put("financial modeling", 1.5);
        skillWeights.put("market research", 1.3);
    }

    // Finding function
    public ArrayList<Matching> findJobsForApplicant(String applicantId) {
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        if (applicant == null) {
            return new ArrayList<>();
        }

        ArrayList<Matching> results = new ArrayList<>();
        ListInterface<Job> jobs = jobManager.getAllJobs();

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            Matching result = calculateMatch(applicant, job);
            if (result.getMatchScore() > 0.3) { // Only include matches above 30%
                results.add(result);
            }
        }

        // Sort results by match score in descending order
        quickSort(results, 0, results.size() - 1, (r1, r2)
                -> Double.compare(r2.getMatchScore(), r1.getMatchScore()));

        return results;
    }

    public ArrayList<Matching> findApplicantsForJob(String jobId) {
        Job job = jobManager.getJobById(jobId);
        if (job == null) {
            return new ArrayList<>();
        }

        ArrayList<Matching> results = new ArrayList<>();
        ArrayList<Applicant> allApplicants = applicantManager.getApplicants();

        for (int i = 0; i < allApplicants.size(); i++) {
            Applicant applicant = allApplicants.get(i);

            // Only consider applicants who have applied for this job
            if (applicant.hasAppliedForJob(jobId)) {
                Matching result = calculateMatch(applicant, job);
                if (result.getMatchScore() > 0.3) { // Only include matches above 30%
                    results.add(result);
                }
            }
        }

        // Sort results by match score in descending order
        quickSort(results, 0, results.size() - 1, (r1, r2)
                -> Double.compare(r2.getMatchScore(), r1.getMatchScore()));

        return results;
    }

    public ArrayList<Matching> findTopMatches(int count) {
        ArrayList<Matching> allMatches = new ArrayList<>();
        ArrayList<Applicant> applicants = applicantManager.getApplicants();
        ListInterface<Job> jobs = jobManager.getAllJobs();

        // Calculate all possible matches
        for (int i = 0; i < applicants.size(); i++) {
            for (int j = 0; j < jobs.size(); j++) {
                Matching result = calculateMatch(applicants.get(i), jobs.get(j));
                if (result.getMatchScore() > 0.5) { // Only consider match score >= 50%
                    allMatches.add(result); // add into array list to be displayed later
                }
            }
        }

        // Sort all matches by score desc
        quickSort(allMatches, 0, allMatches.size() - 1, (r1, r2)
                -> Double.compare(r2.getMatchScore(), r1.getMatchScore()));

        // Return top N 4 matches
        ArrayList<Matching> topMatches = new ArrayList<>();
        for (int i = 0; i < Math.min(count, allMatches.size()); i++) {
            topMatches.add(allMatches.get(i));
        }

        return topMatches;
    }
    
    
    
    // Match score percentage/sum up 
    private Matching calculateMatch(Applicant applicant, Job job) {
        double totalScore = 0;
        double maxPossibleScore = 0;
        StringBuilder matchDetails = new StringBuilder();

        // 1. Skill Matching (40% weight)
        Set<String> jobSkills = parseSkills(job.getSkills());
        Set<String> applicantSkills = applicant.getSkills();
        double skillScore = calculateSkillMatch(jobSkills, applicantSkills);
        totalScore += skillScore * 0.4;
        maxPossibleScore += 0.4;
        // Store the results into matchDetail to be printed out for reporting in future
        // Called by getMatchDetails()
        matchDetails.append(String.format("Skills: %.1f%%, ", skillScore * 100));

        // 2. CGPA (30% weight)
        double cgpaScore = calculateCGPAMatch(
                applicant.getApplicantCGPA(),
                job.getMinCGPA());
        totalScore += cgpaScore * 0.3;
        maxPossibleScore += 0.3;
        matchDetails.append(String.format("CGPA: %.1f%%, ", cgpaScore * 100));

        // 3. Job Position (15% weight)
        double jobTypeScore = calculateJobTypeMatch(
                applicant.getDesiredJobType(),
                job.getJobType());
        totalScore += jobTypeScore * 0.15;
        maxPossibleScore += 0.15;
        matchDetails.append(String.format("Job Position: %.1f%%, ", jobTypeScore * 100));

        // 4. Location (15% weight)
        double locationScore = calculateLocationMatch(
                applicant.getLocation(),
                job.getLocation());
        totalScore += locationScore * 0.15;
        maxPossibleScore += 0.15;
        matchDetails.append(String.format("Location: %.1f%%", locationScore * 100));

        // Normalize score
        double normalizedScore;
        if (maxPossibleScore > 0) {
            normalizedScore = totalScore / maxPossibleScore;
        } else {
            normalizedScore = 0;
        }

        return new Matching(applicant, job, normalizedScore, matchDetails.toString());
    }

    
    
    // match score calculation
    // 1.Skill
    private double calculateSkillMatch(Set<String> jobSkills, Set<String> applicantSkills) {
        if (jobSkills.isEmpty()) {
            return 0.5; // Neutral score if no skills specified
        }
        double baseScore = 0.3; // Base score for having some skills
        double matchBonus = 0.0;
        double maxPossibleBonus = 0.0;

        // Calculate bonus for matched important(hard) skills
        Iterator<String> jobSkillIter = jobSkills.iterator();
        while (jobSkillIter.hasNext()) {
            String jobSkill = jobSkillIter.next().toLowerCase();
            double skillWeight = skillWeights.getOrDefault(jobSkill, 1.0);  // if the skill is important(hard) skill, get extra marks, else mark = 1

            // Check if applicant has this skill
            boolean hasSkill = false;
            Iterator<String> applicantSkillIter = applicantSkills.iterator();
            while (applicantSkillIter.hasNext()) {
                if (applicantSkillIter.next().equalsIgnoreCase(jobSkill)) {
                    hasSkill = true;
                    break;
                }
            }

            if (hasSkill) {
                // Matched skills get 3x multiplier
                matchBonus += skillWeight * 3.0;
            }
            maxPossibleBonus += skillWeight * 3.0;
        }

        // Normalize the bonus and combine with base score
        double normalizedBonus;
        if (maxPossibleBonus > 0) {
            normalizedBonus = matchBonus / maxPossibleBonus;
        } else {
            normalizedBonus = 0;
        }
        
        return baseScore + (normalizedBonus * 0.7); // Bonus contributes up to 70% of total skill score
    }
    
    // 2. CGPA
    private double calculateCGPAMatch(double applicantCGPA, double jobRequiredCGPA) {
        if (jobRequiredCGPA <= 0) {
            return 1.0; // Job doesn't require min CGPA
        }
        if (applicantCGPA >= jobRequiredCGPA) {
            return 1.0;
        }

        // Linear scale from 0 to required CGPA
        return Math.min(1.0, (double) applicantCGPA / jobRequiredCGPA);
    }

    // 3. job type
    private double calculateJobTypeMatch(String applicantJobType, String jobJobType) {
        if (applicantJobType == null || jobJobType == null) {
            return 0.5;
        }
        if (applicantJobType.equalsIgnoreCase(jobJobType)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    // 4. location
    private double calculateLocationMatch(String applicantLocation, String jobLocation) {
        if (applicantLocation == null || jobLocation == null) {
            return 0;
        }
        if (applicantLocation.equalsIgnoreCase(jobLocation)) {
            return 1.0;
        } else {
            return 0.3;
        }
    }

    
    
    // helper to parse ',' in job/applicant SKILL(s)
    private Set<String> parseSkills(String skillsString) {
        Set<String> skills = new HashSet<>();
        if (skillsString == null || skillsString.isEmpty()) {
            return skills;
        }

        String[] skillArray = skillsString.split(",");
        for (String skill : skillArray) {
            skills.add(skill.trim().toLowerCase());
        }
        return skills;
    }

    
    
    // Report generation
    public String generateMatchingReport() {
        StringBuilder report = new StringBuilder();
        ArrayList<Matching> topMatches = findTopMatches(10);

        report.append("=== Job Matching Analysis Report ===\n\n");
        report.append("Top 10 Job Matches:\n");
        report.append("------------------------------------------------------------\n");

        for (int i = 0; i < topMatches.size(); i++) {
            Matching match = topMatches.get(i);
            report.append(String.format("%d. %s <-> %s (Score: %.2f%%)\n",
                    i + 1,
                    match.getApplicant().getName(),
                    match.getJob().getJobType() + " at " + match.getJob().getCompany(),
                    match.getMatchScore() * 100));
            report.append("    ").append(match.getMatchDetails()).append("\n\n");
        }

        // Add statistics
        ArrayList<Applicant> applicants = applicantManager.getApplicants();
        ListInterface<Job> jobs = jobManager.getAllJobs();

        report.append("\n=== Matching Statistics ===\n");
        report.append("Total Applicants: ").append(applicants.size()).append("\n");
        report.append("Total Jobs: ").append(jobs.size()).append("\n");
        report.append("Average Match Score: ").append(calculateAverageMatchScore()).append("%\n");

        return report.toString();
    }

    private double calculateAverageMatchScore() {
        ArrayList<Applicant> applicants = applicantManager.getApplicants();
        ListInterface<Job> jobs = jobManager.getAllJobs();
        double totalScore = 0;
        int count = 0;

        // Sample a subset of matches for performance
        int sampleSize = Math.min(10, applicants.size());
        for (int i = 0; i < sampleSize; i++) {
            ArrayList<Matching> matches = findJobsForApplicant(applicants.get(i).getId());
            if (!matches.isEmpty()) {
                totalScore += matches.get(0).getMatchScore(); // Take top match
                count++;
            }
        }

        if (count > 0) {
            return (totalScore / count) * 100;
        } else {
            return 0;
        }
    }
    
    
    
    // QuickSort implementation for sorting matching results
    private <T> void quickSort(ArrayList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private <T> int partition(ArrayList<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);   // Choose last element as pivot
        int i = low - 1;    // Index of smaller element

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) < 0) {
                i++;
                // Swap elements at i and j
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Swap pivot with element at i+1
        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;   // Return pivot index
    }



    // Approve Application
    public boolean approveApplicant(String applicantId, String jobId) {
        // 1. Validate applicant and job exist
        Applicant applicant = applicantManager.getApplicantById(applicantId);
        Job job = jobManager.getJobById(jobId);

        if (applicant == null || job == null) {
            return false;
        }

        // 2. Verify the applicant has applied for this job
        if (!applicant.hasAppliedForJob(jobId)) {
            return false;
        }

        // 3.Check for existing approved application
        if (applicant.hasApprovedApplication()) {
            return false;
        }

        // 4. Update application status to "Approved"
        applicant.setApplicationStatus(jobId, "Approved");

        return true;
    }
    
    public Job findApprovedJob(Applicant applicant) {
        ArrayList<Job> appliedJobs = applicant.getAppliedJobs();
        for (int i = 0; i < appliedJobs.size(); i++) {
            Job job = appliedJobs.get(i);
            if ("Approved".equalsIgnoreCase(applicant.getApplicationStatus(job.getJobID()))) {
                return job;
            }
        }
        return null;
    }
    
}
