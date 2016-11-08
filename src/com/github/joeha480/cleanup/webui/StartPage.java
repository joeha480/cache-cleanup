package com.github.joeha480.cleanup.webui;

import java.io.ByteArrayOutputStream;

import com.googlecode.ajui.AContainer;
import com.googlecode.ajui.APage;

public class StartPage extends APage {

	public StartPage(ByteArrayOutputStream log) {
		addStylePath("style.css");
		setTitle("Cache Cleanup");
		AContainer view = new AContainer();
		view.add(new Tab1());
		setView(view);
	}

	@Override
	public void close() {
		super.close();
	}
	
	
}
