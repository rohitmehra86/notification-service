package com.paxaris.email.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationResponse implements Serializable {
	private static final long serialVersionUID = 8527396919659211547L;
	private String result;
}
