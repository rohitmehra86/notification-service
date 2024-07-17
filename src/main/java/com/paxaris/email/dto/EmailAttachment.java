package com.paxaris.email.dto;

import java.io.InputStream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailAttachment {
	private String filename;
	private String contentType;
	private InputStream file;
}
