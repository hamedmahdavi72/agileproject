package dao;

import models.Admin;

/**
 * Created by hamed on 12/20/16 AD.
 */
public class AdminDAOWrapper {
    private GenericDAO<Admin> adminDAO= null;
    private static AdminDAOWrapper instance = new AdminDAOWrapper();

    private AdminDAOWrapper(){
        adminDAO = new GenericDAO<>(Admin.class);
    }

    public static AdminDAOWrapper getInstance(){
        return instance;
    }

    public GenericDAO<Admin> getAdminDAO() {
        return adminDAO;
    }

    public Admin findByUsername(String username){
        return  adminDAO.findOneByFieldName("username", username);
    }

}
