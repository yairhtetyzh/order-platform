package com.assignment.foodorder.demo.enums;

public enum CommonStatus {

	ACTIVE(1, "Active"), INACTIVE(2, "Inactive");

	Integer code;
	String description;

	@Override
	public String toString() {
		return description;
	}

	private CommonStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static String getDescription(int code) {
		for (CommonStatus u : CommonStatus.values()) {
			if (u.code == code)
				return u.getDescription();
		}
		return "";
	}
}