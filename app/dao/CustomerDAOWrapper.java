package dao;

import models.Customer;

/**
 * Created by ARYA on 12/18/2016.
 */
public class CustomerDAOWrapper {
    private GenericDAO<Customer> customerDAO= null;
    private static CustomerDAOWrapper instance = new CustomerDAOWrapper();

    private CustomerDAOWrapper(){
        customerDAO = new GenericDAO<Customer>(Customer.class);
    }

    public static CustomerDAOWrapper getInstance(){
        return instance;
    }

    public GenericDAO<Customer> getCustomerDAO() {
        return customerDAO;
    }

    public Customer findByUsername(String username){
        return  customerDAO.findOneByFieldName("_id", username);
    }
}
