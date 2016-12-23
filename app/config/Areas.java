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
            addArea("1", 35.805851, 51.461568);
            addArea("2",35.743785, 51.359248);
            addArea("3",35.767999, 51.426143);
            addArea("4",35.752885, 51.526144);
            addArea("5",35.746506, 51.305152);
            addArea("6",35.726726, 51.403205);
            addArea("7",35.722263, 51.443342);
            addArea("8",35.727059, 51.493027);
            addArea("9",35.684483, 51.319026);
            addArea("10",35.685001, 51.367113);
            addArea("11",35.681589, 51.395255);
            addArea("12",35.680386, 51.427012);
            addArea("13",35.704822, 51.487884);
            addArea("14",35.673674, 51.483350);
            addArea("15",35.639805, 51.472006);
            addArea("16",35.639346, 51.414180);
            addArea("17",35.654852, 51.362489);
            addArea("18",35.658375, 51.296474);
            addArea("19",35.624933, 51.367934);
            addArea("20",35.599458, 51.436705);
            addArea("21",35.709225, 51.197391);
            addArea("22",35.741721, 51.209322);

        }

        public static Areas getInstance(){
            return instance;
        }
}
