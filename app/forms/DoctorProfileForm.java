package forms;

import models.Doctor;
import models.MongoLocation;
import models.User;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public class DoctorProfileForm extends  UserProfileForm {
    private String clinicAddress;
    private String clinicPhoneNumber;
    private ArrayList<String> supportedInsuranceCompanies;


    public DoctorProfileForm(Doctor doctor) {
        super(doctor);
        this.clinicAddress = doctor.getClinicAddress();
        this.clinicPhoneNumber = doctor.getClinicPhoneNumber();
        this.supportedInsuranceCompanies = doctor.getSupportedInsuranceCompanies();


    }
}
