package com.paxaris.email.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.paxaris.email.util.EmailStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_log")
public class EmailLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email_to", nullable = false, columnDefinition = "varchar(50)")
	private String emailTo;

	@Column(name = "email_from", nullable = false, columnDefinition = "varchar(50)")
	private String emailFrom;

	@Column(name = "subject", nullable = false, columnDefinition = "varchar(50)")
	private String subject;

	@Column(name = "body", nullable = true, columnDefinition = "varchar(50)")
	private String body;

	@Column(name = "sent_datetime")
	private LocalDateTime sentDatetime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EmailStatus status;
}
