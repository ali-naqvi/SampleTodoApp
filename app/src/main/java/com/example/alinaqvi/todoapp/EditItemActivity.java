package com.example.alinaqvi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Button button = (Button) findViewById(R.id.bntSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = getIntent();
                EditText editText = (EditText) findViewById(R.id.mtEditItem);
                data.putExtra("ITEM_NEW_TEXT", editText.getText().toString());
                setResult(RESULT_OK, data);
                EditItemActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        int pos = extras.getInt("ITEM_POS");
        String text = extras.getString("ITEM_TEXT");
        ((EditText) findViewById(R.id.mtEditItem)).setText(text);
        setTitle(getTitle() + " - " + (pos + 1));
    }
}
