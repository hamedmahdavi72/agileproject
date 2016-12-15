package models;

import java.nio.file.Path;
import java.util.Date;

/**
 * Created by HamedMahdavi on 12/15/2016.
 */
public class DegreeInfo {
    private Date graduationDate;
    private String university;
    private Path degreePath;
    private String speciality;

    public void setDegreePath(Path degreePath) {
        this.degreePath = degreePath;
    }

    public Path getDegreePath() {
        return degreePath;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
