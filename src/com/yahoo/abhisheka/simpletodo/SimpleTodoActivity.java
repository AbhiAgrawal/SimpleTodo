package com.yahoo.abhisheka.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SimpleTodoActivity extends Activity {

	ListView lvTodoItems;
	List<String> items;
	ArrayAdapter<String> itemsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_todo);
		lvTodoItems = (ListView) findViewById(R.id.lvTodoItems);
		
		readItems();
		itemsAdapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, items);
		lvTodoItems.setAdapter(itemsAdapter);
		setUpListViewListener();
	}

	private void setUpListViewListener() {
		lvTodoItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	public void addTodoItem(View v) {
		// fire when button is pressed
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();

	}

	private void readItems() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (Exception e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}

	}

	private void saveItems() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
