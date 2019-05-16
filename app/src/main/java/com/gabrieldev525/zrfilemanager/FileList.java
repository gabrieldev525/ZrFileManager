package com.gabrieldev525.zrfilemanager;

public class FileList {
	private String name;
	private double size;
	private String path;
	private boolean dir;

	public void setIsDir(boolean dir) {
		this.dir = dir;
	}

	public boolean isDir() {
		return dir;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getSize() {
		return size;
	}
}
