package com.paxaris.email.util;

import java.util.Objects;

import lombok.Getter;

@Getter
public enum EmailStatus {
	SENT(1, "sent"), 
	FAILED(2, "failed"),
	BLOCKED(3, "blocked");
	
	private final Integer id;
	private final String value;

	private EmailStatus(final Integer id, final String value) {
		this.id = id;
		this.value = value;
	}

	public static EmailStatus valueOf(final Object value) {

		if (Objects.isNull(value)) {
			return null;
		}

		for (EmailStatus type : EmailStatus.values()) {
			if (value instanceof Integer && type.id == ((Integer) value).intValue()) {
				return type;
			} else if (value instanceof String && type.value.equalsIgnoreCase(value.toString())) {
				return type;
			}
		}
		return null;
	}
}
