package com.paxaris.email.dto;

import java.io.Serializable;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationRequestNew implements Serializable {
	private static final long serialVersionUID = 1444814966934662430L;
	private String to;
	private String cc[];
	private String bcc[];
	private String subject;
	private String messageBody;
	private String template;
	private Map<String, Object> templateModel;
	private MultipartFile attachment;
}
