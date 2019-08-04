package com.codepath.simpletodo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // a numeric code to identify the edit activity
    public final static int EDIT_REQUEST_CODE = 28;
    //key used for passing data between activities
    public final static String ITEM_TEXT = "itemText";
    public final static String ITEM_POSITION = "itemPosition";

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);


        //mock data
        //items.add("First Item")
        //items.add("Second Item")


        setupListViewListener();
    }

    private void readItems() {

    }

    public void onAddItem(View view) {
         EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item add to list", Toast.LENGTH_SHORT).show();

    }

    private void writeItems() {
}



     private void setupListViewListener() {
         Log.i("MainActivity", "Setting up lister view ");
         lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

             @Override


             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 Log.i("MainActivity", "Item removed from list: " + position);
                 items.remove(position);
                 itemsAdapter.notifyDataSetChanged();
                 writeItems();
                 return true;
             }
         });

         // set up item listener for edit (regular click)
         lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 // create the new activity
                 Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                 // pass the data being edited
                 i.putExtra(ITEM_TEXT, items.get(position));
                 i.putExtra(ITEM_POSITION, position);
                 // display the activity
                 startActivityForResult(i, EDIT_REQUEST_CODE);
             }
         });


     }
}