package com.github.joeha480.cleanup.webui;

import java.io.ByteArrayOutputStream;

import com.googlecode.ajui.BrowserUI;

public class Main {

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		ByteArrayOutputStream st = new ByteArrayOutputStream();
		BrowserUI ui = new BrowserUI.Builder("com/github/joeha480/cleanup/webui/resource-files")
			//.logStream(new PrintStream(st))
			.build();
		String key = ui.registerContents(new StartPage(st));
		ui.display(key+".html");
			//.display("index.html");
		System.out.println("Started in: " + (System.currentTimeMillis() -t1) + " ms");
	}

}
