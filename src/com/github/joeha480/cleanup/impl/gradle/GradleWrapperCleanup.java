package com.github.joeha480.cleanup.impl.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.ItemDescriptor;
import com.github.joeha480.cleanup.api.ResultDescriptor;
import com.github.joeha480.cleanup.impl.Version;

public class GradleWrapperCleanup implements Cleanup {
	private static final Version eclipseGradle = Version.parseVersion("2.2.1");
	private int generations = 1;
	
	@Override
	public void setGenerationsToKeep(int generations) {
		this.generations = generations;
	}

	@Override
	public ResultDescriptor collect() {
		List<ItemDescriptor> desc = collect(generations);
		return new ResultDescriptor(desc);		
	}
	
	private static List<ItemDescriptor> collect(int generations) {
		File userHome = new File(System.getProperty("user.home"));
		File gradleWrappers = new File(new File(new File(userHome, ".gradle"), "wrapper"), "dists");
		if (gradleWrappers.isDirectory()) {
			List<File> candidates = Arrays.asList(gradleWrappers.listFiles( it -> it.isDirectory() ));
			return candidates.stream()
				.sorted((e1, e2) -> 
					Version.parseVersion(e1.getName()).compareTo(Version.parseVersion(e2.getName())))
				.limit(Math.max(candidates.size()-generations, 0))
				.map(f -> collectItem(f))
				.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	private static ItemDescriptor collectItem(File path) {
		try {
			return new ItemDescriptor(
					path.getName(), 
					Files.walk(path.toPath()).map(p -> p.toFile()).collect(Collectors.toList()),
					Version.parseVersion(path.getName()).equals(eclipseGradle));
		} catch (IOException e) {
			return new ItemDescriptor(path.getName() + " (error)", Collections.emptyList());
		}
	}

}
