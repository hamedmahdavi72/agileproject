import dao.DoctorDAOWrapper;
import models.JongoInstanceProvider;
import play.Application;
import play.GlobalSettings;

/**
 * Created by HamedMahdavi on 12/22/2016.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        DoctorDAOWrapper.getInstance().getDoctorDAO().getCollection().ensureIndex("{geoLocation : \"2dsphere\"}");
    }
}
