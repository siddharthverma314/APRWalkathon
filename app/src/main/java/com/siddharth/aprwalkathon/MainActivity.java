package com.siddharth.aprwalkathon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String TRACK_JSON = "json";
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize Tracks
        Tracks.init(getApplicationContext());
        ListView tracks = (ListView) findViewById(R.id.listTracks);
        tracks.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> tracksArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, Tracks.getArrayDescriptions());
        tracks.setAdapter(tracksArrayAdapter);
        tracks.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
            }
        });

        //Start button
        Button start = (Button) findViewById(R.id.startTrack);
        start.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(selected == -1){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Please select a Track.");
            alertDialogBuilder.setTitle("Alert");
            alertDialogBuilder.setPositiveButton("OK", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        } else {
            Intent intent = new Intent(this, Track.class);
            intent.putExtra(TRACK_JSON, Tracks.getTrack(selected).JSONString);
            startActivity(intent);
        }
    }

}
