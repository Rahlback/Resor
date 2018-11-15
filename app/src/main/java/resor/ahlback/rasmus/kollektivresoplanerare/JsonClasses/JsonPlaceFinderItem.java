package resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonPlaceFinderItem {
    private String name;
    private String siteId;
    private String type;
    private String xCoord;
    private String yCoord;

    public JsonPlaceFinderItem(String name, String siteId, String type, String xCoord, String yCoord){
        this.name = name;
        this.siteId = siteId;
        this.type = type;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public JsonPlaceFinderItem(JSONObject obj){
//        Log.d("JsonPlaceFinderItem", obj.get("ResponseData"));
        Log.d("JsonPlaceFinderItem", obj.toString());
        try{
            name = obj.getString("Name");
            siteId = obj.getString("SiteId");
            type = obj.getString("Type");
            xCoord = obj.getString("X");
            yCoord = obj.getString("Y");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        String itemString =   "Name: "      + name      + "\n"
                            + "SiteId: "    + siteId    + "\n"
                            + "Type: "      + type      + "\n"
                            + "X-coord: "   + xCoord    + "\n"
                            + "Y-coord: "   + yCoord    + "\n";
        return itemString;
    }
}
