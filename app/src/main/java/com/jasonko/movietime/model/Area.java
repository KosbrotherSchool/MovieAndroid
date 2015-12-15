package com.jasonko.movietime.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/20/15.
 */
public class Area {

    String name;
    int area_id;

    public Area(String name, int area_id){
        this.name = name;
        this.area_id = area_id;
    }

    public String getName(){
        return name;
    }

    public int getArea_id(){
        return area_id;
    }

    public static String area_message = "[{\"id\":1,\"name\":\"台北東區\"},{\"id\":2,\"name\":\"台北西區\"},{\"id\":3,\"name\":\"台北南區\"},{\"id\":4,\"name\":\"台北北區\"},{\"id\":5,\"name\":\"新北市\"},{\"id\":6,\"name\":\"台北二輪\"},{\"id\":7,\"name\":\"基隆\"},{\"id\":8,\"name\":\"桃園\"},{\"id\":9,\"name\":\"中壢\"},{\"id\":10,\"name\":\"新竹\"},{\"id\":11,\"name\":\"苗栗\"},{\"id\":12,\"name\":\"台中\"},{\"id\":13,\"name\":\"彰化\"},{\"id\":14,\"name\":\"雲林\"},{\"id\":15,\"name\":\"南投\"},{\"id\":16,\"name\":\"嘉義\"},{\"id\":17,\"name\":\"台南\"},{\"id\":18,\"name\":\"高雄\"},{\"id\":19,\"name\":\"宜蘭\"},{\"id\":20,\"name\":\"花蓮\"},{\"id\":21,\"name\":\"台東\"},{\"id\":22,\"name\":\"金門\"},{\"id\":23,\"name\":\"屏東\"},{\"id\":24,\"name\":\"澎湖\"},{\"id\":25,\"name\":\"所有二輪\"}]";

    public static ArrayList<Area> getAreas(){
        ArrayList<Area> areas = new ArrayList<Area>();
        try {
            JSONArray areaArray = new JSONArray(area_message);
            for (int i = 0; i < areaArray.length(); i++){
                JSONObject areaObject = areaArray.getJSONObject(i);

                String name = "";
                int area_id = 0;

                try {
                    name = areaObject.getString("name");
                    area_id = areaObject.getInt("id");
                }catch (Exception e){

                }
                Area newArea = new Area(name, area_id);
                areas.add(newArea);
            }
        }catch (Exception e){

        }
        return  areas;
    }

    public static Area getAreaByID(int the_area_id){
        Area mArea = null;
        try {
            JSONArray areaArray = new JSONArray(area_message);
            for (int i = 0; i < areaArray.length(); i++){
                JSONObject areaObject = areaArray.getJSONObject(i);
                if (areaObject.getInt("id") == the_area_id) {
                    String name = "";
                    int area_id = 0;

                    try {
                        name = areaObject.getString("name");
                        area_id = areaObject.getInt("id");
                    } catch (Exception e) {

                    }
                    mArea = new Area(name, area_id);
                }
            }
        }catch (Exception e){

        }
        return mArea;
    }
}
