package com.github.joeha480.cleanup.api;


public interface CleanupService {

	public ServiceDescriptor getServiceDescription();
	
	public Cleanup newService();
}
