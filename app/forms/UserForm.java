package forms;
import play.data.validation.Constraints;



/**
 * Created by ARYA on 12/18/2016.
 */
public class UserForm {
    @Constraints.Required
    private String username;

    @Constraints.Required
    @Constraints.MinLength(6)
    private String password;

    public UserForm(){}
    public UserForm(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
