package com.github.joeha480.cleanup.api;

public class ServiceDescriptor {
	private final String name;
	private final String description;
	
	public ServiceDescriptor(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}