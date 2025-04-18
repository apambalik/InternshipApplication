package entity;

///**
// *
// * @author Lai Zheng Xuan
// */
public class Matching {
    private Applicant applicant;
    private Job job;
    private double matchScore;
    private String matchDetails;

    public Matching(Applicant applicant, Job job, double matchScore, String matchDetails) {
        this.applicant = applicant;
        this.job = job;
        this.matchScore = matchScore;
        this.matchDetails = matchDetails;
    }

    // Getters
    public Applicant getApplicant() {
        return applicant;
    }

    public Job getJob() {
        return job;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public String getMatchDetails() {
        return matchDetails;
    }

    @Override
    public String toString() {
        return String.format("Match Score: %.2f%%\nApplicant: %s\nJob: %s\nDetails:\n%s",
                matchScore * 100,
                applicant.getName(),
                job.getCategory() + " at " + job.getCompany(),
                matchDetails);
    }
}
