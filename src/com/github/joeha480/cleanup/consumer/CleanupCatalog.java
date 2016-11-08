package com.github.joeha480.cleanup.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.CleanupCatalogService;
import com.github.joeha480.cleanup.api.CleanupService;
import com.github.joeha480.cleanup.api.ServiceDescriptor;

public class CleanupCatalog implements CleanupCatalogService {
	private Map<String, CleanupService> factories;
	
	public CleanupCatalog() {
		factories = new HashMap<>();
		ServiceLoader<CleanupService> loader = ServiceLoader.load(CleanupService.class);
		for (CleanupService c : loader) {
			factories.put(c.getClass().getCanonicalName(), c);
		}
	}
	
	public static CleanupCatalogService newCatalog() {
		return new CleanupCatalog();
	}

	@Override
	public Set<String> listServices() {
		return factories.keySet();
	}
	
	@Override
	public ServiceDescriptor getDescription(String id) {
		return factories.get(id).getServiceDescription();
	}

	@Override
	public Cleanup newInstance(String id) {
		return factories.get(id).newService();
	}

}
