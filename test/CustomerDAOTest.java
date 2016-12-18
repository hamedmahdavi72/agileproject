/**
 * Created by HamedMahdavi on 12/11/2016.
 */

import dao.CustomerDAOWrapper;
import models.Customer;
import models.User;
import org.junit.*;

import play.mvc.*;
import play.test.*;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;


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
