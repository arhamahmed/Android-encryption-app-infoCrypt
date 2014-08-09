package com.example.infocrypt.infocrypt;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

/*
Class: StartNewCrypt
Purpose: presents a UI such that a list of datum can be made to populate a file
Fields:
    - newList: contains list of items
Methods:
    - onCreate: initializes view and binds button to adding items to the list
    - onCreateOptionsMenu: self explanatory
    - onOptionsItemSelected: self explanatory
    - prepFile: sends list of items to a new activity for processing
*/

public class StartNewCrypt extends ActionBarActivity {

    private ArrayList<ListItem> newList = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_crypt);

        // Setup the list view
        final ListView lv = (ListView) findViewById(R.id.list);
        final CustomListAdapter customListAdapter = new CustomListAdapter(this, R.layout.listcontent, newList);
        lv.setAdapter(customListAdapter);

        final Button btAdd = (Button)findViewById(R.id.addMore);
        final EditText etTitle = (EditText)findViewById(R.id.userTitle);
        final EditText etDescrip = (EditText)findViewById(R.id.userDescrip);

        //upon clicking the add button, info is retrieved from the edtittexts & appended to the list
        btAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View newView){
                String newTitle = etTitle.getText().toString();
                String newDescrip = etDescrip.getText().toString();
                if (newTitle.length()>0 && newDescrip.length()>0) {
                    ListItem newItem = new ListItem(newTitle,newDescrip);
                    customListAdapter.add(newItem);
                    etTitle.setText("");
                    etDescrip.setText("");
                }
            }
        });

        //on a long click of any list item, the item will be deleted
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                ListItem x = customListAdapter.getItem(position);
                customListAdapter.remove(x);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_new_crypt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Method: prepFile
    Parameters: view (button)
    Purpose: sends information from this activity to another to be processed [for encryption]
    */
    public void prepFile (View view) {
        Intent intent = new Intent(this, PrepFile.class);
        intent.putExtra("list", newList);
        startActivity(intent);
    }
}