package resor.ahlback.rasmus.kollektivresoplanerare;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import resor.ahlback.rasmus.kollektivresoplanerare.Interfaces.JsonDownloadResponse;

public class PlannerFragment extends Fragment implements JsonDownloadResponse {
    private String apiKey ;
    private ApiHandler apiHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.planner_layout, container,false);
        View view = inflater.inflate(R.layout.planner_layout,
                container, false);

        apiKey = getActivity().getResources().getString(R.string.platsuppslag);
        apiHandler = new ApiHandler(this);
        setPlannerButton(view);

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
        try {
            Log.d("ApiHandler", output.get("ResponseData").toString());
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}
