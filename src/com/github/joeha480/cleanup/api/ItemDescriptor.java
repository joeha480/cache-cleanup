package com.github.joeha480.cleanup.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemDescriptor {
	private final String shortName;
	private final List<File> files;
	private boolean pinned;
	private long bytesSaved = -1;
	
	public ItemDescriptor(String shortName, List<File> files) {
		this(shortName, files, false);
	}

	public ItemDescriptor(String shortName, List<File> files, boolean pinned) {
		super();
		this.shortName = shortName;
		this.files = Collections.unmodifiableList(new ArrayList<>(files));
		this.pinned = pinned;
	}

	public String getShortName() {
		return shortName;
	}
	
	public List<File> getFileList() {
		return files;
	}

	public long getBytesSaved() {
		if (bytesSaved<0) {
			bytesSaved = files.stream().mapToLong(f -> f.length()).sum();
		}
		return bytesSaved;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

}
