package dao;

import models.Issue;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

/**
 * Created by hamed on 2/18/17 AD.
 */
public class IssueDAOWrapper {
    private GenericDAO<Issue> issueDAO = null;
    private static IssueDAOWrapper instance = new IssueDAOWrapper();

    public IssueDAOWrapper(GenericDAO<Issue> issueDAO){
        this.issueDAO = issueDAO;
    }

    private IssueDAOWrapper(){
        issueDAO = new GenericDAO<>(Issue.class);
    }

    public static IssueDAOWrapper getInstance(){
        return instance;
    }

    public GenericDAO<Issue> getIssueDAO() {
        return issueDAO;
    }

    public MongoCursor<Issue> findAll(){return issueDAO.findAll();}

    public MongoCursor<Issue> findByUsername(String customerUsername){return issueDAO.findByFieldName("custormerUsername", customerUsername);}

    public void setIssueDAO(GenericDAO<Issue> issueDAO) {
        this.issueDAO = issueDAO;
    }

    public Issue findById(ObjectId id){
        return issueDAO.findById(id);
    }

    public MongoCursor<Issue> findBySolved(boolean value){
        MongoCursor<Issue> issues = issueDAO.findByFieldName("isSolved",value);
        return issues;
    }
}
