package com.github.joeha480.cleanup.webui;

import com.github.joeha480.cleanup.api.CleanupCatalogService;
import com.github.joeha480.cleanup.consumer.CleanupCatalog;
import com.googlecode.ajui.ABlockComponent;
import com.googlecode.ajui.AContainer;

public class Services extends AContainer {
	private CleanupCatalogService cc;
	
	public Services() {
		Thread t = new Thread(()->{
			cc = CleanupCatalog.newCatalog();
			for (String id : cc.listServices()) {
				this.add(new ServicePanel(id, cc.getDescription(id)));
			}
		});
		t.start();
	}
	
	public void getResults() {
		if (cc==null) {
			return;
		}
		Thread t = new Thread(()->{
			for (ABlockComponent panel : getChildren()) {
				if (panel instanceof ServicePanel) {
					ServicePanel sp = (ServicePanel)panel;
					sp.setResult(cc.newInstance(sp.getServiceId()).collect());
				} else {
					System.out.println("NO panel");
				}
			}
		});
		t.start();
	}

}
