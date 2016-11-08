package com.github.joeha480.cleanup.api;

import java.util.List;

public class ResultDescriptor {
	private final List<ItemDescriptor> purgedItems;

	public ResultDescriptor(List<ItemDescriptor> purgedItems) {
		super();
		this.purgedItems = purgedItems;
	}

	public long getBytesSaved() {
		return purgedItems.stream()
				.filter(i -> !i.isPinned())
				.mapToLong(i -> i.getBytesSaved()).sum();
	}

	public List<ItemDescriptor> listPurgedItems() {
		return purgedItems;
	}
	
	public void cleanup() {
		purgedItems.stream()
			.filter(i -> !i.isPinned())
			.map(i -> i.getFileList()).forEach(fl -> fl.stream().forEach(f -> {
				if (!f.delete()) {
					f.deleteOnExit();
				}
			}));
	}
	
}
