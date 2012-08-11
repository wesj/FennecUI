package com.example.androidtest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    	Spinner spinner = (Spinner)findViewById(R.id.size_spinner);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_dropdown_item);
		adapter.add("Phone");
		adapter.add("10\" Tablet");
		adapter.add("7\" Tablet");
		spinner.setEnabled(false);
		spinner.setAdapter(adapter);

    	CheckBox cb = (CheckBox)findViewById(R.id.menu_checkbox);
    	cb.setEnabled(false);
    }
    
}
