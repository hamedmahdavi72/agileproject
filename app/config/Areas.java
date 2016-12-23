package config;

import models.MongoLocation;

import java.util.HashMap;

/**
 * Created by HamedMahdavi on 12/23/2016.
 */
public class Areas {
        private HashMap<String, MongoLocation> areasMap;
        private static Areas instance = new Areas();

        public void addArea(String areaName,double latitude,double longitude){
            areasMap.put(areaName,new MongoLocation(latitude,longitude));
        }

        public MongoLocation getArea(String areaName){
            return areasMap.get(areaName);
        }

        private Areas(){
            areasMap = new HashMap<>();
            addArea("1", 35.768139, 51.448629);
        }

        public static Areas getInstance(){
            return instance;
        }
}
