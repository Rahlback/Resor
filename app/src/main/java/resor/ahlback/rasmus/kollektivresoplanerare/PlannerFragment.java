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

import resor.ahlback.rasmus.kollektivresoplanerare.Interfaces.JsonDownloadResponse;
import resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses.JsonPlaceFinderItem;

public class PlannerFragment extends Fragment implements JsonDownloadResponse {
    private String apiKey ;
    private ApiHandler apiHandler;

    ArrayList<String> fromStationStringList;
    ArrayList<JsonPlaceFinderItem> data;
    ArrayAdapter<String> fromStationAdapter;

    AutoCompleteTextView fromStationAutocompleteText;
    JsonPlaceFinderItem fromStation = null;
    JsonPlaceFinderItem toStation = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.planner_layout, container,false);
        View view = inflater.inflate(R.layout.planner_layout,
                container, false);

        apiKey = getActivity().getResources().getString(R.string.platsuppslag);
        apiHandler = new ApiHandler(this);
        setPlannerButton(view);

        fromStationStringList = new ArrayList<>();
//        String[] test = {"Test", "Text", "Hyper"};
//        for(String testItem : test){
//            languages.add(testItem);
//        }

        fromStationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, fromStationStringList);
        //Find TextView control
        fromStationAutocompleteText = (AutoCompleteTextView) view.findViewById(R.id.plannerTextInput);
        //Set the number of characters the user must type before the drop down list is shown
        fromStationAutocompleteText.setThreshold(1);
        //Set the adapter
        fromStationAutocompleteText.setAdapter(fromStationAdapter);

//        fromStationAdapter = new ArrayAdapter<JsonPlaceFinderItem>(getActivity(), android.R.layout.select_dialog_item, data);
        fromStationAutocompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                fromStation = findDataPoint(arg0.getItemAtPosition(arg2).toString());
                Log.d("PlannerFragment", "Data chosen> " + fromStation.toString());
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
                
            }
        });

        return view;
    }

    private JsonPlaceFinderItem findDataPoint(String dataString){
        for(JsonPlaceFinderItem item : data){
            if(dataString.equals(item.getName())){
                return item;
            }
        }
        return null;
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

    private void updateAutocompleteText(ArrayList<JsonPlaceFinderItem> newStrings){
        fromStationAdapter.clear();
        for (JsonPlaceFinderItem string: newStrings) {
            fromStationAdapter.add(string.getName());
        }

        fromStationAdapter.getFilter().filter(fromStationAutocompleteText.getText(), fromStationAutocompleteText);
    }

    @Override
    public void processFinished(JSONObject output, int responseCode) {
        data = getListOfPlaces(output);

        switch (responseCode){
            case 1:
                updateAutocompleteText(data);
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

        for(JsonPlaceFinderItem item : items)
            Log.d("PlannerFragment", item.toString());
        return items;

    }



}
