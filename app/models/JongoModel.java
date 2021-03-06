package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by HamedMahdavi on 12/12/2016.
 * we suppose that every class collection name is it class simple name
 */
public  abstract class JongoModel {

    @MongoId
    protected ObjectId id;
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getId() {
        return id;
    }

}
