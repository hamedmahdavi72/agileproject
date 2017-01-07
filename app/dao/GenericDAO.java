package dao;

import models.JongoInstanceProvider;
import models.JongoModel;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class GenericDAO<T extends JongoModel> {
    private  PlayJongo jongo ;
    private  Class<T> type;

    public GenericDAO(){
    }

    public GenericDAO(Class<T> type){
        this.type = type;
        jongo = JongoInstanceProvider.getInstance();
    }


    public MongoCollection getCollection() {
        return jongo.getCollection(getTypeName());
    }

    public void save(T model) {
        getCollection().save(model);
    }

    public void remove(T model) {
        getCollection().remove(model.getId());
    }



    public   MongoCursor<T> findByFieldName(String fieldName,String fieldValue) {

        return getCollection().find("{#: #}", fieldName, fieldValue).as(type);
    }

    public   T findOneByFieldName(String fieldName, String fieldValue) {

        return getCollection().findOne("{#: #}",fieldName, fieldValue).as(type);
    }

    public   MongoCursor<T> findByFieldName(String fieldName, boolean fieldValue) {

        return getCollection().find("{#: #}",fieldName, fieldValue).as(type);
    }

    private String getTypeName() {
        return type.getName();
    }

    public void setJongo(PlayJongo jongo) {
        this.jongo = jongo;
    }

    public PlayJongo getJongo() {
        return jongo;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }


}


