package com.github.joeha480.cleanup.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Version implements Comparable<Version> {
	private static Pattern versionMatch = Pattern.compile("[\\d]+([\\.][\\d]+)+");
	private static Pattern versionSplit = Pattern.compile("[^\\d]+");
	private final int[] parts;
	
	private Version(int[] parts) {
		this.parts = parts;
	}
	
	public static Version parseVersion(String str) {
		Matcher m = versionMatch.matcher(str);
		if (m.find()) {
			String v = str.substring(m.start(), m.end());
			List<String> partsStr = Arrays.asList(versionSplit.split(v)).stream().filter(s -> !"".equals(s)).collect(Collectors.toList());
			int[] parts = new int[partsStr.size()];
			for (int i=0; i<parts.length; i++) {
				parts[i] = Integer.parseInt(partsStr.get(i));
			}
			return new Version(parts);
		} else {
			throw new IllegalArgumentException("No version found.");
		}
	}
	
	int[] getParts() {
		return parts;
	}

	@Override
	public int compareTo(Version o) {
		int i=0;
		for (int part : parts) {
			if (o.parts.length<=i) {
				return 1;
			}
			int ret = Integer.compare(part, o.parts[i]);
			if (ret!=0) {
				return ret;
			}
			i++;
		}
		if (o.parts.length>i) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(parts);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		if (!Arrays.equals(parts, other.parts)) {
			return false;
		}
		return true;
	}

}
