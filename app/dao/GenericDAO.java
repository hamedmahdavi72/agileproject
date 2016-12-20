package dao;

import models.JongoInstanceProvider;
import models.JongoModel;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class GenericDAO<T extends JongoModel> {
    private  static PlayJongo jongo = JongoInstanceProvider.getInstance();
    private  Class<T> type;


    public GenericDAO(Class<T> type){
        this.type = type;
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

        return jongo.getCollection(getTypeName()).find("{#: #}", fieldName, fieldValue).as(type);
    }

    public   T findOneByFieldName(String fieldName, String fieldValue) {

        return jongo.getCollection(getTypeName()).findOne("{#: #}",fieldName, fieldValue).as(type);
    }

    public   MongoCursor<T> findByFieldName(String fieldName, boolean fieldValue) {

        return jongo.getCollection(getTypeName()).find("{#: #}",fieldName, fieldValue).as(type);
    }

    private String getTypeName() {
        return type.getName();
    }
    
}
