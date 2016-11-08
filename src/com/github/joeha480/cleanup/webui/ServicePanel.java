package com.github.joeha480.cleanup.webui;

import com.github.joeha480.cleanup.api.ItemDescriptor;
import com.github.joeha480.cleanup.api.ResultDescriptor;
import com.github.joeha480.cleanup.api.ServiceDescriptor;
import com.googlecode.ajui.AContainer;
import com.googlecode.ajui.AHeading;
import com.googlecode.ajui.ALabel;
import com.googlecode.ajui.AParagraph;
import com.googlecode.ajui.ASpan;
import com.googlecode.ajui.comp.ASelectComponent;

public class ServicePanel extends AContainer {
	private final String serviceId;
	private final ASelectComponent sc;
	private final AContainer resultPanel;
	
	public ServicePanel(String serviceId, ServiceDescriptor desc) {
		setClass("service");
		this.serviceId = serviceId;
		{
			AHeading h2 = new AHeading(2);
			h2.add(new ALabel(desc.getName()));
			add(h2);
		}
		{
			AParagraph p = new AParagraph();
			p.add(new ALabel(desc.getDescription()));
			add(p);
		}
		{
			sc = new ASelectComponent();
			sc.addOption("Enabled", "true");
			sc.addOption("Disabled", "false");
			sc.setSelected("true");
			add(sc);
		}
		{
			resultPanel = new AContainer();
			add(resultPanel);
		}
	}
	
	public boolean isEnabled() {
		return "true".equals(sc.getSelected());
	}
	
	public void setResult(ResultDescriptor r) {
		resultPanel.removeAll();
		for (ItemDescriptor i : r.listPurgedItems()) {
			AParagraph p = new AParagraph();
			p.add(new ALabel(i.getShortName()));
			ASpan size = new ASpan();
			size.add(new ALabel(""+(i.getBytesSaved()/1000000f)));
			size.setClass("size");
			p.add(size);
			resultPanel.add(p);
		}
	}
	
	public String getServiceId() {
		return serviceId;
	}

}
