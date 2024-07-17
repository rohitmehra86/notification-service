package com.paxaris.email.dto;

import java.time.LocalDateTime;

import com.paxaris.email.util.EmailStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailLogDto {
	private Long id;
	private String emailTo;
	private String emailFrom;
	private String subject;
	private String body;
	private LocalDateTime sentDatetime;
	private EmailStatus status;
}
