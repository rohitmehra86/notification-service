package com.paxaris.email.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationRequest implements Serializable {
	private static final long serialVersionUID = 1444814966934662430L;
	private String to;
	private String cc[];
	private String bcc[];
	private String subject;
	private String from;
	private String messageBody;
	private String template;
	private Map<String, Object> templateModel;
	@Builder.Default
	private List<EmailAttachment> attachment = new ArrayList<>();
}
