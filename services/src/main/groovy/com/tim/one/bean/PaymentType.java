package com.tim.one.bean;

public enum PaymentType {
	ADVANCE(0, "Advance"), SETTLEMENT(1, "Settlement"), BOUTH(2, "Bouth");

	private final String name;
	private final int code;

	private PaymentType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static PaymentType getTypeByCode(int code) {
		for (PaymentType item : PaymentType.values()) {
			if (item.code == code) {
				return item;
			}
		}
		return null;
	}
}
