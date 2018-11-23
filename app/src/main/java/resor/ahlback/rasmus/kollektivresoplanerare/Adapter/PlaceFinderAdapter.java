package resor.ahlback.rasmus.kollektivresoplanerare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import resor.ahlback.rasmus.kollektivresoplanerare.JsonClasses.JsonPlaceFinderItem;
import resor.ahlback.rasmus.kollektivresoplanerare.R;


//TODO> Make filter for adapter. Implement into PlannerFragment
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
            TextView placeLabel = (TextView) v.findViewById(R.id.singleChoiceText);
            if (placeLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                placeLabel.setText(item.getName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((JsonPlaceFinderItem)(resultValue)).getName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (JsonPlaceFinderItem customer : itemsAll) {
                    if(customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<JsonPlaceFinderItem> filteredList = (ArrayList<JsonPlaceFinderItem>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (JsonPlaceFinderItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
