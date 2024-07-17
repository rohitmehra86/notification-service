package com.paxaris.email.service;

import com.paxaris.email.dto.EmailLogDto;
import com.paxaris.email.entity.EmailLog;

public interface EmailLogService {

	EmailLogDto insertEmailLog(EmailLog emailLog);

}
