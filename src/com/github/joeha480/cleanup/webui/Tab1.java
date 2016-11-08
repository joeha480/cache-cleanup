package com.github.joeha480.cleanup.webui;

import com.googlecode.ajui.AContainer;
import com.googlecode.ajui.AHeading;
import com.googlecode.ajui.ALabel;
import com.googlecode.ajui.ALink;
import com.googlecode.ajui.AParagraph;

public class Tab1 extends AContainer {
	
	public Tab1() {
		setClass("content");
		{
			AHeading p = new AHeading(1);
			p.add(new ALabel("Cache Cleanup"));
			add(p);
		}
		{
			AParagraph p = new AParagraph();
			p.add(new ALabel("Check the services to use."));
			add(p);
		}
		final Services s = new Services();
		add(s);
		{
			AParagraph p = new AParagraph();
			ALink a = new ALink();
			a.addGetListener(l->{
				s.getResults();
			});
			a.add(new ALabel("Compute"));
			p.add(a);
			add(p);
		}
	}

}
