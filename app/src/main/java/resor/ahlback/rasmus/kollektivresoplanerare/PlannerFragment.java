package resor.ahlback.rasmus.kollektivresoplanerare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import resor.ahlback.rasmus.kollektivresoplanerare.Interfaces.JsonDownloadResponse;
import resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses.JsonPlaceFinderItem;

public class PlannerFragment extends Fragment implements JsonDownloadResponse {
    private String apiKey ;
    private ApiHandler apiHandler;

    String[] languages = { "C","C++","Java","C#","PHP","JavaScript","jQuery","AJAX","JSON" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.planner_layout, container,false);
        View view = inflater.inflate(R.layout.planner_layout,
                container, false);

        apiKey = getActivity().getResources().getString(R.string.platsuppslag);
        apiHandler = new ApiHandler(this);
        setPlannerButton(view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, languages);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) view.findViewById(R.id.plannerTextInput);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

        return view;
    }

    private void setPlannerButton(View view){
        Button button = (Button) view.findViewById(R.id.plannerButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try{
                    apiHandler.placeFinder(apiKey, "Vreta Gårds Väg");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void processFinished(JSONObject output, int responseCode) {
//        try {
////            Log.d("ApiHandler", output.get("ResponseData").toString());
//
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
        getListOfPlaces(output);
    }

    public void getListOfPlaces(JSONObject obj){
        JSONArray list = null;
        ArrayList<JsonPlaceFinderItem> items = new ArrayList<>();

        try {
            list = obj.getJSONArray("ResponseData");
            for(int i = 0; i < list.length(); i++){
                items.add(new JsonPlaceFinderItem(list.getJSONObject(i)));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        for(JsonPlaceFinderItem item : items)
            Log.d("PlannerFragment", item.toString());

    }

}
