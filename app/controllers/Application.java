package controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.JongoModel;
import models.User;
import org.bson.types.ObjectId;
import play.*;
import play.libs.Json;
import play.mvc.*;

import play.twirl.api.Content;
import views.html.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

public class Application extends Controller {

    public static Result index() {

        Content html = views.html.index.render("salam");
        return ok(html);
    }

}


