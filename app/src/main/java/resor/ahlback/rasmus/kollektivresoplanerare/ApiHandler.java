package resor.ahlback.rasmus.kollektivresoplanerare;

import android.util.Log;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

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

    private boolean placeFinderAPIavailable = true;
    private int placeFinderMaxCallsPerMinute = 30;
    private int placeFinderCallsMade = 0;

    private String placeFinderApiKeyBackStack;
    private String placeFinderSearchWordsBackStack;
    private String placeFinderFormatBackStack;
    private Boolean placeFinderStationsOnly;
    private int placeFinderMaxResultsBackStack;
    private Boolean placeFinderBackStackActive = false;
    private Timer placeFinderBackStackCallTimer = new Timer();

    JsonDownloadResponse delegate;

    public ApiHandler(JsonDownloadResponse delegate){
        this.delegate = delegate;
    }

    public int placeFinder(String apiKey, String searchWords) throws Exception{
        return placeFinder(apiKey, searchWords, "json", true, 20);
    }

    public int placeFinder(String apiKey, String searchWords, String format, Boolean stationsOnly, int maxResults) throws Exception{
//        StringBuilder url = new StringBuilder("http://api.sl.se/api2/typeahead.");
       // http://api.sl.se/api2/typeahead.<FORMAT>?key=<DIN NYCKEL>&searchstring=<SÖKORD>&stationsonly=<ENDAST STATIONER>&maxresults=<MAX ANTAL SVAR>

        if(searchWords.equals("")){
            return 400;
        }

        if(placeFinderAPIavailable){
            placeFinderAPIavailable = false;
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
            Timer placeFinderTimer = new Timer();
            placeFinderTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    placeFinderAPIavailable = true;
                }
            }, 1000);

            placeFinderCallsMade += 1;
            Log.d("ApiHandler", "Calls made> " +Integer.toString(placeFinderCallsMade));
            Log.d("ApiHandler", "Search> " + searchWords);
            return 200;

        } else {
            addPlaceFinderCallToBackStack(apiKey,searchWords,format,stationsOnly,maxResults);
            return 408;
        }


    }

    private void addPlaceFinderCallToBackStack(String apiKey, String searchWords, String format, Boolean stationsOnly, int maxResults){
        placeFinderApiKeyBackStack = apiKey;
        placeFinderSearchWordsBackStack = searchWords;
        placeFinderFormatBackStack = format;
        placeFinderStationsOnly = stationsOnly;
        placeFinderMaxResultsBackStack = maxResults;

        if(!placeFinderBackStackActive){
            placeFinderBackStackActive = true;
//            placeFinderBackStackCallTimer.cancel();
//            placeFinderBackStackCallTimer.purge();
//            placeFinderBackStackCallTimer = null;
//            placeFinderBackStackCallTimer = new Timer();
            placeFinderBackStackCallTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    try{
                        Log.d("Timer", "Timer has already run");
                        placeFinder(placeFinderApiKeyBackStack, placeFinderSearchWordsBackStack,placeFinderFormatBackStack,placeFinderStationsOnly,placeFinderMaxResultsBackStack);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    placeFinderBackStackActive = false;
                }
            }, 1000);

        }



    }
}
