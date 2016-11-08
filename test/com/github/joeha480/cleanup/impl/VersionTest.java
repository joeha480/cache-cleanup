package com.github.joeha480.cleanup.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionTest {
	
	@Test
	public void testVersionDifferentLength() {
		Version v1 = Version.parseVersion("2.1");
		Version v2 = Version.parseVersion("2.1.1");
		assertEquals(-1, v1.compareTo(v2));
		assertEquals(1, v2.compareTo(v1));
	}
	
	@Test
	public void testVersionEqual() {
		Version v1 = Version.parseVersion("2.1.123.1");
		Version v2 = Version.parseVersion("2.1.123.1");
		assertEquals(0, v1.compareTo(v2));
		assertEquals(0, v2.compareTo(v1));
	}
	
	@Test
	public void testVersionParts_01() {
		Version v1 = Version.parseVersion("app-2.1.123.1-zip");
		assertArrayEquals(new int[]{2, 1, 123, 1}, v1.getParts());
	}
	
	@Test
	public void testVersionParts_02() {
		Version v1 = Version.parseVersion("app-v2-2.1.123.1-zip");
		assertArrayEquals(new int[]{2, 1, 123, 1}, v1.getParts());
	}

	@Test
	public void testVersionOdd() {
		Version v1 = Version.parseVersion("2.12.1");
		Version v2 = Version.parseVersion("2.011.1");
		assertEquals(1, v1.compareTo(v2));
		assertEquals(-1, v2.compareTo(v1));
	}
}
