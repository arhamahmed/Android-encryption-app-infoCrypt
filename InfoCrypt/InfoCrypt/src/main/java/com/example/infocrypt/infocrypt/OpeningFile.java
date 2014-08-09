package com.example.infocrypt.infocrypt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
Class: OpeningFile
Purpose: presents a UI so a user can open, view, decrypt, and delete existing files
Fields:
    - items: the items contained in the file
    - adapter: the adapter that manages displaying the items in the listview
Methods:
    - onCreate: sets layout to specified
    - onCreateOptionsMenu: self explanatory
    - onOptionsItemsSelected: handles action bar item clicks
    - fileExistance: returns if a file with a given name exists
    - opDecryptFile: decrypts given file and loads text into text-view
    - delFile: deletes a given file if it exists and clears text-view
*/

public class OpeningFile extends ActionBarActivity {

    ArrayList<String> items =new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_file);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opening_file, menu);
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
    Method: fileExistance
    Parameters: fname (name of file)
    Purpose: checks if a file exists
    */
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    /*
    Method: opDecyptFile
    Parameters: the view (button)
    Dependencies: fileExistance, key and file name
    Purpose: decrypts a file and displays it to the user
    */
    public void opDecryptFile (View v) {
        EditText etfN = (EditText)findViewById(R.id.opFileName);
        EditText etKey = (EditText)findViewById(R.id.opUserKey);

        TextView tvTitle = (TextView)findViewById(R.id.opTitle);

        ListView lvContent = (ListView)findViewById(R.id.fileContent);
        items.clear();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        String fN = etfN.getText().toString() + ".txt";
        String key = etKey.getText().toString();

        String title = "Error";
        String message;
        String decryptedLine;

        Encryptor encryptor = new Encryptor(key);

        if (key.length() == 0 && fN.length() > 0)
            message = "You forgot to input a key.";

        else if(key.length() > 0 && fN.length() == 0)
            message = "You forgot to input a file name.";

        else if (fileExistance(fN) && key.length() > 0 && fN.length() > 0) {
            try {
                title = "Success";
                message = "Your file was successfully opened.";

                FileInputStream inputStream = openFileInput(fN);
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = r.readLine()) != null) {
                    decryptedLine = "" + encryptor.decryptLine(line);
                    adapter.add(decryptedLine);
                }
                r.close();
                inputStream.close();
                lvContent.setAdapter(adapter);
                tvTitle.setText("File: " + fN);
            } catch (Exception e) {
                message = "The program encountered a problem: " + e;
            }
        }
        else {
            message = "The program encountered an unexpected problem. Please review your input" +
                    " in terms of a valid file name and key.";
            items.clear();
            adapter.notifyDataSetChanged();
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();

    }

    /*
    Method: delFile
    Parameters: view (button)
    Dependencies: fileExistance
    Purpose: deletes a file if it exists
    */
    public void delFile(View v){
        EditText etKey = (EditText)findViewById(R.id.opUserKey);
        EditText etFn = (EditText)findViewById(R.id.opFileName);
        String Fn = etFn.getText().toString() + ".txt";

        ListView lvContent = (ListView)findViewById(R.id.fileContent);

        String title = "Success";
        String message = "The given file was deleted successfully.";

        if (Fn.length() > 0 && fileExistance(Fn)) {
            deleteFile(Fn);
            items.clear();
            adapter.notifyDataSetChanged();
            lvContent.setAdapter(adapter);
            etFn.setText(Fn + ": this file was just deleted...");
            etKey.setText("");
        } else {
            title = "Failure";
            message = "There was an error while processing your request. A possible cause can be a " +
                    "that valid file name was not provided or givenf file does not exist.";
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
