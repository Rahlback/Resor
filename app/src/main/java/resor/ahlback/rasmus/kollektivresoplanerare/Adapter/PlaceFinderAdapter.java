package resor.ahlback.rasmus.kollektivresoplanerare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses.JsonPlaceFinderItem;
import resor.ahlback.rasmus.kollektivresoplanerare.R;

public class PlaceFinderAdapter extends ArrayAdapter<JsonPlaceFinderItem> {
    private ArrayList<JsonPlaceFinderItem> items;
    private ArrayList<JsonPlaceFinderItem> itemsAll;
    private ArrayList<JsonPlaceFinderItem> suggestions;
    private int viewResourceId;


    public PlaceFinderAdapter(Context context, int viewReosurceId, ArrayList<JsonPlaceFinderItem> items){
        super(context,viewReosurceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<JsonPlaceFinderItem>) items.clone();
        this.suggestions = new ArrayList<>();
        this.viewResourceId = viewReosurceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }

        JsonPlaceFinderItem item = items.get(position);
        if (item != null) {
            TextView placeLabel = (TextView) v.findViewById(R.id.customerNameLabel);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getName());
            }
        }
        return v;
    }
}
