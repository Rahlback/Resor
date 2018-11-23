package resor.ahlback.rasmus.kollektivresoplanerare;

import android.util.Log;

import org.json.JSONObject;

import resor.ahlback.rasmus.kollektivresoplanerare.Interfaces.JsonDownloadResponse;

public class ApiHandler{
//    public JSONObject get(String option, String apiKey, String siteID){
//        switch (option){
//            case "placeFinder":
//                String url = "http://api.sl.se/api2/typeahead.<FORMAT>?key=<DIN NYCKEL>&searchstring=<SÖKORD>&stationsonly=<ENDAST STATIONER>&maxresults=<MAX ANTAL SVAR>"
//                return;
//
//        }
//    }

    JsonDownloadResponse delegate;

    public ApiHandler(JsonDownloadResponse delegate){
        this.delegate = delegate;
    }

    public void placeFinder(String apiKey, String searchWords) throws Exception{
        placeFinder(apiKey, searchWords, "json", true, 20);
    }

    public void placeFinder(String apiKey, String searchWords, String format, Boolean stationsOnly, int maxResults) throws Exception{
//        StringBuilder url = new StringBuilder("http://api.sl.se/api2/typeahead.");
        http://api.sl.se/api2/typeahead.<FORMAT>?key=<DIN NYCKEL>&searchstring=<SÖKORD>&stationsonly=<ENDAST STATIONER>&maxresults=<MAX ANTAL SVAR>
        if (format != "json" && format != "xml"){
            throw new Exception("Wrong format to api call");
        }

        String url = "http://api.sl.se/api2/typeahead." +
                 format  +
                "?key=" + apiKey +
                "&searchstring=" + searchWords +
                "&stationsonly=" + Boolean.toString(stationsOnly) +
                "&maxresults=" + maxResults;

        new JsonDownloader(delegate, 1).execute(url);
    }
}
