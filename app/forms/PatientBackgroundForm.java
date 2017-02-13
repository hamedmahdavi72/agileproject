package forms;

import java.util.Date;
import java.util.List;

/**
 * Created by ARYA on 2/13/2017.
 */
public class PatientBackgroundForm {

    private String patientName;
    private String patientPhoneNumber;
    private List<Date> patientAppointmentsDate;

    public PatientBackgroundForm(){}

    public PatientBackgroundForm(String patientName, String patientPhoneNumber, List<Date> patientAppointmentsDate){
        this.patientName = patientName;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientAppointmentsDate = patientAppointmentsDate;
    }
    public List<Date> getPatientAppointmentsDate() {
        return patientAppointmentsDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientAppointmentsDate(List<Date> patientAppointmentsDate) {
        this.patientAppointmentsDate = patientAppointmentsDate;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }
}
