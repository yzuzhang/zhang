package com.feicent.zhang.util.mail.exception;

import javax.mail.MessagingException;

@SuppressWarnings("serial")
public class TemplateMessagException extends MessagingException {
    
	public TemplateMessagException() {
    }

    public TemplateMessagException(String s) {
        super(s);
    }

    public TemplateMessagException(String s, Exception e) {
        super(s, e);
    }
    
}
