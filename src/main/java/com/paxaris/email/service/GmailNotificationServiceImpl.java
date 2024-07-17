package com.paxaris.email.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.paxaris.email.dto.EmailAttachment;
import com.paxaris.email.dto.EmailNotificationRequest;
import com.paxaris.email.dto.EmailNotificationResponse;
import com.paxaris.email.entity.EmailLog;
import com.paxaris.email.util.EmailStatus;

import lombok.extern.slf4j.Slf4j;

@Service("gmailNotificationService")
@Slf4j
public class GmailNotificationServiceImpl implements EmailNotificationService {

	@Value("${env}")
	private String env;

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.from}")
	private String mailFrom;

	@Value("${email.on}")
	private Boolean mailOn;

	@Autowired
	private EmailLogService emailLogService;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Override
	public EmailNotificationResponse sendEmail(EmailNotificationRequest emailNotificationRequest) {

		EmailStatus emailStatus = EmailStatus.SENT;
		if (!mailOn) {
			emailStatus = EmailStatus.BLOCKED;
		} else {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = null;

			try {
				helper = new MimeMessageHelper(message, true);
				helper.setFrom(mailFrom);
				helper.setTo(emailNotificationRequest.getTo());
				helper.setSubject(customizeEmailSubject(emailNotificationRequest.getSubject()));

				addCc(helper, emailNotificationRequest);
				addBcc(helper, emailNotificationRequest);
				addAttachments(helper, emailNotificationRequest);
				processEmailTemplate(helper, emailNotificationRequest.getTemplate(),
						emailNotificationRequest.getTemplateModel(), emailNotificationRequest.getMessageBody());

				emailSender.send(message);

			} catch (MessagingException e) {
				log.error(e.toString());
				emailStatus = EmailStatus.FAILED;
			}
		}

		insertEmailLog(emailNotificationRequest, emailStatus);

		log.info(emailStatus.getValue());
		return EmailNotificationResponse.builder().result(emailStatus.getValue()).build();
	}

	private String customizeEmailSubject(String emailSubject) {
		if ("test".equalsIgnoreCase(env)) {
			return emailSubject + " - TEST MAIL";
		}
		return emailSubject;
	}

	private void insertEmailLog(EmailNotificationRequest emailNotificationRequest, EmailStatus emailStatus) {

		EmailLog emailLog = EmailLog.builder().emailTo(emailNotificationRequest.getTo())
				.emailFrom(emailNotificationRequest.getFrom()).subject(emailNotificationRequest.getSubject())
				.body(emailNotificationRequest.getMessageBody()).status(emailStatus).sentDatetime(LocalDateTime.now())
				.build();

		emailLogService.insertEmailLog(emailLog);
	}

	private void addCc(MimeMessageHelper helper, EmailNotificationRequest emailNotificationRequest)
			throws MessagingException {
		if (emailNotificationRequest.getCc() != null && emailNotificationRequest.getCc().length > 0) {
			for (String cc : emailNotificationRequest.getCc()) {
				helper.addCc(cc);
			}
		}
	}

	private void addBcc(MimeMessageHelper helper, EmailNotificationRequest emailNotificationRequest)
			throws MessagingException {
		if (emailNotificationRequest.getBcc() != null && emailNotificationRequest.getCc().length > 0) {
			for (String bcc : emailNotificationRequest.getBcc()) {
				helper.addCc(bcc);
			}
		}
	}

	private void processEmailTemplate(MimeMessageHelper mimeMessageHelper, String templateName,
			Map<String, Object> templateModel, String messageBody) throws MessagingException {
		if (templateName != null && !templateName.isEmpty() && templateModel != null && !templateModel.isEmpty()) {
			Context thymeleafContext = new Context();
			thymeleafContext.setVariables(templateModel);
			mimeMessageHelper.setText(thymeleafTemplateEngine.process(templateName, thymeleafContext), true);
		} else if (messageBody != null) {
			mimeMessageHelper.setText(messageBody);
		}
	}

	private void addAttachments(MimeMessageHelper helper, EmailNotificationRequest emailNotificationRequest)
			throws MessagingException {

		if (emailNotificationRequest.getAttachment() != null) {
			for (EmailAttachment attachment : emailNotificationRequest.getAttachment()) {
				ByteArrayDataSource attach = null;
				try {
					attach = new ByteArrayDataSource(attachment.getFile(), attachment.getContentType());
					helper.addAttachment(attachment.getFilename(), attach);
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
		}
	}
}
