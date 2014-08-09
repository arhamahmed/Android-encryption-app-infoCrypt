package com.example.infocrypt.infocrypt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
 Author: Arham
 Date: 7/10/2014
 Purpose: creates a customListAdapter that handles the custom listview

Fields:
    listItemLayoutResource: listview in the GUI
    objects: a list of datum inputted by the user

 Methods:
    getCount: returns # of items in the arrayList
    getItem: returns a list item from a given position
    getView: draws the custom listview correctly
    getWorkingView: resuses convertView if possible
    getViewHolder: maintains references to the views
*/
public final class CustomListAdapter extends ArrayAdapter<ListItem> {

    private final int listItemLayoutResource;
    private ArrayList<ListItem> objects;

    public CustomListAdapter(final Context context, final int listItemLayoutResource, ArrayList<ListItem> items) {
        super(context, 0, items);
        this.listItemLayoutResource = listItemLayoutResource;
        this.objects = items;
    }

    // returns the # of items in the arrayList
    public int getCount() {
        return objects.size();
    }

    //returns a listItem given position in the arrayList
    public ListItem getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final ListItem entry = getItem(position);

        // Setting the title view is straightforward
        viewHolder.titleView.setText(entry.getTitle());

        // Setting the subTitle view requires a tiny bit of formatting
        //final String formattedSubTitle = String.format("= %s",
        //        entry.getDescrip());
        viewHolder.subTitleView.setText(entry.getDescrip());

        return view;
    }

    private View getWorkingView(final View convertView) {

        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            workingView = inflater.inflate(listItemLayoutResource, null);
        } else {
            workingView = convertView;
        }
        return workingView;
    }
    private ViewHolder getViewHolder(final View workingView) {

        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;

        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();
            viewHolder.titleView = (TextView) workingView.findViewById(R.id.listTitle);
            viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.listSubTitle);
            workingView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) tag;
        }
        return viewHolder;
    }
    /*
    ViewHolder allows us to avoid re-looking up view references
    Since views are recycled, these references will never change

    Fields:
        - titleView: view from GUI that will contain the title in a list item object
        - subTitleView: view from GUI that will contain the sub-title in a list item object

     */
    private static class ViewHolder {
        public TextView titleView;
        public TextView subTitleView;
    }
}