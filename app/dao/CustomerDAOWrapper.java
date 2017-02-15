package dao;

import models.Customer;
import org.jongo.MongoCursor;

import java.util.List;

/**
 * Created by ARYA on 12/18/2016.
 */
public class CustomerDAOWrapper {
    private GenericDAO<Customer> customerDAO= null;
    private static CustomerDAOWrapper instance = new CustomerDAOWrapper();

    //just use it for testing!!!!
    public CustomerDAOWrapper(GenericDAO<Customer> customerDAO){
        this.customerDAO = customerDAO;
    }

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
        return  customerDAO.findOneByFieldName("username", username);
    }

    public MongoCursor<Customer> findAll(){
        return  customerDAO.findAll();
    }
}
