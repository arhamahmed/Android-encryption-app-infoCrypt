package com.example.infocrypt.infocrypt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;

import java.io.File;

/*
Class: MainActivity
Purpose: prepare the home screen of the app
Methods:
    - onCreate: sets layout
    - onCreateOptionsMenu: self explanatory
    - onOptionsItemSelected: self explanatory
    - startNew: takes the user to an activity where data the user enters data
    - openFile: takes the user to an activity where they can view, open, and delete files
*/

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    Method: startNew
    Parameters: view (button)
    Purpose: takes the user to a new activity to populate a list with datum
    */
    public void startNew(View view) {
        Intent intent = new Intent(this, StartNewCrypt.class);
        startActivity(intent);
    }

    /*
    Method: openFile
    Parameters: view (button)
    Purpose: takes the user to an activity where they can view/open/delete files
    */
    public void openFile(View v) {
        Intent intent = new Intent(this, OpeningFile.class);
        startActivity(intent);
    }
}
