package integration; /**
 * Created by HamedMahdavi on 12/11/2016.
 */

import dao.CustomerDAOWrapper;
import models.Customer;
import org.junit.Test;

import static play.test.Helpers.*;


public class CustomerDAOTest {
    @Test
    public void testSave(){

        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            Customer customer = new Customer();
            customer.setLastName("mahdavi");
            CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
        });

    }
}
