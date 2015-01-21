package com.example.alinaqvi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;


public class SimpleTodo extends Activity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        setupClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_todo, menu);
        return true;
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if(itemText != null && !"".equals(itemText.trim())) {
            itemsAdapter.add(itemText);
            writeItems();
        }
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newText = data.getExtras().getString("ITEM_NEW_TEXT");
            int pos = data.getExtras().getInt("ITEM_POS");
            if(newText != null && !"".equals(newText.trim())) {
                items.set(pos, newText);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (Exception e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupClickListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SimpleTodo.this, EditItemActivity.class);
                i.putExtra("ITEM_POS", position);
                i.putExtra("ITEM_TEXT", ((TextView) view).getText().toString());
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
}
