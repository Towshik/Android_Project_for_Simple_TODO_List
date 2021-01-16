package com.example.simpletodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptar;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems=(ListView) findViewById(R.id.lvlItems);
        items=new ArrayList<String>();
        readItems();
        itemsAdaptar =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdaptar);
        //items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
    }

    public void addToDoItem(View v){

      EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
      itemsAdaptar.add(etNewItem.getText().toString());
      etNewItem.setText("");
      saveItems();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdaptar.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });

    }

    public void readItems(){
     File filesDir=getFilesDir();
     File todoFile=new File(filesDir,"todo.txt");
     try {
         items=new ArrayList<String>(FileUtils.readLines(todoFile,(String) null));
     }catch(IOException e){
         items=new ArrayList<String>();
         e.printStackTrace();
     }

    }

    public void saveItems(){
        File filesDir=getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}