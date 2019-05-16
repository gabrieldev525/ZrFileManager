package com.gabrieldev525.zrfilemanager;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.gabrieldev525.zrfilemanager.adapter.*;
import java.io.*;
import java.util.*;

public abstract class FileManager {
	private String sdCard;
	private File file;
	private File files[];

	private ArrayList<FileList> list = new ArrayList<FileList>();

	private FileAdapter adapter;

	private Activity ctx;

	private ListView listView;
	private GridView gridView;
	private LinearLayout layoutMain;

	private String type;

	public FileManager(Activity ctx, String type) {
		this.ctx = ctx;
		this.type = type;

		//return the sdcard directory
		sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
		//acess file
		file = new File(sdCard);
		files = file.listFiles();

		adapter = new FileAdapter(ctx, list, type);
	}


	/*
	 * update the file list
	 * it get all files on current directory and set on array list
	 * later he use notifyChange on adapter to send the update to listviewq
	 *
	 * @params (FileList) adapter - the BaseAdapter of listview
	 */
	public void updateFileListView(FileAdapter adapter) {
		updateFileListCurrentDir();
		//notify the list view to update
		adapter.notifyDataSetChanged();
	}

	/*
	 * set on the ArrayList list all files of the current dir
	 * the File[] files is the array that contain all files of dir
	 *
	 */
	public void updateFileListCurrentDir() {
		list.clear();
		files = file.listFiles();

		for(File f: files) {
			FileList fileItem = new FileList();
			fileItem.setName(f.getName());
			fileItem.setPath(f.getAbsolutePath());
			fileItem.setIsDir(f.isDirectory() && !f.isFile());
			list.add(fileItem);
		}
	}

	/*
	 * initialize the ListView setting the adapter and getting the id
	 *
	 * @params (int) listViewId - the id of ListView on layout xml file
	 */
	public void init(int listViewId, int gridViewId) {
		listView = (ListView) ctx.findViewById(listViewId);
		gridView = (GridView) ctx.findViewById(gridViewId);
		layoutMain = (LinearLayout) ctx.findViewById(R.id.mainLinearLayout);
		
		if(type.equals("LIST")) {
			layoutMain.removeView(gridView);
			
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View view, int position, long p4) {
						onListClickListener(adapter, view, position, p4);
					}
				});
		} else if(type.equals("GRID")) {
			layoutMain.removeView(listView);
			
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View view, int position, long p4) {
						onListClickListener(adapter, view, position, p4);
					}
				});
		}
	}

	/*
	 * open a folder of this current dir
	 *
	 * @params (String) dirName - the name of folder
	 */
	public boolean openDirectory(String dirName) {
		File temp = new File(file.getAbsolutePath() + "/" + dirName);
		if(temp.isDirectory()) {
			file = new File(file.getAbsolutePath() + "/" + dirName);
			return true;
		}

		return false;
	}

	/*
	 * this return to previous dir
	 * it get the currentAbsolutePath and remove the last dir
	 */
	public void openPreviousDir() {
		String[] temp = file.getAbsolutePath().split("/");
		String newPath = "";

		for(int i = 0; i < temp.length - 1; i++) {
			newPath += temp[i] + "/";
		}

		file = new File(newPath);
	}

	/*
	 * return the current path using File file getter
	 *
	 */
	public String getCurrentPath() {
		return file.getAbsolutePath();
	}

	//called on instance of this class=
	public abstract void onListClickListener(AdapterView<?> adapter, View view, int position, long p4);


	//setter and getter
	public void setList(ArrayList<FileList> list) {
		this.list = list;
	}

	public ArrayList<FileList> getList() {
		return list;
	}

	public void setAdapter(FileAdapter adapter) {
		this.adapter = adapter;
	}

	public FileAdapter getAdapter() {
		return adapter;
	}

	public String getSdCard() {
		return sdCard;
	}

}
