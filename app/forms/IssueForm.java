package forms;

import java.util.Date;

/**
 * Created by hamed on 2/18/17 AD.
 */
public class IssueForm {
    private String issueReport;
    private Date issueDate;
    private String subject;

    public IssueForm(){}

    public void setIssueReport(String issueReport) {
        this.issueReport = issueReport;
    }

    public String getIssueReport() {
        return issueReport;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
