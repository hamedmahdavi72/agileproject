package models;

import forms.IssueForm;

import java.util.Date;

/**
 * Created by hamed on 2/18/17 AD.
 */
public class Issue extends JongoModel {
    private String issueReport;
    private String issueDate;
    private String custormerUsername;
    private String subject;

    public Issue(IssueForm issueForm, String custormerUsername){
        this.issueReport = issueForm.getIssueReport();
        this.issueDate = issueForm.getIssueDate();
        this.subject = issueForm.getSubject();
        this.custormerUsername = custormerUsername;
    }

    public Issue(){

    }

    public void setIssueReport(String issueReport) {
        this.issueReport = issueReport;
    }

    public String getIssueReport() {
        return issueReport;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setCustormerUsername(String custormerUsername) {
        this.custormerUsername = custormerUsername;
    }

    public String getCustormerUsername() {
        return custormerUsername;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
