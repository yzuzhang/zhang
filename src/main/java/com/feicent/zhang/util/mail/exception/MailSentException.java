package com.feicent.zhang.util.mail.exception;

import org.springframework.mail.MailException;

@SuppressWarnings("serial")
public class MailSentException extends MailException {
    
	public MailSentException(String msg) {
        super(msg);
    }

    public MailSentException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
