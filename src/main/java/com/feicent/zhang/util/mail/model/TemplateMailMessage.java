package com.feicent.zhang.util.mail.model;

import java.util.Map;

public class TemplateMailMessage extends MailMessage {
    private String templatePath;
    private Map<String,Object> model;

    public TemplateMailMessage() {
    }


    public TemplateMailMessage(String templatePath, Map<String, Object> model) {
        this.templatePath = templatePath;
        this.model = model;
    }

    public TemplateMailMessage(String to, String subject, String templatePath, Map<String, Object> model) {
        super(to, subject);
        this.templatePath = templatePath;
        this.model = model;
    }

    public TemplateMailMessage(String[] to, String subject, String templatePath, Map<String, Object> model) {
        super(to, subject);
        this.templatePath = templatePath;
        this.model = model;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
