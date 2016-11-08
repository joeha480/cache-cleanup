package com.github.joeha480.cleanup.impl.xcode;

import com.github.joeha480.cleanup.api.Cleanup;
import com.github.joeha480.cleanup.api.CleanupService;
import com.github.joeha480.cleanup.api.ServiceDescriptor;

public class XCodeCleanupService implements CleanupService {

	@Override
	public ServiceDescriptor getServiceDescription() {
		return new ServiceDescriptor("XCode Data", "Cleans up XCode (based on http://blog.neverthesamecolor.net/how-to-recover-disk-space-from-xcode/).");
	}

	@Override
	public Cleanup newService() {
		return new XCodeCleanup();
	}

}
