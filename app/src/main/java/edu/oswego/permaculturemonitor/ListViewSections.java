package edu.oswego.permaculturemonitor;

import android.app.ListActivity;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

/**
 * Created by ioniz on 11/7/2016.
 */

public class ListViewSections extends ListActivity {
    // These are the elements for the list
    String [] listItems = {"Section 1", "Section 2", "Section 3", "Section 4"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_sections);

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        // Create an adapter we will use to display data.
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getListView().getContext(),
                android.R.layout.simple_list_item_1, listItems);
        getListView().setAdapter(mAdapter);



    }




//fix this when we get everything else set.
    // @Override
    //public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something when a list item is clicked
    //}
}

