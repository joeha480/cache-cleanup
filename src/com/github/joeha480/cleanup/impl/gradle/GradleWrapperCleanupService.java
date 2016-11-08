package com.github.joeha480.cleanup.impl.gradle;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.CleanupService;
import com.github.joeha480.cleanup.api.ServiceDescriptor;

public class GradleWrapperCleanupService implements CleanupService {

	@Override
	public ServiceDescriptor getServiceDescription() {
		return new ServiceDescriptor("Gradle Wrapper", "Cleans up gradle wrapper versions (version 2.2.1 is pinned by default because it is used"
				+ " by Eclipse).");
	}

	@Override
	public Cleanup newService() {
		return new GradleWrapperCleanup();
	}

}
