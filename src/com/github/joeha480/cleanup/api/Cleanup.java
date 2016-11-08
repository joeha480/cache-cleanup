package com.github.joeha480.cleanup.api;

public interface Cleanup {

	public ResultDescriptor collect();
	
	public void setGenerationsToKeep(int generations);
}
