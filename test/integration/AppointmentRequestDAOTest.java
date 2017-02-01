package integration;

import dao.AppointmentRequestDAOWrapper;
import models.AppointmentInterval;
import models.AppointmentRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import play.Application;
import play.test.Helpers;

import java.util.Date;

/**
 * Created by hamed on 1/9/17 AD.
 */
public class AppointmentRequestDAOTest {

    static Application fakeApp;
    static AppointmentRequest appReq;
    static AppointmentInterval appInt;

    @BeforeClass
    public static void initApp(){
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApp);
        appInt = new AppointmentInterval(DateUtils.addDays(new Date(),-1), 14, 17);
        appReq = new AppointmentRequest("ala","hashemi",appInt);
        AppointmentRequestDAOWrapper.getInstance().getAppointmentRequestDAO().save(appReq);

    }

    @AfterClass
    public static void stopApp(){
        AppointmentRequestDAOWrapper.getInstance().getAppointmentRequestDAO().remove(appReq);
        Helpers.stop(fakeApp);
    }

    @Test
    public void testFindById(){
        AppointmentRequest foundAppointmentRequest = AppointmentRequestDAOWrapper.getInstance()
                .findById(appReq.getId());

        ObjectId id = new ObjectId(1485359404,12725862,(short)3296,7882463);
        System.out.println(id.toString());

        assertNotNull(appReq);
    }




}
