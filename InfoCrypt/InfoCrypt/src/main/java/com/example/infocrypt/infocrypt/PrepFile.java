package com.example.infocrypt.infocrypt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import android.app.AlertDialog;
import java.util.ArrayList;

/*
Class: PrepFile
Purpose: presents a UI to prepare a file
Methods:
    - onCreate: prepares activity
    - onCreateOptionsMenu: self explanatory
    - onOptionsItemSelected: self explanatory
    - genKey [not implemented]: retrieves a random string from random.org
    - createFile: creates a new file and populating it with data
*/

public class PrepFile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_file);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prep_file, menu);
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
    Method: createFile
    Parameters: view (button)
    Purpose: creates a file using data from fields in UI and list of items
    */
    public void createFile (View v) {

        Intent intent = getIntent();
        ArrayList<ListItem> listItems = (ArrayList<ListItem>)intent.getSerializableExtra("list");
        EditText key = (EditText)findViewById(R.id.userKey);
        EditText fN = (EditText)findViewById(R.id.fileName);

        final Intent newIntent = new Intent(this, MainActivity.class);

        String strKey = key.getText().toString();
        String filename = fN.getText().toString();

        Encryptor encryptor = new Encryptor(strKey);
        String encryptedLine;

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        String message;
        String dialogTitle = "Problem";

        try {

            if (strKey.length() > 0 && filename.length() > 0) {
                filename += ".txt";
                FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

                int count = listItems.size();

                for (int iterator = 0; iterator < count; iterator++) {
                    encryptedLine = "" + encryptor.encryptLine(listItems.get(iterator).toString()) + "\r\n";
                    outputStream.write(encryptedLine.getBytes());
                }

                outputStream.close();

                message = "Your file was successfully created and encrypted. It is now all " +
                        "the more difficult for others to get a hold of your information! :-) " +
                        "Remember to write down the key used to make the file as you will need it to " +
                        "open your file. Also, try not to forget the file name either.";
                dialogTitle = "Success!";
            }
            else if (strKey.length() > 0 && filename.length()==0) {
                message = "You have not specified a valid file name.";
            }
            else if (strKey.length() == 0 && filename.length()>0) {
                message = "You have not specified a valid key.";
            }
            else {
                message = "There was an error while processing your request.";
            }

        } catch (Exception e) {

            dialogTitle = "Failure...";
            message = "Your file was not made... The system encountered the following " +
                    "error: " + e + ". Are you sure you followed the directions correctly?";
        }
        alertDialog.setTitle(dialogTitle);
        alertDialog.setMessage(message);
        if (dialogTitle=="Success!") {
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(newIntent);
                }
            });
            alertDialog.show();
        }
        else {
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }
    }

    /*public void genKey(View v) {

    HttpClient httpClient = new DefaultHttpClient();
    final EditText etKey = (EditText)findViewById(R.id.userKey);

        try {
            HttpPost httpPost = new HttpPost("https://api.random.org/json-rpc/1/invoke");
            HttpResponse response;

            JSONObject json = new JSONObject();
            json.put("length", 8);
            json.put("characters","abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
            json.put("apiKey","e71281a3-f040-4468-a8fd-c9f37cb7eb34");
            json.put("n", 1);
            json.put("replacement", "false");

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("id", 1);
            jsonRequest.put("params", json);
            jsonRequest.put("method", "generateStrings");

            StringEntity se = new StringEntity( jsonRequest.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);

                    //Checking response
            if(response!=null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
                etKey.setText(in.toString());
            }


        } catch(Exception e) {
            //e.printStackTrace();
        }
    }*/

}
