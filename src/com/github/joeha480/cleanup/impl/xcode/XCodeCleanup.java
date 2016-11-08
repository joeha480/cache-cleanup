package com.github.joeha480.cleanup.impl.xcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.ItemDescriptor;
import com.github.joeha480.cleanup.api.ResultDescriptor;
import com.github.joeha480.cleanup.impl.Version;

public class XCodeCleanup implements Cleanup {
	private int generations = 1;

	@Override
	public ResultDescriptor collect() {
		File userHome = new File(System.getProperty("user.home"));
		//~/Library/Developer/Xcode/DerivedData/
		//~/Library/Developer/Xcode/iOS DeviceSupport/
		File xcodeHome = new File(new File(new File(userHome, "Library"), "Developer"), "Xcode");
		if (!xcodeHome.isDirectory()) {
			return new ResultDescriptor(Collections.emptyList());
		}
		File xcodeDevices = new File(xcodeHome, "iOS DeviceSupport");
		List<ItemDescriptor> items = new ArrayList<>();
		if (xcodeDevices.isDirectory()) {
			List<File> candidates = Arrays.asList(xcodeDevices.listFiles( it -> it.isDirectory() ));
			items.addAll(candidates.stream()
				.sorted((e1, e2) -> 
					Version.parseVersion(e1.getName()).compareTo(Version.parseVersion(e2.getName())))
				.limit(Math.max(candidates.size()-generations, 0))
				.map(f -> collectItem("iOS Device Support ", f))
				.collect(Collectors.toList()));
		}
		return new ResultDescriptor(items);
	}

	@Override
	public void setGenerationsToKeep(int generations) {
		this.generations = generations;
	}
	
	private static ItemDescriptor collectItem(String prefix, File path) {
		try {
			return new ItemDescriptor(prefix + path.getName(), Files.walk(path.toPath()).map(p -> p.toFile()).collect(Collectors.toList()));
		} catch (IOException e) {
			return new ItemDescriptor(prefix + path.getName() + " (error)", Collections.emptyList());
		}
	}

}
