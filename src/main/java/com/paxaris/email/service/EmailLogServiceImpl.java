package com.paxaris.email.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paxaris.email.dto.EmailLogDto;
import com.paxaris.email.entity.EmailLog;
import com.paxaris.email.repository.EmailLogRepository;

@Service("emailLogService")
public class EmailLogServiceImpl implements EmailLogService {

	@Autowired
	private EmailLogRepository emailLogRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public EmailLogDto insertEmailLog(EmailLog emailLog) {
		EmailLog dbEmailLog = emailLogRepository.save(emailLog);
		return mapper.map(dbEmailLog, EmailLogDto.class);
	}
}
