package resor.ahlback.rasmus.kollektivresoplanerare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import resor.ahlback.rasmus.kollektivresoplanerare.Interfaces.JsonDownloadResponse;
import resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses.JsonPlaceFinderItem;

public class PlannerFragment extends Fragment implements JsonDownloadResponse {
    private String apiKey ;
    private ApiHandler apiHandler;



    ArrayList<String> fromStationStringList;
    ArrayList<JsonPlaceFinderItem> data;
    ArrayAdapter<String> fromStationAdapter;

    ArrayList<String> toStationStringList;
    ArrayAdapter<String> toStationAdapter;

    AutoCompleteTextView fromStationAutocompleteText;
    JsonPlaceFinderItem fromStation = null;
    JsonPlaceFinderItem toStation = null;

    AutoCompleteTextView toStationAutoCompleteText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.planner_layout, container,false);
        View view = inflater.inflate(R.layout.planner_layout,
                container, false);

        apiKey = getActivity().getResources().getString(R.string.platsuppslag);
        apiHandler = new ApiHandler(this);

        fromStationStringList = new ArrayList<>();
        toStationStringList = new ArrayList<>();
//        String[] test = {"Test", "Text", "Hyper"};
//        for(String testItem : test){
//            languages.add(testItem);
//        }

        fromStationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, fromStationStringList);
        fromStationAutocompleteText = (AutoCompleteTextView) view.findViewById(R.id.plannerTextInput);
        fromStationAutocompleteText.setThreshold(1);
        fromStationAutocompleteText.setAdapter(fromStationAdapter);

        toStationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item, toStationStringList);
        toStationAutoCompleteText = (AutoCompleteTextView) view.findViewById(R.id.plannerTextInputTo);
        toStationAutoCompleteText.setThreshold(1);
        toStationAutoCompleteText.setAdapter(fromStationAdapter);


//        fromStationAdapter = new ArrayAdapter<JsonPlaceFinderItem>(getActivity(), android.R.layout.select_dialog_item, data);
        fromStationAutocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                fromStation = findDataPoint(arg0.getItemAtPosition(arg2).toString());
                Log.d("PlannerFragment", "Data chosen> " + fromStation.toString());
            }

        });

        toStationAutoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toStation = findDataPoint(adapterView.getItemAtPosition(i).toString());
                Log.d("Planner Second", "Data chosen> " + toStation.toString());
            }
        });

        toStationAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (toStation == null){
                    placeFinderAPILookUp(editable.toString(), 2);
                }
                else if(!toStation.getName().equals(editable.toString())){
                    placeFinderAPILookUp(editable.toString(),2);
                }
            }
        });

        fromStationAutocompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fromStation == null){
                    placeFinderAPILookUp(editable.toString(), 1);
                }
                else if(!fromStation.getName().equals(editable.toString())){
                    placeFinderAPILookUp(editable.toString(),1);
                }
            }
        });

        return view;
    }

    private void placeFinderAPILookUp(final String place, int responseCode){
        try{
            apiHandler.placeFinder(apiKey, place, responseCode);
//            Log.d("PlannerFragment", "Looking up " + place);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private JsonPlaceFinderItem findDataPoint(String dataString){
        for(JsonPlaceFinderItem item : data){
            if(dataString.equals(item.getName())){
                return item;
            }
        }
        return null;
    }

//
//    private void setPlannerButton(View view){
//        Button button = (Button) view.findViewById(R.id.plannerButton);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//                try{
//                    apiHandler.placeFinder(apiKey, "Vreta Gårds Väg");
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

    private void updateAutocompleteText(ArrayList<JsonPlaceFinderItem> newStrings){
        fromStationAdapter.clear();
        for (JsonPlaceFinderItem string: newStrings) {
            fromStationAdapter.add(string.getName());
        }

        fromStationAdapter.getFilter().filter(fromStationAutocompleteText.getText(), fromStationAutocompleteText);
    }

    private void updateAutocompleteTextTo(ArrayList<JsonPlaceFinderItem> newStrings){
        fromStationAdapter.clear();
        for (JsonPlaceFinderItem string: newStrings) {
            fromStationAdapter.add(string.getName());
        }

        fromStationAdapter.getFilter().filter(toStationAutoCompleteText.getText(), toStationAutoCompleteText);
    }

    @Override
    public void processFinished(JSONObject output, int responseCode) {
        data = getListOfPlaces(output);
        Log.d("Planner fragment", "Download complete");
//        Log.d("Planner", "Data> " + data.toString());
        Log.d("Planner", "Response code>" + Integer.toString(responseCode));

        switch (responseCode){
            case 1:
                updateAutocompleteText(data);
                break;
            case 2:
                updateAutocompleteTextTo(data);
                break;
        }
    }

    public ArrayList<JsonPlaceFinderItem> getListOfPlaces(JSONObject obj){
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

//        for(JsonPlaceFinderItem item : items)
//            Log.d("PlannerFragment", item.toString());
        return items;

    }



}
