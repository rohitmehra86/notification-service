package com.paxaris.email.service;

import com.paxaris.email.dto.EmailNotificationRequest;
import com.paxaris.email.dto.EmailNotificationResponse;

public interface EmailNotificationService {
	EmailNotificationResponse sendEmail(EmailNotificationRequest emailNotificationRequest);
}
