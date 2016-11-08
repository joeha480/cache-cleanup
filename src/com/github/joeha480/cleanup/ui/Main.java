package com.github.joeha480.cleanup.ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.CleanupCatalogService;
import com.github.joeha480.cleanup.api.ItemDescriptor;
import com.github.joeha480.cleanup.api.ResultDescriptor;
import com.github.joeha480.cleanup.api.ServiceDescriptor;
import com.github.joeha480.cleanup.consumer.CleanupCatalog;
import com.github.joeha480.cleanup.impl.Version;

public class Main {

	public static void main(String[] args) {
		//Get a catalog
		CleanupCatalogService cc = CleanupCatalog.newCatalog();
		for (String id : cc.listServices()) {
			//Print description
			printDescription(cc.getDescription(id));

			int generations = readInteger("Generations to keep (1)?", 1);
			//Get an instance
			Cleanup c = cc.newInstance(id);
			//Configure the instance
			c.setGenerationsToKeep(generations);
			//Collect items to delete
			ResultDescriptor r = c.collect();
			if (r.getBytesSaved()>0) {
				boolean redo=true;
				while (redo) {
					redo = false;
					//Print the result
					printResult(r);

					try {
						//Prompt user
						System.out.println("Action? [type 'clean' to clean, type 'pin' or 'unpin' followed by version number to pin/unpin]");
						String input = new LineNumberReader(new InputStreamReader(System.in)).readLine().trim();
						if ("clean".equalsIgnoreCase(input)) {
							//Perform the cleanup
							r.cleanup();
						} else if (input.startsWith("unpin") || input.startsWith("pin")) {
							Version inV = Version.parseVersion(input);
							boolean pinned = input.startsWith("pin");
							for (ItemDescriptor i : r.listPurgedItems()) {
								if (Version.parseVersion(i.getShortName()).equals(inV)) {
									i.setPinned(pinned);
								};
							}
							redo = true;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("No space to save, skipping...");
			}
		}
	}

	private static int readInteger(String prompt, int def) {
		System.out.println(prompt);
		try {
			String input = new LineNumberReader(new InputStreamReader(System.in)).readLine();
			if (!"".equals(input)) {
				return Integer.parseInt(input);
			}
		} catch (Exception e) {
			System.out.println("Failed to parse number, using default.");
		}
		return def;
	}
	
	private static void printDescription(ServiceDescriptor desc) {
		System.out.println("=== " + desc.getName() + " ===");
		System.out.println( desc.getDescription());
		System.out.println();
	}
	
	private static void printResult(ResultDescriptor result) {
		for (ItemDescriptor id : result.listPurgedItems()) {
			System.out.format("%s %s \t%.2f MB%n", 
					id.isPinned()?"Pinned item:":"Item to purge:", 
					id.getShortName(), 
					(id.getBytesSaved()/1000000f));
		}
		System.out.format("Total space to save:\t%.2f MB",  (result.getBytesSaved()/1000000f));
		System.out.println();
	}

}
