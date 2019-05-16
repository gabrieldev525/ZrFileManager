package com.gabrieldev525.zrfilemanager.adapter;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.gabrieldev525.zrfilemanager.*;
import java.util.*;

public class FileAdapter extends BaseAdapter {
	private Activity ctx;
	private ArrayList<FileList> list;
	private String type;
	private int screenWidth, screenHeight;

	public FileAdapter(Activity ctx, ArrayList<FileList> list, String type) {
		this.ctx = ctx;
		this.list = list;
		this.type = type;
		
		//screen sizing
		screenWidth = ctx.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = ctx.getWindowManager().getDefaultDisplay().getHeight();
	}

	@Override
	public int getCount() {
		// TODO: Implement this method
		return list.size();
	}

	@Override
	public Object getItem(int p1) {
		// TODO: Implement this method
		return list.get(p1);
	}

	@Override
	public long getItemId(int p1) {
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup p3) {
		FileList fileList = list.get(position);

		View layout;

		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if(type.equals("LIST")) 
				layout = inflater.inflate(R.layout.file_list, null);
			else if(type.equals("GRID"))
				layout = inflater.inflate(R.layout.file_grid, null);
			else
				layout = inflater.inflate(R.layout.file_list, null);
		} else {
			layout = convertView;
		}

		//setting the file info
		TextView fileName = (TextView) layout.findViewById(R.id.fileListTextFileName);
		fileName.setText(fileList.getName());

		TextView fileSize = (TextView) layout.findViewById(R.id.fileListTextFileSize);

		ImageView fileIcon = (ImageView) layout.findViewById(R.id.fileListImageFileIcon);
		fileIcon.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 6, screenHeight / 10));
		if(fileList.isDir() == true) {
			fileIcon.setImageResource(R.drawable.folder);
		}

		return layout;
	}


}
