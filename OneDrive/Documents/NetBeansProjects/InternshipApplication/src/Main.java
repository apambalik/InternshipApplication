
import boundary.ApplicantUI;
import control.ApplicantManager;
import control.JobPostingManager;

/**
 *
 * @author User
 */
public class Main {

    public static void main(String[] args) {
        ApplicantManager applicantManager = new ApplicantManager();
        JobPostingManager jobPostingManager = new JobPostingManager();
        ApplicantUI applicantUI = new ApplicantUI(applicantManager, jobPostingManager);
        applicantUI.displayApplicantMenu();
    }
}
