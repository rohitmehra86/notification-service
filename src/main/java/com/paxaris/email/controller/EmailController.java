package com.paxaris.email.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paxaris.email.dto.EmailAttachment;
import com.paxaris.email.dto.EmailNotificationRequest;
import com.paxaris.email.dto.EmailNotificationResponse;
import com.paxaris.email.service.EmailNotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class EmailController {

	@Autowired
	@Qualifier("gmailNotificationService")
	EmailNotificationService emailNotificationService;

	@RequestMapping(value = "/send-mail-attachment", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> sendEmailWithAttachment(
			@RequestPart("dto") EmailNotificationRequest emailNotificationRequest,
			@RequestPart("file") MultipartFile[] files) {

		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				emailNotificationRequest.getAttachment().add(getAttachment(file));
			}
		}
		EmailNotificationResponse response = emailNotificationService.sendEmail(emailNotificationRequest);
		return ResponseEntity.ok(response.getResult());
	}

	@RequestMapping(value = "/send-mail", method = RequestMethod.POST)
	public ResponseEntity<String> sendEmail(@RequestBody EmailNotificationRequest emailNotificationRequest) {
		EmailNotificationResponse response = emailNotificationService.sendEmail(emailNotificationRequest);
		return ResponseEntity.ok(response.getResult());
	}

	private EmailAttachment getAttachment(MultipartFile file) {
		try {
			return EmailAttachment.builder().filename(file.getOriginalFilename()).contentType(file.getContentType())
					.file(file.getInputStream()).build();
		} catch (IOException e) {
			log.error(e.toString());
		}
		return EmailAttachment.builder().build();
	}
}
