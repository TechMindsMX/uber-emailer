package com.tim.one.model;

public enum ProjectType {
	PROJECT(0, "Project"), PRODUCT(1, "Product"), REPERTORY(2, "Repertory");

	private final String name;
	private final int code;

	private ProjectType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static ProjectType getTypeByCode(int code) {
		for (ProjectType item : ProjectType.values()) {
			if (item.code == code) {
				return item;
			}
		}
		return null;
	}
}
