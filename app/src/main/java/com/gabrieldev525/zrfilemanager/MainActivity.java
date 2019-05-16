package com.gabrieldev525.zrfilemanager;

import android.app.*;
import android.os.*;
import java.io.*;
import android.widget.*;
import java.util.*;
import com.gabrieldev525.zrfilemanager.adapter.*;
import android.widget.AdapterView.*;
import android.view.*;

public class MainActivity extends Activity {

	private Activity ctx = MainActivity.this;

	FileManager fileManager;

	private String type = "GRID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		fileManager = new FileManager(MainActivity.this, type) {
			@Override
			public void onListClickListener(AdapterView<?> adapter, View view, int position, long p4) {
				FileList fileItem = fileManager.getList().get(position);

				//open only if is a folder
				if(fileManager.openDirectory(fileItem.getName())) {
					fileManager.updateFileListView(fileManager.getAdapter());
				}
			}
		};

		//setting the type of list
		fileManager.init(R.id.mainListView, R.id.mainGridView);
		fileManager.updateFileListCurrentDir();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
			case event.KEYCODE_BACK:
				if(fileManager.getCurrentPath().equals(fileManager.getSdCard())) {
					finish();
				} else {
					fileManager.openPreviousDir();
					fileManager.updateFileListView(fileManager.getAdapter());
				}
				break;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.main_menu, menu);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_view_mode:
				AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);

				ListView list = new ListView(ctx);
				String values[] = {"grid", "list"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
				list.setAdapter(adapter);
				
				dlg.setTitle(getResources().getString(R.string.view_mode))
				.setView(list)
				.show();
				break;
		}
		return false;
	}


}
