package com.github.joeha480.cleanup.api;

import java.util.Set;

public interface CleanupCatalogService {

	public Set<String> listServices();
	
	public ServiceDescriptor getDescription(String id);
	
	public Cleanup newInstance(String id);
}
